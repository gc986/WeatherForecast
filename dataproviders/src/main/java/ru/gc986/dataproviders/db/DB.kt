package ru.gc986.dataproviders.db

import android.content.Context
import androidx.room.Room
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.gc986.models.user.User

class DB(context: Context) : DBI {

    private val DB_NAME = "APP_DB.DB"
    val db: AppDB

    init {
        db = Room.databaseBuilder(context, AppDB::class.java, DB_NAME).build()
    }

    override fun insertAllUsers(users: List<User>): Observable<List<User>> = Observable.create<List<User>> {
            db.getUserDao().insertAll(users)
            it.onNext(users)
            it.onComplete()
        }
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())

    override fun deleteAllUsers(): Observable<Int> = Observable.create<Int> {
            db.getUserDao().getAllUsers()
            it.onNext(0)
            it.onComplete()
        }
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getAllUsers(): Observable<List<User>> = Observable.create<List<User>> {
            it.onNext(db.getUserDao().getAllUsers())
            it.onComplete()
        }
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())

    override fun searchUser(userPattern: String): Observable<List<User>> = Observable.create <List<User>> {
            it.onNext(db.getUserDao().searchUser("%$userPattern%"))
            it.onComplete()
        }
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())


}