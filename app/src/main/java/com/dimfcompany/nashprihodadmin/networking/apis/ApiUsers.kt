package com.dimfcompany.nashprihodadmin.networking.apis

import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.enums.TypeSort
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiUsers
{
    @GET(Constants.Urls.GET_USERS)
    suspend fun getUsers(
            @Query("search") search: String?,
            @Query("status") status: String?,
            @Query("sort") sort: String?,
    ): Response<ResponseBody>

    @GET(Constants.Urls.GET_USER_BY_ID)
    suspend fun getUserById(
            @Query("user_id") user_id: Long
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST(Constants.Urls.EDIT_USER)
    suspend fun userEdit(
            @Field("user_id") user_id: Long,
            @Field("first_name") first_name: String,
            @Field("last_name") last_name: String,
            @Field("email") email: String,
            @Field("birthday") birthday: String?,
            @Field("name_day") name_day: String?,
            @Field("phone") phone: String?,
            @Field("about_me") about_me: String?,
            @Field("avatar_id") avatar_id: Long?,
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST(Constants.Urls.EDIT_USER)
    suspend fun setUserStatus(
            @Field("user_id") user_id: Long,
            @Field("status") status: String,
    ): Response<ResponseBody>
}

suspend fun ApiUsers.getUsersMy(search: String?, status: TypeUserStatus?, sort: TypeSort?): Response<ResponseBody>
{
    return getUsers(search, status?.getNameForServer(), sort?.getNameForServer())
}

suspend fun ApiUsers.makeUserEdit(user: ModelUser): Response<ResponseBody>
{
    return userEdit(
        user.id!!,
        user.first_name!!,
        user.last_name!!,
        user.email!!,
        user.birthday?.formatToString(DateManager.FORMAT_FOR_SERVER_LARAVEL),
        user.name_day?.formatToString(DateManager.FORMAT_FOR_SERVER_LARAVEL),
        user.phone,
        user.about_me,
        user.avatar?.id
    )
}