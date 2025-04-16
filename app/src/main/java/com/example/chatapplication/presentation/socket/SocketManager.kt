package com.example.chatapplication.presentation.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

object SocketManager {
    private const val BASE_URL = "http://13.127.170.51:8080"
    private var socket: Socket? = null

    fun initSocket(authToken: String) {
        if (socket == null) {
            val opts = IO.Options().apply {
                forceNew = true
                reconnection = true
                extraHeaders = mapOf("Authorization" to listOf("Bearer $authToken"))
            }
            socket = IO.socket(BASE_URL, opts)
        }
    }

    fun connect() {
        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun emit(event: String, data: JSONObject) {
        socket?.emit(event, data)
    }

    fun on(event: String, listener: Emitter.Listener) {
        socket?.on(event, listener)
    }

    fun off(event: String) {
        socket?.off(event)
    }
}

