package com.holmadesigns.foodapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.holmadesigns.foodapp.Interface.ItemClickListener;
import com.holmadesigns.foodapp.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView menu_name;
    public ImageView menu_image;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MenuViewHolder(View itemView) {
        super(itemView);
        menu_image = (ImageView) itemView.findViewById(R.id.menu_image);
        menu_name = (TextView) itemView.findViewById(R.id.menu_name);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
