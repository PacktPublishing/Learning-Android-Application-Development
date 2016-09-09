package com.packt.rrafols.example;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NetworkUtils {
    private final static String TAG = NetworkUtils.class.getName();
    private static final int BUFFER_SIZE = 4096;

    @Nullable
    public static byte[] loadDataFromUrl(@NonNull String urlString) {
        HttpURLConnection conn = null;
        BufferedInputStream is = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();

            is = new BufferedInputStream(conn.getInputStream());
            return readStream(is);
        } catch (IOException e) {
            Log.w(TAG, "Exception connecting to " + urlString, e);
        } finally {
            if(conn != null) conn.disconnect();
        }
        return null;
    }

    public static byte[] readStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int count;

        while((count = is.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }

        return baos.toByteArray();
    }
}
