package com.example.shorymovies.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.example.shorymovies.R


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Common Alert popup Util class to help us show alert conveniently from anywhere
 *
 **/
object DialogManager {

    /**
     * show normal alert dialog with message and ok button
     */
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


    /**
     * Show dialog with Edit option to user
     */
    fun showAlertWithEditField(
        context: Context,
        msg: String,
        pos: String,
        neg: String,
        onClick: (String) -> Unit
    ) {

        /** creating custom editable field for movie search */
        val layoutInflater = LayoutInflater.from(context)
        val editLayout: View = layoutInflater.inflate(R.layout.layout_edittext, null)

        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setMessage(msg)

        alertDialog.setView(editLayout)
        val edittext = editLayout.findViewById(R.id.editText) as EditText

        val builder = alertDialog.apply {
            setMessage(msg)
            setPositiveButton(pos) { dialog, _ ->
                edittext.text.toString().let {

                    // don't close dialog if empty text
                    if (it.isNotEmpty()) {
                        onClick(edittext.text.toString())
                        dialog.dismiss()
                    }
                }

            }

            setNegativeButton(neg) { dialog, _ ->
                dialog.dismiss()
            }

        }.create()

        builder.show()

        builder.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.WHITE)
        builder.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.WHITE)

    }
}


