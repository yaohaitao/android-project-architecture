package com.example.android.architecture.scenes.login

import android.annotation.TargetApi
import android.hardware.fingerprint.FingerprintManager
import android.os.Build.VERSION_CODES
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import com.example.android.architecture.common.Constants
import com.example.android.architecture.models.Result
import com.example.android.architecture.models.User
import com.example.android.architecture.scenes.login.LoginContract.Presenter
import com.example.android.architecture.scenes.login.LoginContract.View
import com.example.android.architecture.scenes.post.PostActivity
import com.example.android.architecture.scenes.widget.FingerprintAuthenticationDialogFragment
import com.example.android.architecture.utils.canUseFingerprint
import com.example.android.architecture.services.UserService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.UnrecoverableKeyException
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class LoginPresenter(
  private val service: UserService,
  private val view: View
) : Presenter {

  companion object {
    const val ANDROID_KEY_STORE = "AndroidKeyStore"
    const val KEY_NAME = "FingerprintKeyName"
  }

  private val compositeDisposable: CompositeDisposable = CompositeDisposable()
  // FP.1 KeyStoreとKeyGeneratorとCipherを宣言する
  private lateinit var keyStore: KeyStore
  private lateinit var keyGenerator: KeyGenerator
  private lateinit var cipher: Cipher

  init {
    view.presenter = this
  }

  override fun start() {

    if (canUseFingerprint() && Constants.enableFingerprintLogin) {
      view.isEnableFingerprintLoginButton(true)
      // FP.2 KeyStoreとKeyGeneratorの初期化
      setupKeyStoreAndKeyGenerator()
      // FP.3 Cipherの設定
      setupCipher()
      // FP.4 クラスLoginFragmentのライン51に
      // FP.5 キーの生成
      createKey(KEY_NAME)
    } else {
      view.isEnableFingerprintLoginButton(false)
    }

  }

  override fun userLogin(user: User) {
    val disposable: Disposable = service.userLogin(user)
        .subscribe({ result: Result<User>? ->
          if (result == null || result.status == 0) {
            view.showToast("Error username or password!")
          } else {
            view.navigateTo(PostActivity::class.java)
          }
        }, { error: Throwable? ->
          error!!.printStackTrace()
        })
    compositeDisposable.add(disposable)
  }

  @TargetApi(VERSION_CODES.M)
  override fun generateFingerprintAuthenticationDialogFragment(): FingerprintAuthenticationDialogFragment {
    // FP.4.1 DialogFragmentの初期化
    return FingerprintAuthenticationDialogFragment().also {
      // FP.4.2 Cipherを使って、指紋マネージャーを作る
      it.setCryptoObject(FingerprintManager.CryptoObject(cipher))
      // FP.4.3 Callbackの設置
      it.setCallback(this)
      // FP.4.4 Cipherの初期化
      initCipher(cipher)
    }
  }

  @TargetApi(VERSION_CODES.M)
  private fun initCipher(cipher: Cipher): Boolean {
    try {
      // FP.4.4.1 暗号化方を設定して、StoreからSecretKeyをもらって、Cipherを初期化する
      keyStore.load(null)
      cipher.init(Cipher.ENCRYPT_MODE, keyStore.getKey(KEY_NAME, null) as SecretKey)
      return true
    } catch (e: Exception) {
      when (e) {
        is KeyPermanentlyInvalidatedException -> return false
        is KeyStoreException,
        is CertificateException,
        is UnrecoverableKeyException,
        is IOException,
        is NoSuchAlgorithmException,
        is InvalidKeyException -> throw RuntimeException("Failed to init Cipher", e)
        else -> throw e
      }
    }
  }

  override fun success() {
    view.navigateTo(PostActivity::class.java)
  }

  @TargetApi(VERSION_CODES.M)
  override fun createKey(
    keyName: String
  ) {
    try {
      // FP.5.1 storeをオープンする
      keyStore.load(null)
      // FP.5.2 キーの設定
      val keyProperties = KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
      // FP.5.3 ビルダーの設定
      val builder = KeyGenParameterSpec.Builder(keyName, keyProperties)
          .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
          .setUserAuthenticationRequired(true)
          .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
      // FP.5.4 キーを生成して、自動的にストアに保存される
      keyGenerator.run {
        init(builder.build())
        generateKey()
      }
    } catch (e: Exception) {
      when (e) {
        is NoSuchAlgorithmException,
        is InvalidAlgorithmParameterException,
        is CertificateException,
        is IOException -> throw RuntimeException(e)
        else -> throw e
      }
    }
  }

  @TargetApi(VERSION_CODES.M)
  private fun setupKeyStoreAndKeyGenerator() {
    try {
      // FP.2.1 KeyStoreの初期化
      keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
    } catch (e: KeyStoreException) {
      throw RuntimeException("Failed to get an instance of KeyStore", e)
    }

    try {
      // FP.2.2 KeyGeneratorの初期化
      keyGenerator = KeyGenerator.getInstance(
          KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE
      )
    } catch (e: Exception) {
      when (e) {
        is NoSuchAlgorithmException,
        is NoSuchProviderException ->
          throw RuntimeException("Failed to get an instance of KeyGenerator", e)
        else -> throw e
      }
    }
  }

  @TargetApi(VERSION_CODES.M)
  private fun setupCipher() {
    try {
      // FP.3.1 アルゴリズムによって、それをCipherに設定させる
      val cipherString =
        "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}"
      cipher = Cipher.getInstance(cipherString)
    } catch (e: Exception) {
      when (e) {
        is NoSuchAlgorithmException,
        is NoSuchPaddingException ->
          throw RuntimeException("Failed to get an instance of Cipher", e)
        else -> throw e
      }
    }
  }

}