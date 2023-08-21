package com.example.islami.ui.chapterDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }

    private fun initViews() {
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        viewBinding.titleTv.text = chapterName
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
        bindVerses(lines)
    }

    lateinit var adapter: VersesAdapter
    private fun bindVerses(lines: List<String>) {
        adapter = VersesAdapter(lines)
        viewBinding.suraContent.versesRv.adapter = adapter
    }
}