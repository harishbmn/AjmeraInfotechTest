package com.digio.ajmerainfotechassesment.screens.author;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.digio.ajmerainfotechassesment.R;
import com.digio.ajmerainfotechassesment.screens.books.SecondActivity;

import java.util.List;

public
class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.ViewHolder> {
    List<AuthorModel> list;
    Context context;

    public AuthorAdapter(FirstActivity firstActivity, List<AuthorModel> modelList) {
        this.list = modelList;
        this.context = firstActivity;

    }

    @NonNull
    @Override
    public AuthorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_author, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (list.size() != 0) {
            holder.authorName.setText(list.get(position).getAuthor());
            String upperCaseAuthorName = list.get(position).getAuthor().substring(0, 1).toUpperCase();
            holder.firstLetter.setText(upperCaseAuthorName);
            holder.rootLayout.setOnClickListener(view -> {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("author_name", list.get(position).getAuthor());
                intent.putExtra("author_id", list.get(position).getId());
                context.startActivity(intent);
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView firstLetter, authorName;
        LinearLayoutCompat rootLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstLetter = itemView.findViewById(R.id.first_letter);
            authorName = itemView.findViewById(R.id.author_name);
            rootLayout = itemView.findViewById(R.id.root_layout);
        }
    }
}
