package com.packt.rrafols.example;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;

public class ExampleBaseAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context context;
    private ArrayList<String> list;


    private HashMap<Integer, Integer> heightList;

    public ExampleBaseAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        heightList = new HashMap<>();
    }

    private static int dpToPixels(double dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.getText1().setText(list.get(position));
        holder.getImageView().setImageResource(R.drawable.background);

        if(!heightList.containsKey(position)) {
            int height = dpToPixels(50 + Math.random() * 50);
            heightList.put(position, height);
        }

        holder.getCardView().getLayoutParams().height = heightList.get(position);
    }

    public void addItem(String str) {
        list.add(str);
        notifyItemInserted(list.size());
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new RecyclerViewHolder(inflater.inflate(R.layout.cardview, parent, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
}






