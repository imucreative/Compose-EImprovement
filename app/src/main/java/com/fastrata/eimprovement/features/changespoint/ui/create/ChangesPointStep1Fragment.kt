package com.fastrata.eimprovement.features.changespoint.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentChangesPointStep1Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel

import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateCallback
import com.fastrata.eimprovement.utils.CP_CREATE
import com.fastrata.eimprovement.utils.DatePickerCustom
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.SnackBarCustom
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ChangesPointStep1Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding:FragmentChangesPointStep1Binding? = null
    private val binding get() = _binding!!
    private var data : ChangePointCreateModel? = null
    private var source :String = CP_CREATE
    private lateinit var datePicker: DatePickerCustom

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointStep1Binding.inflate(layoutInflater, container, false)

        data = HawkUtils().getTempDataCreateCP()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentChangesPointStep1Binding.bind(view)

        binding.apply {

            datePicker = activity?.let {
                DatePickerCustom(
                    context = binding.root.context,themeDark = true,
                    minDateIsCurrentDate = true,it.supportFragmentManager
                )
            }!!

            if(data?.saldo == null){
                saldo.setText("0")
            }else{
                saldo.setText(data?.saldo.toString())
            }
            cpNo.setText(data?.cpNo)
            name.setText(data?.name)
            nik.setText(data?.nik)
            branch.setText(data?.branch)
            subBranch.setText("FBPST")
            date.setOnClickListener {
                datePicker.showDialog(object  : DatePickerCustom.Callback{
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10)"0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        date.setText("$dayStr-$monthStr-$year")
                    }
                })
            }
            job.setText(data?.job)
            desc.setText(data?.description)
            Timber.w("##### $data")
            setData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData() {
        (activity as ChangesPointCreateWizard).setcpCreateCallback(object  :
            ChangesPointCreateCallback{
            override fun OnDataPass(): Boolean {
                var stat : Boolean
                _binding.apply {
                    when{
                        this!!.desc.text.isNullOrEmpty()->{
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Title must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            desc.requestFocus()
                            stat = false
                        }
                        this!!.date.text.isNullOrEmpty()->{
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Title must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            date.requestFocus()
                            stat = false
                        }
                        else -> {
                            HawkUtils().setTempDataCreateCp(

                                cpno = cpNo.text.toString(),
                                nama = name.text.toString(),
                                nik = nik.text.toString(),
                                branch = branch.text.toString(),
                                departement = department.text.toString(),
                                jabatan = job.text.toString(),
                                date = date.text.toString(),
                                keterangan = desc.text.toString()
                            )
                            stat = true
                        }
                    }
                }
                return stat
            }
            })
    }

}