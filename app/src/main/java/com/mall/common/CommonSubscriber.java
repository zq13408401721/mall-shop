package com.mall.common;

import android.text.TextUtils;


import com.mall.interfaces.IBaseView;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 处理网络请求结果的错误和完成
 * @param <T>
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

    private IBaseView mView;
    private String errorMsg;
    private boolean isShowErrorState = false;

    protected CommonSubscriber(IBaseView view){
        mView = view;
    }

    protected CommonSubscriber(IBaseView view, String emsg){
        mView = view;
        errorMsg = emsg;
    }

    protected CommonSubscriber(IBaseView view, boolean isError){
        mView = view;
        isShowErrorState = isError;
    }

    protected CommonSubscriber(IBaseView view, String emsg, boolean isError){
        mView = view;
        errorMsg = emsg;
        isShowErrorState = isError;
    }

    @Override
    public void onError(Throwable t) {
        //LoadingUtil.getInstance().hideLoading();
        if(mView == null) return;
        if(errorMsg != null && TextUtils.isEmpty(errorMsg)){
            mView.showTips(errorMsg);
        }
    }

    @Override
    public void onComplete() {
        //LoadingUtil.getInstance().hideLoading();
        //mView.showLoading(View.GONE);
    }
}
