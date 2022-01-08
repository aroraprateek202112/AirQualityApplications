package com.prateek.airquality.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prateek.airquality.R
import com.prateek.airquality.model.interfaces.IBaseModel

class CitiesAQIAdapter(
    private var mCityAqis: List<IBaseModel>,
    private var mListener: CitiesAQIHolder.IItemClickListener
) : RecyclerView.Adapter<CitiesAQIAdapter.GenericHolder>() {

    abstract class GenericHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindValue(itemModel: IBaseModel)
    }

    private lateinit var mInflater: LayoutInflater

    fun update(cityAqis: List<IBaseModel>) {
        this.mCityAqis = cityAqis
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericHolder {
        if (!::mInflater.isInitialized) {
            mInflater = LayoutInflater.from(parent.context)
        }
        return if (viewType == IBaseModel.TYPE_HEADER) {
            HeaderHolder(mInflater.inflate(R.layout.item_view, parent, false))
        } else {
            CitiesAQIHolder(mInflater.inflate(R.layout.item_view, parent, false))
        }
    }

    override fun onBindViewHolder(holder: GenericHolder, position: Int) {
        holder.bindValue(mCityAqis[position])
        if (holder is CitiesAQIHolder) {
            holder.setItemClickListener(mListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mCityAqis[position].getType()
    }

    override fun getItemCount(): Int = mCityAqis.size
}