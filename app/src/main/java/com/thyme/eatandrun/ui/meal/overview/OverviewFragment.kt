package com.thyme.eatandrun.ui.meal.overview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.thyme.eatandrun.data.MealDatabase
import com.thyme.todolist.R
import com.thyme.todolist.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    private lateinit var listenerCurrent: OnOverviewCurrent
    private lateinit var viewModel: OverviewViewModel
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)
        binding.lifecycleOwner = this

        binding.btnOverviewToSearch.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_overviewFragment_to_searchFragment)
        )

        val application = requireNotNull(activity).application
        val dataSource = MealDatabase.getInstance(application).mealDatabaseDao

        val viewModelFactory = OverviewViewModelFactory(dataSource,application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(OverviewViewModel::class.java)


        binding.viewModel = viewModel

        val adapter = OverviewRVAdapter(OverviewRVAdapter.OnBtnDeleteListener {
            viewModel.onDeleteChoosedMeal(it)
        })

        binding.rvOverview.adapter = adapter

        viewModel.meals.observe(viewLifecycleOwner, {
            it?.let {
                adapter.data = it
            }
        })


        return binding.root
    }

    override fun onStart() {
        super.onStart()


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
