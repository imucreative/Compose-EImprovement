package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep2Binding
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.HawkUtils

class ProjectImprovStep2Fragment : Fragment () {

    private lateinit var _binding: FragmentProjectImprovementStep2Binding
    private val binding get() = _binding
    private lateinit var datePicker: DatePickerCustom
    private var data : ProjectImprovementCreateModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep2Binding.inflate(layoutInflater, container, false)
        data = HawkUtils().getTempDataCreatePi()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep2Binding.bind(view)

        datePicker = activity?.let {
            DatePickerCustom(
                context = binding.root.context,themeDark = true,
                minDateIsCurrentDate = true,it.supportFragmentManager
            )
        }!!

        binding.apply {

            dariStatus1PI.setOnClickListener {datePicker.showDialog(object : DatePickerCustom.Callback {
                override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                    val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                    val mon = month + 1
                    val monthStr = if (mon < 10) "0$mon" else "$mon"
                    binding.dariStatus1PI.setText("$dayStr-$monthStr-$year")
                }
            })
            }

            sampaiStatus1PI.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.sampaiStatus1PI.setText("$dayStr-$monthStr-$year")
                        }
                    })
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

            indenDari.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.indenDari.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            indenSampai.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.indenSampai.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            analisaDari.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.analisaDari.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            analisaSampai.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.analisaSampai.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            analisaAkarDari.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.analisaAkarDari.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            analisaAkarSampai.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.analisaAkarSampai.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            menyusunDari.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.menyusunDari.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            menyusunSampai.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.menyusunSampai.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            implementasiDari.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.implementasiDari.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            implementasiSampai.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.implementasiSampai.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            analisaperiksadari.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.analisaperiksadari.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            analisaperiksasampai.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            binding.analisaperiksasampai.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

        }
    }
}


