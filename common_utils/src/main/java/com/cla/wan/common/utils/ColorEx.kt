package com.cla.wan.common.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.Utils
import kotlin.math.abs
import kotlin.math.min

val colorC1 by lazyNone { R.color.c1.colorValue() }
val colorC4 by lazyNone { R.color.c4.colorValue() }
val colorC5 by lazyNone { R.color.c5.colorValue() }
val colorC6 by lazyNone { R.color.c6.colorValue() }
val colorC8 by lazyNone { R.color.c8.colorValue() }
val colorC9 by lazyNone { R.color.c9.colorValue() }
val colorC11 by lazyNone { R.color.c11.colorValue() }
val colorC12 by lazyNone { R.color.c12.colorValue() }

/**
 * 转换颜色值
 */
fun Context.colorValue(
    @ColorRes colorRes: Int,
    percent: Float = 1f
) = colorRes.run {
    kotlin.runCatching {
        val colorInt = ContextCompat.getColor(this@colorValue, this)
        val r = Color.red(colorInt)
        val g = Color.green(colorInt)
        val b = Color.blue(colorInt)
        val a = Color.alpha(colorInt)
        val alpha = min(abs((a * percent).toInt()), 255)
        Color.argb(alpha, r, g, b)
    }.getOrElse {
        this
    }
}

fun Fragment.colorValue(@ColorRes colorRes: Int, alpha: Float = 1f) = requireContext().colorValue(colorRes, alpha)
fun View.colorValue(@ColorRes colorRes: Int, alpha: Float = 1f) = context.colorValue(colorRes, alpha)

fun Int.colorValue(percent: Float = 1f) = Utils.getApp().colorValue(this, percent)

val String.colorValue get() = kotlin.runCatching { ColorUtils.string2Int(this) }.getOrNull()