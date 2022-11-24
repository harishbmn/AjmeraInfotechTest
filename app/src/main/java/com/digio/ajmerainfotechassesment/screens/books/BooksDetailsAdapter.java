package com.digio.ajmerainfotechassesment.screens.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.digio.ajmerainfotechassesment.R;
import com.digio.ajmerainfotechassesment.screens.model.BookDetails;
import com.digio.ajmerainfotechassesment.screens.model.BooksModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BooksDetailsAdapter extends RecyclerView.Adapter<BooksDetailsAdapter.ViewHolder> {
    Context context;
    List<BookDetails> booksModelArrayList;


    public BooksDetailsAdapter(SecondActivity secondActivity, List<BookDetails> booksModelList) {
        this.booksModelArrayList = booksModelList;
        this.context = secondActivity;
    }

    @NonNull
    @Override
    public BooksDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksDetailsAdapter.ViewHolder holder, int position) {

        if (booksModelArrayList.size() != 0) {
            holder.name.setText(booksModelArrayList.get(position).getBookName());
            String price = booksModelArrayList.get(position).getPrice();
            holder.book.setText(price);

        }

    }

    @Override
    public int getItemCount() {
        return booksModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView name, book, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            book = itemView.findViewById(R.id.book);
            price = itemView.findViewById(R.id.price);
        }
    }
}
