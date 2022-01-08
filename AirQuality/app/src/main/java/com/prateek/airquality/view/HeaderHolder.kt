package com.prateek.airquality.view

import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import com.prateek.airquality.R
import com.prateek.airquality.model.interfaces.IBaseModel
import com.prateek.airquality.model.interfaces.IHeaderModel
import kotlinx.android.synthetic.main.item_view.view.*

class HeaderHolder(itemView: View) : CitiesAQIAdapter.GenericHolder(itemView) {

    override fun bindValue(itemModel: IBaseModel) {
        itemModel as IHeaderModel
        itemView.tv_city.text = itemModel.getCityText()
        itemView.tv_city.typeface = Typeface.DEFAULT_BOLD
        itemView.tv_aqi.text = itemModel.getAQIText()
        itemView.tv_aqi.typeface = Typeface.DEFAULT_BOLD
        itemView.tv_last_updated.text = itemModel.getLastUpdatedText()
        itemView.tv_last_updated.typeface = Typeface.DEFAULT_BOLD
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.header_bg_color))
    }
}