package com.prateek.airquality.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.Entry
import com.prateek.airquality.model.interfaces.ICityAQIModel
import com.prateek.airquality.model.interfaces.IDataManager
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CityAQILevelViewModel(dataManager: IDataManager) : BaseViewModel(dataManager) {

    private var mSelectedCity: String? = null

    private val cityAqiLevels: MutableLiveData<ArrayList<Entry>> by lazy {
        MutableLiveData<ArrayList<Entry>>()
    }

    override fun onPublishData(cityAQIList: List<ICityAQIModel>) {
        Timber.d("onPublishData")
        cityAQIList.forEach {
            if (mSelectedCity == it.getCityText().trim()) {
                dataManager.setAQILevelForParticularCity(it.getCityText().trim(), it)
            }
        }
        refreshCityAqiLevels()
    }

    fun showAqiLevelForParticularCity(city: String): LiveData<ArrayList<Entry>> {
        this.mSelectedCity = city.trim()
        return cityAqiLevels
    }

    fun hideAqiLevelForParticularCity() {
        this.mSelectedCity = null
        dataManager.resetAQILevelForParticularCity()
    }

    private fun refreshCityAqiLevels() {
        if (mSelectedCity != null) {
            val cityAqiLevels = dataManager.getAQILevelsForParticularCity(mSelectedCity!!)
            if (!cityAqiLevels.isNullOrEmpty()) {
                Timber.d("refreshCityAqiLevels size : ${cityAqiLevels.size}")
                val baseXVale: Long = cityAqiLevels[0].getLastUpdatedTimeStamp()
                val entries: ArrayList<Entry> = arrayListOf()
                cityAqiLevels.forEach {
                    entries.add(
                        Entry(
                            TimeUnit.MILLISECONDS.toSeconds(it.getLastUpdatedTimeStamp() - baseXVale).toFloat(),
                            it.getAQIValue().toFloat()
                        )
                    )
                }
                this.cityAqiLevels.postValue(entries)
            }
        }

    }
}