package com.dimfcompany.nashprihodadmin.networking

import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.ObjWithMedia
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.extensions.addParseCheckerForObj
import com.dimfcompany.nashprihodadmin.base.extensions.toObjOrThrow
import com.dimfcompany.nashprihodadmin.logic.models.ModelFile
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.models.responses.*
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderNet
import com.dimfcompany.nashprihodadmin.logic.utils.files.FileManager
import com.dimfcompany.nashprihodadmin.logic.utils.files.MyFileItem
import com.dimfcompany.nashprihodadmin.networking.apis.ApiFiles
import com.dimfcompany.nashprihodadmin.networking.apis.makeInsertOrUpdateNotice
import okhttp3.MultipartBody
import javax.inject.Inject

class BaseNetworker @Inject constructor(val base_act: BaseActivity)
{
    companion object
    {
        suspend fun uploadObjsWithMedia(objs: ArrayList<ObjWithMedia>, api_files: ApiFiles): ArrayList<Long>
        {
            val uploaded_file_ids: ArrayList<Long> = arrayListOf()

            for (obj in objs)
            {
                if (obj is ModelFile)
                {
                    obj.id?.let(
                        {
                            uploaded_file_ids.add(it)
                        })
                }
                else if (obj is MyFileItem)
                {
                    if (obj.type == TypeMedia.VIDEO)
                    {
                        val uploaded_file_id = BaseNetworker.uploadVideo(obj, api_files).id
                        if (uploaded_file_id != null)
                        {
                            uploaded_file_ids.add(uploaded_file_id)
                        }
                    }
                    else if (obj.type == TypeMedia.IMAGE)
                    {
                        val uploaded_file_id = BaseNetworker.uploadImage(obj, api_files).id
                        if (uploaded_file_id != null)
                        {
                            uploaded_file_ids.add(uploaded_file_id)
                        }
                    }
                }
            }

            return uploaded_file_ids
        }

        suspend fun uploadImage(my_file_item: MyFileItem, api_files: ApiFiles): ModelFile
        {
            val part_file = my_file_item.toMultiPartData()
            val model_file = api_files.uploadImage(part_file)
                    .toObjOrThrow(RespFile::class.java)
                    .addParseCheckerForObj({ it.file?.id != null })
                    .file!!

            return model_file
        }

        suspend fun uploadVideo(file_video: MyFileItem, api_files: ApiFiles): ModelFile
        {
            val file = FileManager.getPosterFromVideo(file_video.getUriAsString())
            var preview_file_id: Long? = null

            if (file != null)
            {
                val my_file = MyFileItem.createFromFile(file)
                preview_file_id = uploadImage(my_file, api_files).id
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

    fun getNews(action_success: (ArrayList<ModelNews>) -> Unit, action_error: ((Throwable) -> Unit)? = null)
    {
        BuilderNet<RespNews>()
                .setBaseActivity(base_act)
                .setActionResponseBody(
                    {
                        base_act.api_news.getNews()
                    })
                .setObjClass(RespNews::class.java)
                .setActionSuccess(
                    {
                        action_success(it.news ?: arrayListOf())
                    })
                .setActionError(
                    {
                        action_error?.invoke(it)
                    })
                .run()
    }

    fun getNewsById(news_id: Long, action_success: (ModelNews) -> Unit, action_error: ((Throwable) -> Unit)? = null)
    {
        BuilderNet<RespNewsSingle>()
                .setBaseActivity(base_act)
                .setActionResponseBody(
                    {
                        base_act.api_news.getNewsById(news_id)
                    })
                .setObjClass(RespNewsSingle::class.java)
                .setActionParseChecker(
                    {
                        it.news != null
                    })
                .setActionSuccess(
                    {
                        action_success(it.news!!)
                    })
                .setActionError(
                    {
                        action_error?.invoke(it)
                    })
                .run()
    }

    fun makeNoticeUpsert(notice: ModelNotice, action_success: (ModelNotice) -> Unit, action_error: ((Throwable) -> Unit)? = null)
    {
        BuilderNet<RespNoticeSingle>()
                .setBaseActivity(base_act)
                .setActionResponseBody(
                    {
                        base_act.api_news.makeInsertOrUpdateNotice(notice)
                    })
                .setObjClass(RespNoticeSingle::class.java)
                .setActionParseChecker(
                    {
                        it.notice?.id != null
                    })
                .setActionSuccess(
                    {
                        action_success(it.notice!!)
                    })
                .setActionError(
                    {
                        action_error?.invoke(it)
                    })
                .run()
    }


    fun getNotices(action_success: (ArrayList<ModelNotice>) -> Unit, action_error: ((Throwable) -> Unit)? = null)
    {
        BuilderNet<RespNotices>()
                .setBaseActivity(base_act)
                .setActionResponseBody(
                    {
                        base_act.api_news.getNotices()
                    })
                .setObjClass(RespNotices::class.java)
                .setActionSuccess(
                    {
                        action_success(it.notices ?: arrayListOf())
                    })
                .setActionError(
                    {
                        action_error?.invoke(it)
                    })
                .run()
    }

    fun getNoticeById(notice_id: Long, action_success: (ModelNotice) -> Unit, action_error: ((Throwable) -> Unit)? = null)
    {
        BuilderNet<RespNoticeSingle>()
                .setBaseActivity(base_act)
                .setActionResponseBody(
                    {
                        base_act.api_news.getNoticeById(notice_id)
                    })
                .setObjClass(RespNoticeSingle::class.java)
                .setActionParseChecker(
                    {
                        it.notice != null
                    })
                .setActionSuccess(
                    {
                        action_success(it.notice!!)
                    })
                .setActionError(
                    {
                        action_error?.invoke(it)
                    })
                .run()
    }

}