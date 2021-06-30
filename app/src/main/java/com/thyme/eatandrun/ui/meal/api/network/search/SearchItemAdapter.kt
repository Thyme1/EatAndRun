package com.thyme.eatandrun.ui.meal.api.network.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thyme.eatandrun.ui.meal.api.network.model.Meal
import com.thyme.eatandrun.utils.mealNameToShortString
import com.thyme.eatandrun.utils.toKcalString
import com.thyme.todolist.databinding.ListItemSearchBinding


class SearchItemAdapter(val onClickListener: OnClickListener) :
    RecyclerView.Adapter<SearchItemAdapter.ViewHolder>() {

    var data = listOf<Meal>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchItemAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SearchItemAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater
//                    .inflate(R.layout.list_item_search, parent, false)
                val binding = ListItemSearchBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

        fun bind(item: Meal) {
//            val res = binding.context.resources

//            binding.searchItemName.text = convertFoodNameToShortString(item.label)
            binding.searchItemName.text = item.label.mealNameToShortString()
//            binding.searchItemKcal.text = convertFoodKcalDoubletoString(item.nutrients.kcal)
            binding.searchItemKcal.text = item.nutrients.kcal.toKcalString()
        }

    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Food]
     * associated with the current item to the [onClick] function.
     * It will be called in onBindViewHolder
     * @param clickListener lambda that will be called with the current [Food]
     */
    class OnClickListener(val clickListener: (food: Meal) -> Unit) {
        fun onClick(food: Meal) = clickListener(food)
    }


}