package com.geogebra.ui.materials.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.geogebra.core.dto.Material
import com.geogebra.core.repository.MaterialsRepository
import com.geogebra.core.utils.SingleLiveEvent
import kotlinx.coroutines.launch


class MaterialsViewModelImpl(
    private val materialsRepository: MaterialsRepository
) : MaterialsViewModel() {

    private val _errors : SingleLiveEvent<Throwable> = SingleLiveEvent()
    private val _materials : SingleLiveEvent<List<Material>> = SingleLiveEvent()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        materialsRequest()
    }

    override fun materialsRequest() {
        viewModelScope.launch {
            try {
                val list = materialsRepository.materials().getOrThrow()
                println("data result has been received $list")
                _materials.postValue(list)
            } catch (e: Exception) {
                _errors.postValue(e)
            }

        }
    }

    override fun materialsObservable() = _materials

    override fun errorObservable(): LiveData<Throwable> = _errors
}