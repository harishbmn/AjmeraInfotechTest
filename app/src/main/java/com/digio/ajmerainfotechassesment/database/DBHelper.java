package com.digio.ajmerainfotechassesment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.digio.ajmerainfotechassesment.screens.author.AuthorModel;
import com.digio.ajmerainfotechassesment.screens.model.BooksModel;
import com.google.gson.JsonArray;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Books.db";
    public static final String BOOKS_TABLE = "books_list";
    public static final String AUTHOR_TABLE = "author_list";
    public static final String AUTHOR = "author";
    public static final String BOOK_DETAILS = "book_details";
    private static final int DATABASE_VERSION = 12;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + BOOKS_TABLE +
                        "(" + AUTHOR + " TEXT , " +
                        BOOK_DETAILS + " TEXT)"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + AUTHOR_TABLE +
                        "(" + AUTHOR + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AUTHOR_TABLE);
        onCreate(sqLiteDatabase);
    }


    public void insertBookDetails(String author, JsonArray book_details) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AUTHOR, author);
        contentValues.put(BOOK_DETAILS, String.valueOf((book_details)));


        db.insert(BOOKS_TABLE, null, contentValues);
    }

    public void insertAuthorList(String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AUTHOR, author);
        db.insert(AUTHOR_TABLE, null, contentValues);
    }


    public ArrayList<AuthorModel> getAuthorList() {
        ArrayList<AuthorModel> arraylist = new ArrayList<>();
        SQLiteDatabase sql = this.getWritableDatabase();
        Cursor c = sql.rawQuery("SELECT * FROM " + AUTHOR_TABLE, null);
        while (c.moveToNext()) {
            AuthorModel Data = new AuthorModel();
            Data.setAuthor(c.getString(0));
            arraylist.add(Data);
        }
        c.close();
        return arraylist;
    }

    public ArrayList<BooksModel> getBooksList() {
        ArrayList<BooksModel> arraylist = new ArrayList<>();
        SQLiteDatabase sql = this.getWritableDatabase();
        Cursor c = sql.rawQuery("SELECT * FROM " + BOOKS_TABLE, null);
        while (c.moveToNext()) {
            BooksModel Data = new BooksModel();
            Data.setAuthor(c.getString(0));
            Data.setBooks(c.getString(1));


            arraylist.add(Data);
        }
        c.close();
        return arraylist;
    }

    public void deleteAuthorBooks(String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOKS_TABLE, "author = ?", new String[]{author});
    }

    public void deleteAuthor(String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AUTHOR_TABLE, "author = ?", new String[]{author});
    }
}
