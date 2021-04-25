package com.dimfcompany.nashprihodadmin.base

import android.graphics.drawable.GradientDrawable
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.dp2pxInt
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import kotlin.math.min

@BindingAdapter(value = ["my_divider_size", "my_divider_color"], requireAll = false)
fun setDivider(lal: LinearLayout, my_divider_size: Float, my_divider_color: Int?)
{
    val drw = GradientDrawable()
    drw.shape = GradientDrawable.RECTANGLE
    if (lal.orientation == LinearLayout.VERTICAL)
    {
        drw.setSize(0, dp2pxInt(my_divider_size))
    }
    else
    {
        drw.setSize(dp2pxInt(my_divider_size), 0)
    }
    val color = my_divider_color ?: R.color.transparent
    drw.setColor(getColorMy(color))
    lal.dividerDrawable = drw
}

@BindingAdapter(value = ["make_round_my"])
fun makeRoundMy(card_view: CardView, make_round_my: Boolean)
{
    if (!make_round_my)
    {
        return
    }

    card_view.post(
        {
            val width = card_view.width
            val height = card_view.height
            val corner_radius = (min(width, height)) / 2f
            card_view.radius = corner_radius
        })
}
