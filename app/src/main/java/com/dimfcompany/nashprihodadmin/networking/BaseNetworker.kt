package com.dimfcompany.nashprihodadmin.networking

import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.extensions.addParseCheckerForObj
import com.dimfcompany.nashprihodadmin.base.extensions.toObjOrThrow
import com.dimfcompany.nashprihodadmin.logic.models.ModelFile
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.models.responses.BaseResponse
import com.dimfcompany.nashprihodadmin.logic.models.responses.RespFile
import com.dimfcompany.nashprihodadmin.logic.models.responses.RespUser
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderNet
import com.dimfcompany.nashprihodadmin.logic.utils.files.FileManager
import com.dimfcompany.nashprihodadmin.logic.utils.files.MyFileItem
import com.dimfcompany.nashprihodadmin.networking.apis.ApiFiles
import okhttp3.MultipartBody
import javax.inject.Inject

class BaseNetworker @Inject constructor(val base_act: BaseActivity)
{
    companion object
    {
        suspend fun uploadVideo(file_video: MyFileItem, api_files: ApiFiles): ModelFile
        {
            val file = FileManager.getPosterFromVideo(file_video.getUriAsString())
            var preview_file_id: Long? = null

            if (file != null)
            {
                val my_file = MyFileItem.createFromFile(file)
                val part_file = my_file.toMultiPartData()
                preview_file_id = api_files.uploadImage(part_file)
                        .toObjOrThrow(RespFile::class.java)
                        .addParseCheckerForObj({ it.file?.id != null })
                        .file?.id
            }

            val part_file_vide = file_video.toMultiPartData()
            val params = mapOf(Pair("preview_file_id", preview_file_id?.toString()))

            val uploaded_video = api_files.uploadVideo(params, part_file_vide)
                    .toObjOrThrow(RespFile::class.java)
                    .addParseCheckerForObj({ it.file?.id != null })
                    .file!!

            return uploaded_video
        }
    }

    fun makeRegister(
            first_name: String,
            last_name: String, email:
            String, password: String,
            avatar: MyFileItem?,
            action_success: (ModelUser) -> Unit,
            action_error: ((Throwable) -> Unit)? = null)
    {
        val map_params = mutableMapOf<String, String>()
        map_params["first_name"] = first_name
        map_params["last_name"] = last_name
        map_params["email"] = email
        map_params["password"] = password

        var part_avatar: MultipartBody.Part? = null
        part_avatar = avatar?.toMultiPartData("avatar", "image/jpeg")

        BuilderNet<RespUser>()
                .setBaseActivity(base_act)
                .setActionResponseBody(
                    {
                        return@setActionResponseBody base_act.api_auth.register(map_params, part_avatar)
                    })
                .setObjClass(RespUser::class.java)
                .setActionParseChecker(
                    {
                        it.user != null
                    })
                .setActionSuccess(
                    {
                        action_success(it.user!!)
                    })
                .setActionError(
                    {
                        action_error?.invoke(it)
                    })
                .run()
    }

    fun makePassReset(email: String, action_success: () -> Unit, action_error: ((Throwable) -> Unit)? = null)
    {
        BuilderNet<BaseResponse>()
                .setBaseActivity(base_act)
                .setActionResponseBody(
                    {
                        base_act.api_auth.forgotPass(email)
                    })
                .setObjClass(BaseResponse::class.java)
                .setActionSuccess(
                    {
                        action_success()
                    })
                .setActionError(
                    {
                        action_error?.invoke(it)
                    })
                .run()
    }

    fun makeLogin(email: String, password: String, fb_token: String?, action_success: (ModelUser) -> Unit, action_error: ((Throwable) -> Unit)? = null)
    {
        BuilderNet<RespUser>()
                .setBaseActivity(base_act)
                .setActionResponseBody(
                    {
                        base_act.api_auth.login(email, password, fb_token)
                    })
                .setObjClass(RespUser::class.java)
                .setActionParseChecker(
                    {
                        it.user != null
                    })
                .setActionSuccess(
                    {
                        action_success(it.user!!)
                    })
                .setActionError(
                    {
                        action_error?.invoke(it)
                    })
                .run()
    }
}