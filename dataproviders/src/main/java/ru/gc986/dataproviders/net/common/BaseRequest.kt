package ru.gc986.dataproviders.net.common

import android.content.Context
import ru.gc986.dataproviders.R
import ru.gc986.dataproviders.net.Requests

open class BaseRequest(val context: Context, showDebugInfo: Boolean) {

    private val baseNetConstructor: BaseNetConstructor = BaseNetConstructor(showDebugInfo)
    private var mainServer: String? = null

    protected val bnc: Requests
        get() = baseNetConstructor.create(getMainServer(), Requests::class.java)

    fun setMainServer(url: String) {
        mainServer = url
    }

    fun getMainServer(): String {
        return mainServer?:throw Exception(context.getString(R.string.main_server_not_set))
    }

}
