package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep6Binding
import com.fastrata.eimprovement.utils.Tools
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.cos

class ProjectImprovStep6Fragment : Fragment() {

    private lateinit var _binding: FragmentProjectImprovementStep6Binding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep6Binding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep6Binding.bind(view)

        binding.apply {
            benefit.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                    if (cost.text.toString() == "" || cost.text.toString() == null){
                        nqi.setText(p0.toString())
                    }else{
                        var sums : String? = Tools.sumValues(p0.toString(),cost.text.toString())
                        nqi.setText(sums)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

            cost.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                    if (benefit.text.toString() == "" || benefit.text.toString() == null) {
                        nqi.setText(p0.toString())
                    }else{
                        var sums :String? = Tools.sumValues(p0.toString(),benefit.text.toString())
                        nqi.setText(sums)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
        }
    }
}