package com.holmadesigns.foodapp.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.holmadesigns.foodapp.Interface.ItemClickListener;
import com.holmadesigns.foodapp.Model.Order;
import com.holmadesigns.foodapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> orderList = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.cart_layout, viewGroup, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        TextDrawable textDrawable = TextDrawable.builder().buildRound(""
                +orderList.get(i).getQuantity(), Color.GREEN);
        cartViewHolder.imageCart.setImageDrawable(textDrawable);

        Locale locale = new Locale("en", "Nigeria");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(String.valueOf(orderList.get(i).getPrice()))) * (Integer.parseInt(orderList.get(i).getQuantity()));
        cartViewHolder.txtPrice.setText(numberFormat.format(price));

        cartViewHolder.txtCartName.setText(orderList.get(i).getProductName());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}

class CartViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
    public TextView txtCartName, txtPrice;
    public ImageView imageCart;

    ItemClickListener itemClickListener;

    public void setTxtCartName(TextView txtCartName) {
        this.txtCartName = txtCartName;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtCartName = (TextView) itemView.findViewById(R.id.cart_item_name);
        txtPrice = (TextView) itemView.findViewById(R.id.cart_item_Price);
        imageCart = (ImageView) itemView.findViewById(R.id.cart_item_count);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}
