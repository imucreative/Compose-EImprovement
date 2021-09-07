package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep1Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.CategorySuggestionItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.SS_CATEGORY_OTHER
import com.fastrata.eimprovement.utils.SS_CATEGORY_OTHER_VALUE
import com.fastrata.eimprovement.utils.SnackBarCustom
import timber.log.Timber
import javax.inject.Inject

class SuggestionSystemStep1Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemStep1Binding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private lateinit var categoryViewModel: SsCreateCategorySuggestionViewModel
    private lateinit var categoryAdapter: SsCreateCategorySuggestionAdapter
    private val listCategory = ArrayList<CategorySuggestionItem?>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep1Binding.inflate(inflater, container, false)

        categoryViewModel = injectViewModel(viewModelFactory)
        //categoryViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SsCreateCategorySuggestionViewModel::class.java)
        categoryViewModel.setCategorySuggestion()
        categoryAdapter = SsCreateCategorySuggestionAdapter()
        categoryAdapter.notifyDataSetChanged()
        data = HawkUtils().getTempDataCreateSs()

        if (!data?.categorySuggestion.isNullOrEmpty()) {
            data?.categorySuggestion?.let { listCategory.addAll(it) }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep1Binding.bind(view)

        binding.apply {
            if (data?.title != null) {
                titleSuggestion.setText(data?.title)
            }

            setInitCategory(data?.categorySuggestion)
            setData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setInitCategory(category: ArrayList<CategorySuggestionItem?>?) {
        binding.apply {
            rvCategorySuggestion.addItemDecoration(DividerItemDecoration(rvCategorySuggestion.context, DividerItemDecoration.VERTICAL))
            rvCategorySuggestion.setHasFixedSize(true)
            rvCategorySuggestion.layoutManager = LinearLayoutManager(context)
            rvCategorySuggestion.adapter = categoryAdapter
        }

        categoryAdapter.ssCreateCallback(object : SuggestionSystemCreateCategorySuggestionCallback {
            override fun checkClicked(data: CategorySuggestionItem, checked: Boolean) {
                if (checked) {
                    if(data.id == SS_CATEGORY_OTHER) {
                        binding.etOther.visibility = View.VISIBLE
                    }

                    listCategory.add(
                        CategorySuggestionItem(
                            id = data.id,
                            category = data.category,
                            checked = checked
                        )
                    )

                } else {
                    if(data.id == SS_CATEGORY_OTHER) {
                        binding.etOther.visibility = View.GONE
                    }

                    listCategory.remove(
                        CategorySuggestionItem(
                            id = data.id,
                            category = data.category,
                            checked = !checked  // belom bener
                        )
                    )
                }

                println("##### listCategory $listCategory")
                println("##### category $category")
            }
        })

        categoryViewModel.getCategorySuggestion().observe(viewLifecycleOwner, {
            if (it != null) {

                categoryAdapter.setListCategorySuggestion(it, listCategory)
                listCategory.map { checkList ->
                    if (checkList?.id == SS_CATEGORY_OTHER_VALUE) {
                        binding.apply {
                            etOther.visibility = View.VISIBLE
                            edtLainLain.setText(checkList.category)
                        }

                    }
                }

                Timber.e("### ambil dari getCategorySuggestion $it")
                Timber.e("### $category")
            }
        })
    }

    private fun setData() {
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object : SuggestionSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    if (etOther.isVisible && !edtLainLain.text.isNullOrBlank()){
                        listCategory.add(
                            CategorySuggestionItem(id = SS_CATEGORY_OTHER_VALUE, category = edtLainLain.text.toString(), checked = true)
                        )
                    }else{
                        if(listCategory.contains(
                                CategorySuggestionItem(id = SS_CATEGORY_OTHER_VALUE, category = edtLainLain.text.toString(), checked = true))
                        ) {
                            listCategory.remove(
                                CategorySuggestionItem(
                                    id = SS_CATEGORY_OTHER_VALUE,
                                    category = edtLainLain.text.toString(),
                                    checked = true
                                )
                            )
                        }
                    }

                    when {
                        titleSuggestion.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root.rootView, layoutInflater, resources, root.rootView.context,
                                "Title must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            titleSuggestion.requestFocus()
                            stat = false
                        }
                        listCategory.isEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root.rootView, layoutInflater, resources, root.rootView.context,
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
