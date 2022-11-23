package com.geogebra.core.dto

import androidx.annotation.Keep
import androidx.annotation.StringRes

@Keep
data class KeyValue(
    @StringRes val header : Int = 0,
    val text : String? = null
)