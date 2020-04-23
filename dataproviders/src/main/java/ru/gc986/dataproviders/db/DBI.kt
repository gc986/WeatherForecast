package ru.gc986.dataproviders.db

import io.reactivex.Observable
import ru.gc986.models.user.User

interface DBI {

    fun insertAllUsers(users: List<User>): Observable<List<User>>

    fun deleteAllUsers(): Observable<Int>

    fun getAllUsers(): Observable<List<User>>

    fun searchUser(userPattern: String): Observable<List<User>>

}