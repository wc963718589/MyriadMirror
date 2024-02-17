package com.example.myriadmirror.util

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var toast: Toast? = null

    fun init(context: Context) {
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    }

    fun show(text: String) {
        toast?.setText(text)
        toast?.show()
    }
}