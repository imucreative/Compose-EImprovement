package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep1Binding

class SuggestionSystemStep1Fragment: Fragment() {

    private lateinit var _binding:FragmentSuggestionSystemStep1Binding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep1Binding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep1Binding.bind(view)

        binding.apply {
            chkbxOther.setOnCheckedChangeListener { buttonView, isChecked ->
                println(isChecked)

                if (isChecked) {
                    etOther.visibility = View.VISIBLE
                } else {
                    etOther.visibility = View.INVISIBLE
                }
            }

        }
    }

}