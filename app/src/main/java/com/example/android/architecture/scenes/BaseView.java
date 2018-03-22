package com.example.android.architecture.scenes;

/**
 * Created by YaoHaitao on 2018/3/8.
 */

public interface BaseView<T extends BasePresenter> {

  void setPresenter(T presenter);
}
