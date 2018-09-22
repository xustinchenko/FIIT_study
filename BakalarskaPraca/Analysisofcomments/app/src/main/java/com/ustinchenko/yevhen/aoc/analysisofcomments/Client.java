package com.ustinchenko.yevhen.aoc.analysisofcomments;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Client {

    private static final String LOG_TAG = "myServerApp";
    private String mServerName = "192.168.137.161";
    private int mServerPort = 1111;
    private Socket mSocket = null;


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeConnection();
    }

    public void sendData(byte[] data) throws Exception {
        if (mSocket == null || mSocket.isClosed()) {
            throw new Exception("Невозможно отправить данные. Сокет не создан или закрыт");
        }     /* Отправка данных */
        try {
            mSocket.getOutputStream().write(data);
            mSocket.getOutputStream().flush();
        } catch (IOException e) {
            throw new Exception("Невозможно отправить данные: " + e.getMessage());
        }
    }

    public void openConnection() throws Exception {
        closeConnection();
        try {
            mSocket = new Socket(mServerName, mServerPort);
        } catch (IOException e) {
            throw new Exception("Невозможно создать сокет: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (mSocket != null && !mSocket.isClosed()) {
            try {
                mSocket.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Невозможно закрыть сокет: " + e.getMessage());
            } finally {
                mSocket = null;
            }
        }
        mSocket = null;
    }


}