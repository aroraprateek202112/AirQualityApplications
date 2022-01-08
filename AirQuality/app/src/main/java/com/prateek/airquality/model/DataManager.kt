package com.prateek.airquality.model

import com.prateek.airquality.model.interfaces.IBaseModel
import com.prateek.airquality.model.interfaces.ICityAQIModel
import com.prateek.airquality.model.interfaces.IDataManager
import timber.log.Timber

class DataManager : IDataManager {

    private val citiesAQIMap: MutableMap<String, ICityAQIModel> = mutableMapOf()

    private var selectedCity: String? = null
    private val cityAQILevelList: MutableList<ICityAQIModel> = mutableListOf()

    override fun addCitiesAQI(citiesAQIList: List<ICityAQIModel>) {
        Timber.d("addCitiesAQI")
        citiesAQIList.forEach {
            citiesAQIMap[it.getCityText().trim()] = it
        }
    }

    override fun getCitiesAQI(): MutableList<IBaseModel> {
        return citiesAQIMap.values.toMutableList()
    }

    override fun resetAQILevelForParticularCity() {
        Timber.d("resetAQILevelForParticularCity")
        this.selectedCity = null
        this.cityAQILevelList.clear()
    }

    override fun setAQILevelForParticularCity(city: String, cityAQIModel: ICityAQIModel) {
        Timber.d("getAQILevelsForParticularCity city: $city")
        this.selectedCity = city

        cityAQILevelList.add(cityAQIModel)
    }

    override fun getAQILevelsForParticularCity(city: String): List<ICityAQIModel> {
        Timber.d("getAQILevelsForParticularCity city: $city")
        return if (selectedCity == city) {
            cityAQILevelList
        } else mutableListOf()
    }


}