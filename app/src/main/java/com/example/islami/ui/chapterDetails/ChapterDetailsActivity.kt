package com.example.islami.ui.chapterDetails

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.islami.R
import com.example.islami.databinding.ActivitySurasDetailsBinding
import com.example.islami.ui.Constants


class ChapterDetailsActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivitySurasDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySurasDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initParma()
        initViews()
        readFile()
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
        viewBinding.titleTv.text = "سورة " + chapterName
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    lateinit var chapterName: String
    var chapterIndex: Int = 0

    private fun initParma() {
        chapterName = intent.getStringExtra(Constants.EXTRA_CHAPTER_NAME) ?: ""
        chapterIndex = intent.getIntExtra(Constants.EXTRA_CHAPTER_INDEX, 0)
    }

    private fun readFile() {
        val fileContent = assets.open("$chapterIndex.txt").bufferedReader().use { it.readText() }
        val lines = fileContent.trim().split("\n")
        val ayatArr = mutableListOf<Int>()
        for (i in lines.indices) {
            ayatArr.add(i + 1)
        }
        bindVerses(lines, ayatArr)
    }

    lateinit var adapter: VersesAdapter
    private fun bindVerses(lines: List<String>, ayatNumbers: List<Int>) {
        adapter = VersesAdapter(lines, ayatNumbers)
        viewBinding.suraContent.versesRv.adapter = adapter
    }
}