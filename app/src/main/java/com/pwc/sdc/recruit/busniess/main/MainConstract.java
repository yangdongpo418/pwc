package com.pwc.sdc.recruit.busniess.main;

/**
 * @author:dongpo 创建时间: 2016/6/25
 * 描述:
 * 修改:
 */
public interface MainConstract {

    /**
     * 由view实现方法，供presenter调用
     */
    interface View {

    }

    /**
     * 有presenter实现，由view层调用
     */
    interface Presenter{
        String requestClick(String value);
    }

    /**
     *  由model层实现，供presenter调用
     */
    interface Model{
        String dealWithValue(String value);
    }
}
