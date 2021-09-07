package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep3Binding
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep2Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class ProjectImprovStep3Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var _binding: FragmentProjectImprovementStep3Binding
    private val binding get() = _binding
    private var data : ProjectImprovementCreateModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep3Binding.inflate(layoutInflater, container, false)
        data = HawkUtils().getTempDataCreatePi()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep3Binding.bind(view)

        binding.apply {


        }
    }
}