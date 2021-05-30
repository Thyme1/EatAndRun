package com.thyme.eatandrun.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thyme.eatandrun.data.Meal
import com.thyme.eatandrun.ui.meal.addMeal.AddMealViewModel
import com.thyme.todolist.R
import com.thyme.todolist.databinding.FragmentMealListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealListFragment : Fragment(R.layout.fragment_meal_list) {

    private var _binding: FragmentMealListBinding? = null
    private val binding get() = _binding!!
    private lateinit var mealAdapter: MealAdapter
    private val viewModel: AddMealViewModel by viewModels()
    private val mealListViewModel: MealListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.fabAddMeal.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_mealListFragment_to_addMealFragment
            )
        }

        binding.fabDeleteAllMeals.setOnClickListener {
            removeData()
        }

    }


    private fun setupRecyclerView() {

        mealAdapter = MealAdapter()


        binding.rvMealList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mealAdapter
        }




        viewModel.allToDos.observe(requireActivity()) { listMeal ->
            updateUi(listMeal)
            mealAdapter.mMeal = listMeal.asReversed()
        }
    }

    private fun updateUi(list: List<Meal>) {

        if (list.isNotEmpty()) {
            binding.rvMealList.visibility = View.VISIBLE
            binding.cardView.visibility = View.GONE
        } else {
            binding.rvMealList.visibility = View.GONE
            binding.cardView.visibility = View.VISIBLE
        }
    }

    fun removeData() {
        mealListViewModel.deleteAll()
    }


}
