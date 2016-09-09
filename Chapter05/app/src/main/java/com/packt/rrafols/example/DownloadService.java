package com.packt.rrafols.example;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class DownloadService extends IntentService {
    private static final String TAG = DownloadService.class.getName();

    public static final String DOWNLOAD_PAYLOAD = "DownloadService.DOWNLOAD_PAYLOAD";
    public static final String DOWNLOAD_URL = "DownloadService.DOWNLOAD_URL";
    public static final String PARAM_RECEIVER = "DownloadService.RECEIVER";

    public static final int DOWNLOAD_SUCCESS = 0;
    public static final int DOWNLOAD_FAIL = 1;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getDataString();
        ResultReceiver receiver = intent.getParcelableExtra(PARAM_RECEIVER);

        byte[] data = NetworkUtils.loadDataFromUrl(url);

        Bundle bundle = new Bundle();
        bundle.putString(DOWNLOAD_URL, url);

        if(data != null) {
            bundle.putByteArray(DOWNLOAD_PAYLOAD, data);
            receiver.send(DOWNLOAD_SUCCESS, bundle);
        } else {
            receiver.send(DOWNLOAD_FAIL, bundle);
        }
    }


}



