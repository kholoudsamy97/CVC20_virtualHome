package com.example.kamran.eleganttheme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.journaldev.androidarcoredistancecamera.dist;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class signup extends AppCompatActivity {
    EditText passwordd,mobphone,length,height;
    TextView login,draw,take_measure;
   /* static TextView replyMessageTextView;*/
    static final String SERVER_IP = "192.168.1.3"; // The SERVER_IP must be the same in server and client
    static final int PORT = 1234; // You can put any arbitrary PORT value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        length = (EditText) findViewById(R.id.len);/*length */
        height = (EditText) findViewById(R.id.heigh);/* height */
        draw = (TextView)findViewById(R.id.sup);/* draw */
        /*passwordd = (EditText)findViewById(R.id.passwrd);*/

        /*mobphone = (EditText) findViewById(R.id.mobphone);*/
        /*login = (TextView)findViewById(R.id.logiin);/* Back*/

        take_measure = (TextView)findViewById(R.id.measure);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        draw.setTypeface(custom_font);
        length.setTypeface(custom_font);
        /*mobphone.setTypeface(custom_font);*/
       /* passwordd.setTypeface(custom_font);*/
        height.setTypeface(custom_font);
        /*replyMessageTextView = (TextView) findViewById(R.id.replyMessageTextView);*/
      /*  login.setTypeface(custom_font);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signup.this,login.class);
                startActivity(it);
            }
        });*/
        draw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              //  replyMessageTextView.setText("Waiting python reply");

                ConnectPyTask task = new ConnectPyTask(length.getText().toString(),height.getText().toString());
                ConnectPyTask.context = getApplicationContext();
                task.execute();
                //task.execute();
            }
        });

        take_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signup.this, dist.class);
                startActivity(it);
            }
        });
    }
    static class ConnectPyTask extends AsyncTask<Void,Void,Void> {
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