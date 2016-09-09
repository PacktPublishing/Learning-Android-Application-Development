package com.packt.rrafols.example;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.packt.rrafols.example.model.Fields;
import com.packt.rrafols.example.model.Model;
import com.packt.rrafols.example.storage.ModelStorage;
import com.packt.rrafols.example.storage.ModelStorageHelper;
import com.packt.rrafols.example.storage.ModelStorageRealm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private CheckBox checkbox1;
    private CheckBox checkbox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkbox2 = (CheckBox) findViewById(R.id.checkBox2);

        readState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                synchronizeData();
                readLocalData();
                return null;
            }

        }.execute();


    }

    private void readLocalData() {
//        final ModelStorage storage = new ModelStorageRealm(this);
        final ModelStorage storage = new ModelStorageHelper(this);
        List<Fields> fieldList = storage.retrieveFieldList();
        for(Fields fields : fieldList) {
            System.out.println(fields);
        }
    }

    private void synchronizeData() {
        final ModelStorage storage = new ModelStorageRealm(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://finance.yahoo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YahooFinanceService symbols = retrofit.create(YahooFinanceService.class);

        Call<Model> symbolsCall = symbols.getQuote("YHOO,GOOG,AAPL");
        symbolsCall.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                if(response.isSuccessful()) {

                    Model model = response.body();
                    storage.storeModel(model);

                } else {
                    Toast.makeText(MainActivity.this, "Request not successful: " +
                            response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error processing request: " +
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveState() {
        FileOutputStream fos = null;
        try {
            File settingsFile = new File(getFilesDir(), "settings.properties");

            fos = new FileOutputStream(settingsFile);
            fos.write(checkbox1.isChecked() ? 1 : 0);
            fos.write(checkbox2.isChecked() ? 1 : 0);
            fos.flush();
            fos.close();
        } catch(IOException e) {
            Log.w(TAG, "Exception writing settings file", e);
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch(IOException e) {}
            }
        }
    }

    private void saveStateAlternative() {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("settings.properties", Context.MODE_PRIVATE);
            fos.write(checkbox1.isChecked() ? 1 : 0);
            fos.write(checkbox2.isChecked() ? 1 : 0);
            fos.flush();
            fos.close();
        } catch(IOException e) {
            Log.w(TAG, "Exception writing settings file", e);
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch(IOException e) {}
            }
        }
    }

//    Log.d(TAG, "getFilesDir: " + getFilesDir());
//    Log.d(TAG, "getCacheDir: " + getCacheDir());
//    Log.d(TAG, "getExternalCacheDir: " + getExternalCacheDir());
//    Log.d(TAG, "getExternalFilesDir(null): " + getExternalFilesDir(null));
//    Log.d(TAG, "getExternalFilesDir(DIRECTORY_DCIM): " + getExternalFilesDir(Environment.DIRECTORY_DCIM));
//    Log.d(TAG, "getExternalFilesDir(DIRECTORY_PICTURES): " +
//    getExternalFilesDir(Environment.DIRECTORY_PICTURES));
//    Log.d(TAG, "getExternalStoragePublicDirectory(DIRECTORY_PICTURES): " +
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));

    private void readState() {
        FileInputStream fis = null;
        try {
            File settingsFile = new File(getFilesDir(), "settings.properties");
            if(settingsFile.exists()) {
                fis = new FileInputStream(settingsFile);
                checkbox1.setChecked(fis.read() == 1);
                checkbox2.setChecked(fis.read() == 1);
            } else {
                Log.i(TAG, "Settings file does not exist yet. Setting default properties");
            }
        } catch(IOException e) {
            Log.w(TAG, "Exception reading settings file", e);
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch(IOException e) {}
            }
        }
    }

    private void readStateAlternative() {
        FileInputStream fis = null;
        try {
            fis = openFileInput("settings.properties");
            checkbox1.setChecked(fis.read() == 1);
            checkbox2.setChecked(fis.read() == 1);
        } catch(FileNotFoundException fnfe) {
            Log.i(TAG, "Settings file does not exist yet. Setting default properties");
        } catch(IOException e) {
            Log.w(TAG, "Exception reading settings file", e);
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch(IOException e) {}
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    @Override
    protected void onDestroy() {
        saveState();
        super.onDestroy();
    }
}



