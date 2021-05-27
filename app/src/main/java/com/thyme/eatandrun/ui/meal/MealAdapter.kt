package com.thyme.eatandrun.ui.meal

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    inner class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)
    var mTodo: List<Task>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = mTodo[position]

        holder.binding.apply {
            textView.text = currentTask.name
            taskDate.text = currentTask.date
            taskTime.text = currentTask.time

        }

        holder.binding.cbTodo.apply {
            setOnClickListener {
                holder.binding.apply {
                    if (isChecked) {
                        textView.paintFlags =
                            textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                    } else {
                        textView.paintFlags =
                            textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                    }
                }
            }
        }
    }

    override fun getItemCount() = mTodo.size


}