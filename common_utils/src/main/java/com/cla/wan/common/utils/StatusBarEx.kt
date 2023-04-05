package com.cla.wan.common.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.BarUtils

val statusBarHeight get() = BarUtils.getStatusBarHeight()

/** 导航栏的高度 */
val navBarHeight: Int
    get() = BarUtils.getNavBarHeight().let {
        if (topAty != null) {
            return if (BarUtils.isNavBarVisible(topAty)) it else 0
        } else {
            it
        }
    }

fun Activity.statusBarLightMode(isLightMode: Boolean) {
    BarUtils.setStatusBarLightMode(this, isLightMode)
}


fun Fragment.statusColorInt(
    @ColorInt statusColorInt: Int,
    light: Boolean = false,
    fitWindow: Boolean = true
) {
    requireActivity().statusColorInt(statusColorInt, light, fitWindow)
}

fun Fragment.statusColor(
    @ColorRes statusColorRes: Int,
    light: Boolean = false,
    fitWindow: Boolean = true
) {
    statusColorInt(colorValue(statusColorRes), light, fitWindow)
}

/**
 * 设置状态栏颜色
 * @param statusColorRes 状态栏颜色
 * @param  light  状态栏字体 true: 灰色，false: 白色 Android 6.0+; 导航栏按钮 true: 灰色，false: 白色 Android 8.0+
 * @param fitWindow 布局是否侵入状态栏（true 不侵入，false 侵入）
 */
fun FragmentActivity.statusColor(
    @ColorRes statusColorRes: Int,
    light: Boolean = false,
    fitWindow: Boolean = true
) {
    statusColorInt(colorValue(statusColorRes), light, fitWindow)
}


/**
 * 设置状态栏颜色
 * @param statusColorInt 状态栏颜色
 * @param  light  状态栏字体 true: 灰色，false: 白色 Android 6.0+; 导航栏按钮 true: 灰色，false: 白色 Android 8.0+
 * @param fitWindow 布局是否侵入状态栏（true 不侵入，false 侵入）
 */
fun FragmentActivity.statusColorInt(
    @ColorInt statusColorInt: Int,
    light: Boolean = false,
    fitWindow: Boolean = true
) {
    val colorResCompat = statusBarColorInt(statusColorInt)
    BarUtils.setStatusBarColor(this, colorResCompat, fitWindow)
    BarUtils.setStatusBarLightMode(this, light)
}

/**
 * 设置导航栏颜色
 * @param colorRes 导航栏颜色
 */
fun Fragment.navBarColorRes(@ColorRes colorRes: Int, light: Boolean = false) {
    requireActivity().navBarColorInt(colorRes.colorValue(), light)
}


/**
 * 设置导航栏颜色
 * @param colorInt 导航栏颜色
 */
fun Fragment.navBarColorInt(@ColorInt colorInt: Int, light: Boolean = false) {
    requireActivity().navBarColorInt(colorInt, light)
}

/**
 * 设置导航栏颜色
 * @param colorRes 导航栏颜色
 */
fun FragmentActivity.navBarColorRes(@ColorRes colorRes: Int, light: Boolean = false) {
    navBarColorInt(colorRes.colorValue(), light)
}

/**
 * 设置导航栏颜色
 * @param colorInt 导航栏颜色
 */
fun FragmentActivity.navBarColorInt(@ColorInt colorInt: Int, light: Boolean = false) {
    if (!BarUtils.isSupportNavBar()) {
        return
    }

    BarUtils.setNavBarColor(this, colorInt)
    BarUtils.setNavBarLightMode(this, light)
}

/**
 * Android 7.0 以下, 没有 light 模式,所以状态栏的颜色不能修改为暗色, 状态栏就不能为纯白色,否则整个状态栏都是一片纯白的颜色看不见文字
 */
fun statusBarColorInt(
    @ColorInt statusColorInt: Int
) = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
    Color.GRAY
} else statusColorInt
