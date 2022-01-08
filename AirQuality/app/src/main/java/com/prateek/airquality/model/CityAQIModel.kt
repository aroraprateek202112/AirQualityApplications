package com.prateek.airquality.model

import com.prateek.airquality.model.interfaces.IBaseModel
import com.prateek.airquality.model.interfaces.ICityAQIModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CityAQIModel(
    val city: String,
    val aqi: Double,
    val timeStamp: Long = System.currentTimeMillis()
) : ICityAQIModel {

    override fun getType(): Int = IBaseModel.TYPE_ITEM

    override fun getCityText(): String = city

    override fun getAQIText(): String = String.format("%.2f", aqi)

    override fun getAQIValue(): Double = aqi

    override fun getLastUpdatedTimeStamp(): Long = timeStamp

    override fun toString(): String {
        return "CityAQIModel{city : $city, aqi : ${getAQIText()}}"
    }
}
