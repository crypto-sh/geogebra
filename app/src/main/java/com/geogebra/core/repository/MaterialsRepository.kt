package com.geogebra.core.repository

import com.geogebra.core.dto.KeyValue
import com.geogebra.core.dto.Material


interface MaterialsRepository {

    suspend fun materials() : Result<List<Material>>

    suspend fun materialDetails(material: Material) : Result<List<KeyValue>>
}