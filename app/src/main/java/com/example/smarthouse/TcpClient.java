 package com.example.smarthouse;

import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


 public class TcpClient {
    public Socket s = null;
    public void connect() {
        try {
            s = new Socket("myhomerouter.ddns.net", 9090);
            Log.i("TcpClient", "connect successful");
//            String connectStatus = "Connect successful to myhomerouter.ddns.net:9090" + System.getProperty("line.separator");
//            textView.setText("R.string.connectStatus");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(String data) {
       // BufferedWriter out = null;
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        out.print(data+ System.getProperty("line.separator"));
        out.flush();
        Log.i("TcpClient", "sent: " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String get() {
        BufferedWriter in = new BufferedWriter(new InputStreamReader(s.getInputStream()));
        String data = in.readLine() + System.getProperty("line.separator");
        Log.i("TcpClient", "received: " + data);
        s.close();
        return data;
    }
}
