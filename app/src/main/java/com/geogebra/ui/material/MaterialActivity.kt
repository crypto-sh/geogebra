package com.geogebra.ui.material

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.geogebra.R
import com.geogebra.core.base.BaseActivity
import com.geogebra.core.utils.findNavController
import com.geogebra.databinding.ActivityMaterialBinding
import com.geogebra.ui.material.viewModels.MaterialViewModel
import com.geogebra.ui.material.viewModels.MaterialViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MaterialActivity : BaseActivity<ActivityMaterialBinding, MaterialViewModel>() {

    @Inject
    lateinit var factory: MaterialViewModelFactory

    override fun getFactory(): ViewModelProvider.Factory = factory

    override fun getResourceLayoutId(): Int = R.layout.activity_material

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.material_nav_host)
        val appbar = AppBarConfiguration.Builder(navController.graph).build()
        setupActionBarWithNavController(navController, appbar)
    }

    override fun getViewModelClass(): Class<MaterialViewModel> = MaterialViewModel::class.java
}