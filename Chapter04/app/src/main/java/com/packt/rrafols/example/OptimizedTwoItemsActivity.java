package com.packt.rrafols.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OptimizedTwoItemsActivity extends AppCompatActivity {

    private static final String TAG = OptimizedTwoItemsActivity.class.getName();
    final String[] fruits = new String[] {"Orange", "Banana", "Pear", "Pineapple", "Mango",
            "Strawberry",  "Apple", "Peach", "Watermelon", "Kiwi", "Cherry", "Grape", "Fig",
            "Plum", "Quince", "Avocado", "Pomegranate", "Lime", "Mandarin", "Grapefruit",
            "Raspberry", "Melon", "Pomelo"};

    final String[] colors = new String[] {"orange", "yellow", "green", "brown", "orangeish",
            "red", "red", "orange", "green", "brown", "red", "green", "burgundy", "burgundy",
            "yellow", "green", "red", "green", "orange", "orange", "red", "green", "green"};

    private static final int INVALID_LAYOUT = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        ArrayAdapter<String> stringList = new ArrayAdapter<String>(this, INVALID_LAYOUT, fruits) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;

                if(convertView == null) {
                    LayoutInflater inflater = (LayoutInflater)
                            OptimizedTwoItemsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    convertView = inflater.inflate(R.layout.two_lines_item, null);

                    holder = new ViewHolder();
                    holder.text1 = (TextView) convertView.findViewById(R.id.item_text1);
                    holder.text2 = (TextView) convertView.findViewById(R.id.item_text2);
                    holder.image = (ImageView) convertView.findViewById(R.id.item_image);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.text1.setText(fruits[position]);
                holder.text2.setText(colors[position]);
                holder.position = position;
                new ImageLoader(position, holder).execute();

                return convertView;
            }
        };

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(stringList);
    }

    private static class ImageLoader extends AsyncTask<ViewHolder, Void, Bitmap> {
        private ViewHolder holder;
        private int position;

        public ImageLoader(int position, ViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        protected Bitmap doInBackground(ViewHolder... params) {
            // load real image here and return something more sensible
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(position == holder.position) {
                holder.image.setImageBitmap(bitmap);
            }
        }
    }

    private static class ViewHolder {
        TextView text1;
        TextView text2;
        ImageView image;
        int position;
    }

}





