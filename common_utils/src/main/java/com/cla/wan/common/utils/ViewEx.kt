package com.cla.wan.common.utils

import android.view.View
import android.view.ViewTreeObserver


/**
 * OnGlobalLayoutListener回调
 */
inline fun View.doOnGlobal(crossinline block: () -> Unit) {
    (getTag(R.id.common_utils_global_layout_listener) as? ViewTreeObserver.OnGlobalLayoutListener?)?.let {
        viewTreeObserver.removeOnGlobalLayoutListener(it)
        setTag(R.id.common_utils_global_layout_listener, null)
    }

    val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (parent == null || width <= 0) {
                //还没有添加到布局中，这里是dispatchOnGlobalLayout的效果
                return
            }

            viewTreeObserver.removeOnGlobalLayoutListener(this)
            setTag(R.id.common_utils_global_layout_listener, null)
            block()
        }
    }

    setTag(R.id.common_utils_global_layout_listener, listener)
    viewTreeObserver.addOnGlobalLayoutListener(listener)
    //强制回调
    viewTreeObserver.dispatchOnGlobalLayout()
}