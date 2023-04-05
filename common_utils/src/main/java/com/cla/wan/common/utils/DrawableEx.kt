package com.cla.wan.common.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.Utils

fun Context.changeSvgColor(
    drawable: Drawable?,
    @ColorRes colorRes: Int? = null,
    @ColorInt color: Int? = null
) = drawable?.apply {
    val ctx = this@changeSvgColor
    try {
        colorRes?.let { DrawableCompat.setTint(mutate(), ContextCompat.getColor(ctx, it)) }
        color?.let { DrawableCompat.setTint(mutate(), it) }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.drawableValue(
    @DrawableRes res: Int?,
    @ColorRes colorRes: Int? = null,
    @ColorInt color: Int? = null
) = res?.run {

    val ctx = this@drawableValue

    try {
        val drawable = ContextCompat.getDrawable(ctx, res)
        drawable?.also {
            ctx.changeSvgColor(drawable, colorRes = colorRes)
            ctx.changeSvgColor(drawable, color = color)
        }
    } catch (e: Exception) {
        null
    }
}

fun Fragment.drawableValue(
    @DrawableRes res: Int?,
    @ColorRes colorRes: Int? = null
) = requireContext().drawableValue(res, colorRes)

fun Int?.drawableValue(
    @ColorRes colorRes: Int? = null,
    @ColorInt color: Int? = null
) = Utils.getApp().drawableValue(this, colorRes, color)