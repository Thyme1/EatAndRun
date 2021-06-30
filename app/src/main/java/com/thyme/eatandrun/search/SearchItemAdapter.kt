package com.thyme.eatandrun.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thyme.eatandrun.foodNameToShortString
import com.thyme.eatandrun.network.model.Food
import com.thyme.eatandrun.toKcalString
import com.thyme.todolist.databinding.ListItemSearchBinding

class SearchItemAdapter(val onClickListener: OnClickListener) : RecyclerView.Adapter<SearchItemAdapter.ViewHolder>() {

    var data = listOf<Food>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSearchBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Food) {
            binding.searchItemName.text = item.label.foodNameToShortString()
            binding.searchItemKcal.text = item.nutrients.kcal.toKcalString()
        }

    }


    class OnClickListener(val clickListener: (food: Food) -> Unit) {
        fun onClick(food: Food) = clickListener(food)
    }


}