package com.example.kamran.eleganttheme;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    TextView bytes,cus,sell;
    EditText usr,pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       /* usr = (EditText) findViewById(R.id.usrusr);
        pswd = (EditText)findViewById(R.id.passwrd);*/
        /*lin = (TextView)findViewById(R.id.logiin);*/
        cus = (TextView)findViewById(R.id.customer);
        sell = (TextView)findViewById(R.id.seller);
        bytes = (TextView)findViewById(R.id.bytes);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        bytes.setTypeface(custom_font);
      /*  pswd.setTypeface(custom_font);*/
        cus.setTypeface(custom_font);
        /*lin.setTypeface(custom_font);*/
      /*  usr.setTypeface(custom_font);*/
        cus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(login.this,signup.class);
                startActivity(it);
            }
        });

        sell.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(login.this,seller.class);
                startActivity(it);
            }
        });
    }
}
