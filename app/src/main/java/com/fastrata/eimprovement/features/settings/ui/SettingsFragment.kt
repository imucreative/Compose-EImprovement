package com.fastrata.eimprovement.features.settings.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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
import com.fastrata.eimprovement.utils.Tools
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
        context ?: return binding.root

        setHasOptionsMenu(true);

        initToolbar()
        initComponent(requireActivity())

        return binding.root
    }

    private fun initComponent(activity: FragmentActivity) {
        notification = HelperNotification()

        binding.apply {
            tvSaldo.text = Tools.doubleToRupiah(HawkUtils().getDataBalance().toDouble(),2)
            tvName.text = HawkUtils().getDataLogin().FULL_NAME
            tvNik.text = HawkUtils().getDataLogin().NIK
            tvBranch.text = HawkUtils().getDataLogin().BRANCH
            tvSubBranch.text = HawkUtils().getDataLogin().SUB_BRANCH
            tvDepartment.text = HawkUtils().getDataLogin().DEPARTMENT
            tvPosition.text = HawkUtils().getDataLogin().POSITION

            btnMutasi.setOnClickListener {
                val directions = SettingsFragmentDirections.actionSettingsToMutasiFragment(resources.getString(R.string.detail_balance))
                it.findNavController().navigate(directions)
            }

            btnChangePassword.setOnClickListener {
                //Navigation.findNavController(it).navigate(R.id.action_settingsFragment_to_changesPasswordFragment)
                val directions = SettingsFragmentDirections.actionSettingsToChangePasswordFragment(resources.getString(R.string.change_password))
                it.findNavController().navigate(directions)
            }

            btnLogout.setOnClickListener {
                notification.shownotificationyesno(
                    activity,
                    requireContext(),
                    resources.getColor(R.color.blue_500),
                    resources.getString(R.string.info),
                    resources.getString(R.string.log_out),
                    resources.getString(R.string.agree),
                    resources.getString(R.string.not_agree),
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

        setToolbar(toolbar, resources.getString(R.string.setting))
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
