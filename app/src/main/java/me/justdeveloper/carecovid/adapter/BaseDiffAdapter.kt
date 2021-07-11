package me.justdeveloper.carecovid.adapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffAdapter<M : Any>(
    diffCallback: DiffUtil.ItemCallback<M>
) : BaseAdapter<M>() {

    private val mDiff = AsyncListDiffer(this, diffCallback)

    override fun setNewData(newData: List<M>?) {
        mDiff.submitList(newData)
    }

    override fun getItemCount(): Int {
        return mDiff.currentList.size
    }

    override fun getItemAt(position: Int): M? {
        return mDiff.currentList[position]
    }

    override fun getDataList(): List<M> {
        return mDiff.currentList.toMutableList()
    }
}
