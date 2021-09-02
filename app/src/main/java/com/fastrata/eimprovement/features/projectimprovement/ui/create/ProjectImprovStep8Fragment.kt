package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep8Binding
import com.fastrata.eimprovement.features.projectimprovement.adapter.PiCreateAttachmentAdapter
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjecImprovementCreateAttachmentCallback
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.CategorySuggestionItem
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateCategorySuggestionAdapter
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SsCreateCategorySuggestionViewModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateAttachmentCallback
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateCategorySuggestionCallback
import com.fastrata.eimprovement.utils.SS_CATEGORY_OTHER
import com.fastrata.eimprovement.utils.SS_CATEGORY_OTHER_VALUE
import com.fastrata.eimprovement.utils.SnackBarCustom
import timber.log.Timber

class ProjectImprovStep8Fragment : Fragment () {


    private lateinit var _binding: FragmentProjectImprovementStep8Binding
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: SsCreateCategorySuggestionAdapter
    private val listCategory = ArrayList<CategorySuggestionItem?>()
    private lateinit var categoryViewModel: ProjectImprovementViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep8Binding.inflate(layoutInflater, container, false)
        categoryViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ProjectImprovementViewModel::class.java)
        categoryViewModel.setCategorySuggestion()
        categoryAdapter = SsCreateCategorySuggestionAdapter()
        categoryAdapter.notifyDataSetChanged()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep8Binding.bind(view)

        initComponent()
    }

    private fun initComponent() {
        binding.apply {
            setInitCategory()
        }
    }

    private fun setInitCategory() {
        binding.apply {
            rvCategoryImprovement.addItemDecoration(DividerItemDecoration(rvCategoryImprovement.context, DividerItemDecoration.VERTICAL))
            rvCategoryImprovement.setHasFixedSize(true)
            rvCategoryImprovement.layoutManager = LinearLayoutManager(context)
            rvCategoryImprovement.adapter = categoryAdapter
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
            }
        })
    }
}