package com.pwc.sdc.recruit.base;

/**
 * @author:dongpo 创建时间: 6/24/2016
 * 描述: Presenter层基类
 * 修改:
 */
public abstract class BasePresenter<K extends BaseViewContacts, V extends BaseModelContacts> implements BasePresenterConstacts{

    public final K mViewLayer;
    public final V mModelLayer;

    BasePresenter(K k, V v){
        mViewLayer = k;
        mModelLayer = v;
    }

}
