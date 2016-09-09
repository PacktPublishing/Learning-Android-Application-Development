package com.packt.rrafols.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by raimon on 31/08/2016.
 */

public class ImageUtils {
    private static final String TAG = ImageUtils.class.getName();

    public static Bitmap decodeSampleBitmapFromAssets(Context ctx, String name, int width, int height) {
        InputStream is = null;
        try {
            is = ctx.getAssets().open(name);
            return decodeSampleBitmapFromStream(is, width, height);
        } catch(IOException e) {
            Log.w(TAG, "Error loading image " + name);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    // Nothing we can do about it
                }
            }
        }
    }

    public static Bitmap decodeSampleBitmapFromStream(InputStream is, int width, int height) {
        // First decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);

        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    // as is from: https://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
