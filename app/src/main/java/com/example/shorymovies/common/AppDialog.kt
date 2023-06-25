package com.example.shorymovies.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import com.example.shorymovies.R


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Common Alert popup Util class to help us show alert conveniently from anywhere
 *
 **/
class AppDialog {
    companion object {
        fun showMessageDialog(
            context: Context,
            msg: String,
            posClickListener: DialogInterface.OnClickListener? = null
        ) {
            val alertDialog = AlertDialog.Builder(context)

            val builder = alertDialog.apply {
                setMessage(msg)
                setPositiveButton(context.getString(R.string.ok), posClickListener)

            }.create()

            builder.show()

            builder.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.WHITE)
            builder.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.WHITE)
        }


        /**
         * Show Dialog with Pos/Neg buttons
         */
        fun showAlertDialog(
            context: Context,
            title: String?,
            message: String?,
            posClickListener: DialogInterface.OnClickListener? = null,
            negClickListener: DialogInterface.OnClickListener? = null
        ) {
            val alertDialog = AlertDialog.Builder(context).create()
            if (title != null)
                alertDialog.setTitle(title)
            alertDialog.setMessage(message)
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                context.getString(R.string.ok),
                posClickListener
            )
            if (negClickListener != null) {
                val cancelText = context.getString(R.string.cancel)
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, cancelText, negClickListener)
            }
            alertDialog.show()

            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.WHITE)
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.WHITE)
        }

    }
}