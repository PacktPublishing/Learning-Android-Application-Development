package com.packt.rrafols.example;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;

public class MainActivitySharedPreferences extends AppCompatActivity {
    private static final String TAG = MainActivitySharedPreferences.class.getName();
    private CheckBox checkbox1;
    private CheckBox checkbox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
        String checkbox1Property = getString(R.string.checkbox1_property);
        checkbox1.setChecked(sharedPreferences.getBoolean(checkbox1Property, false));

        checkbox2 = (CheckBox) findViewById(R.id.checkBox2);
        String checkbox2Property = getString(R.string.checkbox2_property);
        checkbox2.setChecked(sharedPreferences.getBoolean(checkbox2Property, false));

        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();

        prefEditor.putBoolean(getString(R.string.checkbox1_property), checkbox1.isChecked());
        prefEditor.putBoolean(getString(R.string.checkbox2_property), checkbox2.isChecked());
        boolean saved = prefEditor.commit();
        if (!saved) Log.e(TAG, "Error saving to SharedPreferences");

        super.onDestroy();
    }


    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new
        SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.i(TAG, key + " has been changed on SharedPreferences");
            }
        };
}



