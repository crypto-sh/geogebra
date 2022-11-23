package com.geogebra.core.dto

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.Date

@Keep
@Parcelize
data class Material(
    val id: String,
    val title: String? = null,
    val type: String,
    val dateCreated: Date,
    val dateModified: Date,
    val visibility: String,
    val thumbUrl: String,
    val creator: Creator
) : Parcelable


data class MaterialHits(
    val hits: List<Material>
)