package com.geogebra.core.api

import com.geogebra.core.dto.MaterialHits
import retrofit2.http.GET


interface ApiMaterial {

    @GET("v1.0/search/applets?filter=author:geogebrateam")
    suspend fun list() : MaterialHits



}