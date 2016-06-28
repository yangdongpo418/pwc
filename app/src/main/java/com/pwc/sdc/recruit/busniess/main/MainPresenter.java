package com.pwc.sdc.recruit.busniess.main;

import android.util.Log;

import com.pwc.sdc.recruit.R;
import com.pwc.sdc.recruit.base.BaseActivity;
import com.pwc.sdc.recruit.base.BasePresenter;
import com.pwc.sdc.recruit.busniess.main.fragment.MainFragment;
import com.pwc.sdc.recruit.busniess.main.fragment.SecondFragment;

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
        Log.d("Log_text", "MainPresenter+requestClick + presenter做了逻辑处理 + 对应的model层为" + mModelLayer.getClass().getSimpleName() + "对应的View层为"+ mViewLayer.getClass().getSimpleName());
        return mModelLayer.dealWithValue(value);
    }

    @Override
    public void start() {
        SecondFragment second = (SecondFragment) mActivity.obtainFragment(SecondFragment.class);
        mActivity.addFragmentAddBackStack(R.id.single_fl_container,second);
    }

    @Override
    public void second() {
        Log.d("Log_text", "MainPresenter+requestClick + presenter做了逻辑处理 + 对应的model层为" + mModelLayer.getClass().getSimpleName() + "对应的View层为"+ mViewLayer.getClass().getSimpleName());
    }

    @Override
    public void onViewChange() {

    }
}
