package com.packt.rrafols.example;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivityExample extends ListActivity {

    private final String[] items = new String[] {"Orange", "Banana", "Pear", "Pineapple", "Mango", "Strawberry",
            "Apple", "Peach", "Watermelon", "Kiwi", "Cherry", "Grape", "Fig", "Plum", "Quince",
            "Avocado", "Pomegranate", "Lime", "Mandarin", "Grapefruit", "Raspberry", "Melon", "Pomelo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> stringList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(stringList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(this, items[position], Toast.LENGTH_SHORT).show();
    }
}





