package com.geogebra.core.dto

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Creator(
    val id : Long,
    val name : String,
    val profile : String
) : Parcelable
