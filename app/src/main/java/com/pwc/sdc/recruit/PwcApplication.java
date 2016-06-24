package com.pwc.sdc.recruit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.pwc.sdc.recruit.base.BaseActivity;
import com.thirdparty.proxy.base.BaseApplication;

import java.util.ArrayList;


/**
 * 全局应用程序类：
 *
 * @author
 * @created
 */
public class PwcApplication extends BaseApplication {

    private ArrayList<BaseActivity> mActivities;
    private static PwcApplication mPwcApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mPwcApplication = this;
        mActivities = new ArrayList<>();

        registerBroadCast();
    }

    public void registerBroadCast() {
        IntentFilter filter = new IntentFilter("text_broadcast");
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(), "this is advertsment", Toast.LENGTH_LONG).show();
            }
        }, filter);
    }


    public static PwcApplication getInstance() {
        return mPwcApplication;
    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(BaseActivity activity) {
        mActivities.add(activity);
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(BaseActivity activity) {
        if (activity != null && !activity.isFinishing()) {
            mActivities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivities.size(); i < size; i++) {
            if (null != mActivities.get(i)) {
                mActivities.get(i).finish();
            }
        }
        mActivities.clear();
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }


    /**
     * 获取一个新的Intent
     *
     * @param action
     * @param category
     * @return
     */
    public Intent newIntent(String action, String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        return intent;
    }

    public void sendBroadCast(String action, String category) {
        Intent intent = newIntent(action, category);
        sendBroadcast(intent);
    }

    /**
     *
     */
    public void cleanCache() {

    }


    public void checkUpdate() {

    }
}
