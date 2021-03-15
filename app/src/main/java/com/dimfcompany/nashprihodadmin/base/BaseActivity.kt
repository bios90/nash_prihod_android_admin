package com.dimfcompany.nashprihodadmin.base

import android.app.Activity
import android.content.Intent
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.MessagesManager
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogBottom
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderMediaPicker
import com.dimfcompany.nashprihodadmin.logic.utils.files.MyFileItem
import com.dimfcompany.nashprihodadmin.logic.utils.images.ImageCameraManager
import com.dimfcompany.nashprihodadmin.networking.BaseNetworker
import com.dimfcompany.nashprihodadmin.networking.apis.*
import com.justordercompany.barista.logic.utils.BuilderPermRequest
import com.justordercompany.barista.logic.utils.PermissionManager
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity()
{
    @Inject
    lateinit var view_factory: ViewFactory

    @Inject
    lateinit var permission_manager: PermissionManager

    @Inject
    lateinit var image_camera_manager: ImageCameraManager

    @Inject
    lateinit var messages_manager: MessagesManager

    @Inject
    lateinit var api_auth: ApiAuth

    @Inject
    lateinit var api_files: ApiFiles

    @Inject
    lateinit var api_news: ApiNews

    @Inject
    lateinit var api_users: ApiUsers

    @Inject
    lateinit var api_services: ApiService

    @Inject
    lateinit var base_networker: BaseNetworker


    val composite_disposable = CompositeDisposable()

    var color_status_bar: Int = getColorMy(R.color.blue)
    var is_light_status_bar: Boolean = false
    var color_nav_bar: Int = getColorMy(R.color.blue)
    var is_light_nav_bar: Boolean = false
    var is_full_screen: Boolean = false
    var is_below_nav_bar: Boolean = false


    fun applyStatusNavColors()
    {
        if (is_full_screen)
        {
            this.window?.makeFullScreen(is_below_nav_bar)
        }

        this.window?.setStatusBarColorMy(color_status_bar)
        this.window?.setNavBarColor(color_nav_bar)
        this.window?.setStatusLightDark(is_light_status_bar)
        this.window?.setNavBarLightDark(is_light_nav_bar)
    }

    override fun onResume()
    {
        super.onResume()
        applyStatusNavColors()
        AppClass.top_activity = this
    }

    override fun onPause()
    {
        clearTopActivity()
        super.onPause()
    }

    override fun onDestroy()
    {
        composite_disposable.dispose()
        clearTopActivity()
        super.onDestroy()
    }

    private fun clearTopActivity()
    {
        val current_top = AppClass.top_activity
        if (this.equals(current_top))
        {
            AppClass.top_activity = null
        }
    }

    fun checkAndPickImage(action_picked: (MyFileItem) -> Unit)
    {
        checkPermissions(PermissionManager.permissions_camera,
            {
                BuilderMediaPicker()
                        .setActivity(this)
                        .setActionPickImage(action_picked)
                        .build()
            })
    }

    fun checkAndPickImageAndVideo(action_picked_image: (MyFileItem) -> Unit, action_picked_video: (MyFileItem) -> Unit)
    {
        checkPermissions(PermissionManager.permissions_camera,
            {
                BuilderMediaPicker()
                        .setActivity(this)
                        .setActionPickImage(action_picked_image)
                        .setActionPickVideo(action_picked_video)
                        .build()
            })
    }

    fun checkPermissions(permissions: List<String>, action_success: () -> Unit)
    {
        val text_blocked = getStringMy(R.string.for_full_work_need_permissions)

        BuilderPermRequest()
                .setPermissions(permissions)
                .setActionBlockedNow(
                    {
                        MessagesManager.getBuilderPermissionsBlockedNow(text_blocked,
                            {
                                checkPermissions(permissions, action_success)
                            })
                                .build(this)
                    })
                .setActionBlockedFinally(
                    {
                        MessagesManager.getBuilderPermissionsBlockedFinally(text_blocked)
                                .build(this)
                    })
                .setActionAvailable(
                    {
                        action_success()
                    })
                .build(this)
    }

    fun makePick(pick: ImageCameraManager.TypePick)
    {
        when (pick)
        {
            ImageCameraManager.TypePick.CAMERA_IMAGE ->
            {
                image_camera_manager.pickCameraImage(pick.action_success)
            }

            ImageCameraManager.TypePick.GALLERY_IMAGE ->
            {
                image_camera_manager.pickGalleryImage(pick.action_success)
            }

            ImageCameraManager.TypePick.PDF ->
            {
                image_camera_manager.pickPdfFile(pick.action_success, pick.action_error)
            }

            ImageCameraManager.TypePick.ANY ->
            {
                image_camera_manager.pickAnyFile(pick.action_success, pick.action_error)
            }
        }
    }

    fun finishWithResultOk(data: Intent? = null)
    {
        if (data != null)
        {
            setResult(Activity.RESULT_OK, data)
        }
        else
        {
            setResult(Activity.RESULT_OK)
        }

        finish()
    }

    fun finishWithResultCancel(data: Intent? = null)
    {
        if (data != null)
        {
            setResult(Activity.RESULT_CANCELED, data)
        }
        else
        {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

}