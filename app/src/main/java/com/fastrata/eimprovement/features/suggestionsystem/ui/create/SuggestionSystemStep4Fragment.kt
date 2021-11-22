package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep4Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class SuggestionSystemStep4Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding:FragmentSuggestionSystemStep4Binding?= null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private var ssNo : String? = ""
    private var ssAction : String? = ""
    private var source: String = SS_CREATE
    var statusSuggestion : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep4Binding.inflate(inflater,container,false)
        ssNo = arguments?.getString(SS_DETAIL_DATA)
        ssAction = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (ssNo == "") SS_CREATE else SS_DETAIL_DATA
        data = HawkUtils().getTempDataCreateSs(source)

        statusSuggestion = HawkUtils().getStatusSuggestion()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSuggestionSystemStep4Binding.bind(view)
        binding.apply {
            if (!statusSuggestion){
                idePerbaikan.isEnabled = false
                hasilImplementasi.isEnabled = false
                idePerbaikan.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
                hasilImplementasi.setBackgroundColor(resources.getColor(R.color.blue_grey_200))
            }
        }

        getData()
        setData()

        if ((ssAction == APPROVE) || (ssAction == DETAIL)){
            disableForm()
        }
    }

    private fun disableForm() {
        binding.apply {
            idePerbaikan.isEnabled = false
            hasilImplementasi.isEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData(){
        binding.apply {
            if (data?.proses != null && statusSuggestion){
                idePerbaikan.setText(data?.proses.toString())
                hasilImplementasi.setText(data?.result.toString())
            }else{
                idePerbaikan.setText("")
                hasilImplementasi.setText("")
            }
        }
    }

    private fun setData(){
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object  : SuggestionSystemCreateCallback{
            override fun onDataPass(): Boolean {
                var stat : Boolean
                binding.apply {
                    when{
                        idePerbaikan.text.isNullOrEmpty() && statusSuggestion -> {
                            SnackBarCustom.snackBarIconInfo(
                                root,layoutInflater,resources,root.context,
                                resources.getString(R.string.ide_perbaikan_empty),
                                R.drawable.ic_close,R.color.red_500
                            )
                            idePerbaikan.requestFocus()
                            stat = false
                        }
                        hasilImplementasi.text.isNullOrEmpty() && statusSuggestion -> {
                            SnackBarCustom.snackBarIconInfo(
                                root,layoutInflater,resources,root.context,
                                resources.getString(R.string.hasil_implementasi_empty),
                                R.drawable.ic_close,R.color.red_500
                            )
                            hasilImplementasi.requestFocus()
                            stat = false
                        }
                        else -> {
                            HawkUtils().setTempDataCreateSs(
                                id = data?.id,
                                ssNo = data?.ssNo,
                                date = data?.date,
                                title = data?.title,
                                listCategory = data?.categoryImprovement,
                                name = data?.name,
                                nik = data?.nik,
                                branchCode = data?.branchCode,
                                branch = data?.branch,
                                subBranch = data?.subBranch,
                                department = data?.department,
                                directMgr = data?.directMgr,
                                suggestion = data?.suggestion,
                                problem = data?.problem,
                                statusImplementation = data?.statusImplementation,
                                teamMember = data?.teamMember,
                                attachment = data?.attachment,
                                statusProposal = data?.statusProposal,
                                headId = data?.headId,
                                userId = data?.userId,
                                orgId = data?.orgId,
                                warehouseId = data?.warehouseId,
                                proses = idePerbaikan.text.toString(),
                                result = hasilImplementasi.text.toString(),
                                activityType = data?.activityType,
                                submitType = data?.submitType,
                                comment = data?.comment,
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