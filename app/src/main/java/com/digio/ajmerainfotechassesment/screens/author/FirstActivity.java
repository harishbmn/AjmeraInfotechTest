package com.digio.ajmerainfotechassesment.screens.author;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digio.ajmerainfotechassesment.R;
import com.digio.ajmerainfotechassesment.database.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirstActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AppCompatEditText authorName;
    AppCompatButton addAuthorButton;
    DBHelper dbHelper;
    AuthorAdapter authorAdapter;
    List<AuthorModel> modelList = new ArrayList<>();
    boolean checkingAuthor = false;
    int id =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        authorName = findViewById(R.id.et_author);
        addAuthorButton = findViewById(R.id.add_author_button);
        recyclerView = findViewById(R.id.recyclerview_list);
        getAuthorListFromDB();

        initRecyclerview();
        addAuthorButton.setOnClickListener(view -> {
            String author = Objects.requireNonNull(authorName.getText()).toString().trim();
            if (author.length() != 0) {
                insertAuthor();
                getAuthorListFromDB();
            } else {
                Toast.makeText(FirstActivity.this, "Please Enter Author", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void getAuthorListFromDB() {
        if (dbHelper.getAllParentListItem() != null) {
            modelList.clear();
            modelList = dbHelper.getAllParentListItem();
            initRecyclerview();

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
        authorAdapter = new AuthorAdapter(this, modelList);
        recyclerView.setAdapter(authorAdapter);
    }

    private void insertAuthor() {
        String author = Objects.requireNonNull(authorName.getText()).toString().trim();
        for (int i=0;i<modelList.size(); i++){
            if (author.equalsIgnoreCase(modelList.get(i).getAuthor())){
                checkingAuthor = true;
            }
        }
      if (!checkingAuthor){
          AuthorModel authorModel = new AuthorModel(author,id);
          id++;
          dbHelper.addAuthor(authorModel);
      }else {
          Toast.makeText(this, "Author name already exits", Toast.LENGTH_SHORT).show();
      }
      authorName.getText().clear();

    }
}