package com.pwc.sdc.recruit.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author:dongpo 创建时间: 6/24/2016
 * 描述:
 * 修改:
 */
public class PtrFixFrameLayout extends PtrFrameLayout {

    private FrameLayout mFrameContent;
    private View mChild;

    public PtrFixFrameLayout(Context context) {
        super(context);
    }

    public PtrFixFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        mChild = mFrameContent.getChildAt(0);
        if(canChildScrollUp(mChild)){
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }

    public boolean canChildScrollUp(View Target) {
        return ViewCompat.canScrollVertically(Target, -1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFrameContent = (FrameLayout) mContent;
    }

}
