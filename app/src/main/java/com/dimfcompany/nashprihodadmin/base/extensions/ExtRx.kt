package com.dimfcompany.nashprihodadmin.base.extensions

import com.dimfcompany.nashprihodadmin.base.MyUnknownError
import com.dimfcompany.nashprihodadmin.base.ParsingError
import com.dimfcompany.nashprihodadmin.logic.models.responses.BaseResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import okhttp3.ResponseBody
import retrofit2.Response

fun Disposable.disposeBy(cd: CompositeDisposable)
{
    cd.add(this)
}

fun Completable.mainThreaded(): Completable
{
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.mainThreaded(): Observable<T>
{
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.mainThreaded(): Single<T>
{
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}


fun BehaviorSubject<Optional<String>>.acceptIfNotMatches(opt_str: Optional<String>)
{
    val current_br_text = this.value?.value
    val accepted_text = opt_str.value

    if (current_br_text == null && accepted_text == null)
    {
        return
    }

    if (accepted_text.equals(current_br_text))
    {
        return
    }

    this.onNext(opt_str)
}

fun <T> Response<ResponseBody>.toObjOrThrow(clazz: Class<T>): T
{
    val response_as_str = this.getBodyAsStr()

    if (response_as_str == null)
    {
        throw MyUnknownError()
    }

    val base_response = response_as_str.toObjOrNullGson(BaseResponse::class.java)

    if (base_response == null)
    {
        throw ParsingError()
    }

    if (base_response.isFailed())
    {
        val error = base_response.getError()
        if (error != null)
        {
            throw error
        }
    }

    val obj = response_as_str.toObjOrNullGson(clazz)

    if (obj == null)
    {
        throw ParsingError()
    }

    return obj
}

fun <T> T.addParseCheckerForObj(action: (T) -> Boolean): T
{
    if (!action(this))
    {
        throw ParsingError()
    }

    return this
}
