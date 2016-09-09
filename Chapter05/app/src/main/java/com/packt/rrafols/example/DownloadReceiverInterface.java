package com.packt.rrafols.example;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

@SuppressLint("ParcelCreator")
public class DownloadReceiverInterface extends ResultReceiver {
    private Receiver receiver;
    public DownloadReceiverInterface(Handler handler) {
        super(handler);
    }

    public interface Receiver {
        void onReceivedResult(int resultCode, Bundle resultData);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) receiver.onReceivedResult(resultCode, resultData);
    }
}







