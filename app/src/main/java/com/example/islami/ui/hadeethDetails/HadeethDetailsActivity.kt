package com.example.islami.ui.hadeethDetails

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.islami.R
import com.example.islami.databinding.ActivityHadeethDetailsBinding
import com.example.islami.ui.Constants
import com.example.islami.ui.model.Hadeeth

class HadeethDetailsActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityHadeethDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHadeethDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        initParams()
        bindHadeeth()
        if (resources.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_NO) {
            viewBinding.mainLayout.background = ContextCompat.getDrawable(this, R.drawable.main_bg)
        } else {
            viewBinding.mainLayout.background = ContextCompat.getDrawable(this, R.drawable.dark_bg)
        }
    }

    private fun initViews() {
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun bindHadeeth() {
        viewBinding.titleTv.text = hadeeth?.title
        viewBinding.hadeethContent.hadeethTV.text = hadeeth?.content
    }

    var hadeeth: Hadeeth? = null

    private fun initParams() {
        if (Build.VERSION.SDK_INT >= 33) {
            hadeeth = intent.getParcelableExtra(Constants.EXTRA_HADEETH, Hadeeth::class.java)
        } else {
            hadeeth = intent.getParcelableExtra(Constants.EXTRA_HADEETH) as Hadeeth?
        }
    }
}