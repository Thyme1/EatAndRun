package com.thyme.eatandrun.ui.meal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thyme.eatandrun.data.MealModel
import com.thyme.todolist.databinding.ItemMealBinding


class MealAdapter : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {


    inner class MealViewHolder(val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<MealModel>() {

        override fun areItemsTheSame(oldItem: MealModel, newItem: MealModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MealModel, newItem: MealModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)
    var mMealModel: List<MealModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            ItemMealBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val currentMeal = mMealModel[position]

        holder.binding.apply {
            kcalNumber.text = currentMeal.kcal.toString()
            productsList.text = currentMeal.name

        }


    }

    override fun getItemCount() = mMealModel.size


}