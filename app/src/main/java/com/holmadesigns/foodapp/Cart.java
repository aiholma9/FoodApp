package com.holmadesigns.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.holmadesigns.foodapp.Common.Common;
import com.holmadesigns.foodapp.Database.DBHelper;
import com.holmadesigns.foodapp.Database.Database;
import com.holmadesigns.foodapp.Model.Category;
import com.holmadesigns.foodapp.Model.Order;
import com.holmadesigns.foodapp.Model.Request;
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

    CartAdapter cartAdapter;

    List<Order> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Common.REQUEST);

        total = (TextView) findViewById(R.id.total);
        btnOrder = (Button) findViewById(R.id.btnPlaceOrder);

        recycler_cart = (RecyclerView)findViewById(R.id.recycler_cart);
        recycler_cart.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_cart.setLayoutManager(layoutManager);
        
        loadCart();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your Address: ");

        final EditText editAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        editAddress.setLayoutParams(layoutParams);
        alertDialog.setView(editAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        editAddress.getText().toString(),
                        total.getText().toString(),
                        cart
                );

                databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                new DBHelper(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Thank you, Order Place", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
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
