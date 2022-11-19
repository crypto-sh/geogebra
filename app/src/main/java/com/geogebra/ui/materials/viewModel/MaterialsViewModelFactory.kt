package com.geogebra.ui.materials.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.geogebra.core.repository.MaterialsRepository

class MaterialsViewModelFactory(private val repository: MaterialsRepository) :
    AbstractSavedStateViewModelFactory() {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        @Suppress("UNCHECKED_CAST")
        return MaterialsViewModelImpl(repository) as T
    }
}