package com.digio.ajmerainfotechassesment.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.digio.ajmerainfotechassesment.screens.author.AuthorModel;
import com.digio.ajmerainfotechassesment.screens.model.BookDetails;
import com.digio.ajmerainfotechassesment.screens.model.BooksModel;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Books.db";
    private static final int DATABASE_VERSION = 12;
    private final String AUTHOR_TABLE = "author_list";
    private final String AUTHOR_LIST_ID = "id";
    private final String AUTHOR_NAME = "author_name";
    private final String BOOKS_TABLE = "books_list";
    private final String BOOK_LIST_ID = "book_id";
    private final String TABLE_AUTHOR_LIST_ID = "parent_id";
    private final String BOOK_NAME = "name";
    private final String BOOK_PRICE = "price";
    private final String AUTHOR = "author";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_MAIN_LIST_TABLE = "CREATE TABLE " + AUTHOR_TABLE + "("
                + AUTHOR_LIST_ID + " INTEGER PRIMARY KEY,"
                + AUTHOR_NAME + " TEXT" + ")";

        String CREATE_TABLE_CHILD_LIST = "CREATE TABLE " + BOOKS_TABLE + "("
                + BOOK_LIST_ID + " INTEGER PRIMARY KEY,"
                + TABLE_AUTHOR_LIST_ID + " INTEGER,"
                + BOOK_NAME + " TEXT ,"
                + AUTHOR + " TEXT ,"
                + BOOK_PRICE + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_MAIN_LIST_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_CHILD_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AUTHOR_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addAuthor(AuthorModel authorModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AUTHOR_NAME, authorModel.getAuthor());
        db.insert(AUTHOR_TABLE, null, values);
        db.close();
    }

    public void addBookDetails(BookDetails item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, item.getBookName());
        values.put(BOOK_PRICE, item.getPrice());
        values.put(AUTHOR, item.getAuthorName());
        values.put(TABLE_AUTHOR_LIST_ID, item.getId());
        db.insert(BOOKS_TABLE, null, values);
        db.close();
    }

    public List<AuthorModel> getAllParentListItem() {
        List<AuthorModel> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(AUTHOR_TABLE, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            AuthorModel item = new AuthorModel();
            item.setId(getIntByColumName(cursor, AUTHOR_LIST_ID));
            item.setAuthor(getStringByColumName(cursor, AUTHOR_NAME));
            result.add(item);
        }
        cursor.close();
        db.close();
        return result;
    }

    public List<BookDetails> getBooks() {
        List<BookDetails> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BOOKS_TABLE, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            BookDetails item = new BookDetails();
            item.setId(Integer.valueOf(getStringByColumName(cursor,BOOK_LIST_ID)));
            item.setBookName(getStringByColumName(cursor, BOOK_NAME));
            item.setPrice(getStringByColumName(cursor, BOOK_PRICE));
            item.setAuthorName(getStringByColumName(cursor, AUTHOR));
            result.add(item);
        }
        cursor.close();
        db.close();
        return result;
    }

    @SuppressLint("Range")
    public int getIntByColumName(Cursor cursor, String tableColumn) {
        return cursor.getInt(cursor.getColumnIndex(tableColumn));
    }


    @SuppressLint("Range")
    public double getDoubleByColumName(Cursor cursor, String tableColumn) {
        return cursor.getDouble(cursor.getColumnIndex(tableColumn));
    }


    @SuppressLint("Range")
    public String getStringByColumName(Cursor cursor, String tableColumn) {
        return cursor.getString(cursor.getColumnIndex(tableColumn));
    }



}
