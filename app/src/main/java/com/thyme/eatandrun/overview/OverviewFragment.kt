package com.thyme.eatandrun.overview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.thyme.eatandrun.MainActivity
import com.thyme.eatandrun.database.FoodDatabase
import com.thyme.eatandrun.getCurrentDayString
import com.thyme.todolist.R
import com.thyme.todolist.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    private lateinit var listenerCurrent: OnOverviewCurrent
    private lateinit var viewModel: OverviewViewModel
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentOverviewBinding>(inflater, R.layout.fragment_overview, container, false)
        binding.lifecycleOwner = this
        activity?.let { activity?.window?.setStatusBarColor(it.getColor(R.color.blue_dark)) };


        binding.btnOverviewToSearch.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_overviewFragment_to_searchFragment)
        )

        val application = requireNotNull(activity).application
        val dataSource = FoodDatabase.getInstance(application).foodDatabaseDao

        val viewModelFactory = OverviewViewModelFactory(dataSource,application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(OverviewViewModel::class.java)


        binding.viewModel = viewModel

        val adapter = OverviewRVAdapter(OverviewRVAdapter.OnBtnDeleteListener {
            viewModel.onDeleteChoosedFood(it)
        })

        binding.rvOverview.adapter = adapter

        viewModel.foods.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val activity = activity as MainActivity
        val selectedDate = activity.selectedDate
        if (selectedDate != null) {
            viewModel.setDateSelected(selectedDate)
            if (selectedDate != getCurrentDayString()) {
                // Hide search btn
                binding.btnOverviewToSearch.visibility = View.INVISIBLE
            } else {
                binding.btnOverviewToSearch.visibility = View.VISIBLE
            }

        }

        listenerCurrent.onOverviewCurrent(true)
    }

    override fun onStop() {
        super.onStop()
        listenerCurrent.onOverviewCurrent(false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnOverviewCurrent) {
            listenerCurrent = context
        } else {
            throw ClassCastException(
                context.toString() + " must implement OnOverviewCurrent.")
        }
    }

    interface OnOverviewCurrent {
        fun onOverviewCurrent(isCurrent: Boolean)
    }

}
