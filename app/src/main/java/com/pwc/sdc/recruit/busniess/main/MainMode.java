package com.pwc.sdc.recruit.busniess.main;

import android.util.Log;

import com.pwc.sdc.recruit.base.BaseModel;

/**
 * @author:dongpo 创建时间: 2016/6/25
 * 描述:
 * 修改:
 */
public class MainMode extends BaseModel implements MainConstract.Model{
    @Override
    public String dealWithValue(String value) {
        Log.d("Log_text", "MainMode+dealWithValue + Model完成处理");
        return "dealed";
    }
}
