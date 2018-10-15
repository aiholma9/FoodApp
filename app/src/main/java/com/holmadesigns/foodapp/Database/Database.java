package com.holmadesigns.foodapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.holmadesigns.foodapp.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper{

    public static final  String DB_NAME = "FoodApp.db";
    private static final  int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    public List<Order> getCarts(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        String[] sql = {"ProductName","Quantity","Price","Discount"};
        String sqlTable = "OrderDetails";

        sqLiteQueryBuilder.setTables(sqlTable);
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, sql, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                result.add(new Order(cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getLong(cursor.getColumnIndex("Price")),
                        cursor.getLong(cursor.getColumnIndex("Discount"))));
            }
            while (cursor.moveToNext());
        }

        return result;
    }

    public void addToCart(Order order){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductName,Quantity,Price,Discount) VALUES ('%s','%s','%s','%s');",
                 order.getProductName(), order.getQuantity(), order.getPrice(), order.getDiscount());
        sqLiteDatabase.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        sqLiteDatabase.execSQL(query);
    }

}
