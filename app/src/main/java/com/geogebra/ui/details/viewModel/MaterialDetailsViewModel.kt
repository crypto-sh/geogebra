package com.geogebra.ui.details.viewModel

import androidx.lifecycle.LiveData
import com.geogebra.core.base.BaseViewModel
import com.geogebra.core.dto.KeyValue

abstract class MaterialDetailsViewModel : BaseViewModel() {

    abstract fun loadDetails()

    abstract fun materialDetails() : LiveData<List<KeyValue>>

    abstract fun errorObservable() : LiveData<Throwable>
}