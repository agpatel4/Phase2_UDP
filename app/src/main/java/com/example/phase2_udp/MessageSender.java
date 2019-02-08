package com.example.phase2_udp;
import java.io.DataOutputStream;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MessageSender extends AsyncTask <byte [], Void, Void> {

    DatagramSocket socket;
    int Dest_Port;

    InetAddress address;
    byte[] buf;

    @Override
    protected Void doInBackground(byte []... voids)
    {
        byte[]  data = voids[0];
        try
        {

            socket = new DatagramSocket();
            address = InetAddress.getLocalHost();

            //buf = inp.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 50000);
            socket.send(packet);
            Log.d("This is a test ", data.toString());
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
