package com.pwc.sdc.recruit.base;

import com.pwc.sdc.recruit.base.interf.ActivityModel;
import com.pwc.sdc.recruit.data.remote.BackPointService;
import com.pwc.sdc.recruit.data.remote.HttpSubscriber;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author:dongpo 创建时间: 2016/6/25
 * 描述:
 * 修改:
 */
public abstract class BaseModel implements ActivityModel {
    /**
     * 当前activity所持有的所有网络请求
     */
    private CompositeSubscription mRequestSubscriptions;
    private BackPointService mBackEndService;

    public BaseModel(){
        mRequestSubscriptions = new CompositeSubscription();
    }

    public <T> Subscription sendHttpRequest(Observable<T> observable, HttpSubscriber<T> callBack) {
        Subscription request = observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(callBack);
        addAsyncRequests(request);
        return request;
    }

    protected BackPointService getBackPointService() {
        return mBackEndService;
    }

    public void addAsyncRequests(Subscription request) {
        mRequestSubscriptions.add(request);
    }

    /**
     * 取消一个网络请求
     *
     * @param request
     */
    public void cancelRequest(Subscription request) {
        request.unsubscribe();
        request = null;
    }


    /**
     * 取消当前Activity相关的网络请求
     */
    public void cancelAllRequest() {
        if (mRequestSubscriptions != null) {
            mRequestSubscriptions.unsubscribe();
            mRequestSubscriptions.clear();
        }
    }
}
