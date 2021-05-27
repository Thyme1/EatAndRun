package com.thyme.eatandrun.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thyme.eatandrun.R
import com.thyme.eatandrun.databinding.FragmentMealListBinding
import com.thyme.eatandrun.ui.meal.addMeal.AddMealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealListFragment : Fragment(R.layout.fragment_meal_list) {

    private var _binding: FragmentMealListBinding? = null
    private val binding get() = _binding!!
    private lateinit var todoAdapter: MealAdapter
    private val viewModel: AddMealViewModel by viewModels()
    private val taskListViewModel: MealListViewModel by viewModels()

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

        binding.fabAddTask.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_taskListFragment_to_addTaskFragment
            )
        }

        binding.fabClearAllTasks.setOnClickListener {
            removeData()


        }

    }


    private fun setupRecyclerView() {

        todoAdapter = MealAdapter()

        binding.rvTodoList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }

        viewModel.allToDos.observe(requireActivity()) { listTodo ->
            updateUi(listTodo)
            todoAdapter.mTodo = listTodo
        }
    }

    private fun updateUi(list: List<Task>) {
        if (list.isNotEmpty()) {
            binding.rvTodoList.visibility = View.VISIBLE
            binding.cardView.visibility = View.GONE
        } else {
            binding.rvTodoList.visibility = View.GONE
            binding.cardView.visibility = View.VISIBLE
        }
    }

    fun removeData() {
        taskListViewModel.deleteAll()
    }


}
