package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep2Binding

class ProjectImprovStep2Fragment : Fragment () {

    private lateinit var _binding: FragmentProjectImprovementStep2Binding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProjectImprovementStep2Binding.inflate(layoutInflater,container,false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep2Binding.bind(view)

        binding.apply {
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