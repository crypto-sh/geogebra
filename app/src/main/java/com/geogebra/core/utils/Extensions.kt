package com.geogebra.core.utils

import android.content.Context
import android.os.Build
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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

inline fun <reified T : Fragment?> AppCompatActivity.getForegroundFragment(@IdRes resource : Int): T {
    val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(resource)
    return navHostFragment?.childFragmentManager?.fragments?.first() as T
}

inline fun <reified T : Parcelable> Fragment.getParcelable(key  : String) : T {
    return if (Build.VERSION.SDK_INT >= 33) {
        arguments?.getParcelable(key, T::class.java)
    } else {
        arguments?.getParcelable(key)
    } ?: T::class.java.newInstance()
}

fun AppCompatActivity.findNavController(@IdRes id : Int) : NavController {
    val navHostFragment = supportFragmentManager.findFragmentById(id) as NavHostFragment
    return navHostFragment.navController
}

fun Date?.toShortString(separator : String  = "/"): String {
    return if (this == null)
        ""
    else
        SimpleDateFormat("dd${separator}MM${separator}yyyy", Locale.getDefault()).format(this)
}