package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep8Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.ui.adapter.CategoryImprovementAdapter
import com.fastrata.eimprovement.ui.adapter.CategoryImprovementCallback
import com.fastrata.eimprovement.ui.model.CategoryImprovementItem
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class ProjectImprovStep8Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep8Binding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryImprovementAdapter
    private val listCategory = ArrayList<CategoryImprovementItem?>()
    private lateinit var categoryViewModel: ProjectImprovementViewModel
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    private var source: String = PI_CREATE
    var statusImplement : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep8Binding.inflate(inflater, container, false)
        categoryViewModel = injectViewModel(viewModelFactory)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)

        categoryViewModel.setCategorySuggestion()
        categoryAdapter = CategoryImprovementAdapter()
        categoryAdapter.notifyDataSetChanged()

        statusImplement = HawkUtils().getStatusImplementation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep8Binding.bind(view)

        binding.apply {
            if (data?.statusImplementation?.sudah == null) {
                cardViewHasilImplementasi.visibility = View.GONE
            } else {
                cardViewHasilImplementasi.visibility = View.VISIBLE
                hasilImplementasiImprovement.setText(data?.implementationResult)
            }
            data?.categoryFixing?.let { category ->
                listCategory.addAll(category)
            }
        }

        setInitCategory()
        setData()

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
            checkboxOther.isEnabled = false
            tvCheckboxOther.isClickable = false
            edtLainLain.isEnabled = false

            hasilImplementasiImprovement.isEnabled = false
        }
    }

    private fun setInitCategory() {
        binding.apply {
            rvCategoryImprovement.addItemDecoration(DividerItemDecoration(rvCategoryImprovement.context, DividerItemDecoration.VERTICAL))
            rvCategoryImprovement.setHasFixedSize(true)
            rvCategoryImprovement.layoutManager = LinearLayoutManager(context)
            rvCategoryImprovement.adapter = categoryAdapter

            tvCheckboxOther.setOnClickListener {
                checkboxOther.isChecked = !checkboxOther.isChecked

                if (checkboxOther.isChecked) {
                    edtLayoutLainLain.visibility = View.VISIBLE
                } else {
                    edtLayoutLainLain.visibility = View.GONE
                }
            }
        }

        categoryAdapter.categoryImprovementCreateCallback(object : CategoryImprovementCallback {
            override fun checkClicked(data: CategoryImprovementItem, checked: Boolean) {
                data.checked = checked
                if (checked) {
                    listCategory.add(data)
                } else {
                    listCategory.remove(data)
                }
            }
        })

        categoryViewModel.getCategorySuggestion().observe(viewLifecycleOwner, {
            if (it != null) {
                categoryAdapter.setListCategoryImprovement(it, listCategory, action!!)
                listCategory.map { checkList ->
                    if (checkList?.id == 0) {
                        binding.apply {
                            checkboxOther.isChecked = !checkboxOther.isChecked
                            edtLayoutLainLain.visibility = View.VISIBLE
                            edtLainLain.setText(checkList.category)
                        }
                    }
                }
            }
        })
    }

    private fun setData() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object :
            ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    if (checkboxOther.isChecked && !edtLainLain.text.isNullOrBlank()){
                        val (match, notMatch) = listCategory.partition { it?.id == 0 }
                        if (match.isNotEmpty()) {
                            listCategory.remove(
                                CategoryImprovementItem(
                                    id = 0,
                                    category = match[0]?.category.toString(),
                                    checked = true
                                )
                            )
                        }
                        listCategory.add(
                            CategoryImprovementItem(
                                id = 0,
                                category = edtLainLain.text.toString(),
                                checked = true
                            )
                        )

                    } else {
                        listCategory.remove(
                            CategoryImprovementItem(
                                id = 0,
                                category = edtLainLain.text.toString(),
                                checked = true
                            )
                        )
                    }

                    when {
                        checkboxOther.isChecked && edtLainLain.text.isNullOrBlank() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Other category must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            edtLainLain.requestFocus()
                            stat = false
                        }
                        hasilImplementasiImprovement.text.isNullOrEmpty() && data?.statusImplementation?.sudah != null -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Hasil Implementasi Improvement must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            hasilImplementasiImprovement.requestFocus()
                            stat = false
                        }
                        listCategory.isEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Category must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            stat = false
                        }
                        else -> {
                            HawkUtils().setTempDataCreatePi(
                                id = data?.id,
                                piNo = data?.piNo,
                                date = data?.createdDate,
                                title = data?.title,
                                branch = data?.branch,
                                subBranch = data?.subBranch,
                                department = data?.department,
                                years = data?.years,
                                statusImplementation = data?.statusImplementation,
                                identification = data?.identification,
                                target = data?.setTarget,
                                sebabMasalah = data?.problem,
                                akarMasalah = data?.akarMasalah,
                                nilaiOutput = data?.outputValue,
                                perhitunganNqi = data?.nqi,
                                teamMember = data?.teamMember,
                                categoryFixingItem = listCategory,
                                hasilImplementasi = hasilImplementasiImprovement.text.toString(),
                                attachment = data?.attachment,
                                statusProposal = data?.statusProposal,
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
