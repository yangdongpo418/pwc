package com.pwc.sdc.recruit.manager;

import com.pwc.sdc.recruit.PwcApplication;
import com.pwc.sdc.recruit.constants.Constants;
import com.pwc.sdc.recruit.data.model.User;

/**
 * @author
 * @created
 */
public class AccountManager {

    private static AccountManager mInstance = new AccountManager();
    private User mUser;
    private AccountManager() {}

    /**
     * 单一实例
     */
    public static AccountManager getAccountManager() {
        return mInstance;
    }

    /**
     * 获取当前用户
     * @return
     */
    public User getUser(){
        return mUser;
    }

    /**
     * 登入
     * @param user
     */
    public void login(User user){
        mUser = user;
        PwcApplication.getInstance().sendBroadCast(Constants.ACTION_LOGIN,Constants.CATEGORY_DEFAULT);
    }

    /**
     * 登出
     */
    public void logout(){
        mUser = null;
        PwcApplication.getInstance().sendBroadCast(Constants.ACTION_LOGOUT, Constants.CATEGORY_DEFAULT);
    }

    public boolean isLogin(){
        return mUser == null;
    }




}
