package com.thirdparty.proxy.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.thirdparty.proxy.net.frecso.OkHttpImagePipelineConfigFactory;


public class BaseApplication extends Application {

    private static final String PREF_NAME = "baseApplication";
    private static BaseApplication mContext;
    private static Resources mResource;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mResource = mContext.getResources();
        init();
    }

    public static BaseApplication getInstance(){
        return mContext;
    }

    private void init() {
        //TLog.init(getPackageName(), BuildConfig.DEBUG);

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this)
                .build();
        Fresco.initialize(this,config);
    }


    public static Resources resources() {
        return mResource;
    }

    public static String string(int id) {
        return mResource.getString(id);
    }

    public static String string(int id, Object... args) {
        return mResource.getString(id, args);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
        SharedPreferences pre = mContext.getSharedPreferences(PREF_NAME,
                Context.MODE_MULTI_PROCESS);
        return pre;
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public static void showToast(String message, int duration) {
        Toast.makeText(mContext, "", duration).show();
    }
}
