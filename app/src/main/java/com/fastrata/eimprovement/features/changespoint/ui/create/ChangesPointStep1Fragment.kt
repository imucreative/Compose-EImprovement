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
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.Tools.textLimitReplaceToDots
import javax.inject.Inject

class ChangesPointStep1Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentChangesPointStep1Binding? = null
    private val binding get() = _binding!!
    private var data : ChangePointCreateModel? = null
    private var source :String = CP_CREATE
    private var action: String? = ""
    private var cpNo: String? = ""
    private lateinit var datePicker: DatePickerCustom
    private var intSaldo : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointStep1Binding.inflate(inflater, container, false)

        cpNo = arguments?.getString(CP_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (cpNo == "") CP_CREATE else CP_DETAIL_DATA

        data = HawkUtils().getTempDataCreateCp(source)
        intSaldo = HawkUtils().getDataBalance()

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
                saldo.text = Tools.doubleToRupiah("0".toDouble(),2)
            }else{
                saldo.text = Tools.doubleToRupiah(intSaldo.toString().toDouble(),2)
            }

            cpNo.setText(data?.cpNo)
            name.setText(data?.name?.let { textLimitReplaceToDots(it) })
            nik.setText(data?.nik)
            branch.setText(data?.branch?.let { textLimitReplaceToDots(it) })
            subBranch.setText(data?.subBranch?.let { textLimitReplaceToDots(it) })
            department.setText(data?.department?.let { textLimitReplaceToDots(it) })
            position.setText(data?.position?.let { textLimitReplaceToDots(it) })

            date.setText(data?.date)
            date.setOnClickListener {
                datePicker.showDialog(object  : DatePickerCustom.Callback{
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10)"0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        date.setText("$year-$monthStr-$dayStr")
                    }
                })
            }
            desc.setText(data?.description)

            setData()

            if ((action == APPROVE) || (action == DETAIL)) {
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
                                branchCode = data?.branchCode,
                                saldo = data?.saldo,
                                rewardData = data?.reward,
                                statusProposal = data?.statusProposal,
                                headId = data?.headId,
                                userId = data?.userId,
                                orgId = data?.orgId,
                                warehouseId = data?.warehouseId,
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