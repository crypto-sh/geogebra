package com.geogebra.ui.details.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.geogebra.core.repository.MaterialsRepository

class MaterialDetailsViewModelFactory(
    val repository: MaterialsRepository
) : AbstractSavedStateViewModelFactory() {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        @Suppress("UNCHECKED_CAST")
        return MaterialDetailsViewModelImpl(handle, repository) as T
    }
}