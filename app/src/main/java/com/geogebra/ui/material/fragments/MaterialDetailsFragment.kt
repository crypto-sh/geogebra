package com.geogebra.ui.material.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.geogebra.R
import com.geogebra.core.base.BaseFragment
import com.geogebra.core.dto.Material
import com.geogebra.core.utils.getParcelable
import com.geogebra.core.utils.messageHandler
import com.geogebra.databinding.FragmentDetailsMaterialBinding
import com.geogebra.ui.material.adapter.RvAdapterMaterialDetails
import com.geogebra.ui.material.viewModels.MaterialViewModel
import com.geogebra.ui.material.viewModels.MaterialViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MaterialDetailsFragment : BaseFragment<FragmentDetailsMaterialBinding>(){

    @Inject
    lateinit var factory: MaterialViewModelFactory

    val viewModel : MaterialViewModel by activityViewModels(factoryProducer = { factory })

    private val adapter : RvAdapterMaterialDetails by lazy {
        RvAdapterMaterialDetails()
    }

    override fun getResourceLayoutId(): Int = R.layout.fragment_details_material

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.materialDetailsObservable().observe(this) {
            adapter.submitList(it)
        }
        viewModel.errorObservable().observe(this){
            it.messageHandler(requireContext())
        }
        val details = getParcelable<Material>("bundle_data_key")
        viewModel.loadDetails(details)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialDetailsRecyclerView.adapter = adapter
    }
}