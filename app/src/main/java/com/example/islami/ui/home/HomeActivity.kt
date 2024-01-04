package com.example.islami.ui.home

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.islami.R
import com.example.islami.databinding.ActivityHomeBinding
import com.example.islami.ui.home.tabs.hadeeth.HadeethFragment
import com.example.islami.ui.home.tabs.quran.QuranFragment
import com.example.islami.ui.home.tabs.radio.RadioFragment
import com.example.islami.ui.home.tabs.settings.SettingsFragment
import com.example.islami.ui.home.tabs.tasbeeh.TasbeehFragment

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeBinding
    var isDarkTheme = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.content
            .bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_quran -> {
                        showTabFragment(QuranFragment())
                        viewBinding.themeIcon.isVisible = true
                    }

                    R.id.nav_hadeeth -> {
                        showTabFragment(HadeethFragment())
                        viewBinding.themeIcon.isVisible = true
                    }

                    R.id.nav_sebha -> {
                        showTabFragment(TasbeehFragment())
                        viewBinding.themeIcon.isVisible = true
                    }

                    R.id.nav_radio -> {
                        showTabFragment(RadioFragment())
                        viewBinding.themeIcon.isVisible = true
                    }

                    R.id.nav_settings -> {
                        showTabFragment(SettingsFragment())
                        viewBinding.themeIcon.isVisible = false
                    }
                }
                true
            }

        viewBinding.content.bottomNav.selectedItemId = R.id.nav_quran

        viewBinding.themeIcon.setOnClickListener {
            if (resources.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        toggleTheme()
    }

    private fun showTabFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun toggleTheme() {
        if (resources.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_NO) {
            isDarkTheme = false
            viewBinding.constraintLayout.background =
                ContextCompat.getDrawable(this, R.drawable.main_bg)
            viewBinding.themeIcon.setImageResource(R.drawable.ic_light_mode)
        } else {
            isDarkTheme = true
            viewBinding.constraintLayout.background =
                ContextCompat.getDrawable(this, R.drawable.dark_bg)
            viewBinding.themeIcon.setImageResource(R.drawable.ic_dark_mode)
        }
    }

}