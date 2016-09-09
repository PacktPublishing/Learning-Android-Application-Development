package com.packt.rrafols.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class MainActivityGlide extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ImageView imageViewLeft = (ImageView) findViewById(R.id.iv1);
        ImageView imageViewRight = (ImageView) findViewById(R.id.iv2);

        Glide.with(this).load("http://labs.rafols.org/image1.jpg").into(imageViewLeft);
        Glide.with(this).load("http://labs.rafols.org/image2.jpg").into(imageViewRight);
    }
}
