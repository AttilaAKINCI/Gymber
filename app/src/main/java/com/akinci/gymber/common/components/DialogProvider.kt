package com.akinci.gymber.common.components

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.akinci.gymber.R

object DialogProvider {

    fun createNetworkProblemAlertDialog(
        context: Context,
        positiveAction: (DialogInterface)->Unit
    ) {
        with(context){
            val dialog = AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.alert_title))
                .setMessage(resources.getString(R.string.alert_message))
                .setPositiveButton(resources.getString(R.string.retry), null)
                .create()

            /**
             * Alert dialog dismiss automatically when click both of its buttons (negative/positive)
             * to prevent that usage the code below overrides positive buttons click actions
             * **/
            dialog.setOnShowListener { dialogInterface ->
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    positiveAction.invoke(dialogInterface)
                }
            }
            dialog.show()
        }
    }
}