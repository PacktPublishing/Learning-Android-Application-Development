package com.packt.rrafols.example;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MainActivityUIL extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);

        ImageView imageViewLeft = (ImageView) findViewById(R.id.iv1);
        final ImageView imageViewRight = (ImageView) findViewById(R.id.iv2);

        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage("http://labs.rafols.org/image1.jpg", imageViewLeft);
        imageloader.loadImage("http://labs.rafols.org/image2.jpg", new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageViewRight.setImageBitmap(loadedImage);
            }
        });
    }
}
