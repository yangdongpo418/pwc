package com.pwc.sdc.recruit.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pwc.sdc.recruit.R;

import butterknife.Bind;

/**
 * baseActionBar Activity
 *
 * @author
 * @created
 */
public abstract class ToolBarActivity extends BaseActivity{

    @Bind(R.id.common_tl_toolbar)
    public Toolbar mToolbar;

    private ActionBar mActionBar;

    @Bind(R.id.common_tv_title)
    public TextView mTvToolBarCenter;
    private boolean mIsEnableActionBar = true;

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        initActionBar(mActionBar);
    }


    @Override
    protected View onDealWithContentView(View contentView) {
        contentView = super.onDealWithContentView(contentView);
        LinearLayout newContent = new LinearLayout(this);
        newContent.setOrientation(LinearLayout.VERTICAL);
        mInflater.from(this).inflate(R.layout.tool_bar, newContent, true);
        newContent.addView(contentView);

        LinearLayout.LayoutParams contentViewLayoutParams = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        contentView.setLayoutParams(params);
        newContent.setLayoutParams(contentViewLayoutParams);
        return newContent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void hasBackButton(boolean isEnableActionBar) {
        mIsEnableActionBar = isEnableActionBar;
    }


    protected void initActionBar(ActionBar actionBar) {
        if (actionBar == null)
            return;
        if (mIsEnableActionBar) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        } else {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setDisplayUseLogoEnabled(false);
        }
    }

    public void setActionBarTitle(int resId) {
        if (resId != 0) {
            setActionBarTitle(getString(resId));
        }
    }

    public void setActionBarTitleEnable(boolean enable){
        if(enable == false){
            mTvToolBarCenter.setVisibility(View.GONE);
        }
    }

    public void setActionBarTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }

    public void setActionBarBackButtonIcon(int resId) {
        mToolbar.setNavigationIcon(resId);
    }

    public void setActionBarTitleColor(int color) {
        mToolbar.setTitleTextColor(getResources().getColor(color));
    }

    public void setActionBarCenterTitle(int resId) {
        mTvToolBarCenter.setText(resId);
    }

    public void setActionBarCenterTextColor(int resId) {
        mTvToolBarCenter.setTextColor(getResources().getColor(resId));
    }

    public void setActionBarCenterTextSize(float size) {
        mTvToolBarCenter.setTextSize(size);
    }

    public void addActionBarCustomView(int layoutId) {
        mInflater.inflate(layoutId, mToolbar, true);
    }
}
