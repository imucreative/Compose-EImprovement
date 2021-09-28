package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep1Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.ui.adapter.CategoryImprovementAdapter
import com.fastrata.eimprovement.ui.adapter.CategoryImprovementCallback
import com.fastrata.eimprovement.ui.model.CategoryImprovementItem
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class SuggestionSystemStep1Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemStep1Binding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private var ssNo: String? = ""
    private var ssAction: String? = ""
    private lateinit var categoryViewModel: SsCreateCategorySuggestionViewModel
    private lateinit var categoryAdapter: CategoryImprovementAdapter
    private val listCategory = ArrayList<CategoryImprovementItem?>()
    private var source: String = SS_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep1Binding.inflate(inflater, container, false)

        categoryViewModel = injectViewModel(viewModelFactory)
        //categoryViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SsCreateCategorySuggestionViewModel::class.java)

        ssNo = arguments?.getString(SS_DETAIL_DATA)
        ssAction = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (ssNo == "") SS_CREATE else SS_DETAIL_DATA

        data = HawkUtils().getTempDataCreateSs(source)

        categoryViewModel.setCategorySuggestion()
        categoryAdapter = CategoryImprovementAdapter()
        categoryAdapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep1Binding.bind(view)

        binding.apply {
            ssNo.setText(data?.ssNo)
            name.setText(data?.name)
            nik.setText(data?.nik)
            branch.setText(data?.branch)
            department.setText(data?.department)
            directMgr.setText(data?.directMgr)

            titleSuggestion.setText(data?.title)
            data?.categoryImprovement?.let { category -> listCategory.addAll(category) }

            Timber.w("##### $data")
            setInitCategory()
            setData()

            if (ssAction == APPROVE) {
                disableForm()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun disableForm() {
        binding.apply {
            titleSuggestion.isEnabled = false
            //edtLayoutTitle.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey_10))
            checkboxOther.isEnabled = false
            tvCheckboxOther.isClickable = false
            edtLainLain.isEnabled = false
            //edtLayoutLainLain.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey_10))
        }
    }

    private fun setInitCategory() {
        binding.apply {
            rvCategorySuggestion.addItemDecoration(DividerItemDecoration(rvCategorySuggestion.context, DividerItemDecoration.VERTICAL))
            rvCategorySuggestion.setHasFixedSize(true)
            rvCategorySuggestion.layoutManager = LinearLayoutManager(context)
            rvCategorySuggestion.adapter = categoryAdapter

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
                categoryAdapter.setListCategoryImprovement(it, listCategory, ssAction!!)
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
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object : SuggestionSystemCreateCallback {
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
                        titleSuggestion.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Title must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            titleSuggestion.requestFocus()
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
                            HawkUtils().setTempDataCreateSs(
                                ssNo = ssNo.text.toString(),
                                title = titleSuggestion.text.toString(),
                                listCategory = listCategory,
                                name = name.text.toString(),
                                nik = nik.text.toString(),
                                branch = branch.text.toString(),
                                department = department.text.toString(),
                                directMgr = directMgr.text.toString(),
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
