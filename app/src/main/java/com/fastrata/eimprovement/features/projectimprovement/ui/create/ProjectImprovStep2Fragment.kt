package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep2Binding
import com.fastrata.eimprovement.utils.DatePickerCustom

class ProjectImprovStep2Fragment : Fragment () {

    private lateinit var _binding: FragmentProjectImprovementStep2Binding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep2Binding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep2Binding.bind(view)

        binding.apply {

            dariStatus1PI.setOnClickListener {
                activity?.let {
                    DatePickerCustom.dialogDatePicker(
                        context = view.context, fragmentManager = it.supportFragmentManager,
                        themeDark = false, minDateIsCurrentDate = true
                    )
                }
            }

            sampaiStatus1PI.setOnClickListener {
                activity?.let {
                    DatePickerCustom.dialogDatePicker(
                        context = view.context, fragmentManager = it.supportFragmentManager,
                        themeDark = false, minDateIsCurrentDate = true
                    )
                }
            }

            rbStatus2.setOnCheckedChangeListener { compoundButton, ischecked ->
                println(ischecked)
                if (ischecked){
                    linearImplement.visibility = View.VISIBLE
                }else{
                    linearImplement.visibility = View.INVISIBLE
                }
            }
        }
    }
}


