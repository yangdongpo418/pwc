package com.pwc.sdc.recruit.base;

import android.content.Intent;
import android.os.Bundle;

import com.pwc.sdc.recruit.data.remote.HttpSubscriber;

import rx.Observable;

/**
 * @author:dongpo 创建时间: 6/24/2016
 * 描述:
 * 修改:
 */
public interface BasePresenterConstacts {

    void handleIntent(Intent intent);
    void onRestoreInitData(Bundle savedInstanceState);
    <T> void sendHttpRequest(Observable<T> observable, HttpSubscriber<T> callBack);

}
