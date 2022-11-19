package com.geogebra.ui.materials.viewModel

import androidx.lifecycle.LiveData
import com.geogebra.core.base.BaseViewModel
import com.geogebra.core.dto.Material

abstract class MaterialsViewModel : BaseViewModel() {

    abstract fun materialsRequest()

    abstract fun materialsObservable() : LiveData<List<Material>>

    abstract fun errorObservable() : LiveData<Throwable>
}
