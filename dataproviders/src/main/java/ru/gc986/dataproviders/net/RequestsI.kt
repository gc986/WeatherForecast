package ru.gc986.dataproviders.net

import io.reactivex.Observable
import ru.gc986.models.user.User

interface RequestsI {

    fun getUserList(url: String): Observable<List<User>>

}