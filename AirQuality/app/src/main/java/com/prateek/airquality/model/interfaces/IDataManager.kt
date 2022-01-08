package com.prateek.airquality.model.interfaces

interface IDataManager {

    fun addCitiesAQI(citiesAQIList: List<ICityAQIModel>)

    fun getCitiesAQI(): MutableList<IBaseModel>

    fun resetAQILevelForParticularCity()

    fun setAQILevelForParticularCity(city: String, cityAQIModel: ICityAQIModel)

    fun getAQILevelsForParticularCity(city: String): List<ICityAQIModel>
}