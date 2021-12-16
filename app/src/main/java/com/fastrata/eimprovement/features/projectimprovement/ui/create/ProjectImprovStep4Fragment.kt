package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep4Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.adapter.SebabMasalahAdapter
import com.fastrata.eimprovement.features.projectimprovement.callback.SebabMasalahCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import javax.inject.Inject
import android.view.LayoutInflater
import com.fastrata.eimprovement.databinding.DialogFormCreateSebabMasalahBinding
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils

class ProjectImprovStep4Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep4Binding? = null
    private val binding get() = _binding!!
    private lateinit var bindingDialog: DialogFormCreateSebabMasalahBinding
    private lateinit var dialog: Dialog
    private lateinit var viewModel : ProjectImprovementViewModel
    private lateinit var adapter : SebabMasalahAdapter
    private var data : ProjectImprovementCreateModel? = null
    private lateinit var notification: HelperNotification
    private var piNo: String? = ""
    private var action: String? = ""
    private var source: String = PI_CREATE
    private var updateAkarMasalah: ArrayList<AkarMasalahModel?>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep4Binding.inflate(inflater, container, false)
        viewModel = injectViewModel(viewModelFactory)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)
        viewModel.setSebabMasalah(source)

        adapter = SebabMasalahAdapter()
        adapter.notifyDataSetChanged()

        notification = HelperNotification()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep4Binding.bind(view)

        binding.apply {
            rvSebabMasalah.setHasFixedSize(true)
            rvSebabMasalah.layoutManager = LinearLayoutManager(context)
            rvSebabMasalah.adapter = adapter

            createSebabMasalah.setOnClickListener {
                activity?.let { activity -> dialogCreateSebabMasalah(activity) }
            }
        }

        initList(data?.sebabMasalah)
        setValidation()

        when (action){
            APPROVE, DETAIL -> disableForm()
        }

        when {
            conditionImplementation() -> disableForm()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun disableForm() {
        binding.apply {
            createSebabMasalah.isClickable = false
        }
    }

    private fun conditionImplementation(): Boolean {
        return when (data?.statusProposal?.id) {
            6, 9 -> {
                true
            }
            else -> {
                false
            }
        }
    }

    private fun initList(sebabMasalah: ArrayList<SebabMasalahModel?>?) {
        adapter.setSebabMslhCallback(object : SebabMasalahCallback{
            override fun onItemClicked(data: SebabMasalahModel) {

            }

            override fun onItemRemoved(data: SebabMasalahModel, position: Int) {
                binding.apply {
                    if (action != APPROVE && action != DETAIL) {
                        if (!conditionImplementation()) {
                            activity?.let { activity ->
                                notification.showNotificationYesNo(
                                    activity,
                                    requireContext(),
                                    R.color.blue_500,
                                    resources.getString(R.string.delete),
                                    resources.getString(R.string.delete_confirmation),
                                    resources.getString(R.string.agree),
                                    resources.getString(R.string.not_agree),
                                    object : HelperNotification.CallBackNotificationYesNo {
                                        override fun onNotificationNo() {

                                        }

                                        override fun onNotificationYes() {
                                            sebabMasalah?.remove(data)

                                            viewModel.updateSebabMasalah(sebabMasalah, source)
                                            viewModel.getSebabMasalah()
                                                .observe(viewLifecycleOwner, {
                                                    if (it != null) {
                                                        adapter.setList(it)
                                                    }
                                                })
                                            viewModel.removeAkarMasalah(
                                                position,
                                                this@ProjectImprovStep4Fragment.data?.akarMasalah,
                                                source
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        })

        viewModel.getSebabMasalah().observe(viewLifecycleOwner,{
            if(it != null){
                adapter.setList(it)
            }
        })
    }

    private fun dialogCreateSebabMasalah(activity: Activity) {
        bindingDialog = DialogFormCreateSebabMasalahBinding.inflate(layoutInflater)

        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(true)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT

        bindingDialog.apply {
            btnClose.setOnClickListener { dialog.dismiss() }

            btnSave.setOnClickListener {
                if ((action != APPROVE) && (action != DETAIL)) {
                    setData()
                }
            }
        }

        dialog.show()
        dialog.window!!.attributes = lp
    }

    private fun setData() {
        bindingDialog.apply {
            val penyebab = penyebabMasalah.text.toString()
            val valueW1 = w1.text.toString()
            val valueW2 = w2.text.toString()
            val valueW3 = w3.text.toString()
            val valueW4 = w4.text.toString()
            val valueW5 = w5.text.toString()
            val prioritas = akarMasalahPrioritas.text.toString()

            when {
                penyebab.isEmpty() -> {
                    SnackBarCustom.snackBarIconInfo(
                        root, layoutInflater, resources, root.context,
                        resources.getString(R.string.problem_reason),
                        R.drawable.ic_close, R.color.red_500)
                    penyebabMasalah.requestFocus()
                }
                valueW1.isEmpty() -> {
                    SnackBarCustom.snackBarIconInfo(
                        root, layoutInflater, resources, root.context,
                        resources.getString(R.string.why_required),
                        R.drawable.ic_close, R.color.red_500)
                    w1.requestFocus()
                }
                prioritas.isEmpty() -> {
                    SnackBarCustom.snackBarIconInfo(
                        root, layoutInflater, resources, root.context,
                        resources.getString(R.string.problem_root),
                        R.drawable.ic_close, R.color.red_500)
                    akarMasalahPrioritas.requestFocus()
                }
                else -> {

                    val addData = SebabMasalahModel(
                        penyebab = penyebab,
                        w1 = valueW1,
                        w2 = valueW2,
                        w3 = valueW3,
                        w4 = valueW4,
                        w5 = valueW5,
                        prioritas = prioritas
                    )

                    var kenapa = ""
                    when {
                        valueW5.isNotEmpty() -> {
                            kenapa = valueW5
                        }
                        valueW4.isNotEmpty() -> {
                            kenapa = valueW4
                        }
                        valueW3.isNotEmpty() -> {
                            kenapa = valueW3
                        }
                        valueW2.isNotEmpty() -> {
                            kenapa = valueW2
                        }
                        valueW1.isNotEmpty() -> {
                            kenapa = valueW1
                        }
                    }

                    data?.akarMasalah?.size?.plus(1)?.let {
                        val dataSaranAkarMasalah = AkarMasalahModel(
                            sequence = it,
                            kenapa = kenapa,
                            aksi = "",
                            detail_langkah = ""
                        )
                        updateAkarMasalah = viewModel.updateAkarMasalah(dataSaranAkarMasalah, -1, source)
                    }

                    viewModel.addSebabMasalah(addData, data?.sebabMasalah, source)

                    penyebabMasalah.setText("")
                    w1.setText("")
                    w2.setText("")
                    w3.setText("")
                    w4.setText("")
                    w5.setText("")
                    akarMasalahPrioritas.setText("")
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setValidation() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object : ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean

                binding.apply {
                    stat = if (data?.sebabMasalah?.size == 0) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.problem_root_reason),
                            R.drawable.ic_close, R.color.red_500)
                        false
                    } else {
                        HawkUtils().setTempDataCreatePi(
                            id = data?.id,
                            piNo = data?.piNo,
                            date = data?.date,
                            title = data?.title,
                            branch = data?.branch,
                            subBranch = data?.subBranch,
                            department = data?.department,
                            years = data?.years,
                            statusImplementationModel = data?.statusImplementationModel,
                            identification = data?.identification,
                            target = data?.target,
                            sebabMasalah = data?.sebabMasalah,
                            akarMasalah = updateAkarMasalah,
                            nilaiOutput = data?.nilaiOutput,
                            nqiModel = data?.nqiModel,
                            teamMember = data?.teamMember,
                            categoryFixing = data?.categoryFixing,
                            hasilImplementasi = data?.implementationResult,
                            attachment = data?.attachment,
                            statusProposal = data?.statusProposal,
                            headId = data?.headId,
                            userId = data?.userId,
                            orgId = data?.orgId,
                            warehouseId = data?.warehouseId,
                            historyApproval = data?.historyApproval,
                            activityType = data?.activityType,
                            submitType = data?.submitType,
                            comment = data?.comment,
                            source = source
                        )
                        true
                    }
                }

                return stat
            }
        })
    }
}