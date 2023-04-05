package com.cla.wan.common.utils

import android.app.UiModeManager
import android.content.Context
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.Utils


/** 屏幕宽 */
val screenWidth: Int
    get() = ScreenUtils.getScreenWidth()

/** 屏幕高 */
val screenHeight: Int
    get() = ScreenUtils.getScreenHeight()

/**
 * 屏幕中除了状态栏和导航栏的高度
 */
val screenContentHeight: Int
    get() = screenHeight - statusBarHeight - navBarHeight

/** app是否处于深色模式 */
val isDark
    get() = Utils.getApp().let { app ->
        val uiModeManager = app.getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager?
        uiModeManager?.nightMode == UiModeManager.MODE_NIGHT_YES
    }