package com.packt.rrafols.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TwoItemsActivity extends AppCompatActivity {

    private static final String TAG = TwoItemsActivity.class.getName();
    final String[] fruits = new String[] {"Orange", "Banana", "Pear", "Pineapple", "Mango",
            "Strawberry",  "Apple", "Peach", "Watermelon", "Kiwi", "Cherry", "Grape", "Fig",
            "Plum", "Quince", "Avocado", "Pomegranate", "Lime", "Mandarin", "Grapefruit",
            "Raspberry", "Melon", "Pomelo"};

    final String[] colors = new String[] {"orange", "yellow", "green", "brown", "orangeish",
            "red", "red", "orange", "green", "brown", "red", "green", "burgundy", "burgundy",
            "yellow", "green", "red", "green", "orange", "orange", "red", "green", "green"};

    private static final int INVALID_LAYOUT = -1;

    /**
     * Not optimized implementation. Just shown as reference, optimized version using ViewHolder
     * pattern and reusing views can be found in the<code>OptimizedTwoItemsActivity</code> class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        ArrayAdapter<String> stringList = new ArrayAdapter<String>(this, INVALID_LAYOUT, fruits) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)
                        TwoItemsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View inflatedView = inflater.inflate(R.layout.two_lines_item, null);

                ((TextView) inflatedView.findViewById(R.id.item_text1)).setText(fruits[position]);
                ((TextView) inflatedView.findViewById(R.id.item_text2)).setText(colors[position]);

                return inflatedView;
            }
        };

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(stringList);
    }
}





