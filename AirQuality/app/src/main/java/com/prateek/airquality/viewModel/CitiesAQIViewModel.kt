package com.prateek.airquality.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prateek.airquality.model.interfaces.IBaseModel
import com.prateek.airquality.model.interfaces.ICityAQIModel
import com.prateek.airquality.model.interfaces.IDataManager
import timber.log.Timber

class CitiesAQIViewModel(dataManager: IDataManager) : BaseViewModel(dataManager) {

    private val cityAqis: MutableLiveData<MutableList<IBaseModel>> by lazy {
        MutableLiveData<MutableList<IBaseModel>>()
    }

    fun getCityAqis() : LiveData<MutableList<IBaseModel>> = cityAqis

    private fun refreshCitiesAqi() {
        val list = dataManager.getCitiesAQI()
        Timber.d("refreshCityAqis size : ${list.size}")
        cityAqis.postValue(list)
    }

    override fun onPublishData(cityAQIList: List<ICityAQIModel>) {
        Timber.d("onPublishData")
        dataManager.addCitiesAQI(cityAQIList)
        refreshCitiesAqi()
    }


}