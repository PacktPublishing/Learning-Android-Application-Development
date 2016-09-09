package com.packt.rrafols.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        final String[] fruits = new String[] {"Orange", "Banana", "Pear", "Pineapple", "Mango", "Strawberry",
                "Apple", "Peach", "Watermelon", "Kiwi", "Cherry", "Grape", "Fig", "Plum", "Quince",
                "Avocado", "Pomegranate", "Lime", "Mandarin", "Grapefruit", "Raspberry", "Melon", "Pomelo"};

        ArrayAdapter<String> stringList = new ArrayAdapter<String>(this, R.layout.item, R.id.item_text, fruits);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(stringList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, fruits[position], Toast.LENGTH_SHORT).show();
            }
        });

    }

}


