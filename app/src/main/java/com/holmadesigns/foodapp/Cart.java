package com.holmadesigns.foodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.holmadesigns.foodapp.Common.Common;
import com.holmadesigns.foodapp.Database.DBHelper;
import com.holmadesigns.foodapp.Model.Category;
import com.holmadesigns.foodapp.Model.Order;
import com.holmadesigns.foodapp.ViewHolder.CartAdapter;
import com.holmadesigns.foodapp.ViewHolder.MenuViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView total;
    Button btnOrder;

    RecyclerView recycler_cart;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerOptions<Category> options;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    CartAdapter cartAdapter;

    List<Order> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Common.REQUEST);

        total = (TextView) findViewById(R.id.total);
        btnOrder = (Button) findViewById(R.id.btnOrder);

        recycler_cart = (RecyclerView)findViewById(R.id.recycler_cart);
        recycler_cart.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_cart.setLayoutManager(layoutManager);
        
        loadCart();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void loadCart() {
        cart = new DBHelper(this).getCarts();
        cartAdapter = new CartAdapter(cart, this);
        recycler_cart.setAdapter(cartAdapter);

        int totalCart = 0;
        for (Order order:cart)
            totalCart += (Integer.parseInt(String.valueOf(order.getPrice()))) * (Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en", "Nigeria");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        total.setText(numberFormat.format(totalCart));
    }
}
