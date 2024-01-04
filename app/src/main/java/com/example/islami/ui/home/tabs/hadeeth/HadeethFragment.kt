package com.example.islami.ui.home.tabs.hadeeth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.islami.databinding.FragmentHadeethBinding
import com.example.islami.ui.Constants
import com.example.islami.ui.hadeethDetails.HadeethDetailsActivity
import com.example.islami.ui.model.Hadeeth

class HadeethFragment : Fragment() {

    lateinit var viewBinding: FragmentHadeethBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHadeethBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        bindHadeethList()
    }

    override fun onStart() {
        super.onStart()
        loadHadeethFile()
    }

    private fun bindHadeethList() {
        adapter.bindItems(hadeethList)
    }

    val hadeethList = mutableListOf<Hadeeth>()

    private fun loadHadeethFile() {
        val fileContent =
            requireActivity().assets.open("ahadeth.txt").bufferedReader().use { it.readText() }
        val singleHadeethList = fileContent.trim().split("#")
        singleHadeethList.forEach { ele ->
            val lines = ele.trim().split("\n")
            val title = lines[0]
            val content = lines.joinToString("\n")
            val hadeeth = Hadeeth(title, content)
            hadeethList.add(hadeeth)
        }
    }

    lateinit var adapter: HadeethNameRecyclerAdapter
    private fun initRecyclerView() {
        adapter = HadeethNameRecyclerAdapter(null)
        viewBinding.recyclerView.adapter = adapter
        adapter.onItemClickListener =
            HadeethNameRecyclerAdapter.OnItemClickListener { position, hadeeth ->
                showHadeethDetails(hadeeth)
            }
    }

    private fun showHadeethDetails(hadeeth: Hadeeth) {
        val intent = Intent(context, HadeethDetailsActivity::class.java)
        intent.putExtra(Constants.EXTRA_HADEETH, hadeeth)
        startActivity(intent)
    }
}