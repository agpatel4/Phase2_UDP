package com.example.phase2_udp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {


    EditText message;
    Button send;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (EditText) findViewById(R.id.user_input);
        send = (Button) findViewById(R.id.send_button);


        Thread myThread = new Thread (new ServerThread());
        myThread.start();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v)
            {
                byte [] buf;
                String data = message.getText().toString();
                Log.d("Capstone", data);
                MessageSender messageSender = new MessageSender();
                buf = data.getBytes();
             //   Log.d("Capstione:data sent is ", data);
                String test = buf.toString();

                //messageSender.execute(buf);
                try {
                    client();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("Capstione:data sent is ", test);
            }
        });


    }

    class ServerThread implements  Runnable {
        Socket s;
        ServerSocket ss;
        InputStream is;
        InputStreamReader isr;
        BufferedReader buffer;
        Handler h = new Handler();
        String received;

        @Override
        public void run ()
        {
            try
            {
                ss = new ServerSocket(2000);
                while (true)
                {
                    s = ss.accept();
                    is = s.getInputStream();
                    isr = new InputStreamReader(is);
                    buffer = new BufferedReader(isr);
                    received = buffer.readLine();

                    h.post(new Runnable() {
                            @Override
                        public void run() {
                                System.out.println("received");
                                Log.d("capstone", received);
                }
                    });
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }


   public void send(View v)
   {
       MessageSender messageSender = new MessageSender();
       String x = message.getText().toString();
       byte [] y;
       y = x.getBytes();
       messageSender.execute(y);
   }

   public void client() throws IOException{
        DatagramSocket client_socket = new DatagramSocket(1234);
       InetAddress IPAddress = InetAddress.getByName("192.168.1.97");
       String send_data = "Test";
       byte [] input;
       while (true)
       {
           input = send_data.getBytes();
           DatagramPacket send_packet = new DatagramPacket(input, input.length, IPAddress, 50000);
           client_socket.send(send_packet);

           client_socket.close();
       }

   }
}

