package com.thyme.eatandrun.addFood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.thyme.eatandrun.database.FoodDatabase
import com.thyme.todolist.R
import com.thyme.todolist.databinding.FragmentAddFoodBinding

class AddFoodFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAddFoodBinding>(inflater, R.layout.fragment_add_food, container, false)
        binding.lifecycleOwner = this

        val food = AddFoodFragmentArgs.fromBundle(requireArguments()).selectedFood
        val dataSource = FoodDatabase.getInstance(application).foodDatabaseDao

        val viewModelFactory = AddFoodViewModelFactory(food, dataSource, application)
        val viewModel = ViewModelProvider(this,
            viewModelFactory).get(AddFoodViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.navigateToOverview.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(AddFoodFragmentDirections.actionAddFoodFragmentToOverviewFragment())
//                Navigation.createNavigateOnClickListener(R.id.action_addFoodFragment_to_overviewFragment)
//                viewModel.onNavigateToOverviewCompleted()
            }
        })

        activity?.let { activity?.window?.setStatusBarColor(it.getColor(R.color.blue_dark)) };





        return binding.root
    }


}
