package com.pwc.sdc.recruit.busniess.main.fragment;

import android.util.Log;
import android.view.View;

import com.pwc.sdc.recruit.R;
import com.pwc.sdc.recruit.base.BaseFragment;
import com.pwc.sdc.recruit.busniess.main.MainConstract;

import butterknife.OnClick;

/**
 * @author:dongpo 创建时间: 6/23/2016
 * 描述:
 * 修改:
 */
public class SecondFragment extends BaseFragment<MainConstract.Presenter>{
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    @OnClick(R.id.second_mvp)
    public void second(View view){
        Log.d("Log_text", "MainPresenter+requestClick + View 发起事件 presenter为"+mPresenter.getClass().getSimpleName());
        mPresenter.second();
    }
}
