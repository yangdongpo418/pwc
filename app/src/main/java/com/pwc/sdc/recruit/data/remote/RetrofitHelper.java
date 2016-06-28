package com.pwc.sdc.recruit.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author:dongpo 创建时间: 6/20/2016
 * 描述:
 * 修改:
 */
public class RetrofitHelper {

    private final Retrofit mRetrofit;
    private final BackPointService mBackPointService;
    private static RetrofitHelper client = new RetrofitHelper();

    private static final String BASE_URL = "http://apicloud.mob.com/v1/weather/query/";

    private RetrofitHelper(){

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        mBackPointService = mRetrofit.create(BackPointService.class);
    }

    public static RetrofitHelper getInstance(){
        return client;
    }

    public BackPointService getService(){
        return mBackPointService;
    }


}
