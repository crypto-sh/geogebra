package com.geogebra.core.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Creator(
    val id : Long,
    val name : String,
    val profile : String
) : Parcelable
