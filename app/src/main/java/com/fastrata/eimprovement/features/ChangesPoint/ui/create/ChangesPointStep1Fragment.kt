package com.fastrata.eimprovement.features.changesPoint.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.databinding.FragmentChangesPointStep1Binding
import com.fastrata.eimprovement.di.Injectable
import javax.inject.Inject

class ChangesPointStep1Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var _binding:FragmentChangesPointStep1Binding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointStep1Binding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentChangesPointStep1Binding.bind(view)

        binding.apply {

        }
    }

}