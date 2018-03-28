package com.example.android.architecture.scenes

/**
 * 基礎となるビューインターフェース。
 */
interface BaseView <T: BasePresenter> {
  /**
   * ビューの中に、プレゼンターが必要だ。
   */
  var presenter: T
}