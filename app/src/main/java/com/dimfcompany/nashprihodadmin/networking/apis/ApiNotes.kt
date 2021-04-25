package com.dimfcompany.nashprihodadmin.networking.apis

import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.getDeviceName
import com.dimfcompany.nashprihodadmin.base.extensions.toInt
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiNotes
{
    @FormUrlEncoded
    @POST(Constants.Urls.INSERT_NOTE)
    suspend fun insertNote(
            @Field("names") names: String,
            @Field("for_health") for_health: Int,
            @Field("user_id") user_id: Long,
            @Field("donation_sum") donation_sum: Double?,
            @Field("donation_id") donation_id: String?,
    ): Response<ResponseBody>

    @GET(Constants.Urls.GET_NOTES)
    suspend fun getNotes(
            @Query("status") status: String? = null,
            @Query("user_id") user_id: Long? = null,
            @Query("search") search: String? = null
    ): Response<ResponseBody>

    @GET(Constants.Urls.GET_NOTE_BY_ID)
    suspend fun getNoteById(@Query("note_id") note_id: Long): Response<ResponseBody>

    @FormUrlEncoded
    @POST(Constants.Urls.CHANGE_NOTE_STATUS)
    suspend fun changeNoteStatus(
            @Field("note_id") note_id: Long,
            @Field("status") status: String,
            @Field("reader_id") reader_id: Long?
    ): Response<ResponseBody>
}

suspend fun ApiNotes.insertNoteMy(note: ModelNote): Response<ResponseBody>
{
    //Todo laterrr remove 1
    val user_id = SharedPrefsManager.pref_current_user.get().value?.id ?: 1
    return insertNote(note.names!!, note.for_health!!.toInt(), user_id, note.donation_sum, note.donation_id)
}