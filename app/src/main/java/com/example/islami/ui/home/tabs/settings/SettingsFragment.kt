package com.example.islami.ui.home.tabs.settings

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.islami.R
import com.example.islami.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {

    lateinit var viewBinding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        changeLanguage()
        changeTheme()
    }

    fun checkTheme(): Boolean {
        return resources.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_NO
    }

    fun changeTheme() {
        val modes: Array<String> = resources.getStringArray(R.array.modes)
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, modes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        viewBinding.modeSpinner.adapter = adapter
        viewBinding.modeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    itemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val mode = parent.getItemAtPosition(position).toString()

                    if ((mode.equals("Light") || mode.equals("مشرق")) && checkTheme()) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else if ((mode.equals("Dark") || mode.equals("داكن")) && !checkTheme()) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    private fun changeLanguage() {
        val languages: Array<String> = resources.getStringArray(R.array.languages)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        viewBinding.languageSpinner.adapter = adapter
        viewBinding.languageSpinner.setSelection(0)
        viewBinding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val language = parent?.getItemAtPosition(position).toString()
                    if (language.equals("English") || language.equals("انجليزي")) {
                        updateLanguage("en", requireContext().resources)
                        restartActivity()
                    } else if (language.equals("Arabic") || language.equals("عربي")) {
                        updateLanguage("ar", requireContext().resources)
                        restartActivity()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    fun updateLanguage(language: String, resources: Resources?) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = resources?.configuration
        configuration?.setLocale(locale)
        configuration?.setLayoutDirection(locale)
        resources?.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun restartActivity() {
        activity?.finish()
        activity?.startActivity(activity?.intent)

    }
}