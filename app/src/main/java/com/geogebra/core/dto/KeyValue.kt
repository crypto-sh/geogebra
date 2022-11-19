package com.geogebra.core.dto

import androidx.annotation.StringRes


data class KeyValue(
    @StringRes val header : Int = 0,
    val text : String? = null
)