package com.pwc.sdc.recruit.base;

import android.content.Intent;
import android.os.Bundle;

import com.pwc.sdc.recruit.PwcApplication;
import com.pwc.sdc.recruit.base.interf.ActivityModel;
import com.pwc.sdc.recruit.base.interf.ActivityPresenter;
import com.pwc.sdc.recruit.base.interf.ViewLayer;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author:dongpo 创建时间: 6/24/2016
 * 描述: Presenter层基类
 * 修改:
 */
public abstract class BasePresenter<V extends ViewLayer, M extends ActivityModel> implements ActivityPresenter<V> {

    public V mViewLayer;
    public final M mModelLayer;

    public BasePresenter(V viewLayer, M modelLayer){
        mViewLayer = viewLayer;
        mModelLayer = modelLayer;
    }

    public void onRefresh(PtrFrameLayout frame){}

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mModelLayer.cancelAllRequest();
    }

    public void sendBroadCast(String action, String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PwcApplication.getInstance().sendBroadcast(intent);
    }

    public void onActivitySaveInstanceState(Bundle outState){}
    public void onFragmentSaveInstanceState(Bundle outState){}
}
