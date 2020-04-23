package ru.gc986.dialogs

import android.R
import android.app.AlertDialog
import android.content.Context

class Dialogs {

    fun showSimpleQuest(
        context: Context,
        title: String,
        description: String? = null,
        isCancelable: Boolean,
        answer: (Boolean) -> Unit
    ) {
        val ad: AlertDialog.Builder = AlertDialog.Builder(context)
        ad.setTitle(title)

        description?.let { ad.setMessage(it) }

        ad.setPositiveButton(context.resources.getText(R.string.ok)) { dialog, arg1 ->
            answer(true)
        }
        ad.setNegativeButton(context.resources.getText(R.string.cancel)) { dialog, arg1 ->
            answer(false)
        }
        ad.setCancelable(isCancelable)
        ad.setOnCancelListener {
            answer(false)
        }
        ad.show()
    }

}