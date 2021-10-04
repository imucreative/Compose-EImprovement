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
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateItemModel
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateCallback
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class ChangesPointStep1Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentChangesPointStep1Binding? = null
    private val binding get() = _binding!!
    private var data : ChangePointCreateItemModel? = null
    private var source :String = CP_CREATE
    private var action: String? = ""
    private var cpNo: String? = ""
    private lateinit var datePicker: DatePickerCustom

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointStep1Binding.inflate(inflater, container, false)

        cpNo = arguments?.getString(CP_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (cpNo == "") CP_CREATE else CP_DETAIL_DATA

        data = HawkUtils().getTempDataCreateCP(source)

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
                saldo.text = "0"
            }else{
                saldo.text = data?.saldo.toString()
            }

            cpNo.setText(data?.cpNo)
            name.setText(data?.name)
            nik.setText(data?.nik)
            branch.setText(data?.branch)
            subBranch.setText(data?.subBranch)
            department.setText(data?.department)
            position.setText(data?.position)

            date.setText(data?.date)
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
            desc.setText(data?.description)

            setData()

            if(action == APPROVE){
                disableForm()
            }
        }
    }

    private fun disableForm() {
        binding.apply {
            desc.isEnabled = false
            date.isEnabled = false
            //edtLayoutTitle.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey_10))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData() {
        (activity as ChangesPointCreateWizard).setCpCreateCallback(object  : ChangesPointCreateCallback{
            override fun onDataPass(): Boolean {
                var stat : Boolean
                binding.apply {
                    when{
                        date.text.isNullOrEmpty()->{
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.date_empty),
                                R.drawable.ic_close, R.color.red_500)
                            date.requestFocus()
                            stat = false
                        }
                        desc.text.isNullOrEmpty()->{
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.desc_empty),
                                R.drawable.ic_close, R.color.red_500)
                            desc.requestFocus()
                            stat = false
                        }
                        else -> {
                            HawkUtils().setTempDataCreateCp(
                                cpNo = cpNo.text.toString(),
                                name = name.text.toString(),
                                nik = nik.text.toString(),
                                branch = branch.text.toString(),
                                departement = department.text.toString(),
                                position = position.text.toString(),
                                date = date.text.toString(),
                                keterangan = desc.text.toString(),
                                id = data?.id,
                                subBranch = data?.subBranch,
                                saldo = data?.saldo,
                                rewardData = data?.reward,
                                source = source
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