package com.akshayaap.mouseremote;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    ImageView imageView_wifiIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Connecting con = new Connecting();
        Thread conThread = new Thread(con);
        conThread.start();

    }

    public class Connecting implements Runnable {
        public void run() {
            try {
                DatagramSocket serverSocket = new DatagramSocket(Config.ECHO_PORT);
                byte[] receiveData = new byte[4];

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                serverSocket.receive(receivePacket);
                InetAddress IPAddress = receivePacket.getAddress();

                Intent myIntent = new Intent(MainActivity.this, TouchPad.class);
                myIntent.putExtra("ip", IPAddress.toString().substring(1));
                startActivity(myIntent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private Layout item;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}