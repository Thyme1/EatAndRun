package com.thyme.eatandrun.ui.meal.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thyme.eatandrun.CalorieCounterApplication

import com.thyme.eatandrun.data.MealModel
import com.thyme.eatandrun.utils.mealNameToShortString
import com.thyme.todolist.R
import com.thyme.todolist.databinding.ItemMealOverviewBinding

class OverviewRVAdapter(val onBtnDeleteListener: OnBtnDeleteListener) : RecyclerView.Adapter<OverviewRVAdapter.ViewHolder>()  {

    var data = listOf<MealModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewRVAdapter.ViewHolder {
        return ViewHolder.from(parent, onBtnDeleteListener)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: OverviewRVAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.click(item)


    }


    class ViewHolder constructor(val binding: ItemMealOverviewBinding, val onBtnDeleteListener: OnBtnDeleteListener)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, onBtnDeleteListener: OnBtnDeleteListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMealOverviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onBtnDeleteListener)
            }
        }

        fun bind(item: MealModel) {
            binding.tvItemOverviewName.text = item.name?.mealNameToShortString()
            binding.tvItemOverviewGrams.text = CalorieCounterApplication.instance.getString(R.string.format_grams, item.grams)
            binding.tvItemOverviewKcal.text = CalorieCounterApplication.instance.getString(R.string.format_total_kcal, item.kcal)
            binding.tvItemOverviewCarbs.text = CalorieCounterApplication.instance.getString(R.string.format_grams, item.carbs)
            binding.tvItemOverviewProteins.text = CalorieCounterApplication.instance.getString(R.string.format_grams, item.proteins)
            binding.tvItemOverviewFats.text = CalorieCounterApplication.instance.getString(R.string.format_grams, item.fats)
        }

        fun click(item: MealModel) {
            binding.btnDeleteItemOverview.setOnClickListener {
                onBtnDeleteListener.onClick(item)
            }
        }


    }


    class OnBtnDeleteListener(val clickListener: (foodModel: MealModel) -> Unit) {
        fun onClick(foodModel: MealModel) = clickListener(foodModel)
    }

}