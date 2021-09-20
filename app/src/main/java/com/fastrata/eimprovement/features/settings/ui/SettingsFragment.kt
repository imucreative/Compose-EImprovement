package com.fastrata.eimprovement.features.settings.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentSettingsBinding
import com.fastrata.eimprovement.databinding.ToolbarBinding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.splashscreen.SplashScreenActivity
import com.fastrata.eimprovement.ui.setToolbar
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.HelperNotification
import com.fastrata.eimprovement.utils.HelperNotification.CallBackNotificationYesNo
import javax.inject.Inject

class SettingsFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private lateinit var notification: HelperNotification

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)

        setHasOptionsMenu(true);

        initToolbar()
        initComponent(requireActivity())

        return binding.root
    }

    private fun initComponent(activity: FragmentActivity) {
        notification = HelperNotification()

        binding.apply {
            tvSaldo.text = HawkUtils().getDataLogin().SALDO
            tvName.text = HawkUtils().getDataLogin().USER_NAME
            tvNik.text = HawkUtils().getDataLogin().NIK
            tvBranch.text = HawkUtils().getDataLogin().BRANCH
            tvSubBranch.text = HawkUtils().getDataLogin().SUB_BRANCH
            tvDepartment.text = HawkUtils().getDataLogin().DEPARTMENT
            tvPosition.text = HawkUtils().getDataLogin().POSITION

            btnLogout.setOnClickListener {
                notification.shownotificationyesno(activity,"Setting","Apakah anda yakin keluar",
                object  :CallBackNotificationYesNo {
                    override fun onNotificationNo() {

                    }

                    override fun onNotificationYes() {
                        HawkUtils().setStatusLogin(false)
                        HawkUtils().setDataLogin(null)
                        startActivity(Intent(activity, SplashScreenActivity::class.java))
                    }
                })
            }
        }
    }

    private fun initToolbar() {
        val toolbar = toolbarBinding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black)

        setToolbar(toolbar, "Setting")
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