package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep2Binding
import com.fastrata.eimprovement.utils.DatePickerCustom

class SuggestionSystemStep2Fragment : Fragment() {

    private lateinit var _binding: FragmentSuggestionSystemStep2Binding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep2Binding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep2Binding.bind(view)

        binding.apply {
            dariStatus1.setOnClickListener {
                activity?.let {
                    DatePickerCustom.dialogDatePicker(
                        context = view.context, fragmentManager = it.supportFragmentManager,
                        themeDark = false, minDateIsCurrentDate = true
                    )
                }
            }
            sampaiStatus1.setOnClickListener {
                activity?.let {
                    DatePickerCustom.dialogDatePicker(
                        context = view.context, fragmentManager = it.supportFragmentManager,
                        themeDark = false, minDateIsCurrentDate = true
                    )
                }
            }
            dariStatus2.setOnClickListener {
                activity?.let {
                    DatePickerCustom.dialogDatePicker(
                        context = view.context, fragmentManager = it.supportFragmentManager,
                        themeDark = false, minDateIsCurrentDate = true
                    )
                }
            }
            sampaiStatus2.setOnClickListener {
                activity?.let {
                    DatePickerCustom.dialogDatePicker(
                        context = view.context, fragmentManager = it.supportFragmentManager,
                        themeDark = false, minDateIsCurrentDate = true
                    )
                }
            }

            rbStatus1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    dariStatus1.isEnabled = true
                    sampaiStatus1.isEnabled = true

                    dariStatus2.isEnabled = false
                    sampaiStatus2.isEnabled = false
                }
            }

            rbStatus2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    dariStatus1.isEnabled = false
                    sampaiStatus1.isEnabled = false

                    dariStatus2.isEnabled = true
                    sampaiStatus2.isEnabled = true
                }
            }

        }
    }
}