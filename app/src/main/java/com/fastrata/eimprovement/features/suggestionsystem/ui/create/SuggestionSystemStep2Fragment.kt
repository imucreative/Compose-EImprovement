package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep2Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.suggestionsystem.data.model.StatusImplementation
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.SnackBarCustom
import javax.inject.Inject

class SuggestionSystemStep2Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemStep2Binding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private lateinit var datePicker: DatePickerCustom

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep2Binding.inflate(inflater, container, false)
        data = HawkUtils().getTempDataCreateSs()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep2Binding.bind(view)

        datePicker = activity?.let {
            DatePickerCustom(
            context = binding.root.context,themeDark = true,
                minDateIsCurrentDate = true,it.supportFragmentManager
            )
        }!!

        initComponent(binding)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initComponent(binding: FragmentSuggestionSystemStep2Binding) {

        binding.apply {
            etFromStatus1.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        binding.etFromStatus1.setText("$dayStr-$monthStr-$year")
                    }
                })
            }
            etToStatus1.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        binding.etToStatus1.setText("$dayStr-$monthStr-$year")
                    }
                })
            }
            etFromStatus2.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        binding.etFromStatus2.setText("$dayStr-$monthStr-$year")
                    }
                })
            }
            etToStatus2.setOnClickListener {
                datePicker.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        binding.etToStatus2.setText("$dayStr-$monthStr-$year")
                    }
                })
            }

            // init false condition
            etFromStatus1.isEnabled = false
            etToStatus1.isEnabled = false
            etFromStatus2.isEnabled = false
            etToStatus2.isEnabled = false

            setLogic(binding)

            if (data?.problem != null){
                getData(binding)
            }

            setData(binding)
        }
    }

    private fun setLogic(binding: FragmentSuggestionSystemStep2Binding) {
        binding.run {
            rbStatus1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    etFromStatus1.isEnabled = true
                    etToStatus1.isEnabled = true

                    etFromStatus2.isEnabled = false
                    etToStatus2.isEnabled = false
                    etFromStatus2.setText("")
                    etToStatus2.setText("")

                }
            }

            rbStatus2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    etFromStatus1.isEnabled = false
                    etToStatus1.isEnabled = false
                    etFromStatus1.setText("")
                    etToStatus1.setText("")

                    etFromStatus2.isEnabled = true
                    etToStatus2.isEnabled = true
                }
            }
        }
    }

    private fun getData(binding: FragmentSuggestionSystemStep2Binding) {
        binding.apply {

            problem.setText(data?.problem.toString())
            suggestion.setText(data?.suggestion.toString())

            if (data?.statusImplementation?.status?.contains("Sudah pernah diimplementasi") == true) {
                rbStatus1.isChecked = true
                rbStatus2.isChecked = false

                etFromStatus1.setText(data?.statusImplementation?.from)
                etToStatus1.setText(data?.statusImplementation?.to)

                etFromStatus2.setText("")
                etToStatus2.setText("")
            } else {
                rbStatus1.isChecked = false
                rbStatus2.isChecked = true

                etFromStatus1.setText("")
                etToStatus1.setText("")

                etFromStatus2.setText(data?.statusImplementation?.from)
                etToStatus2.setText(data?.statusImplementation?.to)
            }

        }
    }

    private fun setData(binding: FragmentSuggestionSystemStep2Binding) {
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object : SuggestionSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    lateinit var tempStatus: String
                    lateinit var tempFrom: String
                    lateinit var tempTo: String

                    if (rbStatus1.isChecked) {
                        tempStatus = rbStatus1.text.toString()
                        tempFrom = etFromStatus1.text.toString()
                        tempTo = etToStatus1.text.toString()
                    } else {
                        tempStatus = rbStatus2.text.toString()
                        tempFrom = etFromStatus2.text.toString()
                        tempTo = etToStatus2.text.toString()
                    }

                    val status = StatusImplementation(
                        status = tempStatus,
                        from = tempFrom,
                        to = tempTo
                    )

                    if (problem.text.isNullOrEmpty()) {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Problem must be fill before next",
                            R.drawable.ic_close, R.color.red_500)
                        problem.requestFocus()
                        stat = false

                    } else if (suggestion.text.isNullOrEmpty()) {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Suggestion must be fill before next",
                            R.drawable.ic_close, R.color.red_500)
                        suggestion.requestFocus()
                        stat = false

                    } else  if (!rbStatus1.isChecked && !rbStatus2.isChecked) {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Status Implementation must be fill before next",
                            R.drawable.ic_close, R.color.red_500
                        )
                        stat = false

                    } else {
                        HawkUtils().setTempDataCreateSs(
                            suggestion = suggestion.text.toString(),
                            problem = problem.text.toString(),
                            statusImplementation = status
                        )
                        stat = true

                    }
                }
                return stat
            }
        })
    }
}
