package com.framgia.soundcloud.screen;

/**
 * Created by anh on 19/09/2017.
 */

/**
 * BaseView
 *
 * @param <T> this is type paremeter
 */
public interface BasePresenter<T extends BaseViewModel> {

    void onStart();

    void onStop();

    void setViewModel(T viewModel);
}
