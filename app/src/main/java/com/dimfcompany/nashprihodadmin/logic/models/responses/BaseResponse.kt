package com.dimfcompany.nashprihodadmin.logic.models.responses

import com.dimfcompany.nashprihodadmin.base.MyServerError
import com.dimfcompany.nashprihodadmin.base.enums.TypeResponseStatus
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.utils.StringManager
import com.google.gson.annotations.SerializedName
import java.lang.RuntimeException
import kotlin.reflect.KClass

open class BaseResponse(

        var status: TypeResponseStatus = TypeResponseStatus.FAILED,
        var errors: ArrayList<String>? = null
)
{
    fun isFailed(): Boolean
    {
        return status == TypeResponseStatus.FAILED
    }

    fun getErrorMessage(): String?
    {
        if (this.errors != null && this.errors!!.size > 0)
        {
            return StringManager.listOfStringToSingle(this.errors!!)
        }

        return null
    }

    fun getError(): RuntimeException?
    {
        if (this.status == TypeResponseStatus.SUCCESS)
        {
            return null
        }

        val message = getErrorMessage() ?: "Неизвестная ошибка сервера"
        return MyServerError(message)
    }
}

class BaseResponseWithData<T : Any>()
{
    @SerializedName("data")
    val data: T? = null

    companion object
    {
        inline fun <reified T : Any> getClassMy(): Class<T>
        {
            return T::class.java
        }
    }
}

