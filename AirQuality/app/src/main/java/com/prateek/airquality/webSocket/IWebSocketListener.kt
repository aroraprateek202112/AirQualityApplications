package com.prateek.airquality.webSocket

interface IWebSocketListener {

    fun onConnectSuccess()

    fun onConnectFailed(throwable: Throwable)

    fun onClose()

    fun onMessage(text: String?)
}