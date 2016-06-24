package com.pwc.sdc.recruit.constants;

/**
 * 常量类
 * 
 * @author
 * @version
 * 
 */

public class Constants {

    //这个广播是注销登陆会发送的
    public static final String ACTION_LOGOUT = "pwc.intent.action.logout";
    public static final String ACTION_LOGIN = "pwc.intent.action.login";

    public static final String CATEGORY_DEFAULT = "pwc.intent.category.default";

    /**------------网络请求的状态码，用来更新界面----------------*/
    public static final int STATE_SUCCESS = 0;
    public static final int STATE_ERROR = 1;
    public static final int STATE_LOADING = 2;
    public static final int STATE_EMPTY = 3;

}
