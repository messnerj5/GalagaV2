package com.messner.patel.galaga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {

    Button singlePlayer , multiPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        singlePlayer = (Button) findViewById(R.id.btnSingle);

        setContentView(R.layout.activity_main_screen);


    }
}
