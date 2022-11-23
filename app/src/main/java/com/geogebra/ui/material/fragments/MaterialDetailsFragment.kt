package com.geogebra.ui.material.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.geogebra.R
import com.geogebra.core.base.BaseFragment
import com.geogebra.core.utils.messageHandler
import com.geogebra.databinding.FragmentDetailsMaterialBinding
import com.geogebra.ui.material.adapter.RvAdapterMaterialDetails
import com.geogebra.ui.material.viewModels.MaterialViewModel
import com.geogebra.ui.material.viewModels.MaterialViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MaterialDetailsFragment : BaseFragment<FragmentDetailsMaterialBinding>(){

    val args : MaterialDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var factory: MaterialViewModelFactory

    private val viewModel : MaterialViewModel by activityViewModels(factoryProducer = { factory })

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

        viewModel.loadDetails(args.bundleDataKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialDetailsRecyclerView.adapter = adapter
    }
}