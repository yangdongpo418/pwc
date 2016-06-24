package com.pwc.sdc.recruit.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pwc.sdc.recruit.PwcApplication;
import com.pwc.sdc.recruit.R;
import com.pwc.sdc.recruit.data.remote.BackPointService;
import com.pwc.sdc.recruit.data.remote.HttpSubscriber;
import com.pwc.sdc.recruit.data.remote.RetrofitClient;
import com.pwc.sdc.recruit.view.widget.LoadStateFrameLayout;
import com.pwc.sdc.recruit.view.widget.dialog.CommonToast;
import com.thirdparty.proxy.log.TLog;
import com.thirdparty.proxy.utils.DialogHelp;
import com.thirdparty.proxy.utils.WindowUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author:dongpo 创建时间: 5/24/2016
 * 描述:
 * 修改:
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private boolean _isVisible;
    private ProgressDialog _waitDialog;
    protected LayoutInflater mInflater;
    private LoadStateFrameLayout mLoadStateFrameLayout;
    public FragmentManager mFragmentManager;
    /**
     * 当前activity所持有的所有网络请求
     */
    private ArrayList<Subscription> mRequestSubscriptions;
    private BackPointService mBackEndService;
    private Bundle mObj;
    /**
     * startFragment中携带的bundle的头信息
     */
    public static final String FRAGMENT_MESSAGE_HEADER = "start_fragment_message_header";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PwcApplication.getInstance().addActivity(this);
        mInflater = getLayoutInflater();
        mFragmentManager = getSupportFragmentManager();

        View layoutView = onBeforeSetContentLayout(getLayoutView());
        View loadingView = getLoadingStateView(layoutView);

        if (loadingView != null) {
            mLoadStateFrameLayout = addLoadingStateView(loadingView);
            layoutView = mLoadStateFrameLayout;
        }

        setContentView(layoutView);

        ButterKnife.bind(this);
        initView();

        mRequestSubscriptions = new ArrayList<>();
        mBackEndService = RetrofitClient.getInstance().getService();

        Intent intent = getIntent();
        if (intent != null) {
            handleIntent(intent);
        }

        if(savedInstanceState != null){
            onRestoreInitData(savedInstanceState);
        }

        initData();
        _isVisible = true;
    }

    protected void onRestoreInitData(Bundle savedInstanceState){};

    protected void handleIntent(Intent intent) {

    }

    /**
     * @param targetView  用户要添加加载状态的view
     * @return 添加加载状态
     */
    public LoadStateFrameLayout addLoadingStateView(View targetView) {
        LoadStateFrameLayout loadstateView = new LoadStateFrameLayout(this);
        ViewGroup parent = (ViewGroup) targetView.getParent();
        ViewGroup.LayoutParams targetParams = targetView.getLayoutParams();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if(targetParams == null){
            targetParams = params;
        }

        targetView.setLayoutParams(params);
        if (parent != null) {
            int index = parent.indexOfChild(targetView);
            parent.removeView(targetView);
            loadstateView.addSuccessView(targetView);
            parent.addView(loadstateView, index, targetParams);
        } else {
            loadstateView.setLayoutParams(targetParams);
            loadstateView.addSuccessView(targetView);
        }
        return loadstateView;
    }

    /**
     * 给一个View添加加载的4种状态（成功，失败，超时，网络异常）
     *
     * @param contentView
     * @return
     */
    protected View getLoadingStateView(View contentView) {
        return null;
    }

    public void updateViewState(int state) {
        if (mLoadStateFrameLayout != null) {
            mLoadStateFrameLayout.updateState(state);
        }
    }

    protected View onBeforeSetContentLayout(View contentView) {
        return contentView;
    }

    protected View getLayoutView() {
        return mInflater.inflate(getLayoutId(), null);
    }

    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }

    /**
     * @param savedInstanceState activity被后台杀死，后恢复进行调用
     */
    protected void restoreInit(Bundle savedInstanceState) {
    }

    /**
     * 发送网络请求
     *
     * @param observable
     * @param callBack
     * @param <T>
     */
    protected <T> Subscription sendHttpRequest(Observable<T> observable, HttpSubscriber<T> callBack) {
        Subscription request = observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(callBack);
        addAsyncRequests(request);
        return request;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAllRequest();
        ButterKnife.unbind(this);
        WindowUtils.hideSoftKeyboard(getCurrentFocus());
        PwcApplication.getInstance().finishActivity(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(FRAGMENT_MESSAGE_HEADER, mObj);
    }

    public void sendBroadCast(String action, String category) {
        Intent intent = getAppContext().newIntent(action, category);
        sendBroadcast(intent);
    }

    public PwcApplication getAppContext() {
        return PwcApplication.getInstance();
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action跳转界面
     **/
    public void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    public void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void showToast(String message) {
        showToast(message, 0, Gravity.BOTTOM);
    }

    public void showToast(int msgResid, int icon, int gravity) {
        showToast(getString(msgResid), icon, gravity);
    }

    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(this);
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }


    public ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }


    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }


    public ProgressDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * Debug输出Log信息
     *
     * @param msg
     */
    protected void showDebugLog(String msg) {
        TLog.d(msg);
    }

    /**
     * Error输出Log信息
     *
     * @param msg
     */
    protected void showErrorLog(String msg) {
        TLog.e(msg);
    }

    /**
     * Info输出Log信息
     *
     * @param msg
     */
    protected void showInfoLog(String msg) {
        TLog.i(msg);
    }

    protected BackPointService getBackPointService() {
        return mBackEndService;
    }

    public void addAsyncRequests(Subscription request) {
        mRequestSubscriptions.add(request);

    }

    /**
     * 取消一个网络请求
     *
     * @param request
     */
    public void cancelRequest(Subscription request) {
        request.unsubscribe();
        request = null;
    }

    /**
     * 取消当前Activity相关的网络请求
     */
    private void cancelAllRequest() {
        if (mRequestSubscriptions != null && mRequestSubscriptions.size() > 0) {
            int size = mRequestSubscriptions.size();
            for (int i = 0; i < size; i++) {
                Subscription request = mRequestSubscriptions.get(i);
                if (request != null && !request.isUnsubscribed()) {
                    request.unsubscribe();
                    request = null;
                }
            }
            mRequestSubscriptions.clear();
        }
    }

    public void addFragment(int container, BaseFragment fragment) {
        if (fragment != null && !fragment.isAdded()) {
            String tag = fragment.getClass().getName();
            mFragmentManager.beginTransaction().add(container, fragment, tag).commitAllowingStateLoss();
        }
    }

    public void addFragmentAddBackStack(int container, BaseFragment fragment) {
        if (fragment != null && !fragment.isAdded()) {
            String tag = fragment.getClass().getName();
            mFragmentManager.beginTransaction().add(container, fragment, tag).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    public void removeFragment(BaseFragment fragment) {
        if (fragment != null && fragment.isAdded()) {
            mFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }

    }

    public void showFragment(BaseFragment fragment) {
        if (fragment != null && fragment.isAdded()) {
            mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        }
    }

    public void showFragmentAddBackStack(BaseFragment fragment) {
        if (fragment != null && fragment.isAdded()) {
            mFragmentManager.beginTransaction().show(fragment).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    public void hideFragment(BaseFragment fragment) {
        if (fragment != null && fragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

    public void replaceFragment(int container, BaseFragment fragment) {
        if (fragment != null) {
            mFragmentManager.beginTransaction().replace(container, fragment).commitAllowingStateLoss();
        }
    }

    public void startFragment(int container, BaseFragment originFragment, Class<? extends BaseFragment> clazz, Bundle bundle) {
        BaseFragment targetFragment = obtainFragment(clazz);

        if(bundle != null){
            String tag = targetFragment.getTag();
            bundle.putString(FRAGMENT_MESSAGE_HEADER, tag);
            setTag(bundle);
        }

        if (targetFragment.isAdded()) {
            showFragmentAddBackStack(targetFragment);
        } else {
            addFragmentAddBackStack(container, targetFragment);
        }

        hideFragment(originFragment);

    }


    public BaseFragment obtainFragment(Class<? extends BaseFragment> clazz) {
        BaseFragment fragment = findFragmentByClazz(clazz);
        if (fragment == null) {
            try {
                fragment = clazz.newInstance();
            } catch (Exception e) {
                TLog.exception(e);
                showDebugLog("instance fragment failed" + e.getMessage());
            }
            return fragment;
        }
        return fragment;
    }

    public BaseFragment findFragmentByClazz(Class<? extends BaseFragment> clazz) {
        String tag = clazz.getClass().getName();
        return (BaseFragment) mFragmentManager.findFragmentByTag(tag);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setTag(Bundle bundle) {
        mObj = bundle;
    }

    public Bundle getTag() {
        return mObj;
    }

    public Bundle obtainBundle() {
        if (mObj == null) {
            mObj = new Bundle();
        } else {
            mObj.clear();
        }
        return mObj;
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutId();
}
