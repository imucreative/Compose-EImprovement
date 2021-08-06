package com.fastrata.eimprovement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.fastrata.eimprovement.features.utils.Tools

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Tools.setSystemBarColor(this, R.color.colorMainEImprovement)
        Tools.setSystemBarLight(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting_menu -> {
                /*Intent(this, SettingsActivity::class.java).also {
                    startActivity(it)
                }*/
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
