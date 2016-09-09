package com.packt.rrafols.example;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class MainActivityPicasso extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ImageView imageViewLeft = (ImageView) findViewById(R.id.iv1);
        ImageView imageViewRight = (ImageView) findViewById(R.id.iv2);


        Picasso.with(this).load("http://labs.rafols.org/image1.jpg").into(imageViewLeft);
        Picasso.with(this)
                .load("http://labs.rafols.org/image2.jpg")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        int size = Math.min(source.getWidth(), source.getHeight());
                        int x = (source.getWidth() - size) / 2;
                        int y = (source.getHeight() - size) / 2;
                        Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
                        if (result != source) {
                            source.recycle();
                        }
                        return result;
                    }

                    @Override
                    public String key() {
                        return "crop_square_transformation";
                    }
                })
                .resize(100, 100)
                .centerCrop()
                .into(imageViewRight);
    }
}
