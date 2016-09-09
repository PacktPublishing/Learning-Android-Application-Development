package com.packt.rrafols.example;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainAdapterActivity extends AppCompatActivity {

    private final String[] fruits = new String[] {"Orange", "Banana", "Pear", "Pineapple", "Mango", "Strawberry",
            "Apple", "Peach", "Watermelon", "Kiwi", "Cherry", "Grape", "Fig", "Plum", "Quince",
            "Avocado", "Pomegranate", "Lime", "Mandarin", "Grapefruit", "Raspberry", "Melon", "Pomelo"};

    private int index;
    private ExampleBaseAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ExampleBaseAdapter(this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.addItemDecoration(new RecyclerItemDecoration(ContextCompat.getColor(this, R.color.decorationBackground)));


        findViewById(R.id.addline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index < fruits.length) {
                    adapter.addItem(fruits[index++]);
                }
            }
        });

        findViewById(R.id.delline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getItemCount() > 0) {
                    int i = (int) (Math.random() * adapter.getItemCount());
                    adapter.removeItem(i);
                }
            }
        });
    }
}





