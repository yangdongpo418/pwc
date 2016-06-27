package com.pwc.sdc.recruit.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pwc.sdc.recruit.PwcApplication;
import com.pwc.sdc.recruit.R;
import com.pwc.sdc.recruit.base.interf.ActivityPresenter;
import com.pwc.sdc.recruit.widget.LoadStateFrameLayout;
import com.pwc.sdc.recruit.widget.PtrFixFrameLayout;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * 碎片基类
 *
 * @author
 * @created
 */
public abstract class BaseFragment<T extends ActivityPresenter> extends Fragment {

    protected LayoutInflater mInflater;
    public BaseActivity mActivity;
    private android.view.View mContentView;
    private LoadStateFrameLayout mLoadStateFrameLayout;
    private PtrFixFrameLayout mPtrFrameLayout;
    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
        mPresenter = (T) mActivity.mPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mInflater = inflater;

        if (setPullToRefreshEnable()) {
            mPtrFrameLayout = (PtrFixFrameLayout) inflater.inflate(R.layout.fragment_base, container, false);
            FrameLayout content = (FrameLayout) mPtrFrameLayout.getContentView();
            inflater.inflate(getLayoutId(), content, true);
            mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    mPresenter.onRefresh(frame);
                }
            });
            mContentView = mPtrFrameLayout;
        } else {
            View contentView = inflater.inflate(getLayoutId(), container, false);
            mContentView = contentView;
        }

        if (setLoadStateEnable()) {
            mLoadStateFrameLayout = mActivity.addLoadingStateView(mContentView);
            mContentView = mLoadStateFrameLayout;
        }

        ButterKnife.bind(this, mContentView);
        initView();
        return mContentView;
    }

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            onRestoreInitData(savedInstanceState);
        }

        Bundle bundle = mActivity.getTag();
        if (bundle != null) {
            String targetTag = bundle.getString(mActivity.FRAGMENT_MESSAGE_HEADER);
            String ownTag = getTag();
            if (TextUtils.equals(targetTag, ownTag)) {
                handleBundle(bundle);
            }
        }
        initData();
    }

    protected void onRestoreInitData(Bundle savedInstanceState) {
    }

    protected void handleBundle(Bundle bundle) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public PwcApplication getApplication() {
        return mActivity.getPwcApplication();
    }

    protected android.view.View inflateView(int resId) {
        return this.mInflater.inflate(resId, null);
    }

    public void hideWaitDialog() {
        mActivity.hideWaitDialog();
    }

    public ProgressDialog showWaitDialog() {
        return mActivity.showWaitDialog();
    }

    public ProgressDialog showWaitDialog(int resid) {
        return mActivity.showWaitDialog(resid);
    }

    public ProgressDialog showWaitDialog(String str) {
        return mActivity.showWaitDialog(str);
    }

    /**
     * 默认下拉刷新是打开的
     *
     * @return
     */
    protected boolean setPullToRefreshEnable() {
        return true;
    }

    /**
     * 默认为整个Fragment添加4种状态
     *
     * @return
     */
    protected boolean setLoadStateEnable() {
        return true;
    }

    /**
     * 完成刷新
     */
    protected void completeRefresh() {
        if (mPtrFrameLayout != null && mPtrFrameLayout.isRefreshing()) {
            mPtrFrameLayout.refreshComplete();
        }
    }

    /**
     * 获取默认的下拉刷新的布局参数
     *
     * @return
     */
    protected int getPullToRefreshLayout() {
        return 0;
    }


    protected void updateStateView(int state) {
        mLoadStateFrameLayout.updateState(state);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onFragmentSaveInstanceState(outState);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutId();

}
