 package com.example.smarthouse;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.smarthouse.MainActivity;

 public class TcpClient {


  
     MainActivity mainActivity;
     BufferedWriter out;
    public Socket s = null;
    public String address = "myhomerouter.ddns.net";
    public String dataFromSend = null;

    public TcpClient(MainActivity activity){
        this.mainActivity = activity;
    }

     public BufferedWriter getWriter(){
         return this.out;
     }

     public Socket getSocket(){
         return this.s;
     }

    Runnable connect = new Runnable() {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                msg.setData(bundle);
                Log.i("TcpClient", "connect start");
                InetAddress serverAddr = InetAddress.getByName(address);
                s = new Socket(serverAddr.getHostAddress(), 9090);
                bundle.putString("status", "connected");
                mainActivity.h.sendMessage(msg);
                Log.i("TcpClient", "connect successful");

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable sendData = new Runnable() {
        @Override
        public void run() {
                try {
                    out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    out.write(dataFromSend + System.getProperty("line.separator"));
                    out.flush();
                    Log.i("TcpClient", "sent: " + dataFromSend);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    };


    public String get() {
        String data = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            data = in.readLine() + System.getProperty("line.separator");
            Log.i("TcpClient", "received: " + data);
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

     Handler h = new Handler() {
         public void handleMessage(android.os.Message msg) {
             // обновляем TextView
             Bundle bundle = msg.getData();
             if (bundle.getString("send") != null)
             {
                 Log.i("tcp","Handler geted msg");
                 dataFromSend = bundle.getString("send");
                 try {
                     Thread send = new Thread(sendData);
                     send.start();
                     //sendData(bundle.getString("send"));
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                // sendData(bundle.getString("send"));
             }
         };
     };
}
