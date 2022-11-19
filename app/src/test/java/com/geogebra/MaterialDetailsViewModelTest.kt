package com.geogebra

import androidx.lifecycle.SavedStateHandle
import com.geogebra.core.dto.KeyValue
import com.geogebra.core.dto.Material
import com.geogebra.core.repository.MaterialsRepository
import com.geogebra.ui.details.viewModel.MaterialDetailsViewModel
import com.geogebra.ui.details.viewModel.MaterialDetailsViewModelImpl
import com.geogebra.ui.details.viewModel.bundle_material_details_key
import com.geogebra.utils.BaseUnitTest
import com.geogebra.utils.mockMaterials
import junit.framework.TestCase.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException


class MaterialDetailsViewModelTest : BaseUnitTest() {

    private val repository: MaterialsRepository = mock()
    private val data: Material = mockMaterials().first()
    private val exception = IOException("here is the exception message")

    lateinit var viewModel: MaterialDetailsViewModel

    @Before
    fun setup() {
        val handler = SavedStateHandle(mapOf(bundle_material_details_key to data))
        viewModel = MaterialDetailsViewModelImpl(handler, repository)
    }

    @Test
    fun materialDetailsCallCheck() = runTest {
        viewModel.loadDetails()
        verify(repository, times(1)).materialDetails(data)
    }

    @Test
    fun materialDetailsResultSuccess() = runTest {
        whenever(repository.materialDetails(data)).thenReturn(Result.success(listOf(KeyValue(), KeyValue())))
        viewModel.loadDetails()
        val value = viewModel.materialDetails().blockingObserver()
        val size = value?.size ?: 0
        assertEquals(2, size)
    }

    @Test
    fun materialDetailsResultFailure() = runTest {
        whenever(repository.materialDetails(data)).thenReturn(Result.failure(exception))
        viewModel.loadDetails()
        val result = viewModel.materialDetails().blockingObserver()
        assertNull(result)
        val error = viewModel.errorObservable().blockingObserver()
        assertNotNull(error)
        assertEquals(exception.message, error?.message)
    }
}