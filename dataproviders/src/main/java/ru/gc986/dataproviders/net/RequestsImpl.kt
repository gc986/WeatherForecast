package ru.gc986.dataproviders.net

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.gc986.dataproviders.net.common.BaseRequest
import ru.gc986.models.user.User

class RequestsImpl(context: Context, showDebugInfo: Boolean) : BaseRequest(context, showDebugInfo),
    RequestsI {

    override fun getUserList(url: String): Observable<List<User>> =
        bnc.getUserList(url)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()

}