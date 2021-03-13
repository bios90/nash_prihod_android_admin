package com.dimfcompany.nashprihodadmin.networking.apis

import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.enums.TypeSort
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiUsers
{
    @GET(Constants.Urls.GET_USERS)
    suspend fun getUsers(
            @Query("search") search: String?,
            @Query("status") status: String?,
            @Query("sort") sort: String?,
    ): Response<ResponseBody>
}

suspend fun ApiUsers.getUsersMy(search: String?, status: TypeUserStatus?, sort: TypeSort?): Response<ResponseBody>
{
    return getUsers(search, status?.getNameForServer(), sort?.getNameForServer())
}