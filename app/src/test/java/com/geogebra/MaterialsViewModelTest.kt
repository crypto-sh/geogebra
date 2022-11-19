package com.geogebra

import com.geogebra.core.repository.MaterialsRepository
import com.geogebra.ui.materials.viewModel.MaterialsViewModel
import com.geogebra.ui.materials.viewModel.MaterialsViewModelImpl
import com.geogebra.utils.BaseUnitTest
import com.geogebra.utils.mockMaterials
import junit.framework.TestCase.*
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException


class MaterialsViewModelTest : BaseUnitTest() {

    private val repository : MaterialsRepository = mock()

    private val viewModel : MaterialsViewModel

    private val exception = IOException("here is the exception message")

    init {
        viewModel = MaterialsViewModelImpl(repository)
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
}