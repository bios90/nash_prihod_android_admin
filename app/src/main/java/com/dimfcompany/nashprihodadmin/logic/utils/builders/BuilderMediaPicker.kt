package com.babydayka.logic.utils.builders

import android.content.Intent
import android.provider.MediaStore
import com.babydayka.R
import com.babydayka.base.Constants
import com.babydayka.base.activity.BaseActivity
import com.babydayka.base.extensions.disposeBy
import com.babydayka.base.getStringMy
import com.babydayka.base.makeActionWithDelay
import com.babydayka.logic.utils.BtnAction
import com.babydayka.logic.utils.files.FileManager
import com.babydayka.logic.utils.files.MyFileItem
import com.babydayka.ui.ActGallery.ActGallery
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BuilderMediaPicker
{
    var is_multi_images: Boolean = false
        private set
    var action_picked_video: ((MyFileItem) -> Unit)? = null
        private set
    var action_picked_images: ((ArrayList<MyFileItem>) -> Unit)? = null
        private set
    var action_delete: (() -> Unit)? = null
        private set
    var activity: BaseActivity? = null
        private set
    var max_count_to_pick: Int? = null
        private set
    var has_premium: Boolean? = null
        private set
    var action_clicked_video_but_no_premium: (() -> Unit)? = null
        private set

    fun setActionPickVideo(action: ((MyFileItem) -> Unit)?) = apply(
        {
            this.action_picked_video = action
        })

    fun setActionPickImages(action: ((ArrayList<MyFileItem>) -> Unit)?) = apply(
        {
            this.action_picked_images = action
        })

    fun setActionDelete(action: (() -> Unit)?) = apply(
        {
            this.action_delete = action
        })

    fun setActionClickedVideoButNoPremium(action: (() -> Unit)?) = apply(
        {
            this.action_clicked_video_but_no_premium = action
        })

    fun setActivity(activity: BaseActivity) = apply(
        {
            this.activity = activity
        })

    fun setIsMultiImages(is_multi: Boolean) = apply(
        {
            this.is_multi_images = is_multi
        })

    fun setMaxCountToPick(count: Int?) = apply(
        {
            this.max_count_to_pick = count
        })

    fun setHasPremium(has_premium: Boolean?) = apply(
        {
            this.has_premium = has_premium
        })

    fun build()
    {
        if (activity == null)
        {
            throw RuntimeException("**** Error no activity setted ****")
        }

        if (action_picked_images == null && action_picked_video == null)
        {
            throw RuntimeException("**** No actions setted ****")
        }

        activity!!.permissionChecker.check_camera_and_storage()
                .subscribe(
                    {
                        val btns = getBottomBtns()
                        if (btns.size == 0)
                        {
                            throw RuntimeException("**** Error no btns to show ****")
                        }

                        activity!!.messagesManager.showBottomSheet(btns)
                    })
                .disposeBy(activity!!.compositeDisposable)
    }

    private fun getBottomBtns(): ArrayList<BtnAction>
    {
        val btns: ArrayList<BtnAction> = arrayListOf()

        if (action_picked_images != null)
        {
            val btn_image_gallery = BtnAction(getStringMy(R.string.choose_foto),
                {
                    HelperMediaPicker.pickGalleryMy(activity!!, action_picked_images!!, max_count_to_pick, !is_multi_images)
                })

            val btn_image_camera = BtnAction(getStringMy(R.string.take_photo),
                {
                    HelperMediaPicker.pickCameraImage(activity!!,
                        {
                            action_picked_images!!(arrayListOf(it))
                        })
                })

            if (canPickMoreImages())
            {
                btns.add(btn_image_gallery)
                btns.add(btn_image_camera)
            }
        }

        if (action_picked_video != null)
        {
            val img_lock:Int? = if(has_premium == false) R.drawable.ic_lock_red else null

            val btn_video_gallery = BtnAction(getStringMy(R.string.choose_video),
                {
                    if(has_premium == false)
                    {
                        action_clicked_video_but_no_premium?.invoke()
                    }
                    else
                    {
                        HelperMediaPicker.pickGalleryVideo(activity!!, action_picked_video!!)
                    }
                },img_lock)

            val btn_video_camera = BtnAction(getStringMy(R.string.take_video),
                {
                    if(has_premium == false)
                    {
                        action_clicked_video_but_no_premium?.invoke()
                    }
                    else
                    {
                        HelperMediaPicker.pickCameraVideo(activity!!, action_picked_video!!)
                    }
                },img_lock)

            btns.add(btn_video_gallery)
            btns.add(btn_video_camera)
        }

        if (action_delete != null)
        {
            val btn_delete = BtnAction(getStringMy(R.string.delete_media),
                {
                    action_delete!!.invoke()
                })
            btns.add(btn_delete)
        }

        return btns
    }

    private fun canPickMoreImages(): Boolean
    {
        return max_count_to_pick == null || max_count_to_pick!! > 0
    }
}

class HelperMediaPicker
{
    companion object
    {
        fun pickCameraImage(activity: BaseActivity, action: ((MyFileItem) -> Unit))
        {
            val file = FileManager.createTempImageFile()
            val uri = FileManager.uriFromFile(file)

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            activity.startForResult(intent)
            { result ->

                if (file.length() < 1000L)
                {
                    activity.messagesManager.showProgressDialog()
                    makeActionWithDelay(3000,
                        {
                            activity.messagesManager.dismissProgressDialog()
                            val my_file = MyFileItem.createFromFile(file)
                            action(my_file)
                        },
                        {
                            activity.messagesManager.dismissProgressDialog()
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

        fun pickGalleryMy(activity: BaseActivity, action: ((ArrayList<MyFileItem>) -> Unit), max_count_to_pick: Int?, is_single_image_mode: Boolean = false)
        {
            activity.navigationManager.getBuilderIntent()
                    .addParam(Constants.EXTRA_MAX_IMAGES_COUNT, max_count_to_pick)
                    .addParam(Constants.EXTRA_IS_SINGLE_IMAGE_MODE, is_single_image_mode)
                    .setActivityToStart(ActGallery::class.java)
                    .setOkAction(
                        {
                            val images = it?.getSerializableExtra(Constants.EXTRA_IMAGES_AS_MYFILE) as? ArrayList<MyFileItem>
                            if (!images.isNullOrEmpty())
                            {
                                action(images)
                            }
                        })
                    .startActivity()
        }

        fun pickGalleryDefault(activity: BaseActivity, action: ((MyFileItem) -> Unit))
        {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activity.startForResult(intent)
            { result ->
                result.data ?: return@startForResult
                resultDataToMyFileItem(activity, result.data!!, action)
            }
        }

        fun pickCameraVideo(activity: BaseActivity, action: ((MyFileItem) -> Unit))
        {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            activity.startForResult(intent)
            { result ->
                result.data ?: return@startForResult
                resultDataToMyFileItem(activity, result.data!!, action)
            }
        }


        fun pickGalleryVideo(activity: BaseActivity, action: ((MyFileItem) -> Unit))
        {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            activity.startForResult(intent)
            { result ->
                result.data ?: return@startForResult
                resultDataToMyFileItem(activity, result.data!!, action)
            }
        }


        private fun resultDataToMyFileItem(activity: BaseActivity, data: Intent, action: (MyFileItem) -> Unit)
        {
            Observable.defer({
                Observable.just(MyFileItem.createFromData(data))
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe({ activity.messagesManager.showProgressDialog() })
                    .doFinally({ activity.messagesManager.dismissProgressDialog() })
                    .subscribe(
                        {
                            action(it)
                        },
                        {
                            it.printStackTrace()
                            activity.messagesManager.showFileIntentErrorAlerter()
                        })
                    .disposeBy(activity.compositeDisposable)
        }
    }

}