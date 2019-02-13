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
     Handler h = new Handler();
     MainActivity mainActivity;
    public Socket s = null;
    public String address = "myhomerouter.ddns.net";
    public String dataFromSend = null;
//    Button butConnect = (Button) findViewById(R.id.butConnect);
/*    Runnable returnConnect = new Runnable() {
        public void run() {
            butConnect.setText("Подключено");
        }
    };*/
    public TcpClient(MainActivity activity){
        this.mainActivity = activity;
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
            	out.write(data + System.getProperty("line.separator"));
            	out.flush();
            	Log.i("TcpClient", "sent: " + data);
        	} catch (IOException e) {
            	e.printStackTrace();
        	}
    	}
    };

     public void sendData(String dataFromSend){
         this.dataFromSend = dataFromSend;
         try {
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
             out.write(dataFromSend + System.getProperty("line.separator"));
             out.flush();
             Log.i("TcpClient", "sent: " + dataFromSend);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     Runnable send = new Runnable() {
         @Override
         public void run() {
             // BufferedWriter out = null;

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
}
