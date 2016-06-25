package com.pwc.sdc.recruit.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pwc.sdc.recruit.base.interf.PresenterLayer;
import com.pwc.sdc.recruit.base.interf.ViewLayer;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author:dongpo 创建时间: 6/24/2016
 * 描述: Presenter层基类
 * 修改:
 */
public abstract class BasePresenter<V extends ViewLayer, M extends BaseModel> implements PresenterLayer {

    public V mViewLayer;
    public final M mModelLayer;
    public final BaseActivity mActivity;

    public BasePresenter(BaseActivity activity, V viewLayer, M modelLayer){
        mActivity = activity;
        mViewLayer = viewLayer;
        viewLayer.setPresenter(this);
        mModelLayer = modelLayer;
    }

    public void setViewLayer(V viewLayer){
        if(viewLayer != null){
            mViewLayer = viewLayer;
            viewLayer.setPresenter(this);
        }
    }

    @Override
    public void subscribe() {

    }

    public void onRefresh(PtrFrameLayout frame){}

    @Override
    public void unSubscribe() {
        mModelLayer.cancelAllRequest();
    }

    protected View inflateView(int resId) {
        return mActivity.inflate(resId);
    }

    public void sendBroadCast(String action, String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        mActivity.sendBroadcast(intent);
    }

    protected void onActivitySaveInstanceState(Bundle outState){}
    protected void onFragmentSaveInstanceState(Bundle outState){}
}
