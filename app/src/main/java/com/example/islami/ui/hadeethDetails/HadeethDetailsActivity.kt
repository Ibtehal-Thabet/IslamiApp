package com.example.islami.ui.hadeethDetails

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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