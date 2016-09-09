package com.packt.rrafols.example;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView text1;
    private ImageView imageView;
    private CardView cardView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        text1 = (TextView) itemView.findViewById(R.id.item_text1);
        imageView = (ImageView) itemView.findViewById(R.id.item_background);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
    }

    public TextView getText1() {
        return text1;
    }
    public ImageView getImageView() {
        return imageView;
    }
    public CardView getCardView() {
        return cardView;
    }
}






