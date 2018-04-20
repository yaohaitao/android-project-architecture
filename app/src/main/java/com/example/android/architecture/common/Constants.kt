package com.example.android.architecture.common

import com.orhanobut.hawk.Hawk
import io.reactivex.subjects.BehaviorSubject

class Constants {

  companion object SharedPreferences {

    val enableFingerprintLoginObservable = BehaviorSubject.createDefault(Constants.enableFingerprintLogin)

    var enableFingerprintLogin = false
      get() = Hawk.get("enableFingerprintLogin", false)
      set(value) {
        if (field == value) return
        Hawk.put("enableFingerprintLogin", value)
        enableFingerprintLoginObservable.onNext(value)
        field = value
      }
  }
}