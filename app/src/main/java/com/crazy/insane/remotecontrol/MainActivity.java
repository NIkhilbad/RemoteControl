package com.crazy.insane.remotecontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    OutputStream output;
    InputStream input;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothSocket socket;
        final Vibrator vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Toast.makeText(this, "Connect to a device and relaunch the app", Toast.LENGTH_LONG).show();
            }
            else{
                try {
                    socket = queryPairedDevices(mBluetoothAdapter);
                    if(socket!=null){
                    output = socket.getOutputStream();
                    input = socket.getInputStream();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        ImageButton up = (ImageButton) findViewById(R.id.upButton);
        ImageButton down = (ImageButton) findViewById(R.id.downButton);
        ImageButton left = (ImageButton) findViewById(R.id.leftButton);
        ImageButton right = (ImageButton) findViewById(R.id.rightButton);
        Button stop = (Button) findViewById(R.id.stopButton);

        //UP button 
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    vib.vibrate(30);
                    if(output!=null)
                    output.write('f');
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    vib.vibrate(30);
                    if(output!=null)
                    output.write('b');
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    vib.vibrate(30);
                    if(output!=null)
                    output.write('l');
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    vib.vibrate(30);
                    if(output!=null)
                    output.write('r');
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    vib.vibrate(30);
                    if(output!=null)
                    output.write('s');
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private BluetoothSocket queryPairedDevices(BluetoothAdapter b) throws IOException {

        Set<BluetoothDevice> pairedDevices = b.getBondedDevices();
        UUID my_uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        BluetoothDevice btdevice;
        BluetoothSocket socket = null;

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                //String deviceName = device.getName();
                //String deviceHardwareAddress = device.getAddress(); // MAC address
                btdevice = device;
                socket = btdevice.createRfcommSocketToServiceRecord(my_uuid);
                socket.connect();
            }
        }
        else{
            Toast.makeText(this, "There are no paired devices", Toast.LENGTH_LONG).show();
        }
        return socket;
    }
}
