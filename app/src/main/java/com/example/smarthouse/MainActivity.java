package com.example.smarthouse;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Switch;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import com.example.smarthouse.TcpClient;

import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    Handler h;
    TcpClient tcp = null;
    Thread connectThread = null;
    Bundle bundle = new Bundle();
    Thread sendThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });
        final Button butSendOne = (Button) findViewById(R.id.buttonOn);
        final Button butSendNull = (Button) findViewById(R.id.buttonOff);
        final Button butConnect = (Button) findViewById(R.id.butConnect);
        final Switch switchRele1 = (Switch) findViewById(R.id.switchRele1);
        TextView textView = findViewById(R.id.textView);
        final MainActivity activity = this;

        OnClickListener listenerOfbutConnect = new OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.butConnect:
                        Log.i(TAG,"grydudududuhff");
                        tcp = new TcpClient(activity);
                        Thread connectThread = new Thread(tcp.connect);
                        connectThread.start();
                        break;
                }
            }
        };
        butConnect.setOnClickListener(listenerOfbutConnect);

        OnClickListener listenerOfButton = new OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonOn:
                        Log.i(TAG,"BUTTON send rele 1 1");
                        tcp.dataFromSend = "rele 1 1";
                        sendThread = new Thread(tcp.sendData);
                        sendThread.start();
                        break;
                    case R.id.buttonOff:
                        Log.i(TAG,"BUTTON send rele 1 0");
                        tcp.dataFromSend = "rele 1 0";
                        sendThread = new Thread(tcp.sendData);
                        sendThread.start();
                        break;
                }
            }
        };
        butSendOne.setOnClickListener(listenerOfButton);
        butSendNull.setOnClickListener(listenerOfButton);

        CompoundButton.OnCheckedChangeListener rele1Listener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                        Log.i(TAG,"BUTTON send rele 1 1");
                        tcp.dataFromSend = "rele 1 1";
                        sendThread = new Thread(tcp.sendData);
                        sendThread.start();
                } else {
                        Log.i(TAG,"BUTTON send rele 1 0");
                        tcp.dataFromSend = "rele 1 0";
                        sendThread = new Thread(tcp.sendData);
                        sendThread.start();
                }
            }
        };
        switchRele1.setOnCheckedChangeListener(rele1Listener);
        
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
            // обновляем TextView
                Bundle bundle = msg.getData();
                    if (bundle.getString("status")=="connected")
                    {
                        butConnect.setText("Отсоединиться");
                    }
                    if (bundle.getString("status")=="disconnected")
                    {
                        butConnect.setText("Соединиться");
                    }
            };
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
