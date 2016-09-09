package com.packt.rrafols.example;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

@SuppressLint("ParcelCreator")
public class DownloadReceiver extends ResultReceiver {
    public DownloadReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        byte[] data = resultData.getByteArray(DownloadService.DOWNLOAD_PAYLOAD);
        String url = resultData.getString(DownloadService.DOWNLOAD_URL);
        if(resultCode == DownloadService.DOWNLOAD_SUCCESS) {
            success(url, data);
        } else {
            failure(url);
        }
    }

    public void success(String url, byte[] data) {}
    public void failure(String url) {}
}







