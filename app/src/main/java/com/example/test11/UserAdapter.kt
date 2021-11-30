package com.example.test11

import android.util.Log
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item_layout.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.CustomViewHolder>() {

    var isHost = true

    var mPosition = 0

    var dataList: List<UserItem> = listOf()
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.update(dataList[position], isHost)
        holder.itemView.setOnLongClickListener {
            mPosition = position
            false
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun update(data: List<UserItem>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: CustomViewHolder) {
        holder.itemView.setOnLongClickListener(null)
        super.onViewRecycled(holder)
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {

        private var mIsHost = true

        fun update(userItem: UserItem, isHost: Boolean) {
            itemView.user_item_text.text = userItem.name
            Log.d("tag1", "UPDATE")
            mIsHost = isHost
            if (isHost) {
                itemView.setOnCreateContextMenuListener(this)
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            if (mIsHost) {
                menu?.add(Menu.NONE, CHANGE_HOST_ID, Menu.NONE, "Сделать хостом")
            }
        }
    }

    companion object {
        const val CHANGE_HOST_ID = 69
    }
}