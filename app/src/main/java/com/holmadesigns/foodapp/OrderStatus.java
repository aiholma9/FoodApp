package com.holmadesigns.foodapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.holmadesigns.foodapp.Common.Common;
import com.holmadesigns.foodapp.Interface.ItemClickListener;
import com.holmadesigns.foodapp.Model.Food;
import com.holmadesigns.foodapp.Model.Order;
import com.holmadesigns.foodapp.Model.Request;
import com.holmadesigns.foodapp.ViewHolder.FoodViewHolder;
import com.holmadesigns.foodapp.ViewHolder.MenuViewHolder;
import com.holmadesigns.foodapp.ViewHolder.OrderViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recycler_order;
    RecyclerView.LayoutManager layoutManager;

    Query query;
    FirebaseRecyclerOptions<Request> options;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        recycler_order = (RecyclerView)findViewById(R.id.recycler_order);
        recycler_order.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_order.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getName());
    }

    private void loadOrders(String name) {
        query = FirebaseDatabase.getInstance().getReference(Common.REQUEST)
                .orderByChild("name").equalTo(name);

        options = new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(query, Request.class).build();

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Request model) {
                holder.txtOrderId.setText(adapter.getRef(position).getKey());
                holder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                holder.txtOrderAddress.setText(model.getAddress());
                holder.txtOrderPhone.setText(model.getPhone());
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.order_layout, viewGroup, false);
                return new OrderViewHolder(itemView);
            }
        };

        adapter.startListening();
        recycler_order.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status){
        if (status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }
}
