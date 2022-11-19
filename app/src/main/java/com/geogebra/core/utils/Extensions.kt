package com.geogebra.core.utils

import android.content.Context
import android.widget.Toast
import com.geogebra.R
import java.text.SimpleDateFormat
import java.util.*

fun Throwable?.messageHandler(context: Context) {
    var msg = this?.message ?: ""
    if (msg.isEmpty()){
        msg = context.getString(R.string.general_error)
    }
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}


fun Date?.toShortString(separator : String  = "/"): String {
    return if (this == null)
        ""
    else
        SimpleDateFormat("dd${separator}MM${separator}yyyy", Locale.getDefault()).format(this)
}