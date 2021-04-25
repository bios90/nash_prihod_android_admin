package com.dimfcompany.nashprihodadmin.logic.utils.builders

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.lifecycleScope
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.base.extensions.runActionWithDelay
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.PermissionManager
import com.dimfcompany.nashprihodadmin.logic.utils.files.FileManager
import com.dimfcompany.nashprihodadmin.logic.utils.files.MyFileItem
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BuilderMediaPicker
{
    var action_picked_video: ((MyFileItem) -> Unit)? = null
        private set
    var action_picked_image: ((MyFileItem) -> Unit)? = null
        private set
    var activity: BaseActivity? = null
        private set

    fun setActionPickVideo(action: ((MyFileItem) -> Unit)?) = apply(
        {
            this.action_picked_video = action
        })

    fun setActionPickImage(action: ((MyFileItem) -> Unit)?) = apply(
        {
            this.action_picked_image = action
        })

    fun setActivity(activity: BaseActivity) = apply(
        {
            this.activity = activity
        })

    fun build()
    {
        if (activity == null)
        {
            throw RuntimeException("**** Error no activity setted ****")
        }

        if (action_picked_image == null && action_picked_video == null)
        {
            throw RuntimeException("**** No actions setted ****")
        }

        activity!!.checkPermissions(PermissionManager.permissions_camera,
            {
                val btns = getBottomBtns()
                if (btns.size == 0)
                {
                    throw RuntimeException("**** Error no btns to show ****")
                }

                BuilderDialogBottom()
                        .setBtns(btns)
                        .show(activity!!.supportFragmentManager)
            })

    }

    private fun getBottomBtns(): ArrayList<BtnAction>
    {
        val btns: ArrayList<BtnAction> = arrayListOf()

        if (action_picked_image != null)
        {
            val btn_image_gallery = BtnAction(getStringMy(R.string.select_photo),
                {
                    HelperMediaPicker.pickGalleryDefault(activity!!,
                        {
                            action_picked_image?.invoke(it)
                        })
                })

            val btn_image_camera = BtnAction(getStringMy(R.string.make_photo),
                {
                    HelperMediaPicker.pickCameraImage(activity!!,
                        {
                            action_picked_image?.invoke(it)
                        })
                })


            btns.add(btn_image_gallery)
            btns.add(btn_image_camera)
        }

        if (action_picked_video != null)
        {

            val btn_video_gallery = BtnAction(getStringMy(R.string.select_video),
                {
                    HelperMediaPicker.pickGalleryVideo(activity!!, action_picked_video!!)
                })

            val btn_video_camera = BtnAction(getStringMy(R.string.make_video),
                {
                    HelperMediaPicker.pickCameraVideo(activity!!, action_picked_video!!)
                })

            btns.add(btn_video_gallery)
            btns.add(btn_video_camera)
        }

        return btns
    }
}

class HelperMediaPicker
{
    companion object
    {
        fun pickCameraImage(activity: BaseActivity, action: ((MyFileItem) -> Unit))
        {
            val file = FileManager.create_temp_image_file()
            val uri = FileManager.uriFromFile(file)

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            activity.startForResult(intent)
            { result ->

                if (file.length() < 1000L)
                {
                    activity.messages_manager.showProgressDialog()
                    runActionWithDelay(activity.lifecycleScope, 3000,
                        {
                            activity.messages_manager.dismissProgressDialog()
                            val my_file = MyFileItem.createFromFile(file)
                            action(my_file)
                        },
                        {
                            activity.messages_manager.dismissProgressDialog()
                        })
                }
                else
                {
                    val my_file = MyFileItem.createFromFile(file)
                    action(my_file)
                }
            }.onFailed(
                { result ->

                })
        }

        fun pickGalleryDefault(activity: BaseActivity, action: ((MyFileItem) -> Unit))
        {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activity.startForResult(intent)
            { result ->
                result.data?.data ?: return@startForResult
                resultDataToMyFileItem(activity, result.data!!.data!!, action)
            }
        }

        fun pickCameraVideo(activity: BaseActivity, action: ((MyFileItem) -> Unit))
        {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            activity.startForResult(intent)
            { result ->
                result.data?.data ?: return@startForResult
                resultDataToMyFileItem(activity, result.data!!.data!!, action)
            }
        }


        fun pickGalleryVideo(activity: BaseActivity, action: ((MyFileItem) -> Unit))
        {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            activity.startForResult(intent)
            { result ->
                result.data?.data ?: return@startForResult
                resultDataToMyFileItem(activity, result.data!!.data!!, action)
            }
        }


        private fun resultDataToMyFileItem(activity: BaseActivity, uri: Uri, action: (MyFileItem) -> Unit)
        {
            Observable.defer({
                Observable.just(MyFileItem.createFromData(uri))
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe({ activity.messages_manager.showProgressDialog() })
                    .doFinally({ activity.messages_manager.dismissProgressDialog() })
                    .subscribe(
                        {
                            action(it)
                        },
                        {
                            it.printStackTrace()
                            BuilderAlerter.getRedBuilder(getStringMy(R.string.error_on_getting_file))
                                    .show(activity)
                        })
                    .disposeBy(activity.composite_disposable)
        }
    }

}