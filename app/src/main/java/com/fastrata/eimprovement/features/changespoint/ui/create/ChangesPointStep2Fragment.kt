package com.fastrata.eimprovement.features.changespoint.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentChangesPointStep2Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateItemModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangeRewardCallback
import com.fastrata.eimprovement.features.changespoint.data.model.HadiahItem
import com.fastrata.eimprovement.features.changespoint.data.model.RewardItem
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateCallback
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChangesPointStep2Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentChangesPointStep2Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ChangesPointRewardViewModel
    private lateinit var rewardAdapter: ChangesRewardAdapter
    private var data : ChangePointCreateItemModel? = null
    private var source: String = CP_CREATE
    private var action: String? = ""
    private var cpNo: String? = ""
    var intTotal : Int = 0
    private lateinit var listReward : ArrayList<HadiahItem>
    private lateinit var selectedReward: HadiahItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointStep2Binding.inflate(inflater, container, false)
        viewModel = injectViewModel(viewModelFactory)

        cpNo = arguments?.getString(CP_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (cpNo == "") CP_CREATE else CP_DETAIL_DATA

        data = HawkUtils().getTempDataCreateCP(source)

        if (data?.reward != null){
            checkBalance()
        }

        viewModel.setChangeRewardPoint(source)

        listReward = DataDummySs.generateDummyReward()

        rewardAdapter = ChangesRewardAdapter()
        rewardAdapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentChangesPointStep2Binding.bind(view)

        binding.apply {
            //totalReward.text = data?.saldo.toString()

            rvChangereward.setHasFixedSize(true)
            rvChangereward.layoutManager = LinearLayoutManager(context)
            rvChangereward.adapter = rewardAdapter

            totalReward.text = intTotal.toString()
        }

        initComponent()
        initList(data?.reward)
        setData()
        setValidation()

        if (action == APPROVE) {
            disableForm()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun disableForm() {
        binding.apply {
            hadiahCp.isEnabled = false
            keteranganCp.isEnabled = false

            addReward.isClickable = false
        }
    }

    private val onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
        binding.apply {
            nilaiCp.setText(listReward[i].nilai)
            selectedReward = listReward[i]
        }
        hideKeyboard()
    }

    private fun initComponent() {
        binding.apply {
            val adapterReward = ArrayAdapter(
                requireContext(),android.R.layout.simple_list_item_1,
                listReward.map{ value ->
                    value.hadiah
                }
            )
            hadiahCp.setAdapter(adapterReward)
            hadiahCp.onItemClickListener = onItemClickListener
        }
    }

    private fun initList(reward: ArrayList<RewardItem?>?) {
        rewardAdapter.setChangeRewardCallback(object : ChangeRewardCallback {
            override fun removeClicked(data: RewardItem) {
                if (action != APPROVE) {

                    reward?.remove(data)
                    viewModel.updateReward(reward)
                    viewModel.getChangeRewardPoint().observe(viewLifecycleOwner, {
                        if (it != null) {
                            rewardAdapter.setListReward(it)
                        }
                    })

                    val total = checkBalance()
                    viewModel.setTotalReward(total)
                    viewModel.getTotalReward().observe(viewLifecycleOwner, {
                        binding.totalReward.text = it.toString()
                    })
                }
            }
        })

        viewModel.getChangeRewardPoint().observe(viewLifecycleOwner, {
            if (it != null){
                rewardAdapter.setListReward(it)
            }
        })
    }

    private fun checkBalance(): Int {
        if(data?.reward != null){
            val itemCount = data?.reward!!.map { values ->
                values!!.nilai.toInt()
            }.sum()
            intTotal = itemCount
            Timber.i("Total : $intTotal")
        }else{
            intTotal = 0
        }
        return intTotal
    }

    private fun setData() {
        binding.apply {
            addReward.setOnClickListener {
                val reward = hadiahCp.text.toString()
                val point = nilaiCp.text.toString()
                val desc = keteranganCp.text.toString()

                when{
                    reward.isEmpty() ->{
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Reward must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        hadiahCp.requestFocus()
                    }
                    desc.isEmpty() ->{
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Description must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        keteranganCp.requestFocus()
                    }
                    else -> {
                        val add = RewardItem(
                            no = selectedReward.id,
                            hadiah = selectedReward.hadiah,
                            nilai = selectedReward.nilai,
                            keterangan = desc
                        )

                        viewModel.addReward(add, data?.reward)

                        val total = checkBalance()
                        viewModel.setTotalReward(total)
                        viewModel.getTotalReward().observe(viewLifecycleOwner,{
                            totalReward.text = it.toString()
                        })

                        hadiahCp.setText("")
                        nilaiCp.setText("")
                        keteranganCp.setText("")
                        hideKeyboard()
                    }
                }
            }
        }
    }

    private fun setValidation() {
        (activity as ChangesPointCreateWizard).setCpCreateCallback(
            object : ChangesPointCreateCallback {
                    override fun onDataPass(): Boolean {
                    var stat : Boolean
                    binding.apply {
                        stat = if (data?.reward?.size == 0) {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Reward must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            false
                        } else {
                            HawkUtils().setTempDataCreateCp(
                                cpNo = data?.cpNo,
                                name = data?.name,
                                nik = data?.nik,
                                branch = data?.branch,
                                departement = data?.department,
                                position = data?.position,
                                date = data?.date,
                                keterangan = data?.description,
                                id = data?.id,
                                subBranch = data?.subBranch,
                                saldo = data?.saldo,
                                rewardData = data?.reward,
                                source = source
                            )
                            true
                        }
                    }
              return stat
                }
            }
        )
    }

}