package com.geogebra.ui.materials

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.geogebra.R
import com.geogebra.core.base.BaseActivity
import com.geogebra.core.utils.messageHandler
import com.geogebra.databinding.ActivityMaterialsBinding
import com.geogebra.ui.details.MaterialDetailsActivity
import com.geogebra.ui.details.viewModel.bundle_material_details_key
import com.geogebra.ui.materials.adapter.RvAdapterMaterials
import com.geogebra.ui.materials.viewModel.MaterialsViewModel
import com.geogebra.ui.materials.viewModel.MaterialsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MaterialsActivity : BaseActivity<ActivityMaterialsBinding, MaterialsViewModel>() {

    @Inject
    lateinit var factory: MaterialsViewModelFactory

    private val adapter : RvAdapterMaterials by lazy {
        RvAdapterMaterials { material ->
            startActivity(Intent(this, MaterialDetailsActivity::class.java).apply {
                putExtra(bundle_material_details_key, material)
            })
        }
    }

    override fun getResourceLayoutId(): Int = R.layout.activity_materials

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.materialsObservable().observe(this) {
            adapter.submitList(it)
        }
        viewModel.errorObservable().observe(this) {
            it?.messageHandler(this)
        }
        binding.materialRecyclerView.adapter = adapter
    }

    override fun getFactory(): ViewModelProvider.Factory = factory

    override fun getViewModelClass(): Class<MaterialsViewModel> = MaterialsViewModel::class.java
}