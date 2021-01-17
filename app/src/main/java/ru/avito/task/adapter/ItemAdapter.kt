package ru.avito.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.avito.task.R

import ru.avito.task.model.Item

class ItemAdapter : ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffUtilCallback()) {

    private lateinit var onItemClickListener: ((position: Int) -> Unit)

    fun setOnItemClickListener(listener: (position: Int) -> Unit){
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val number = v.findViewById<TextView>(R.id.number)

        private val btnClose = v.findViewById<ImageView>(R.id.btnClose)
        fun bind(item: Item) {
            number.text = item.title.toString()
            btnClose.setOnClickListener {
                onItemClickListener(adapterPosition)
            }
        }
    }


    class ItemDiffUtilCallback : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Item, newItem: Item) = true
    }
}


