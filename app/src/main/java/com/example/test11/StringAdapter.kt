package com.example.test11

import android.util.Log
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item_layout.view.*

class StringAdapter : RecyclerView.Adapter<StringAdapter.CViewHolder>() {

    var dataList: List<String> = listOf()
        private set

    var onClick : (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CViewHolder {
        return CViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CViewHolder, position: Int) {
        holder.update(dataList[position])
        holder.setOnClickListener {
            onClick.invoke(it)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun update(list: List<String>) {
        dataList = list
        notifyDataSetChanged()
    }

    class CViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setOnClickListener(callback: (Int) -> Unit) {
            itemView.setOnClickListener { callback.invoke(adapterPosition) }
        }

        fun update(item: String) {
            itemView.user_item_text.text = item
        }
    }
}