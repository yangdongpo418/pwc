package com.pwc.sdc.recruit.view.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author:dongpo 创建时间: 6/24/2016
 * 描述:
 * 修改:
 */
public class PtrFixFrameLayout extends PtrFrameLayout {
    public PtrFixFrameLayout(Context context) {
        super(context);
    }

    public PtrFixFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void updateContent() {
        mContent = null;
        onFinishInflate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if(canChildScrollUp(mContent)){
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }

    public boolean canChildScrollUp(View Target) {
        return ViewCompat.canScrollVertically(Target, -1);
    }
}
