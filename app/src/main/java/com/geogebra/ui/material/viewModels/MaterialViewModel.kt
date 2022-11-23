package com.geogebra.ui.material.viewModels

import androidx.lifecycle.*
import com.geogebra.core.base.BaseViewModel
import com.geogebra.core.dto.KeyValue
import com.geogebra.core.dto.Material
import com.geogebra.core.repository.MaterialsRepository
import com.geogebra.core.utils.SingleLiveEvent
import kotlinx.coroutines.launch


abstract class MaterialViewModel : BaseViewModel() {

    abstract fun loadDetails(material: Material)

    abstract fun materialDetailsObservable() : LiveData<List<KeyValue>>

    abstract fun materialsObservable() : LiveData<List<Material>>

    abstract fun errorObservable() : LiveData<Throwable>

}

class MaterialViewModelImpl(
    private val materialsRepository: MaterialsRepository
) : MaterialViewModel() {

    private val _errors : SingleLiveEvent<Throwable> = SingleLiveEvent()
    private val _details: MutableLiveData<List<KeyValue>> = MutableLiveData()
    private val _materials : SingleLiveEvent<List<Material>> = SingleLiveEvent()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        materialsRequest()
    }

    override fun loadDetails(material: Material) {
        viewModelScope.launch {
            try {

                val details = materialsRepository.materialDetails(material).getOrThrow()
                _details.postValue(details)
            } catch (e: Exception) {
                _errors.postValue(e)
            }
        }
    }

    private fun materialsRequest() {
        viewModelScope.launch {
            try {
                val list = materialsRepository.materials().getOrThrow()
                _materials.postValue(list)
            } catch (e: Exception) {
                _errors.postValue(e)
            }

        }
    }

    override fun materialsObservable() = _materials

    override fun materialDetailsObservable() = _details

    override fun errorObservable(): LiveData<Throwable> = _errors
}


class MaterialViewModelFactory(
    private val materialsRepository: MaterialsRepository
) : AbstractSavedStateViewModelFactory() {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        @Suppress("UNCHECKED_CAST")
        return MaterialViewModelImpl(materialsRepository) as T
    }
}