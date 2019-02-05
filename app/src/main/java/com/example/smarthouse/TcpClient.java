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

 private void runTcpClient() {
                            ServerSocket ss = null;
                            try {
                                Socket s = new Socket("myhomerouter.ddns.net", 9090);
                                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                                //send output msg
                                String outMsg = "TCP connecting to " + "9090" + System.getProperty("line.separator");
                                out.write(outMsg);
                                out.flush();
                                Log.i("TcpClient", "sent: " + outMsg);
                                //accept server response
                                String inMsg = in .readLine() + System.getProperty("line.separator");
                                Log.i("TcpClient", "received: " + inMsg);
                                //close connection
                                s.close();
                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
