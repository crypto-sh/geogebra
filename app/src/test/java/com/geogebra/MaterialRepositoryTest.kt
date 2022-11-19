package com.geogebra

import com.geogebra.core.api.ApiMaterial
import com.geogebra.core.dto.MaterialHits
import com.geogebra.core.repository.MaterialsRepository
import com.geogebra.core.repository.impl.MaterialsRepositoryImpl
import com.geogebra.utils.mockMaterials
import junit.framework.TestCase.*
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException


class MaterialRepositoryTest {

    private val api : ApiMaterial = mock()
    private val repository : MaterialsRepository
    private val expected = MaterialHits(mockMaterials())
    private val exception = IOException("unexpected exception")

    init {
        repository = MaterialsRepositoryImpl(api)
    }

    @Test
    fun materialsListApiCall() = runTest {
        repository.materials()
        verify(api, times(1)).list()
    }

    @Test
    fun materialsCallSuccessCase() = runTest {
        whenever(api.list()).thenReturn(expected)
        val result = repository.materials()
        assertTrue(result.isSuccess)
        assertEquals(expected.hits, result.getOrNull())
    }

    @Test
    fun materialsCallFailure() = runTest {
        whenever(api.list()).thenAnswer {
            throw exception
        }
        val result = repository.materials()
        val msg = result.exceptionOrNull()?.message
        assertEquals(exception.message, msg)
    }

    @Test
    fun materialDetailsTest() = runTest {
        val material = mockMaterials().first()
        val result = repository.materialDetails(material)
        assertTrue(result.isSuccess)
        val details = result.getOrNull()
        assertNotNull(details)
        assertEquals(details!!.size, 5)
    }

}