package com.holmadesigns.foodapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.holmadesigns.foodapp.Common.Common;
import com.holmadesigns.foodapp.Model.Food;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    TextView food_name, food_price, food_desc;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatortLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);

        food_image = (ImageView) findViewById(R.id.imageThumb);
        Picasso.get().load(Common.food.getImage()).error(R.drawable.ic_terrain_black_24dp)
                .into(food_image);

        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_desc = (TextView) findViewById(R.id.food_desc);

        food_name.setText(Common.food.getName());
        food_price.setText(String.valueOf(Common.food.getPrice()));
        food_desc.setText(Common.food.getDescription());

        collapsingToolbarLayout.setTitle(Common.food.getName());

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
