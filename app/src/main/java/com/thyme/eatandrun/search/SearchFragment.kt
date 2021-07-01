package com.thyme.eatandrun.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.thyme.todolist.R
import com.thyme.todolist.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentSearchBinding>(inflater, R.layout.fragment_search, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = SearchItemAdapter(SearchItemAdapter.OnClickListener {
            viewModel.displayAddFood(it)
        })
        binding.searchRecyclerview.adapter = adapter

        viewModel.searchListFood.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })


        viewModel.navigateToSelectedFood.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToAddFoodFragment(it))
                viewModel.displayAddFoodIsComplete()
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
        viewModel.showFoodNotFound.observe(viewLifecycleOwner, Observer {
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
