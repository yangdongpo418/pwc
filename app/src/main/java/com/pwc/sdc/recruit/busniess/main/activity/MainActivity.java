package com.pwc.sdc.recruit.busniess.main.activity;

import android.util.Log;

import com.pwc.sdc.recruit.base.BaseFragment;
import com.pwc.sdc.recruit.base.SingleFragmentActivity;
import com.pwc.sdc.recruit.busniess.main.MainConstract;
import com.pwc.sdc.recruit.busniess.main.MainMode;
import com.pwc.sdc.recruit.busniess.main.MainPresenter;
import com.pwc.sdc.recruit.busniess.main.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity<MainConstract.Presenter> implements MainConstract.View{

    private MainFragment mBaseFragment;

    @Override
    protected BaseFragment getFirstFragment() {
        mBaseFragment = (MainFragment) obtainFragment(MainFragment.class);
        return mBaseFragment;
    }


    @Override
    protected MainConstract.Presenter instancePresenter() {
        MainPresenter mainPresenter = new MainPresenter(this,new MainMode());
        Log.d("Log_text", "MainActivity+instancePresenter" + mainPresenter.toString());
        return mainPresenter;
    }
}
