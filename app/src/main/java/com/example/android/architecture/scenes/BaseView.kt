package com.example.android.architecture.scenes

/**
 * Created by YaoHaitao on 2018/3/23.
 */
interface BaseView <T: BasePresenter> {

  var presenter: T
}