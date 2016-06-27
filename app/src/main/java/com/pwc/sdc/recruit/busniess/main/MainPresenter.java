package com.pwc.sdc.recruit.busniess.main;

import android.util.Log;

import com.pwc.sdc.recruit.base.BaseActivity;
import com.pwc.sdc.recruit.base.BasePresenter;
import com.pwc.sdc.recruit.busniess.main.fragment.MainFragment;

/**
 * @author:dongpo 创建时间: 2016/6/25
 * 描述:
 * 修改:
 */
public class MainPresenter extends BasePresenter<MainConstract.View, MainMode> implements MainConstract.Presenter {


    public MainPresenter(BaseActivity activity, MainFragment viewLayer, MainMode modelLayer) {
        super(activity, viewLayer, modelLayer);
    }

    @Override
    public String requestClick(String value) {
        Log.d("Log_text", "MainPresenter+requestClick + presenter做了逻辑处理");
        return mModelLayer.dealWithValue(value);
    }
}
