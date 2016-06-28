package com.pwc.sdc.recruit.base.interf;

import android.os.Bundle;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author:dongpo 创建时间: 6/27/2016
 * 描述:
 * 修改:
 */
public interface ActivityPresenter<V> extends PresenterLayer{
    void onRefresh(PtrFrameLayout frame);
    void onActivitySaveInstanceState(Bundle outState);
    void onFragmentSaveInstanceState(Bundle outState);
    void setViewLayer(V viewLayer);
    void onViewChange(V viewLayer);
}
