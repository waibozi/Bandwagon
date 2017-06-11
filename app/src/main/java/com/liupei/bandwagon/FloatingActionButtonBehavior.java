package com.liupei.bandwagon;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/6/11.
 */

public class FloatingActionButtonBehavior extends FloatingActionButton.Behavior {

    private boolean isVisible=true;

    public FloatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.e("ff", dyConsumed + "");
        if (dyConsumed > 0 && isVisible) {
            isVisible = false;
            child.animate().scaleX(0).scaleY(0).start();
        } else if (dyConsumed < 0 && !isVisible) {
            isVisible = true;
            child.animate().scaleX(1).scaleY(1).start();
        }
    }
}
