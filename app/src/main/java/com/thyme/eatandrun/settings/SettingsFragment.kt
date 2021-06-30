package com.thyme.eatandrun.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thyme.todolist.R
import com.thyme.todolist.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(R.layout.fragment_settings), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        activity?.let {
            activity?.window?.setStatusBarColor(it.getColor(R.color.green_dark))
        }


        val spinner: Spinner = binding.spinnerActivity

        val adapter = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.activities,
                android.R.layout.simple_spinner_item
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


}