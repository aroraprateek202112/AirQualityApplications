package com.prateek.airquality.model

import com.prateek.airquality.model.interfaces.IBaseModel
import com.prateek.airquality.model.interfaces.IHeaderModel

class HeaderModel(
    private val city: String,
    private val aqi: String,
    private val lastUpdated: String
) : IHeaderModel {

    override fun getType(): Int = IBaseModel.TYPE_HEADER

    override fun getCityText(): String = city

    override fun getAQIText(): String = aqi

    override fun getLastUpdatedText(): String = lastUpdated

}