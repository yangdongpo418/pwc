package com.pwc.sdc.recruit.data.remote;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @author:dongpo 创建时间: 6/20/2016
 * 描述:
 * 修改:
 */
public abstract class HttpSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException error = (HttpException) e;
            int code = error.code();
            ResponseBody responseBody = error.response().errorBody();
            try {
                byte[] bytes = responseBody.bytes();
                String err = new String(bytes, "UTF-8");
                onFailure(code, err);
            } catch (IOException e1) {
                e1.printStackTrace();
                onFailure(code, e1.getMessage());
            }
        }else{
            onFailure(-1,"Unknown Exception");
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onStart() {
        onRequestStart();
    }

    public void onRequestStart() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(int errorCode, String message);


}
