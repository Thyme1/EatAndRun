package com.thyme.eatandrun.ui.meal.addMeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.thyme.eatandrun.data.MealDatabase
import com.thyme.todolist.R
import com.thyme.todolist.databinding.FragmentAddMealBinding

class  AddMealFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAddMealBinding>(inflater, R.layout.fragment_add_meal, container, false)
        binding.lifecycleOwner = this

        val meal = AddMealFragmentArgs.fromBundle(requireArguments()).selectedMeal
        val dataSource = MealDatabase.getInstance(application).mealDatabaseDao

        val viewModelFactory = AddMealViewModelFactory(meal, dataSource, application)
        val viewModel = ViewModelProvider(this,
            viewModelFactory).get(AddMealViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.navigateToOverview.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(AddMealFragmentDirections.actionAddMealFragmentToOverviewFragment())
//                Navigation.createNavigateOnClickListener(R.id.action_addFoodFragment_to_overviewFragment)
//                viewModel.onNavigateToOverviewCompleted()
            }
        })




        return binding.root
    }


}






