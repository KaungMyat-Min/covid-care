package me.justdeveloper.carecovid.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<M : Any>(itemView: View) :
    RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

    lateinit var data: M

    open fun setupUi() {
        getClickableViews(itemView)?.forEach { it.setOnClickListener(this) }
    }

    /**
     * return views that can be clicked
     *
     * @param itemView
     * @return
     */
    abstract fun getClickableViews(itemView: View): List<View>?

    abstract override fun onClick(v: View?)

    abstract fun updateUi(data: M)
}
