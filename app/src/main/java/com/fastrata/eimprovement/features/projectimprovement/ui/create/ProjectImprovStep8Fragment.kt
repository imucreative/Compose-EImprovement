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
import com.fastrata.eimprovement.features.suggestionsystem.data.model.CategorySuggestionItem
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateCategorySuggestionAdapter
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateCallback
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateCategorySuggestionCallback
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateWizard
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class ProjectImprovStep8Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep8Binding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: SsCreateCategorySuggestionAdapter
    private val listCategory = ArrayList<CategorySuggestionItem?>()
    private lateinit var categoryViewModel: ProjectImprovementViewModel
    private var data : ProjectImprovementCreateModel? = null
    private var ssNo: String? = ""
    private var ssAction: String? = ""
    private var source: String = PI_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep8Binding.inflate(inflater, container, false)
        categoryViewModel = injectViewModel(viewModelFactory)

        data = HawkUtils().getTempDataCreatePi()

        categoryViewModel.setCategorySuggestion()
        categoryAdapter = SsCreateCategorySuggestionAdapter()
        categoryAdapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep8Binding.bind(view)

        binding.hasilImplementasiImprovement.setText(data?.implementationResult)
        data?.categoryFixing?.let { category -> listCategory.addAll(category) }

        setInitCategory()
        setData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

        categoryAdapter.ssCreateCallback(object : SuggestionSystemCreateCategorySuggestionCallback {
            override fun checkClicked(data: CategorySuggestionItem, checked: Boolean) {
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
                categoryAdapter.setListCategorySuggestion(it, listCategory, ssAction!!)
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
                                CategorySuggestionItem(
                                    id = 0,
                                    category = match[0]?.category.toString(),
                                    checked = true
                                )
                            )
                        }
                        listCategory.add(
                            CategorySuggestionItem(
                                id = 0,
                                category = edtLainLain.text.toString(),
                                checked = true
                            )
                        )

                    } else {
                        listCategory.remove(
                            CategorySuggestionItem(
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
                        hasilImplementasiImprovement.text.isNullOrEmpty() -> {
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
                                categoryFixingItem = listCategory,
                                hasilImplementasi = hasilImplementasiImprovement.text.toString(),
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