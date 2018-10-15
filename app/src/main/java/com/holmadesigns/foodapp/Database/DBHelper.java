package com.holmadesigns.foodapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.holmadesigns.foodapp.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{

    public static final  String DB_NAME = "App.db";
    private static final  int DB_VERSION = 1;

    public static final  String TABLE_NAME = "OrderDetails";

    public static final  String ID = "Id";
    public static final String PRODUCT_NAME = "ProductName";
    public static final  String QUANTITY = "Quantity";
    public static final  String PRICE = "Price";
    public static final  String DISCOUNT = "Discount";

    public Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                "(" +
                ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PRODUCT_NAME+ " TEXT, " +
                QUANTITY+ " TEXT," +
                PRICE+ " TEXT," +
                DISCOUNT+ " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
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
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sql = "INSERT INTO "+TABLE_NAME+
                "(" +PRODUCT_NAME+ ", " +QUANTITY+ ", " +PRICE+ ", " +DISCOUNT+ ") " +
                "VALUES ('" +order.getProductName()+ "', '" +order.getQuantity()+
                "', '" +order.getPrice()+ "', '" +order.getDiscount()+"')";

        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void cleanCart(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "DELETE FROM " +TABLE_NAME;
        sqLiteDatabase.execSQL(query);
    }
}
