package com.digio.ajmerainfotechassesment.screens.books;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digio.ajmerainfotechassesment.R;
import com.digio.ajmerainfotechassesment.database.DBHelper;
import com.digio.ajmerainfotechassesment.screens.model.BookDetails;
import com.digio.ajmerainfotechassesment.screens.model.BooksModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecondActivity extends AppCompatActivity {
    AppCompatEditText bookName, price;
    AppCompatButton addBookButton;
    RecyclerView recyclerView;
    BooksDetailsAdapter booksDetailsAdapter;
    List<BooksModel> booksModelList = new ArrayList<>();
    List<BooksModel> getBooksList = new ArrayList<>();
    List<BookDetails> bookDetailsList = new ArrayList<>();
    List<BookDetails> list = new ArrayList<>();
    DBHelper dbHelper;
    String authorName;
    String booksList = null;
    private JsonArray respParamArry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        dbHelper = new DBHelper(this);
        bookName = findViewById(R.id.et_book);
        price = findViewById(R.id.et_price);
        addBookButton = findViewById(R.id.add_author_button);
        recyclerView = findViewById(R.id.recyclerview_books_list);


        authorName = getIntent().getStringExtra("author_name");
        initRecyclerview();
        getBooksListFromDB();

        addBookButton.setOnClickListener(view -> {
            String book = Objects.requireNonNull(bookName.getText()).toString().trim();
            String amount = Objects.requireNonNull(price.getText()).toString().trim();
            if (book.length() != 0 && amount.length() != 0) {
                booksModelList.clear();
                insertBooks();
                getBooksListFromDB();

            } else {
                Toast.makeText(SecondActivity.this, "Please Enter Book Details", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void getBooksListFromDB() {
        if (dbHelper.getAuthorList() != null) {
            booksModelList.clear();
            booksModelList = dbHelper.getBooksList();
            for (int i = 0; i < booksModelList.size(); i++) {
                String name = booksModelList.get(i).getAuthor();
                if (name.equalsIgnoreCase(authorName)) {
                    getBooksList.add(booksModelList.get(i));
                    try {
                        convertJsontoPojo(getBooksList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

        }
    }

    private void convertJsontoPojo(List<BooksModel> getBooksList) throws JSONException {
        // list.clear();
        try {
            JSONArray jsonArray = new JSONArray(getBooksList.get(0).getBooks());
            JSONObject values;
            booksList = String.valueOf(jsonArray);

            for (int j = 0; j < jsonArray.length(); j++) {
                values = jsonArray.getJSONObject(j);
                String book = values.getString("book");
                String mrp = values.getString("mrp");

                BookDetails data = new BookDetails(book, mrp);
                list.add(data);
                initRecyclerview();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void insertBooks() {
        String book = Objects.requireNonNull(bookName.getText()).toString().trim();
        String amount = Objects.requireNonNull(price.getText()).toString().trim();
        BookDetails booksModel = new BookDetails(book, amount);
        bookDetailsList.add(booksModel);
        dbHelper.deleteAuthorBooks(authorName);
        saveBooksIntoObject();
        dbHelper.insertBookDetails(authorName, respParamArry);
        getBooksListFromDB();
        initRecyclerview();

    }

    public void saveBooksIntoObject() {

        try {
            respParamArry = new JsonArray();
            for (int i = 0; i < bookDetailsList.size(); i++) {
                String books = bookDetailsList.get(i).getBookName();
                String mrp = (bookDetailsList.get(i).getPrice());

                JsonObject respParamObj = new JsonObject();

                respParamObj.addProperty("book", books);
                respParamObj.addProperty("mrp", mrp);
                respParamArry.add(respParamObj);

            }
        } catch (Exception e) {
            Log.e("list", "books exp: " + e.getMessage());
        }
    }

    private void initRecyclerview() {
        recyclerView.setNestedScrollingEnabled(false);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        booksDetailsAdapter = new BooksDetailsAdapter(this, list);
        recyclerView.setAdapter(booksDetailsAdapter);
    }

}