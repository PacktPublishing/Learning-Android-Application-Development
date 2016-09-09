package com.packt.rrafols.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.packt.rrafols.ApplicationName;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(ApplicationName.APPLICATION_FLAVOR);
    }
}
