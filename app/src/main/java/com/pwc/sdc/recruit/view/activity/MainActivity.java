package com.pwc.sdc.recruit.view.activity;

import com.pwc.sdc.recruit.base.BaseFragment;
import com.pwc.sdc.recruit.base.SingleFragmentActivity;
import com.pwc.sdc.recruit.view.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return obtainFragment(MainFragment.class);
    }


}
