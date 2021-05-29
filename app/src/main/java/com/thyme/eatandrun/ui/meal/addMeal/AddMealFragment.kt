package com.thyme.eatandrun.ui.meal.addMeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.thyme.todolist.R
import com.thyme.todolist.databinding.AddMealFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMealFragment : Fragment(R.layout.add_meal_fragment) {

    private var _binding: AddMealFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddMealViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddMealFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener { mView ->
            saveNote(mView)
        }

        binding.cancelButton.setOnClickListener {
            binding.editTextMealName.text.clear()
            view.findNavController().navigate(
                R.id.action_addMealFragment_to_mealListFragment
            )
        }
    }

    private fun saveNote(view: View) {
        val mealName = binding.editTextMealName.text.toString()
        val mealGrams = binding.editTextMealGrams.text.toString()

    //TODO API to count calories and get fats,carbs etc

//        if (mealName.isNotEmpty()) {
//            val todo = Meal(
//                0,
//                name = mealName,
//                grams = mealGrams,
//                carbs = ,
//                proteins =  ,
//                fats =  ,
//                kcal=  ,
//                date = ,
//
//            )
//
//            viewModel.insertTask(todo)
//
//            Snackbar.make(
//                view, "Meal Saved Successfully",
//                Snackbar.LENGTH_SHORT
//            ).show()
//
//            view.findNavController().navigate(
//                R.id.action_addMealFragment_to_mealListFragment
//            )
//
//        } else {
//            val toast = Toast.makeText(
//                activity,
//                "Meal name can not be empty",
//                Toast.LENGTH_SHORT
//            )
//            toast.setGravity(Gravity.CENTER, 0, 0)
//            toast.show()
//        }
    }
}






