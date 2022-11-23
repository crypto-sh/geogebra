package com.geogebra.ui.material.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.geogebra.R
import com.geogebra.core.base.BaseFragment
import com.geogebra.core.dto.Material
import com.geogebra.core.utils.messageHandler
import com.geogebra.databinding.FragmentMaterialListBinding
import com.geogebra.ui.material.adapter.RvAdapterMaterials
import com.geogebra.ui.material.viewModels.MaterialViewModel
import com.geogebra.ui.material.viewModels.MaterialViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MaterialListFragment : BaseFragment<FragmentMaterialListBinding>() {

    @Inject
    lateinit var factory: MaterialViewModelFactory

    val viewModel : MaterialViewModel by activityViewModels(factoryProducer = { factory })

    private val adapter : RvAdapterMaterials by lazy {
        RvAdapterMaterials { material ->
            navigateDetails(material)
        }
    }

    override fun getResourceLayoutId(): Int = R.layout.fragment_material_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.materialsObservable().observe(this) {
            adapter.submitList(it)
        }
        viewModel.errorObservable().observe(this) {
            it?.messageHandler(requireContext())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialRecyclerView.adapter = adapter
    }

    fun navigateDetails(material : Material) {
        val action = MaterialListFragmentDirections.toDetails(material)
        findNavController().navigate(action)
    }
}