package com.fastrata.eimprovement.features.settings.ui.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.databinding.FragmentChangePasswordBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.settings.ui.changepassword.data.model.ChangePasswordCreateViewModel
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class ChangePasswordFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var notification: HelperNotification
    private lateinit var viewModel : ChangePasswordCreateViewModel

    private var old_password: String? = ""
    private var new_password: String? = ""
    private var conf_password: String? = ""
    private var userId : Int = 0
    private var userName : String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater,container,false)
        toolbarBinding = ToolbarBinding.bind(binding.root)

        viewModel = injectViewModel(viewModelFactory)
        userId = HawkUtils().getDataLogin().USER_ID
        userName = HawkUtils().getDataLogin().USER_NAME
        context ?: return binding.root
        setHasOptionsMenu(true);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding =  FragmentChangePasswordBinding.bind(view)
        initToolbar()
        initComponent(requireActivity())
    }

    private fun initComponent(requireActivity: FragmentActivity) {
        notification = HelperNotification()

        binding.apply {
            old_password = oldPassword.text.toString()
            new_password = newPassword.text.toString()
            conf_password = confirmPassword.text.toString()

            btnSubmit.setOnClickListener {
                validateData()
            }

        }
    }

    private fun validateData() {
        binding.apply {
            when{
                oldPassword.text.isNullOrEmpty() ->{
                    SnackBarCustom.snackBarIconInfo(
                        root, layoutInflater, resources, root.context,
                        resources.getString(R.string.old_pass_empty),
                        R.drawable.ic_close, R.color.red_500)
                    oldPassword.requestFocus()
                }
                newPassword.text.isNullOrEmpty()->{
                    SnackBarCustom.snackBarIconInfo(
                        root, layoutInflater, resources, root.context,
                         resources.getString(R.string.new_pass_empty),
                        R.drawable.ic_close, R.color.red_500)
                    newPassword.requestFocus()
                }
                confirmPassword.text.isNullOrEmpty()-> {
                    SnackBarCustom.snackBarIconInfo(
                        root, layoutInflater, resources, root.context,
                         resources.getString(R.string.conf_pass_empty),
                        R.drawable.ic_close, R.color.red_500)
                    confirmPassword.requestFocus()
                }
                newPassword.text.toString() != confirmPassword.text.toString()->{
                    SnackBarCustom.snackBarIconInfo(
                        root, layoutInflater, resources, root.context,
                         resources.getString(R.string.not_match_conf_password),
                        R.drawable.ic_close, R.color.red_500)
                    confirmPassword.requestFocus()
                }
                else->{
                    sendData(old_password!!, new_password!!)
                }
            }
        }
    }

    private fun sendData(oldPassword: String, newPassword: String) {
        viewModel.setChangePassword(userId,userName,oldPassword,newPassword)
        viewModel.getChangePassword.observeEvent(this){resultObserve ->
            resultObserve.observe(viewLifecycleOwner,{ result->
                if (result != null){
                    when(result.status){
                        Result.Status.LOADING -> {
                            HelperLoading.displayLoadingWithText(requireContext(),"",false)
                            Timber.d("###-- Loading Change Password")
                        }
                        Result.Status.SUCCESS -> {
                            HelperLoading.hideLoading()
                            if (!findNavController().popBackStack()) activity?.finish()
                        }
                        Result.Status.ERROR -> {
                            HelperLoading.hideLoading()
                            Timber.d("###-- Error Change Password")
                        }
                    }
                }
            })
        }
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)
        setToolbar(toolbar, resources.getString(R.string.change_password))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if (!findNavController().popBackStack()) activity?.finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }




}