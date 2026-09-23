package com.example.cse5011.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cse5011.R;
import com.example.cse5011.model.Product;
import com.example.cse5011.model.users;

import java.util.ArrayList;
import java.util.List;

public class dbconnect extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Printexpress";
    private static final int DATABASE_VERSION = 3;

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String U_ID = "id";
    private static final String U_NAME = "name";
    private static final String U_EMAIL = "email";
    private static final String U_PASSWORD = "password";

    // Products Table
    private static final String TABLE_PRODUCTS = "products";
    private static final String P_ID = "id";
    private static final String P_TITLE = "title";
    private static final String P_DESC = "description";
    private static final String P_PRICE = "price";
    private static final String P_IMAGE = "image_res";

    // Cart Table
    private static final String TABLE_CART = "cart";
    private static final String C_ID = "id";
    private static final String C_TITLE = "title";
    private static final String C_PRICE = "price";
    private static final String C_SIZE = "size";
    private static final String C_QUANTITY = "quantity";
    private static final String C_IMAGE = "image_res";

    public dbconnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + U_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + U_NAME + " TEXT,"
                + U_EMAIL + " TEXT,"
                + U_PASSWORD + " TEXT" + ")";

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + P_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + P_TITLE + " TEXT,"
                + P_DESC + " TEXT,"
                + P_PRICE + " TEXT,"
                + P_IMAGE + " INTEGER" + ")";

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + C_TITLE + " TEXT,"
                + C_PRICE + " TEXT,"
                + C_SIZE + " TEXT,"
                + C_QUANTITY + " INTEGER,"
                + C_IMAGE + " INTEGER" + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    // --- Users Table Operations ---
    public void addUser(users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U_NAME, user.getName());
        values.put(U_EMAIL, user.getEmail());
        values.put(U_PASSWORD, user.getPassword());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE (" + U_NAME + "=? OR " + U_EMAIL + "=?) AND " + U_PASSWORD + "=?",
                new String[]{username, username, password}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // --- Products Table Operations ---
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(P_TITLE, product.getTitle());
        values.put(P_DESC, product.getDescription());
        values.put(P_PRICE, product.getPrice());
        values.put(P_IMAGE, product.getImageRes());
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(P_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(P_TITLE));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(P_DESC));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(P_PRICE));
                int imageRes = cursor.getInt(cursor.getColumnIndexOrThrow(P_IMAGE));

                productList.add(new Product(id, title, desc, price, imageRes));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }

    // --- Cart Table Operations ---
    public void addToCart(String title, String price, String size, int quantity, int imageRes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_TITLE, title);
        values.put(C_PRICE, price);
        values.put(C_SIZE, size);
        values.put(C_QUANTITY, quantity);
        values.put(C_IMAGE, imageRes);
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public int getCartCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_CART, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }

    // Helper to insert sample data if database is empty
    public void insertSampleProductsIfEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PRODUCTS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();

        if (count == 0) {
            addProduct(new Product("Custom T-Shirt", "High quality cotton printed T-shirt with customizable graphics", "$15.99", R.mipmap.ic_launcher));
            addProduct(new Product("Printed Ceramic Mug", "11oz ceramic mug with vivid custom color print", "$8.99", R.mipmap.ic_mug));
            addProduct(new Product("Business Cards", "100 pcs premium matte finish custom business cards", "$12.50", R.mipmap.ic_businesscard));
            addProduct(new Product("Custom Poster Print", "A3 glossy photo paper high resolution print", "$6.00", R.mipmap.ic_poster));
            addProduct(new Product("Custom Hoodie", "Fleece pullover hoodie with custom chest print", "$34.99", R.mipmap.ic_hoodie));
        }
    }
}
