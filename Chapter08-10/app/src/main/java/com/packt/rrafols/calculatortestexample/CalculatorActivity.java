package com.packt.rrafols.calculatortestexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class CalculatorActivity extends AppCompatActivity {
    private final static int PURCHASE_REQUEST_CODE = 1001;
    private final static String TAG = CalculatorActivity.class.getName();


    private CalculatorLogic logic;
    private BillingManager billingManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        billingManager = new BillingManager();
        billingManager.init(this);

        logic = new CalculatorLogic();

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText value1Field = (EditText) findViewById(R.id.value1);
                EditText value2Field = (EditText) findViewById(R.id.value2);
                TextView resultViewer = (TextView) findViewById(R.id.result_viewer);

                String value1 = value1Field.getText() != null ? value1Field.getText().toString() : null;
                String value2 = value2Field.getText() != null ? value2Field.getText().toString() : null;
                String result = logic.add(value1, value2);

                resultViewer.setText(result);
            }
        });

        findViewById(R.id.multiply_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText value1Field = (EditText) findViewById(R.id.value1);
                EditText value2Field = (EditText) findViewById(R.id.value2);
                TextView resultViewer = (TextView) findViewById(R.id.result_viewer);

                String value1 = value1Field.getText() != null ? value1Field.getText().toString() : null;
                String value2 = value2Field.getText() != null ? value2Field.getText().toString() : null;
                String result = logic.multiply(value1, value2);

                resultViewer.setText(result);
            }
        });

        findViewById(R.id.purchase_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(billingManager.isBillingSupported()) {
                    billingManager.purchaseProduct(PURCHASE_REQUEST_CODE, "prod_2", BillingManager.IAP_TYPE, "extra");
                } else {
                    Log.e(TAG, "No Billing supported");
                }
            }
        });


        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        if(mAdView != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PURCHASE_REQUEST_CODE) {
            billingManager.handleActivityResult(resultCode, data);
        } else {
            Log.e(TAG, "Uknown requestCode " + requestCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (billingManager != null) billingManager.shutdown();
    }
}
