package com.geogebra.ui.details

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.geogebra.R
import com.geogebra.core.base.BaseActivity
import com.geogebra.core.utils.messageHandler
import com.geogebra.databinding.ActivityDetailsMaterialBinding
import com.geogebra.ui.details.adapter.RvAdapterMaterialDetails
import com.geogebra.ui.details.viewModel.MaterialDetailsViewModel
import com.geogebra.ui.details.viewModel.MaterialDetailsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MaterialDetailsActivity : BaseActivity<ActivityDetailsMaterialBinding, MaterialDetailsViewModel>() {

    private val adapter : RvAdapterMaterialDetails by lazy {
        RvAdapterMaterialDetails()
    }

    @Inject
    lateinit var factory: MaterialDetailsViewModelFactory

    override fun getResourceLayoutId(): Int = R.layout.activity_details_material

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.materialDetails().observe(this) {
            adapter.submitList(it)
        }
        viewModel.errorObservable().observe(this){
            it.messageHandler(this)
        }
        binding.materialDetailsRecyclerView.adapter = adapter
    }

    override fun getFactory(): ViewModelProvider.Factory = factory

    override fun getViewModelClass(): Class<MaterialDetailsViewModel> = MaterialDetailsViewModel::class.java
}