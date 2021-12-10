package com.fastrata.eimprovement

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.fastrata.eimprovement.api.AMQPConsumer
import com.fastrata.eimprovement.databinding.ActivityHomeBinding
import com.fastrata.eimprovement.features.dashboard.ui.DashboardFragmentDirections
import com.google.gson.Gson
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import java.net.URL
import javax.inject.Inject
import com.fastrata.eimprovement.data.model.MessageItem
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils


class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var navController: NavController

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        navController = findNavController(R.id.nav_fragment)

        startAMQPConsumer(this)
        if(intent.hasExtra("message")){
            val ts = intent.getStringExtra("message")
            correctivePlanOnClickListener(ts!!)
        }

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement, this)
        Tools.setSystemBarLight(this)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private fun correctivePlanOnClickListener(ts : String){
        Timber.e("type :$ts")
        val msg: MessageItem = Gson().fromJson(ts, MessageItem::class.java)
        val type: String = msg.type
        val doc: String = msg.doc
        Timber.i("doc id : $doc")
        changeDestinationFragment(type,doc)
    }

    private fun changeDestinationFragment( type :String,doc: String){
        try {
            when(type){
                SS ->{
                    val direction = DashboardFragmentDirections.
                    actionDashboardFragmentToSuggestionSystemFragment(resources.getString(R.string.suggestion_system), docId = doc)
                    navController.navigate(direction)
                }
                PI->{
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToProjectImprovementFragment(resources.getString(R.string.project_improvement), docId = doc)
                    navController.navigate(direction)
                }
                CP->{
                    val direction = DashboardFragmentDirections.actionDashboardFragmentToChangesPointFragment(resources.getString(R.string.change_point), docId = doc)
                    navController.navigate(direction)
                }
                "APPR"->{
                    val directions = DashboardFragmentDirections.actionDashboardFragmentToListApprovalFragment(resources.getString(R.string.list_approval), docId = doc)
                    navController.navigate(directions)
                }
            }
        }catch (e: Exception){
            Timber.e("Exception : ${e.message}")
        }
    }


    companion object HomeSingleton {
        private var amqpConsumer: AMQPConsumer? = null

        fun startAMQPConsumer(context: Context){
            val url: URL = URL(BuildConfig.BASE_URL)
            val amqpHost = "e-improvement.fastratabuana.co.id"
            val amqpUser = "user"
            val amqpPassword = "user"
            val amqpPort = 81
            val amqpVhost = "/"
            val queueName = "EMP."+ HawkUtils().getDataLogin().USER_ID.toString()

            if (amqpConsumer != null) {
                if (amqpConsumer!!.isConnected){
                    amqpConsumer!!.stop()
                }
                amqpConsumer = null
            }
            amqpConsumer = AMQPConsumer(
                amqpHost,
                amqpPort,
                amqpUser,
                amqpPassword,
                amqpVhost,
                Build.PRODUCT + " " + Build.MODEL, context
            );
            amqpConsumer!!.start(queueName)
        }

        fun stopAMQPConsumer(){
            amqpConsumer!!.stop()
        }
    }
}
