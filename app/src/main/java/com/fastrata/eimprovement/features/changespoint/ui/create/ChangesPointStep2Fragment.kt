package com.fastrata.eimprovement.features.changespoint.ui.create

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
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
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangeRewardCallback
import com.fastrata.eimprovement.features.changespoint.data.model.RewardItem
import com.fastrata.eimprovement.features.changespoint.data.model.hadiahItem
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateCallback
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChangesPointStep2Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentChangesPointStep2Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:ChangesPointRewardViewModel
    private lateinit var rewardAdapter: ChangesRewardAdapter
    private var data : ChangePointCreateModel? = null
    private var source :String = CP_CREATE
    var intTotal : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointStep2Binding.inflate(layoutInflater, container, false)
        data = HawkUtils().getTempDataCreateCP()
//        if (data?.reward != null){
//            checkBalance()
//        }
        viewModel = injectViewModel(viewModelFactory)
        viewModel.setChangeRewardPoint()
        rewardAdapter = ChangesRewardAdapter()
        rewardAdapter.notifyDataSetChanged()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentChangesPointStep2Binding.bind(view)
        binding.apply {
            totalReward.setText(data?.saldo.toString())
            rvChangereward.setHasFixedSize(true)
            rvChangereward.layoutManager = LinearLayoutManager(context)
            rvChangereward.adapter = rewardAdapter
            totalReward.setText(intTotal.toString())
        }
        initComponent()
        initlist(data?.reward)
        setData()
        settValidation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private val onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
        hideKeyboard()
    }

    private fun initComponent() {
        var timer: Timer? = null
        binding.apply {
            val listReward : List<hadiahItem> = DataDummySs.generateDummyReward()
            val adapterReward = ArrayAdapter(
                requireContext(),android.R.layout.simple_list_item_1,
                listReward.map{ value ->
                    value.hadiah
                }
            )
            hadiahCp.setAdapter(adapterReward)
            hadiahCp.onItemClickListener = onItemClickListener

            nilaiCp.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int){}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask(){
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {

                            }
                        }
                    },1000)
                }
            })



        }
    }

    private fun initlist(reward: ArrayList<RewardItem?>?) {
        rewardAdapter.setChangeRewardCallback(object : ChangeRewardCallback {
            override fun removeClicked(data: RewardItem) {
                Toast.makeText(context,data.hadiah,Toast.LENGTH_LONG).show()

                reward?.remove(data)
                viewModel.updateReward(reward)
                viewModel.getChangeRewardPoint()
                    .observe(viewLifecycleOwner,{
                        if (it != null){
                            rewardAdapter.setListReward(it)
                        }
                    })
            }
        })

        viewModel.getChangeRewardPoint().observe(viewLifecycleOwner,
            {
                if (it != null){
                    rewardAdapter.setListReward(it)
                }
            })
    }

    private fun checkBalance() {
        if(data?.reward != null){
            val itemcount = data?.reward!!
                .map { values ->
                    values!!.nilai.toInt()
                }.sum()
            intTotal = itemcount
            Timber.i("total : $intTotal")
        }else{
            intTotal = 0
        }
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
                            "Name must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        hadiahCp.requestFocus()
                    }
                    desc.isEmpty() ->{
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Name must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        keteranganCp.requestFocus()
                    }
                    else -> {
                        val add = RewardItem(
                            no = 0,
                            hadiah = reward,
                            nilai = point,
                            keterangan = desc
                        )

                        viewModel.addReward(add,data?.reward)

                        hadiahCp.requestFocus()
                        hadiahCp.setText("")
                        nilaiCp.setText("")
                        keteranganCp.setText("")
                    }
                }
            }
        }
    }


    private fun settValidation() {
        (activity as ChangesPointCreateWizard).setcpCreateCallback(
            object : ChangesPointCreateCallback {
                override fun OnDataPass(): Boolean {
                    var stat : Boolean
                    binding.apply {
                        stat = if (data?.reward?.size == 0) {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Reward must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            false
                        } else {
                            true
                        }
                    }
              return stat
                }
            }
        )
    }







}