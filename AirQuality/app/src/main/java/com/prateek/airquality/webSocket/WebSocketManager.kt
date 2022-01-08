package com.prateek.airquality.webSocket

import com.prateek.airquality.Constants
import okhttp3.*
import okio.ByteString
import timber.log.Timber
import java.util.concurrent.TimeUnit

object WebSocketManager {

    private const val CLOSE_BY_USER = 1001
    private var mListener: IWebSocketListener? = null
    private var mWebSocket: WebSocket? = null
    private var isConnected = false

    private fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .build()

    fun registerListener(listener: IWebSocketListener) {
        this.mListener = listener
    }

    fun unRegisterListener() {
        this.mListener = null
    }

    fun startWebSocket() {
        if (isConnected) {
            Timber.d("startWebSocket return since already connected")
            return
        }
        Timber.d("startWebSocket")
        val request: Request = Request.Builder().url(Constants.URL).build()
        val okHttpClient: OkHttpClient = getOkHttpClient();

        mWebSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Timber.d("onOpen response.code = ${response.code}")
                isConnected = response.code == 101
                if (isConnected) {
                    mListener?.onConnectSuccess()
                } else {
                    Timber.d("onOpen Fail try reconnect")
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Timber.d("onMessage byteString")
                mListener?.onMessage(bytes.base64())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Timber.d("onMessage string : $text")
                mListener?.onMessage(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Timber.d("onClosing code : $code, reason : $reason")
                isConnected = false
                mListener?.onClose()
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Timber.d("onClosed code : $code, reason : $reason")
                isConnected = false
                mListener?.onClose()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Timber.d("onFailure throwable : ${t.message}")
                isConnected = false
                mListener?.onConnectFailed(t)
            }
        })
        okHttpClient.dispatcher.executorService.shutdown()
    }

    fun stopWebSocket() {
        if (!isConnected) {
            Timber.d("stopWebSocket return since not connected")
            return
        }
        Timber.d("stopWebSocket")
        mWebSocket?.close(CLOSE_BY_USER, "User Intervention")
        isConnected = false
    }
}