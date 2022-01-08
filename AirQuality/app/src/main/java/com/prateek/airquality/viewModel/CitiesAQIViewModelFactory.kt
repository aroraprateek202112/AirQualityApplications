package com.prateek.airquality.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prateek.airquality.model.interfaces.IDataManager

class CitiesAQIViewModelFactory(private val dataManager: IDataManager): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CitiesAQIViewModel::class.java)) {
            CitiesAQIViewModel(this.dataManager) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}