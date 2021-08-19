package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep1Binding
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.utils.HawkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SuggestionSystemStep1Fragment: Fragment() {

    private var _binding: FragmentSuggestionSystemStep1Binding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep1Binding.inflate(inflater, container, false)
        data = HawkUtils().getTempDataCreateSs()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep1Binding.bind(view)

        binding.apply {
            chkbxOther.setOnCheckedChangeListener { buttonView, isChecked ->
                println(isChecked)

                if (isChecked) {
                    etOther.visibility = View.VISIBLE
                } else {
                    etOther.visibility = View.INVISIBLE
                }
            }

            if (data?.title != null) {
                getData()
            }

            setData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {

        binding.apply {
            titleSuggestion.setText(data?.title)
            if (data?.categorySuggestion?.contains("Meningkatkan Penjualan") == true) {
                chkbxMeningkatkanPenjualan.isChecked = true
            }
            if (data?.categorySuggestion?.contains("Menurunkan Biaya") == true) {
                chkbxMenurunkanBiaya.isChecked = true
            }
            if (data?.categorySuggestion?.contains("Mencegah Pelanggaran atau Kecurangan") == true) {
                chkbxMencegahPelanggaran.isChecked = true
            }
            if (data?.categorySuggestion?.contains("Menyederhanakan Proses kerja") == true) {
                chkbxMenyederhanakanProsesKerja.isChecked = true
            }

            // TODO
            // untuk chkBox yg other, belum selesai logicnya

        }
    }

    private fun setData() {
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object : SuggestionSystemCreateCallback {
            override fun onDataPass() {
                CoroutineScope(Dispatchers.Default).launch {
                    binding.apply {

                        val listCategory = ArrayList<String?>()
                        if (chkbxMeningkatkanPenjualan.isChecked) {
                            listCategory.add(chkbxMeningkatkanPenjualan.text.toString())
                        }
                        if (chkbxMenurunkanBiaya.isChecked) {
                            listCategory.add(chkbxMenurunkanBiaya.text.toString())
                        }
                        if (chkbxMencegahPelanggaran.isChecked) {
                            listCategory.add(chkbxMencegahPelanggaran.text.toString())
                        }
                        if (chkbxMenyederhanakanProsesKerja.isChecked) {
                            listCategory.add(chkbxMenyederhanakanProsesKerja.text.toString())
                        }
                        if (chkbxOther.isChecked) {
                            listCategory.add(edtLainLain.text.toString())
                        }

                        HawkUtils().setTempDataCreateSs(
                            ssNo = ssNo.text.toString(),
                            title = titleSuggestion.text.toString(),
                            listCategory = listCategory,
                            name = name.text.toString(),
                            nik = nik.text.toString(),
                            branch = branch.text.toString(),
                            department = department.text.toString(),
                            directMgr = directMgr.text.toString(),
                        )
                    }
                }
            }
        })
    }

}
