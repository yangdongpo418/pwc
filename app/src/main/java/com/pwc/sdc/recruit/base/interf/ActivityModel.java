package com.pwc.sdc.recruit.base.interf;

import com.pwc.sdc.recruit.data.remote.HttpSubscriber;

import rx.Observable;
import rx.Subscription;

/**
 * @author:dongpo 创建时间: 2016/6/25
 * 描述:
 * 修改:
 */
public interface ActivityModel extends ModelLayer{
     <T> Subscription sendHttpRequest(Observable<T> observable, HttpSubscriber<T> callBack);
     void cancelRequest(Subscription request);
     void cancelAllRequest();
     void addAsyncRequests(Subscription request);
}
