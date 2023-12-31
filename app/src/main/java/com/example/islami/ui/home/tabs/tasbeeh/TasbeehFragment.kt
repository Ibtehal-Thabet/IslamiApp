package com.example.islami.ui.home.tabs.tasbeeh

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.islami.R
import com.example.islami.databinding.FragmentTasbeehBinding
import com.example.islami.ui.Constants

class TasbeehFragment : Fragment() {

    lateinit var viewBinding: FragmentTasbeehBinding
    var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTasbeehBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.sebhaNumber.text = "$counter"
        viewBinding.sebhaTV.text = Constants.TASBEEH

        if (resources.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_NO) {
            viewBinding.sebhaHeadImage.setImageResource(R.drawable.head_of_seb7a)
            viewBinding.sebhaImage.setImageResource(R.drawable.body_of_seb7a)
        } else {
            viewBinding.sebhaHeadImage.setImageResource(R.drawable.head_of_seb7a_dark)
            viewBinding.sebhaImage.setImageResource(R.drawable.body_of_seb7a_dark)
        }

        onClickSeb7a()
    }

    private fun onClickSeb7a() {
        viewBinding.sebhaImage.setOnClickListener {
            seb7aImageRotation()
            changeCounter()
        }
    }

    private fun seb7aImageRotation() {
        viewBinding.sebhaImage.rotation = viewBinding.sebhaImage.getRotation() + 10
    }

    private fun changeCounter() {
        counter++
        viewBinding.sebhaNumber.text = "$counter"
        when (viewBinding.sebhaTV.text) {
            Constants.TASBEEH -> {
                if (counter == 33) {
                    counter = 0
                    viewBinding.sebhaNumber.text = "$counter"
                    viewBinding.sebhaTV.text = Constants.HAMD
                }
            }
            Constants.HAMD -> {
                if (counter == 33) {
                    counter = 0
                    viewBinding.sebhaNumber.text = "$counter"
                    viewBinding.sebhaTV.text = Constants.TAKBEER
                }
            }
            Constants.TAKBEER -> {
                if (counter == 33) {
                    counter = 0
                    viewBinding.sebhaNumber.text = "$counter"
                    viewBinding.sebhaTV.text = Constants.TAWHEED
                }
            }
            Constants.TAWHEED -> {
                if (counter == 33) {
                    counter = 0
                    viewBinding.sebhaNumber.text = "$counter"
                    viewBinding.sebhaTV.text = Constants.HAWQALA
                }
            }
            else -> {
                if (counter == 33) {
                    counter = 0
                    viewBinding.sebhaNumber.text = "$counter"
                    viewBinding.sebhaTV.text = Constants.TASBEEH
                }
            }
        }

    }

}