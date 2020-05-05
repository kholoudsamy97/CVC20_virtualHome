package com.example.pro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    Button connectPythonBut;
    static TextView replyMessageTextView;
    EditText data2SendEditText;
    EditText data2SendEditText1;
    static final String SERVER_IP = "192.168.1.6"; // The SERVER_IP must be the same in server and client
    static final int PORT = 1234; // You can put any arbitrary PORT value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectPythonBut = (Button) findViewById(R.id.connectPythonBut);
        replyMessageTextView = (TextView) findViewById(R.id.replyMessageTextView);
        data2SendEditText = (EditText) findViewById(R.id.data2SendEditText);
        data2SendEditText1 = (EditText) findViewById(R.id.data2SendEditText1);

        connectPythonBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                replyMessageTextView.setText("Waiting python reply");

                ConnectPyTask task = new ConnectPyTask(data2SendEditText.getText().toString(),data2SendEditText1.getText().toString());
                ConnectPyTask.context = getApplicationContext();
                task.execute();
                //task.execute();
            }
        });
    }
    static class ConnectPyTask extends AsyncTask<Void,Void,Void>  {
        static Context context = null;
        String h,l;
        static float startTime = 0, endTime = 0;
        @SuppressLint("WrongThread")
        ConnectPyTask(String length,String hight){
            l=length;
            h=hight;
        }
        @Override
        protected Void doInBackground(Void... data) {
            try {
                startTime = System.currentTimeMillis();
                Socket socket = new Socket(SERVER_IP, PORT); //Server IP and PORT
                Scanner sc = new Scanner(socket.getInputStream());
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                printWriter.write(l); // Send Data
                printWriter.flush();
                printWriter.write(h); // Send Data
                printWriter.flush();

                //replyMessageTextView.setText(sc.next()); // Receive data and edit the text view

            }catch (Exception e){
                Log.d("Exception", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            endTime = System.currentTimeMillis();
            String execTime = String.valueOf((endTime - startTime)/1000.0f);
            Toast.makeText(context, "Time execution is: " + execTime + "s", Toast.LENGTH_SHORT).show();
        }
    }
}
