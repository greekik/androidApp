 package com.example.smarthouse;

import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class TcpClient {
    public Socket s = null;
    public void connect() {
        try {
            s = new Socket("myhomerouter.ddns.net", 9090);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(String data) {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        out.println(data+ System.getProperty("line.separator"));
        out.flush();
        Log.i("TcpClient", "sent: " + data);
    }
    public String get() {
        BufferedWriter in = new BufferedWriter(new InputStreamReader(s.getInputStream()));
        String data = in.readLine() + System.getProperty("line.separator");
        Log.i("TcpClient", "received: " + data);
        s.close();
        return data;
    }
}
