package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep2Binding
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep2Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.SnackBarCustom
import timber.log.Timber
import javax.inject.Inject

class ProjectImprovStep2Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep2Binding? = null
    private val binding get() = _binding!!
    private lateinit var datePicker: DatePickerCustom
    private var data : ProjectImprovementCreateModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep2Binding.inflate(inflater, container, false)
        data = HawkUtils().getTempDataCreatePi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep2Binding.bind(view)

        datePicker = activity?.let {
            DatePickerCustom(
                context = binding.root.context,themeDark = true,
                minDateIsCurrentDate = true, it.supportFragmentManager
            )
        }!!

        setLogic()
        initComponent()
        getData()
        setData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLogic() {
        binding.run {
            etFromStatus1.isEnabled = false
            etToStatus1.isEnabled = false
            linearLayoutAkan.visibility = View.GONE
            rbStatus1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    etFromStatus1.isEnabled = true
                    etToStatus1.isEnabled = true

                    linearLayoutAkan.visibility = View.GONE
                }
            }

            rbStatus2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    etFromStatus1.isEnabled = false
                    etToStatus1.isEnabled = false
                    etFromStatus1.setText("")
                    etToStatus1.setText("")

                    linearLayoutAkan.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initComponent() {
        binding.apply {

            etFromStatus1.setOnClickListener {datePicker.showDialog(object : DatePickerCustom.Callback {
                override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                    val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                    val mon = month + 1
                    val monthStr = if (mon < 10) "0$mon" else "$mon"
                    etFromStatus1.setText("$dayStr-$monthStr-$year")
                }
            })
            }

            etToStatus1.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToStatus1.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etFromIdentifikasi.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromIdentifikasi.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etToIdentifikasi.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToIdentifikasi.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etFromAnalisaData.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromAnalisaData.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etToAnalisaData.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToAnalisaData.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etFromAnalisaAkarMasalah.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromAnalisaAkarMasalah.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etToAnalisaAkarMasalah.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToAnalisaAkarMasalah.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etFromMenyusunRencanaPenanggulanganMasalah.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromMenyusunRencanaPenanggulanganMasalah.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etToMenyusunRencanaPenanggulanganMasalah.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToMenyusunRencanaPenanggulanganMasalah.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etFromImplementasiRencanaPerbaikan.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromImplementasiRencanaPerbaikan.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etToImplementasiRencanaPerbaikan.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToImplementasiRencanaPerbaikan.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etFromAnalisaPeriksaDanEvaluasi.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromAnalisaPeriksaDanEvaluasi.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }

            etToAnalisaPeriksaDanEvaluasi.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToAnalisaPeriksaDanEvaluasi.setText("$dayStr-$monthStr-$year")
                        }
                    })
                }
            }
        }
    }

    private fun getData() {
        binding.apply {
            if (data?.statusImplementation?.akan == null) {
                rbStatus1.isChecked = true
                rbStatus2.isChecked = false

                etFromStatus1.setText(data?.statusImplementation?.sudah?.from)
                etToStatus1.setText(data?.statusImplementation?.sudah?.to)

                linearLayoutAkan.visibility = View.GONE
            } else {
                linearLayoutAkan.visibility = View.VISIBLE

                rbStatus1.isChecked = false
                rbStatus2.isChecked = true

                etFromStatus1.setText("")
                etToStatus1.setText("")

                etFromIdentifikasi.setText(data?.statusImplementation?.akan?.identifikasiMasalah?.from)
                etToIdentifikasi.setText(data?.statusImplementation?.akan?.identifikasiMasalah?.to)

                etFromAnalisaData.setText(data?.statusImplementation?.akan?.analisaData?.from)
                etToAnalisaData.setText(data?.statusImplementation?.akan?.analisaData?.to)

                etFromAnalisaAkarMasalah.setText(data?.statusImplementation?.akan?.analisaAkarMasalah?.from)
                etToAnalisaAkarMasalah.setText(data?.statusImplementation?.akan?.analisaAkarMasalah?.to)

                etFromMenyusunRencanaPenanggulanganMasalah.setText(data?.statusImplementation?.akan?.menyusunRencana?.from)
                etToMenyusunRencanaPenanggulanganMasalah.setText(data?.statusImplementation?.akan?.menyusunRencana?.to)

                etFromImplementasiRencanaPerbaikan.setText(data?.statusImplementation?.akan?.implementasiRencana?.from)
                etToImplementasiRencanaPerbaikan.setText(data?.statusImplementation?.akan?.implementasiRencana?.to)

                etFromAnalisaPeriksaDanEvaluasi.setText(data?.statusImplementation?.akan?.analisPeriksaEvaluasi?.from)
                etToAnalisaPeriksaDanEvaluasi.setText(data?.statusImplementation?.akan?.analisPeriksaEvaluasi?.to)
            }

        }
    }

    private fun setData() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object :
            ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    var sudah: Sudah? = null
                    var akan: Akan? = null

                    if (rbStatus1.isChecked) {
                        sudah = Sudah(
                            from = etFromStatus1.text.toString(),
                            to = etToStatus1.text.toString()
                        )
                    } else {
                        akan = Akan(
                            identifikasiMasalah = IdentifikasiMasalah(
                                from = etFromIdentifikasi.text.toString(),
                                to = etToIdentifikasi.text.toString()
                            ),
                            analisaData = AnalisaData(
                                from = etFromAnalisaData.text.toString(),
                                to = etToAnalisaData.text.toString()
                            ),
                            analisaAkarMasalah = AnalisaAkarMasalah(
                                from = etFromAnalisaAkarMasalah.text.toString(),
                                to = etToAnalisaAkarMasalah.text.toString()
                            ),
                            menyusunRencana = MenyusunRencana(
                                from = etFromMenyusunRencanaPenanggulanganMasalah.text.toString(),
                                to = etToMenyusunRencanaPenanggulanganMasalah.text.toString()
                            ),
                            implementasiRencana = ImplementasiRencana(
                                from = etFromImplementasiRencanaPerbaikan.text.toString(),
                                to = etToImplementasiRencanaPerbaikan.text.toString()
                            ),
                            analisPeriksaEvaluasi = AnalisPeriksaEvaluasi(
                                from = etFromAnalisaPeriksaDanEvaluasi.text.toString(),
                                to = etToAnalisaPeriksaDanEvaluasi.text.toString()
                            )

                        )
                    }

                    val statusImplementation = StatusImplementationPi(
                        sudah = sudah,
                        akan = akan
                    )

                    Timber.e("# $statusImplementation")

                    if (!rbStatus1.isChecked && !rbStatus2.isChecked) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Status PI must be fill before next",
                            R.drawable.ic_close, R.color.red_500
                        )
                        stat = false

                    } else {
                        HawkUtils().setTempDataCreatePi(
                            statusImplementationpi = statusImplementation
                        )
                        stat = true

                    }
                }
                return stat
            }
        })
    }
}


