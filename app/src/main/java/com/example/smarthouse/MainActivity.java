package com.example.smarthouse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

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
        Button butSendOne = (Button) findViewById(R.id.buttonOn);
        Button butSendNull = (Button) findViewById(R.id.buttonOff);
        Button butConnect = (Button) findViewById(R.id.butConnect);
        
        OnClickListener listenerOfbutConnect = new OnClickListener() {
            public void onClick(View v) {
				switch (v.getId()){
					case R.id.butConnect:
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
								String inMsg = in.readLine() + System.getProperty("line.separator");
								Log.i("TcpClient", "received: " + inMsg);
								//close connection
								s.close();
							} catch (UnknownHostException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} 
						}
					break;
				}
            }
        };
 
        butConnect.setOnClickListener(listenerOfbutConnect);
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
