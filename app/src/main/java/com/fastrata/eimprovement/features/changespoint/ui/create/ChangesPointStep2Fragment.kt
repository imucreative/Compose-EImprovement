package com.fastrata.eimprovement.features.changesPoint.ui.create

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
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel
import com.fastrata.eimprovement.features.changesPoint.data.model.ChangePointRewardItem
import com.fastrata.eimprovement.features.changesPoint.data.model.hadiahItem
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.SnackBarCustom
import com.fastrata.eimprovement.utils.Tools.hideKeyboard
import javax.inject.Inject

class ChangesPointStep2Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var _binding:FragmentChangesPointStep2Binding
    private lateinit var viewModel:ChangesPointRewardViewModel
    private lateinit var adapter:ChangesRewardAdapter
    private var data : ChangePointCreateModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangesPointStep2Binding.inflate(layoutInflater, container, false)
        data = HawkUtils().getTempDataCreateCP()
        viewModel = injectViewModel(viewModelFactory)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentChangesPointStep2Binding.bind(view)
        initComponent(data?.penukaran_hadiah)

    }

    private val onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
        hideKeyboard()
    }

    private fun initComponent(hadiahData : ArrayList<ChangePointRewardItem?>?) {
        viewModel.setChangeRewardPoint()
        adapter = ChangesRewardAdapter()
        adapter.notifyDataSetChanged()

        _binding.apply {
            rvChangereward.setHasFixedSize(true)
            rvChangereward.layoutManager = LinearLayoutManager(context)
            rvChangereward.adapter = adapter

            val listReward : List<hadiahItem> = DataDummySs.generateDummyReward()
            val adapterlisreward = ArrayAdapter(
                requireContext(),android.R.layout.simple_list_item_1,listReward.map{
                    value -> value.hadiah
                }
            )

            reward.setAdapter(adapterlisreward)
            reward.onItemClickListener = onItemClickListener
        }

        adapter.setChangeRewardCallback(object  : ChangeRewardCallback{
            override fun removeClicked(data: ChangePointRewardItem) {
                Toast.makeText(context, data.hadiah, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getChangeRewardPoint().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })

        setData()
    }



    private fun setData() {
        _binding.apply {
            changePointBtn.setOnClickListener {
                val hadiah = reward.text.toString()
                val nilai = jmlh.text.toString()
                val keterangan = Keterangan.text.toString()

                when {
                    hadiah.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Name must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        reward.requestFocus()
                    }
                    nilai.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Name must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        jmlh.requestFocus()
                    }
                    keterangan.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root.rootView, layoutInflater, resources, root.rootView.context,
                            "Name must be fill before added",
                            R.drawable.ic_close, R.color.red_500)
                        Keterangan.requestFocus()
                    }
                    else -> {
                        val addData = ChangePointRewardItem(
                            no = 1,
                            hadiah = hadiah,
                            nilai = nilai,
                            keterangan = keterangan
                        )

                        reward.requestFocus()
                        jmlh.setText("")
                        Keterangan.setText("")

                    }


                }
            }
        }
    }

}