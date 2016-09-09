package com.packt.rrafols.example;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivityFresco extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(getApplicationContext());

        setContentView(R.layout.activity_main_fresco);

        Uri uri = Uri.parse("http://labs.rafols.org/image1.jpg");
        ((SimpleDraweeView) findViewById(R.id.iv1)).setImageURI(uri);

        uri = Uri.parse("http://labs.rafols.org/image2.jpg");
        ((SimpleDraweeView) findViewById(R.id.iv2)).setImageURI(uri);
    }
}
