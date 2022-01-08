package com.prateek.airquality.view

import android.text.format.DateUtils
import android.view.View
import androidx.core.content.ContextCompat
import com.prateek.airquality.R
import com.prateek.airquality.model.interfaces.IBaseModel
import com.prateek.airquality.model.interfaces.ICityAQIModel
import kotlinx.android.synthetic.main.item_view.view.*

class CitiesAQIHolder(itemView: View) : CitiesAQIAdapter.GenericHolder(itemView) {

    private var mListener: IItemClickListener? = null

    override fun bindValue(itemModel: IBaseModel) {
        itemModel as ICityAQIModel
        itemView.tv_city.text = itemModel.getCityText()
        itemView.tv_city.setOnClickListener {
            mListener?.onClick(itemModel.getCityText())
        }
        itemView.tv_aqi.text = itemModel.getAQIText()
        itemView.tv_last_updated.text = DateUtils.getRelativeTimeSpanString(itemModel.getLastUpdatedTimeStamp(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS)
        itemView.setBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                getColorFromAqiLevel(itemModel.getAQIValue())
            )
        )
    }

    fun setItemClickListener(listener: IItemClickListener) {
        this.mListener = listener
    }

    private fun getColorFromAqiLevel(aqiLevel: Double): Int {
        return when (aqiLevel.toInt()) {
            in 0..50 -> R.color.aqi_good
            in 51..100 -> R.color.aqi_satisfactory
            in 101..200 -> R.color.aqi_moderate
            in 201..300 -> R.color.aqi_poor
            in 301..400 -> R.color.aqi_very_poor
            else -> R.color.aqi_severe
        }
    }

    interface IItemClickListener {
        fun onClick(city: String)
    }
}