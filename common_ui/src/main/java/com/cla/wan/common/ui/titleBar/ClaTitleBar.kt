package com.cla.wan.common.ui.titleBar

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import cn.cla.round.view.widget.ClaRoundImageView
import cn.cla.round.view.widget.ClaRoundLinearLayout
import cn.cla.round.view.widget.ClaRoundTextView
import com.cla.wan.common.ui.R
import com.cla.wan.common.utils.*

/**
 * 标题栏
 */
class ClaTitleBar(context: Context, attr: AttributeSet? = null) : ClaRoundLinearLayout(context, attr) {

    val rlLeft by findView<RelativeLayout>(R.id.rlLeft)
    val rlCenter by findView<RelativeLayout>(R.id.rlCenter)
    val rlRight by findView<RelativeLayout>(R.id.rlRight)

    init {
        orientation = LinearLayoutCompat.HORIZONTAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        inflate(context, R.layout.common_ui_layout_title_bar, this)

        val array = context.obtainStyledAttributes(attr, R.styleable.ClaTitleBar)
        val bottomLineHeight = array.getDimension(R.styleable.ClaTitleBar_title_bar_bottom_line_height, 0f)
        val style = array.getInt(R.styleable.ClaTitleBar_title_bar_style, 0)
        array.recycle()

        setPadding(0, statusBarHeight, 0, 0)

        if (style == 0) {
            doOnGlobal {
                if (rlCenter.childCount == 0) {
                    //因为中间的容器设置了weight，如果没有设置子控件的话，就隐藏它，不然左右两边的容器显示的会有点问题
                    rlCenter.isVisible = false
                    return@doOnGlobal
                }
                rlCenter.isVisible = true

                //如果左右两个控件的宽度不一样，那么中间的文字就可能不会居中
                val leftWidth = rlLeft.width
                val rightWidth = rlRight.width
                val maxWidth = maxOf(leftWidth, rightWidth).coerceAtMost(width / 2)
                //如果中间容器需要显示的话,要留出中间容器的宽度，最小为100dp，如果左右容器的宽度太大的话，可能会把中间容器挤没了
                val realWidth = if (rlCenter.childCount > 0) {
                    minOf((width - dp100) / 2, maxWidth)
                } else {
                    maxWidth
                }

                rlLeft.updateLayoutParams {
                    width = realWidth
                }

                rlRight.updateLayoutParams {
                    width = realWidth
                }
            }
        }

        if (bottomLineHeight > 0) {
            resetClaLine {
                showBottom = true
                lineColor = colorValue(com.cla.wan.common.utils.R.color.c9)
                lineWidth = dpf1
            }
        }
    }
}

/**
 * 标题栏控件的包装类
 * 这是为了不让设置标题栏的方法变得过于设置设置的
 * 如果说设置标题栏的方法，还不能实现你的要求，那么应该在这里添加方法去实现
 */
class TitleBarWrapper(
    val leftImageView: View? = null,
    val centerTextView: View? = null,
    val rightTextView: View? = null,
    val titleBar: ClaTitleBar
) {

    /**
     * 重置标题栏中间的文字
     */
    fun resetCenterText(@StringRes stringRes: Int) {
        try {
            (centerTextView as? TextView?)?.apply {
                setText(stringRes)
                visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置标题栏中间的文字的颜色
     */
    fun resetCenterTextColor(@ColorInt color: Int) {
        try {
            (centerTextView as? TextView?)?.apply {
                setTextColor(color)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置标题栏中间的文字的颜色
     */
    fun resetRightTextColor(@ColorInt color: Int) {
        try {
            (rightTextView as? TextView?)?.apply {
                setTextColor(color)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 重置标题栏中间的文字
     */
    fun resetCenterText(string: String?) {
        try {
            (centerTextView as? TextView?)?.apply {
                text = string
                visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 重置标题栏中间的文字
     */
    fun resetCenterTextSize(size: Float?) {
        try {
            (centerTextView as? TextView?)?.apply {
                textSize = size ?: 0f
                visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置背景颜色
     * @param colorStr 颜色值
     * @param isStatusFontDark 状态栏字体 true: 灰色，false: 白色 Android 6.0+; 导航栏按钮 true: 灰色，false: 白色 Android 8.0+
     */
    fun setBackgroundColor(colorStr: String, isStatusFontDark: Boolean = true) {
        try {
            val color = colorStr.colorValue
            setBackgroundColor(color, isStatusFontDark)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTitleBarBackgroundColor(colorStr: String) {
        try {
            val color = colorStr.colorValue
            setTitleBarBackgroundColor(color)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置背景颜色
     * @param color Int 颜色值
     * @param isStatusFontDark 状态栏字体 true: 灰色，false: 白色 Android 6.0+; 导航栏按钮 true: 灰色，false: 白色 Android 8.0+
     */
    fun setBackgroundColor(@ColorInt color: Int?, isStatusFontDark: Boolean = true) {
        if (color == null) {
            return
        }

        try {
            titleBar.setBackgroundColor(color)
            topAty?.let { aty -> aty.statusBarLightMode(isStatusFontDark) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTitleBarBackgroundColor(@ColorInt color: Int?) {
        if (color == null) {
            return
        }

        kotlin.runCatching {
            titleBar.setBackgroundColor(color)
        }
    }

    fun setLeftImgColor(@ColorRes res: Int) {
        val aty = topAty ?: return

        if (leftImageView is ImageView) {
            leftImageView.setImageDrawable(aty.drawableValue(com.cla.wan.common.utils.R.drawable.common_utils_svg_navigation, res))
        }
    }

    fun setLeftImg(@DrawableRes res: Int) {
        val aty = topAty ?: return

        if (leftImageView is ImageView) {
            leftImageView.setImageDrawable(aty.drawableValue(res))
        }
    }

    fun setLeftDrawable(drawable: Drawable?) {
        if (leftImageView is ImageView) {
            leftImageView.setImageDrawable(drawable)
        }
    }

    /** 是否显示右侧控件的容器 */
    inline fun setRightContainer(container: (RelativeLayout) -> Unit) {
        container(titleBar.rlRight)
    }

    /** 设置中间控件的容器 */
    inline fun setCenterContainer(container: (RelativeLayout) -> Unit) {
        container(titleBar.rlCenter)
    }

    /** 设置左侧控件的容器 */
    inline fun setLeftContainer(container: (RelativeLayout) -> Unit) {
        container(titleBar.rlLeft)
    }

    /**
     * 左侧控件的容器宽度为自适应
     * 中间控件的容器铺满剩下的部分，添加右边距
     * 隐藏右侧控件的容器
     */
    fun setStyle1() {
        setCenterContainer { v -> v.setPadding(0, 0, dp16.toInt(), 0) }
        setRightContainer { v -> v.isVisible = false }
        setLeftContainer { v ->
            v.layoutParams?.let { params ->
                params.width = WRAP_CONTENT
                v.layoutParams = params
            }
        }
    }

    /**
     * 左侧控件的容器宽度为自适应
     * 中间控件的容器铺满剩下的部分
     * 右侧控件的容器宽度为自适应
     */
    fun setStyle2() {
        setCenterContainer { }
        setRightContainer { v ->
            v.layoutParams?.let { params ->
                params.width = WRAP_CONTENT
                v.layoutParams = params
            }
        }
        setLeftContainer { v ->
            v.layoutParams?.let { params ->
                params.width = WRAP_CONTENT
                v.layoutParams = params
            }
        }
    }

    fun showTitleBar(bool: Boolean) {
        titleBar.isVisible = bool
    }
}


fun Fragment.initTitleBar(
    titleBar: ClaTitleBar,
    builder: (TitleBarBuilder.() -> Unit)? = null
) = requireActivity().initTitleBar(titleBar) {
    statusLifecycle = lifecycle
    builder?.invoke(this)
}

fun FragmentActivity.initTitleBar(
    titleBar: ClaTitleBar,
    builder: (TitleBarBuilder.() -> Unit)? = null
): TitleBarWrapper = titleBar.run {

    val aty = this@initTitleBar

    TitleBarBuilder().run {
        builder?.invoke(this)
        val customCenterView = centerView?.invoke(aty, this)
        val customLeftView = leftView?.invoke(aty, this)
        val customRightView = rightView?.invoke(aty, this)

        //设置标题栏中间的文字
        rlCenter.removeAllViews()
        customCenterView?.let { centerChild ->
            val params = centerChild.layoutParams ?: RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).also { it.addRule(RelativeLayout.CENTER_IN_PARENT) }
            rlCenter.addView(centerChild, params)
        }

        //设置标题栏左边的返回图标
        rlLeft.removeAllViews()
        customLeftView?.let { leftChild ->
            val params = leftChild.layoutParams ?: RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).also { it.addRule(RelativeLayout.CENTER_VERTICAL) }
            rlLeft.addView(leftChild, params)
        }

        //设置标题栏右边的文字
        rlRight.removeAllViews()
        customRightView?.let { rightChild ->
            val params = rightChild.layoutParams ?: RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).also { it.addRule(RelativeLayout.CENTER_VERTICAL) }
            rlRight.addView(rightChild, params)
        }

        //设置状态栏颜色
        fun setStatusColor() {
            //设置标题栏和状态栏的颜色
            try {
                titleBar.setBackgroundResource(statusBarColorRes)
                statusColor(statusBarColorRes, light = isStatusLight, fitWindow = false)
                navBarColorRes(navBarColorRes, light = isNavLight)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (resetStatusColorOnResume) {

            if (statusLifecycle == null) {
                statusLifecycle = lifecycle
            }

            statusLifecycle?.addObserver(object : DefaultLifecycleObserver {

                //当activity或者fragment onCreate时，设置状态栏的颜色
                override fun onCreate(owner: LifecycleOwner) {
                    setStatusColor()
                }

                //当activity或者fragment重新onResume时，再设置一次状态栏的颜色
                fun onResume() {
                    setStatusColor()
                }

                fun onDestroy() {
                    statusLifecycle?.removeObserver(this)
                }
            })
        } else {
            setStatusColor()
        }

        TitleBarWrapper(customLeftView, customCenterView, customRightView, titleBar)
    }
}

typealias TitleRightClick = (View) -> Unit

/**
 * titleBar的自定义属性
 *
 * @param centerTextRes 中间显示的字符
 * @param centerTextString 中间显示的字符
 * @param centerTextColor 中间显示的字符串颜色
 * @param backImageColor 左边返回图标的颜色
 * @param isShowBackImage 是否显示左边的返回图标
 * @param rightTextRes 右边显示的字符
 * @param rightTextColor 右边显示的字符串颜色
 * @param statusBarColorRes 状态栏以及标题栏的背景颜色
 * @param navBarColorRes 导航栏背景颜色
 * @param isStatusLight 状态栏是否为亮色模式
 * @param isNavLight 导航栏是否为亮色模式
 * @param  rightTextClick 右边文字点击响应
 * @param resetStatusColorOnResume 在activity或者Fragment重新走到onResume时，是否需要重新设置状态栏颜色
 * @param statusLifecycle Lifecycle activity和Fragment要区分开
 * @param leftView 自定义的标题栏左边的view，如果返回null，那么标题栏左边就是空的，默认实现是一个返回图标
 * @param centerView 自定义的标题栏中间的view，如果返回null，那么标题栏中间就是空的，默认实现是一个TextView
 * @param rightView 自定义的标题提篮右边的view，如果返回null，那么标题栏右边就是空的，默认实现是一个TextView
 */
class TitleBarBuilder(
    @StringRes var centerTextRes: Int? = null,
    var centerTextString: String? = null,
    @ColorRes var centerTextColor: Int = com.cla.wan.common.utils.R.color.ff333333_ff999999,
    @ColorRes var backImageColor: Int = com.cla.wan.common.utils.R.color.ff333333_ff999999,
    var leftImgClick: (FragmentActivity, View) -> Unit = { aty, _ -> aty.finish() },
    var isShowBackImage: Boolean = true,
    @StringRes var rightTextRes: Int? = null,
    @ColorRes var rightTextColor: Int = com.cla.wan.common.utils.R.color.ff333333_ff999999,
    @ColorRes var statusBarColorRes: Int = com.cla.wan.common.utils.R.color.fff6f6f6_00000000,
    @ColorRes var navBarColorRes: Int = com.cla.wan.common.utils.R.color.fff6f6f6_00000000,
    var isStatusLight: Boolean = !isDark,
    var isNavLight: Boolean = !isDark,
    var rightTextClick: TitleRightClick = {},
    //在onResume时，修改状态栏的颜色
    var resetStatusColorOnResume: Boolean = true,
    var statusLifecycle: Lifecycle? = null,
    var leftView: ((FragmentActivity, TitleBarBuilder) -> View?)? = ::defaultLeftBackImageView,
    var centerView: ((FragmentActivity, TitleBarBuilder) -> View?)? = ::defaultCenterTextView,
    var rightView: ((FragmentActivity, TitleBarBuilder) -> View?)? = ::defaultRightTextView
)

private fun Context.getShowText(res: Int? = -1, text: String? = null): String? = try {
    getString(res ?: -1)
} catch (e: Exception) {
    null
} ?: text

private fun defaultLeftBackImageView(
    aty: FragmentActivity,
    builder: TitleBarBuilder
): View? = builder.run {

    if (!isShowBackImage) {
        return@run null
    }

    val leftImageView = ClaRoundImageView(aty)
    leftImageView.changeAlphaWhenPress = true
    leftImageView.scaleType = ImageView.ScaleType.FIT_XY
    leftImageView.setImageDrawable(aty.drawableValue(com.cla.wan.common.utils.R.drawable.common_utils_svg_navigation, backImageColor))
    leftImageView.setBackgroundResource(com.cla.wan.common.utils.R.color.transparent)
    leftImageView.setOnClickListener { leftImgClick(aty, it) }
    leftImageView.setPadding(dp15, dp15, dp15, dp15)

    return@run leftImageView
}

private fun defaultCenterTextView(
    aty: FragmentActivity,
    builder: TitleBarBuilder
) = builder.run {
    val showText = aty.getShowText(centerTextRes, centerTextString)
    if (showText.isNullOrBlank()) {
        return@run null
    }

    val centerTextView = TextView(aty)
    centerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
    centerTextView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)//加粗

    centerTextView.text = showText

    //如果文字太多的话，就隐藏一部分中间的字
    centerTextView.maxWidth = screenWidth / 2
    centerTextView.ellipsize = TextUtils.TruncateAt.END
    centerTextView.maxLines = 1

    centerTextView.setTextColor(aty.colorValue(centerTextColor))
    centerTextView.gravity = Gravity.CENTER
    centerTextView.setPadding(0, dp15, 0, dp15)

    return@run centerTextView
}

private fun defaultRightTextView(
    aty: FragmentActivity,
    builder: TitleBarBuilder
) = builder.run {

    //设置标题栏右边的文字
    val rightTextView = ClaRoundTextView(aty)
    rightTextView.changeAlphaWhenPress = true
    rightTextView.setTextColor(aty.colorValue(rightTextColor))

    aty.getShowText(rightTextRes).let {
        if (it.isNullOrBlank()) {
            rightTextView.text = ""
            rightTextView.visibility = View.GONE
        } else {
            rightTextView.text = it
            rightTextView.visibility = View.VISIBLE
        }
    }

    rightTextView.gravity = Gravity.END
    //左右的padding都设置，增加点击事件能够响应的范围
    rightTextView.setPadding(dp15, dp15, dp15, dp15)
    rightTextView.setOnClickListener { rightTextClick.invoke(it) }

    return@run rightTextView
}

