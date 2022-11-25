package com.digio.ajmerainfotechassesment.screens.books;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digio.ajmerainfotechassesment.R;
import com.digio.ajmerainfotechassesment.database.DBHelper;
import com.digio.ajmerainfotechassesment.screens.model.BookDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecondActivity extends AppCompatActivity {
    AppCompatEditText bookName, price;
    AppCompatButton addBookButton;
    RecyclerView recyclerView;
    AppCompatTextView textView;
    BooksDetailsAdapter booksDetailsAdapter;
    List<BookDetails> list = new ArrayList<>();
    List<BookDetails> detailsList = new ArrayList<>();
    DBHelper dbHelper;
    String authorName;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        dbHelper = new DBHelper(this);
        bookName = findViewById(R.id.et_book);
        price = findViewById(R.id.et_price);
        textView = findViewById(R.id.tv_author);
        addBookButton = findViewById(R.id.add_author_button);
        recyclerView = findViewById(R.id.recyclerview_books_list);

        authorName = getIntent().getStringExtra("author_name");
        id = ((getIntent().getIntExtra("author_id", 0)));
        textView.setText("Author  : " + authorName);

        initRecyclerview();
        getListFromDB();
        addBookButton.setOnClickListener(view -> {
            String book = Objects.requireNonNull(bookName.getText()).toString().trim();
            String amount = Objects.requireNonNull(price.getText()).toString().trim();
            if (book.length() != 0 && amount.length() != 0) {
                insertBooks();
            } else {
                Toast.makeText(SecondActivity.this, "Please Enter Book Details", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void getListFromDB() {
        list.clear();
        detailsList.clear();
        list = dbHelper.getBooks();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAuthorName().equalsIgnoreCase(authorName)) {
                detailsList.add(list.get(i));
            }
        }
    }


    private void insertBooks() {
        String book = Objects.requireNonNull(bookName.getText()).toString().trim();
        String amount = Objects.requireNonNull(price.getText()).toString().trim();
        BookDetails booksModel = new BookDetails(book, amount, id, authorName);
        dbHelper.addBookDetails(booksModel);
        getListFromDB();
        initRecyclerview();
        bookName.getText().clear();
        price.getText().clear();
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
        booksDetailsAdapter.notifyDataSetChanged();
    }

}