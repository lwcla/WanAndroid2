package com.cla.wan.common.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.Utils
import kotlin.math.abs
import kotlin.math.min

/**
 * 不加锁的lazy
 * 加锁太多会影响性能
 */
inline fun <T> lazyNone(crossinline block: () -> T) = lazy(LazyThreadSafetyMode.NONE) { block() }

/**
 * 找到控件
 */
inline fun <reified V : View> View.findView(@IdRes idRes: Int) = lazyNone { findViewById<V>(idRes) }
inline fun <reified V : View> Activity.findView(@IdRes idRes: Int) = lazyNone { findViewById<V>(idRes) }
inline fun <reified V : View> Fragment.findView(@IdRes idRes: Int) = lazyNone { requireView().findViewById<V>(idRes) }


