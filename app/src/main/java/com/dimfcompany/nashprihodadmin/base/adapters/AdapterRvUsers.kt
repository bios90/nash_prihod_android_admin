package com.dimfcompany.nashprihodadmin.base.adapters

import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.diff.BaseDiff
import com.dimfcompany.nashprihodadmin.base.diff.DiffUsers
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.databinding.ItemUserBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.rucode.autopass.logic.utils.images.GlideManager

class AdapterRvUsers : BaseRvAdapter<ModelUser, ItemUserBinding>()
{
    override val layout_id: Int = R.layout.item_user
    override val diff_class: Class<out BaseDiff<ModelUser>> = DiffUsers::class.java

    override fun onBindViewHolder(holder: BaseViewHolder<ModelUser, ItemUserBinding>, position: Int)
    {
        val user = items.get(position)
        holder.bnd.bindUser(user)

        holder.bnd.root.setOnClickListener(
            {
                listener?.invoke(user)
            })
    }
}

fun ItemUserBinding.bindUser(user: ModelUser)
{
    GlideManager.loadImage(this.cvAvatar.imgImg, user.avatar?.url,show_failed_images = false)

    this.tvName.text = user.getFullName()
    this.tvEmail.text = user.email ?: " "
    this.tvPhone.text = user.phone ?: " "

    this.tvStatus.text = user.status?.getNameForDisplay()
    val status_color = user.status?.getColor() ?: getColorMy(R.color.gray5)
    this.tvStatus.setTextColor(status_color)

    this.tvBirthDate.text = user.birthday?.formatToString(DateManager.FORMAT_FOR_DISPLAY_FULL_MONTH)
}