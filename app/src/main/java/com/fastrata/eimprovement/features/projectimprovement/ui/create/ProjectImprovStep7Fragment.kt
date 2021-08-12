package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep7Binding

class ProjectImprovStep7Fragment : Fragment () {

    private lateinit var _binding: FragmentProjectImprovementStep7Binding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProjectImprovementStep7Binding.inflate(layoutInflater,container,false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep7Binding.bind(view)

        binding.apply {

        }

    }
}