package com.JinJJa.socketserver.communication

import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Exception
import java.net.ServerSocket

class TcpSeverManager {
    val port = 55555
    inner class TcpServerThread : Thread(){
        override fun run() {
            try {
                var serverSocket = ServerSocket(port)
                while (true){
                    var socket = serverSocket.accept()
                    Log.d("test1", "socket연결 ${socket}")

                    var clientInput = socket.getInputStream()
                    var clientDIS  = DataInputStream(clientInput)

                    var clientOutput = socket.getOutputStream()
                    var clientDOS = DataOutputStream(clientOutput)

                    var rPPGData = clientDIS.readInt()
                    Log.d("test1", "rPPG: ${rPPGData}")

                    clientDOS.writeInt(200)

                    socket.close()
                    Thread.sleep(1)

                }
            }catch (e : Exception){
                e.printStackTrace()
            }

        }
    }

}