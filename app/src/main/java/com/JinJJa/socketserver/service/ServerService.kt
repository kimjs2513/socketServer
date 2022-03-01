package com.JinJJa.socketserver.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.JinJJa.socketserver.communication.TcpSeverManager

class ServerService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("test1", "서비스 생성")
        var TcpThread = TcpSeverManager().TcpServerThread()
        TcpThread.start()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        var TcpThread = TcpSeverManager().TcpServerThread()
        TcpThread.interrupt()
        Log.d("test1", "서비스 종료")
        super.onDestroy()
    }
}