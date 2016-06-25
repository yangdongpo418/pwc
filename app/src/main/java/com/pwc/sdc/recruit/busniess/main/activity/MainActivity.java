package com.pwc.sdc.recruit.busniess.main.activity;

import android.util.Log;

import com.pwc.sdc.recruit.base.BaseFragment;
import com.pwc.sdc.recruit.base.BasePresenter;
import com.pwc.sdc.recruit.base.SingleFragmentActivity;
import com.pwc.sdc.recruit.busniess.main.MainMode;
import com.pwc.sdc.recruit.busniess.main.MainPresenter;
import com.pwc.sdc.recruit.busniess.main.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    private MainFragment mBaseFragment;

    @Override
    protected BaseFragment getFirstFragment() {
        mBaseFragment = (MainFragment) obtainFragment(MainFragment.class);
        return mBaseFragment;
    }


    @Override
    protected BasePresenter instancePresenter() {
        MainPresenter mainPresenter = new MainPresenter(this, mBaseFragment,new MainMode());
        Log.d("Log_text", "MainActivity+instancePresenter" + mainPresenter.toString());
        return mainPresenter;
    }
}
