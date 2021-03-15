package com.dimfcompany.nashprihodadmin.base.extensions

import android.animation.Animator
import android.app.Dialog
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.AppClass
import com.google.android.gms.common.util.DeviceProperties.isTablet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun View.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null)
{
    if (layoutParams is ViewGroup.MarginLayoutParams)
    {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        left?.run { params.leftMargin = this }
        top?.run { params.topMargin = this }
        right?.run { params.rightMargin = this }
        bottom?.run { params.bottomMargin = this }
        requestLayout()
    }
}

fun View.setPaddingsMy(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null)
{
    val padding_left = left ?: this.paddingLeft
    val padding_top = top ?: this.paddingTop
    val padding_right = right ?: this.paddingRight
    val padding_bottom = bottom ?: this.paddingBottom

    this.setPadding(padding_left, padding_top, padding_right, padding_bottom)
    requestLayout()
}

fun View.animateFadeOut(duration: Int = 300)
{
    this.animate().alpha(1f).setDuration(duration.toLong())
            .setListener(object : Animator.AnimatorListener
            {
                override fun onAnimationRepeat(animation: Animator?)
                {
                }

                override fun onAnimationEnd(animation: Animator?)
                {
                }

                override fun onAnimationCancel(animation: Animator?)
                {
                }

                override fun onAnimationStart(animation: Animator?)
                {
                    this@animateFadeOut.visibility = View.VISIBLE
                }
            })
            .setInterpolator(LinearInterpolator()).start()
}

fun View.animateFadeIn(duration: Int = 300, visibility: Int = View.GONE)
{
    this.animate().alpha(0f).setDuration(duration.toLong())
            .setListener(object : Animator.AnimatorListener
            {
                override fun onAnimationRepeat(animation: Animator?)
                {
                }

                override fun onAnimationEnd(animation: Animator?)
                {
                    this@animateFadeIn.visibility = visibility
                }

                override fun onAnimationCancel(animation: Animator?)
                {
                }

                override fun onAnimationStart(animation: Animator?)
                {
                }
            })
            .setInterpolator(LinearInterpolator()).start()
}

fun TextView.setTextHtml(text: String)
{
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
    {
        this.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY))
    }
    else
    {
        this.setText(Html.fromHtml(text))
    }
}

fun Boolean.toVisibility(): Int
{
    if (this)
    {
        return View.VISIBLE
    }

    return View.GONE
}

fun Dialog.setNavigationBarColor(color: Int)
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
        val window = this.window ?: return
        val metrics = DisplayMetrics()
        window.getWindowManager().getDefaultDisplay().getMetrics(metrics)

        val dimDrawable = GradientDrawable()

        val navigationBarDrawable = GradientDrawable()
        navigationBarDrawable.shape = GradientDrawable.RECTANGLE
        navigationBarDrawable.setColor(color)

        val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)

        val windowBackground = LayerDrawable(layers)

        windowBackground.setLayerInsetTop(1, metrics.heightPixels)

        window.setBackgroundDrawable(windowBackground)
    }
}

fun Dialog.makeNotDraggable()
{
    this.setOnShowListener(
        {
            val dialog = it as BottomSheetDialog
            val bottomSheet: FrameLayout = dialog.findViewById(R.id.design_bottom_sheet) ?: return@setOnShowListener
//                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
//                BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true)
//                BottomSheetBehavior.from(bottomSheet).setHideable(true)

            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback()
            {
                override fun onStateChanged(bottomSheet: View, newState: Int)
                {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING)
                    {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float)
                {
                }
            })
        })
}

fun isTablet(): Boolean
{
    val resources = AppClass.app.resources
    val is_tablet = (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
    return is_tablet
}

fun RadioGroup.getCheckedPosition(): Int?
{
    if (this.childCount == 0)
    {
        return null
    }

    for (a in 0 until this.childCount)
    {
        val rb = this.getChildAt(a) as RadioButton
        if (rb.isChecked)
        {
            return a
        }
    }

    return null
}

fun RadioGroup.setCheckedAtPos(pos: Int): Boolean
{
    if (this.childCount < pos)
    {
        return false
    }

    val rb = this.getChildAt(pos) as RadioButton
    if (rb.isChecked)
    {
        return false
    }

    rb.isChecked = true
    return true
}

fun RecyclerView.setDivider(color: Int, size: Int, orientation: Int = DividerItemDecoration.VERTICAL)
{
    val drw = GradientDrawable()
    drw.shape = GradientDrawable.RECTANGLE

    val itemDecorator: DividerItemDecoration
    if (orientation == DividerItemDecoration.VERTICAL)
    {
        drw.setSize(0, size)
        itemDecorator = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
    }
    else
    {
        drw.setSize(size, 0)
        itemDecorator = DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL)
    }

    drw.setColor(color)

    itemDecorator.setDrawable(drw)
    this.addItemDecoration(itemDecorator)
}
