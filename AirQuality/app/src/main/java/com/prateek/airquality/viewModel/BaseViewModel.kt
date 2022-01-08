package com.prateek.airquality.viewModel

import androidx.lifecycle.ViewModel
import com.prateek.airquality.model.CityAQIModel
import com.prateek.airquality.model.interfaces.ICityAQIModel
import com.prateek.airquality.model.interfaces.IDataManager
import com.prateek.airquality.webSocket.IWebSocketListener
import com.prateek.airquality.webSocket.WebSocketManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import timber.log.Timber

abstract class BaseViewModel(val dataManager: IDataManager) : ViewModel(), IWebSocketListener {

    private val gsonAdapter = Moshi.Builder()
        .build()
        .adapter<List<CityAQIModel>>(
            Types.newParameterizedType(
                List::class.java,
                CityAQIModel::class.java
            )
        )

//    init {
//        subscribeToAQIUpdates()
//
//    }

    abstract fun onPublishData(cityAQIList: List<ICityAQIModel>)

    fun subscribeToAQIUpdates() {
        WebSocketManager.startWebSocket()
        WebSocketManager.registerListener(this)
    }

    fun unSubscribeToAQIUpdates() {
        WebSocketManager.stopWebSocket()
        WebSocketManager.unRegisterListener()
    }

    override fun onConnectSuccess() {
        Timber.d("onConnectSuccess")
    }

    override fun onConnectFailed(throwable: Throwable) {
        Timber.d("onConnectFailed")
    }

    override fun onClose() {
        Timber.d("onClose")
    }

    override fun onMessage(text: String?) {
        Timber.d("onMessage text : $text")
        if (text != null) {
            val cityAQIs = gsonAdapter.fromJson(text)
            if (cityAQIs != null) {
                onPublishData(cityAQIs)
            } else {
                Timber.d("onMessage object list is null")
            }
        }
    }
}