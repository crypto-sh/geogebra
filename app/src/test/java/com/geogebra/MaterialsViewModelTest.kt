package com.geogebra

import com.geogebra.core.dto.KeyValue
import com.geogebra.core.dto.Material
import com.geogebra.core.repository.MaterialsRepository
import com.geogebra.ui.material.viewModels.MaterialViewModel
import com.geogebra.ui.material.viewModels.MaterialViewModelImpl
import com.geogebra.utils.BaseUnitTest
import com.geogebra.utils.mockMaterials
import junit.framework.TestCase.*
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.*
import java.io.IOException


class MaterialsViewModelTest : BaseUnitTest() {

    private val material : Material = mock()

    private val repository : MaterialsRepository = mock()

    private val viewModel : MaterialViewModel

    private val exception = IOException("here is the exception message")

    init {
        viewModel = MaterialViewModelImpl(repository)
    }

    @Test
    fun materialsRequestCalls() = runTest {
        viewModel.materialsRequest()
        verify(repository, times(1)).materials()
    }

    @Test
    fun materialsResultSuccess() = runTest {
        whenever(repository.materials()).thenReturn(Result.success(mockMaterials()))
        viewModel.materialsRequest()
        val value = viewModel.materialsObservable().blockingObserver()
        val size = value?.size ?: 0
        assertEquals(2, size)
    }

    @Test
    fun materialsResultFailure() = runTest {
        whenever(repository.materials()).thenReturn(Result.failure(exception))
        viewModel.materialsRequest()
        val result = viewModel.materialsObservable().blockingObserver()
        assertNull(result)
        val error = viewModel.errorObservable().blockingObserver()
        assertNotNull(error)
        assertEquals(exception.message, error?.message)
    }

    @Test
    fun materialDetailsCallCheck() = runTest {
        viewModel.loadDetails(material)
        verify(repository, times(1)).materialDetails(any())
    }

    @Test
    fun materialDetailsResultSuccess() = runTest {
        whenever(repository.materialDetails(any())).thenReturn(Result.success(listOf(KeyValue(), KeyValue())))
        viewModel.loadDetails(material)
        val value = viewModel.materialDetailsObservable().blockingObserver()
        val size = value?.size ?: 0
        assertEquals(2, size)
    }

    @Test
    fun materialDetailsResultFailure() = runTest {
        whenever(repository.materialDetails(any())).thenReturn(Result.failure(exception))
        viewModel.loadDetails(material)
        val result = viewModel.materialDetailsObservable().blockingObserver()
        assertNull(result)
        val error = viewModel.errorObservable().blockingObserver()
        assertNotNull(error)
        assertEquals(exception.message, error?.message)
    }
}