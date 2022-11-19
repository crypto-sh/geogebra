package com.geogebra.ui.details.viewModel

import androidx.lifecycle.*
import com.geogebra.core.dto.KeyValue
import com.geogebra.core.dto.Material
import com.geogebra.core.repository.MaterialsRepository
import com.geogebra.core.utils.SingleLiveEvent
import kotlinx.coroutines.launch


const val bundle_material_details_key = "bundle_material_details_key"

class MaterialDetailsViewModelImpl(
    handle: SavedStateHandle,
    val repository: MaterialsRepository
) : MaterialDetailsViewModel() {

    val material = handle.get<Material>(bundle_material_details_key)

    private val _details: MutableLiveData<List<KeyValue>> = MutableLiveData()
    private val _error: SingleLiveEvent<Throwable> = SingleLiveEvent()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        loadDetails()
    }

    override fun loadDetails() {
        viewModelScope.launch {
            try {
                if (material == null)
                    throw NullPointerException("the material can not be null")

                val details = repository.materialDetails(material).getOrThrow()
                _details.postValue(details)
            } catch (e: Exception) {
                _error.postValue(e)
            }
        }
    }

    override fun errorObservable(): LiveData<Throwable> = _error

    override fun materialDetails(): LiveData<List<KeyValue>> = _details
}