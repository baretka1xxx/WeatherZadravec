package com.example.weatherzadravec.ui.base

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T, VH : BaseRecyclerViewAdapter<T, VH>.BaseVH> : RecyclerView.Adapter<VH>(), BaseAdapter<T> {

    var listener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun getItemAt(index: Int): T? {
        return items?.get(index)
    }

    override fun getItemPosition(item: T): Int {
        return items?.indexOf(item) ?: -1
    }

    override var items: List<T>? = null
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.doBind(position)
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.doBind(position, payloads)
        }
    }

    abstract inner class BaseVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun doBind(position: Int) {
            val item = getItemAt(position)
            item?.let {
                performBind(it)
            }
        }

        open fun doBind(position: Int, payloads: MutableList<Any>) {
            val item = getItemAt(position)
            item?.let {
                performBind(it, payloads)
            }
        }

        abstract fun performBind(item: T)

        open fun performBind(item: T, payloads: MutableList<Any>) {

        }
    }

    abstract class OnItemClickListener {

        open fun onItemClick(
            adapter: BaseRecyclerViewAdapter<*, *>, viewHolder: RecyclerView.ViewHolder, position: Int
        ) {

        }

        open fun onPopupItemSelected(
            adapter: BaseRecyclerViewAdapter<*, *>, viewHolder: RecyclerView.ViewHolder, position: Int, itemSelected: String
        ) {

        }

        open fun onItemLongClick(
            adapter: BaseRecyclerViewAdapter<*, *>, viewHolder: RecyclerView.ViewHolder, position: Int
        ) {

        }

        open fun onViewClick(
            adapter: BaseRecyclerViewAdapter<*, *>, viewHolder: RecyclerView.ViewHolder, view: View, position: Int
        ) {

        }

        open fun onViewLongClick(
            adapter: BaseRecyclerViewAdapter<*, *>, viewHolder: RecyclerView.ViewHolder, view: View, position: Int
        ) {

        }
    }
}