package com.example.islami.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.islami.R
import com.example.islami.databinding.ActivityHomeBinding
import com.example.islami.ui.home.tabs.hadeeth.HadeethFragment
import com.example.islami.ui.home.tabs.quran.QuranFragment
import com.example.islami.ui.home.tabs.radio.RadioFragment
import com.example.islami.ui.home.tabs.tasbeeh.TasbeehFragment

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.content
            .bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_quran -> {
                        showTabFragment(QuranFragment())
                    }
                    R.id.nav_hadeeth -> {
                        showTabFragment(HadeethFragment())
                    }
                    R.id.nav_sebha -> {
                        showTabFragment(TasbeehFragment())
                    }
                    R.id.nav_radio -> {
                        showTabFragment(RadioFragment())
                    }
                }
                true
            }

        viewBinding.content.bottomNav.selectedItemId = R.id.nav_quran
    }

    private fun showTabFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}