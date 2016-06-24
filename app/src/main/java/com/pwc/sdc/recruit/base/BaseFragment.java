package com.pwc.sdc.recruit.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pwc.sdc.recruit.PwcApplication;
import com.pwc.sdc.recruit.R;
import com.pwc.sdc.recruit.data.remote.BackPointService;
import com.pwc.sdc.recruit.view.widget.LoadStateFrameLayout;
import com.pwc.sdc.recruit.view.widget.PtrFixFrameLayout;
import com.thirdparty.proxy.log.TLog;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Subscription;


/**
 * 碎片基类
 *
 * @author
 * @created
 */
public abstract class BaseFragment extends Fragment {

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;

    protected LayoutInflater mInflater;
    public BaseActivity mActivity;
    private View mContentView;
    private LoadStateFrameLayout mLoadStateFrameLayout;
    private PtrFixFrameLayout mPtrFrameLayout;

    public PwcApplication getApplication() {
        return mActivity.getAppContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mInflater = inflater;

        if (setPullToRefreshEnable()) {
            mPtrFrameLayout = (PtrFixFrameLayout) inflater.inflate(R.layout.fragment_base, container, false);
            inflater.inflate(getLayoutId(), mPtrFrameLayout, true);
            mPtrFrameLayout.updateContent();
            mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    onRefresh(frame);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

    /**
     * 下拉刷新时调用
     * @param frame
     */
    protected void onRefresh(PtrFrameLayout frame){

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

    protected View inflateView(int resId) {
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

    public void addFragment(int container, BaseFragment fragment) {
        mActivity.addFragment(container, fragment);
    }

    public void removeFragment(BaseFragment fragment) {
        mActivity.removeFragment(fragment);
    }

    public void showFragment(BaseFragment fragment) {
        mActivity.showFragment(fragment);
    }

    public void hideFragment(BaseFragment fragment) {
        mActivity.hideFragment(fragment);
    }

    public void replaceFragment(int container, BaseFragment fragment) {
        mActivity.replaceFragment(container, fragment);
    }

    public void startFragment(int container, Class<? extends BaseFragment> clazz, Bundle bundle) {
        mActivity.startFragment(container, this, clazz, bundle);
    }

    public BaseFragment obtainFragment(Class<? extends BaseFragment> clazz) {
        return mActivity.obtainFragment(clazz);
    }

    public void startActivity(Class<?> cls) {
        mActivity.startActivity(cls);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        mActivity.startActivity(cls, bundle);
    }

    public void startActivity(String action) {
        mActivity.startActivity(action);
    }

    public void startActivity(String action, Bundle bundle) {
        mActivity.startActivity(action, bundle);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        mActivity.startActivityForResult(cls, bundle, requestCode);
    }

    public void showToast(String message) {
        showToast(message, 0, Gravity.BOTTOM);
    }

    public void showToast(int msgResid) {
        showToast(getString(msgResid), 0, Gravity.BOTTOM);
    }

    public void showToast(String message, int icon, int gravity) {
        mActivity.showToast(mState, icon, gravity);
    }

    protected void showDebugLog(String msg) {
        TLog.d(msg);
    }

    protected void showErrorLog(String msg) {
        TLog.e(msg);
    }

    protected void showInfoLog(String msg) {
        TLog.i(msg);
    }


    public void addAsyncRequests(Subscription request) {
        mActivity.addAsyncRequests(request);
    }

    public void cancelRequest(Subscription request) {
        mActivity.cancelRequest(request);
    }

    protected BackPointService getBackPointService() {
        return mActivity.getBackPointService();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutId();

}
