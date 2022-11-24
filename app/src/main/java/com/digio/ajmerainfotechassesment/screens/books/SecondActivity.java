package com.digio.ajmerainfotechassesment.screens.books;

import android.database.Cursor;
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
    List<BookDetails> list = new ArrayList<>();
    List<BookDetails> detailsList = new ArrayList<>();
    DBHelper dbHelper;
    String authorName;
    private Integer id;

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
        id = ((getIntent().getIntExtra("author_id", 0)));
        list = dbHelper.getBooks();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                detailsList.add(list.get(i));
            }
        }
        initRecyclerview();

        addBookButton.setOnClickListener(view -> {
            String book = Objects.requireNonNull(bookName.getText()).toString().trim();
            String amount = Objects.requireNonNull(price.getText()).toString().trim();
            if (book.length() != 0 && amount.length() != 0) {
                booksModelList.clear();
                insertBooks();
            } else {
                Toast.makeText(SecondActivity.this, "Please Enter Book Details", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void insertBooks() {
        String book = Objects.requireNonNull(bookName.getText()).toString().trim();
        String amount = Objects.requireNonNull(price.getText()).toString().trim();
        BookDetails booksModel = new BookDetails(book, amount, id);
        dbHelper.addBookDetails(booksModel);
        list = dbHelper.getBooks();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                detailsList.add(list.get(i));
            }
        }
        initRecyclerview();

    }

    private void initRecyclerview() {
        recyclerView.setNestedScrollingEnabled(false);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        booksDetailsAdapter = new BooksDetailsAdapter(this, detailsList);
        recyclerView.setAdapter(booksDetailsAdapter);
    }

}