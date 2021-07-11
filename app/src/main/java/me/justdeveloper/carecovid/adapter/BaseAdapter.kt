package me.justdeveloper.carecovid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.justdeveloper.carecovid.viewHolder.BaseViewHolder

abstract class BaseAdapter<M : Any> : RecyclerView.Adapter<BaseViewHolder<M>>() {

    protected val mData: MutableList<M> = mutableListOf()

    protected abstract fun getLayoutId(viewType: Int): Int

    protected abstract fun createViewHolder(view: View, viewType: Int): BaseViewHolder<M>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<M> {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        val viewHolder = createViewHolder(view, viewType)
        viewHolder.setupUi()
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<M>, position: Int) {
        val data = getItemAt(position)
        data?.let {
            holder.data = it
            holder.updateUi(it)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    open fun getDataList(): List<M> = mData

    open fun getItemAt(position: Int) = getDataList().getOrNull(position)

    fun getIndexOf(function: (item: M) -> Boolean) = getDataList().indexOfFirst(function)

    fun getIndexOf(item: M) = getDataList().indexOf(item)

    open fun setNewData(newData: List<M>?) {
        mData.clear()
        newData?.let {
            mData.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun appendNewData(newData: List<M>) {
        val old = mData.size
        val new = newData.size
        mData.addAll(newData)
        notifyItemRangeInserted(old - 1, new)
    }

    fun appendNewData(data: M) {
        mData.add(data)
        notifyItemInserted(mData.size)
    }

    fun removeData(data: M): Boolean {
        val index = getIndexOf(data)
        return if (index > -1) {
            mData.removeAt(index)
            notifyItemRemoved(index)
            true
        } else {
            false
        }
    }

    fun removeListData(newData: MutableList<M>) {
        mData.removeAll(newData)
        notifyDataSetChanged()
    }

    fun clearData() {
        val count = mData.size
        mData.clear()
        notifyItemRangeRemoved(0, count)
    }

    fun isEmpty() = getDataList().isEmpty()
    fun isNotEmpty() = getDataList().isNotEmpty()
}
