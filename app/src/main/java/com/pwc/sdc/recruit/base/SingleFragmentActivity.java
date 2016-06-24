package com.pwc.sdc.recruit.base;

import com.pwc.sdc.recruit.R;

/**
 * @author:dongpo 创建时间: 6/23/2016
 * 描述:
 * 修改:
 */
public abstract class SingleFragmentActivity extends ToolBarActivity {
    @Override
    protected void initView() {
        addFragmentAddBackStack(R.id.single_fl_container, getFirstFragment());
    }

    @Override
    protected void initData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_single_fragment;
    }

    protected abstract BaseFragment getFirstFragment();
}
