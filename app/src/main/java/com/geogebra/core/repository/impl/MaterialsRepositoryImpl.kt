package com.geogebra.core.repository.impl

import com.geogebra.R
import com.geogebra.core.api.ApiMaterial
import com.geogebra.core.dto.KeyValue
import com.geogebra.core.dto.Material
import com.geogebra.core.repository.MaterialsRepository
import com.geogebra.core.utils.toShortString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MaterialsRepositoryImpl @Inject constructor(private val apiMaterial: ApiMaterial) :
    MaterialsRepository {

    override suspend fun materials(): Result<List<Material>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiMaterial.list().hits
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun materialDetails(material: Material): Result<List<KeyValue>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(
                    listOf(
                        KeyValue(R.string.material_details_header_id, material.id),
                        KeyValue(R.string.material_details_header_title, material.title),
                        KeyValue(R.string.material_details_header_author, material.creator.name),
                        KeyValue(
                            R.string.material_details_header_create_date,
                            material.dateCreated.toShortString()
                        ),
                        KeyValue(
                            R.string.material_details_header_modified_date,
                            material.dateModified.toShortString()
                        )
                    )
                )
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}