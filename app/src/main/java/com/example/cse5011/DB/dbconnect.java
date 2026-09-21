package com.example.cse5011.DB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cse5011.model.users;

public class dbconnect extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Printexpress";
    private static final String TABLE_NAME = "users";
    private static final int DATABASE_VERSION = 1;

    private static String ID ="id";
    private static String NAME ="name";
    private static String EMAIL ="email";
    private static String PASSWORD ="password";


    public dbconnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT,"
                + EMAIL + " TEXT,"
                + PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addUser(users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD, user.getPassword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE (" + NAME + "=? OR " + EMAIL + "=?) AND " + PASSWORD + "=?",
                new String[]{username, username, password}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
}
