package com.thyme.eatandrun.ui.meal.api.network.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.thyme.todolist.R
import com.thyme.todolist.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentSearchBinding>(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the viewModel
        binding.viewModel = viewModel

        // Set the adapter with clickHandler lambad that tells the viewModel when a item is clicked
        val adapter = SearchItemAdapter(SearchItemAdapter.OnClickListener {
            viewModel.displayAddMeal(it)
        })
        binding.searchRecyclerview.adapter = adapter

        viewModel.searchListMeal.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        // Observe navigateToSelectedFood data and navigate if it isn't null
        // After navigate, set the selectedFood to null so that ViewModel is rdy for another navigation
        viewModel.navigateToSelectedMeal.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(SearchFragmentDirections.actionSearchFragmentToAddMealFragment(it))
                // tell the viewModel we've mafe the navigate call
                viewModel.displayAddMealIsComplete()
            }
        })

        // ProgressBar's Visibility
        viewModel.searchInProgress.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                binding.searchProgressbar.visibility = View.INVISIBLE
            } else {
                binding.searchProgressbar.visibility = View.VISIBLE
            }
        })

        // Food Not Found TV visibility
        viewModel.showMealNotFound.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                binding.searchRecyclerview.visibility = View.VISIBLE
                binding.tvNotFound.visibility = View.INVISIBLE
            } else {
                binding.searchRecyclerview.visibility = View.INVISIBLE
                binding.tvNotFound.visibility = View.VISIBLE
            }
        })


        return binding.root
    }


}
