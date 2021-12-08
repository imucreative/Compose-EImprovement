package com.fastrata.eimprovement.features.changespoint.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.R.layout.simple_list_item_1
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentChangesPointStep2Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.*
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
    private lateinit var changesRewardViewModel: ChangesRewardViewModel
    private lateinit var rewardAdapter: ChangesRewardAdapter
    private var data : ChangePointCreateModel? = null
    private var source: String = CP_CREATE
    private var action: String? = ""
    private var cpNo: String? = ""
    var intTotal : Int = 0
    var intSaldo : Int = 0
    var totalHadiah : Int = 0
    private var listRewardItem : List<GiftItem>? = null
    private lateinit var selectedReward: GiftItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointStep2Binding.inflate(inflater, container, false)
        changesRewardViewModel = injectViewModel(viewModelFactory)

        cpNo = arguments?.getString(CP_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (cpNo == "") CP_CREATE else CP_DETAIL_DATA

        data = HawkUtils().getTempDataCreateCp(source)
        intSaldo = HawkUtils().getDataBalance()
        Timber.e("Saldo : $intSaldo")

        if (data?.reward != null){
            totalBalance()
        }

        changesRewardViewModel.setChangeRewardPoint(source)

        changesRewardViewModel.setAllGift()

        rewardAdapter = ChangesRewardAdapter()
        rewardAdapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentChangesPointStep2Binding.bind(view)

        binding.apply {
            rvChangereward.setHasFixedSize(true)
            rvChangereward.layoutManager = LinearLayoutManager(context)
            rvChangereward.adapter = rewardAdapter

            totalReward.text = Tools.doubleToRupiah(intTotal.toDouble(),2)
        }

        retrieveDataGift()

        initList(data?.reward)
        setData()
        setValidation()

        if ((action == APPROVE) || (action == DETAIL)) {
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

    private fun retrieveDataGift(){
        changesRewardViewModel.getAllGift.observeEvent(this) { resultObserve ->
            resultObserve.observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    when (result.status) {
                        Result.Status.LOADING -> {
                            Timber.d("###-- Loading getAllGift")
                        }
                        Result.Status.SUCCESS -> {
                            listRewardItem = result.data?.data
                            initComponentGift()
                            Timber.d("###-- Success getAllGift")
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
                            Timber.d("###-- Error getAllGift")
                        }

                    }

                }
            })
        }
    }

    private val onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
        binding.apply {
            nilaiCp.setText(listRewardItem!![i].nilai.toString())
            selectedReward = listRewardItem!![i]
        }
        hideKeyboard()
    }

    private fun initComponentGift() {
        binding.apply {
            val adapterReward = ArrayAdapter(
                requireContext(), simple_list_item_1,
                listRewardItem!!.map{ value ->
                    value.hadiah
                }
            )
            hadiahCp.setAdapter(adapterReward)
            hadiahCp.onItemClickListener = onItemClickListener
        }
    }

    private fun initList(reward: ArrayList<RewardItem?>?) {
        rewardAdapter.setChangeRewardCallback(object : ChangesRewardCallback {
            override fun removeClicked(data: RewardItem) {
                if ((action != APPROVE) && (action != DETAIL)) {

                    reward?.remove(data)
                    changesRewardViewModel.updateReward(reward)
                    changesRewardViewModel.getChangeRewardPoint().observe(viewLifecycleOwner, {
                        if (it != null) {
                            rewardAdapter.setListReward(it)
                        }
                    })

                    val total = totalBalance()
                    changesRewardViewModel.setTotalReward(total)
                    changesRewardViewModel.getTotalReward().observe(viewLifecycleOwner, {
                        binding.totalReward.text = Tools.doubleToRupiah(it.toDouble(),2)
                    })
                }
            }
        })

        changesRewardViewModel.getChangeRewardPoint().observe(viewLifecycleOwner, {
            if (it != null){
                rewardAdapter.setListReward(it)
            }
        })
    }

    private fun totalBalance(): Int {
        Timber.e("data_reward : "+ data?.reward)
        if(data?.reward != null){
            val itemCount = data?.reward!!.map { values ->
                values!!.nilai
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
                val desc = keteranganCp.text.toString()
                totalHadiah = totalBalance()
                Timber.e("saldo & total : $intSaldo / $totalHadiah")
                when{
                    reward.isEmpty() ->{
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.reward_empty),
                            R.drawable.ic_close, R.color.red_500)
                        hadiahCp.requestFocus()
                    }
                    desc.isEmpty() ->{
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.desc_empty),
                            R.drawable.ic_close, R.color.red_500)
                        keteranganCp.requestFocus()
                    }
                    intSaldo < totalHadiah ->{
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.saldo_kurang),
                            R.drawable.ic_close, R.color.red_500)
                    }
                    else -> {
                        val add = RewardItem(
                            hadiahId = selectedReward.id,
                            hadiah = selectedReward.hadiah,
                            nilai = selectedReward.nilai,
                            keterangan = desc
                        )

                        changesRewardViewModel.addReward(add, data?.reward)

                        val total = totalBalance()
                        changesRewardViewModel.setTotalReward(total)
                        changesRewardViewModel.getTotalReward().observe(viewLifecycleOwner,{
                            totalReward.text = Tools.doubleToRupiah(it.toDouble(),2)
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
                    totalHadiah = totalBalance()
                    binding.apply {
                        when{
                            data?.reward?.size == 0 -> {
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.reward_empty),
                                    R.drawable.ic_close, R.color.red_500)
                                stat = false
                            }
                            intSaldo < totalHadiah -> {
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.saldo_kurang),
                                    R.drawable.ic_close, R.color.red_500)
                                stat = false
                            }
                            else -> {
                                HawkUtils().setTempDataCreateCp(
                                    cpNo = data?.cpNo,
                                    name = data?.name,
                                    nik = data?.nik,
                                    branch = data?.branch,
                                    branchCode = data?.branchCode,
                                    departement = data?.department,
                                    position = data?.position,
                                    date = data?.date,
                                    keterangan = data?.description,
                                    id = data?.id,
                                    subBranch = data?.subBranch,
                                    saldo = totalBalance(),
                                    rewardData = data?.reward,
                                    statusProposal = data?.statusProposal,
                                    headId = data?.headId,
                                    userId = data?.userId,
                                    orgId = data?.orgId,
                                    warehouseId = data?.warehouseId,
                                    historyApproval = data?.historyApproval,
                                    source = source)
                                stat = true
                            }
                        }
                    }
                    return stat
                }
            }
        )
    }

}