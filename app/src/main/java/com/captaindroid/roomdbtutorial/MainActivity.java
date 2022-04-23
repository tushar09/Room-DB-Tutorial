package com.captaindroid.roomdbtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.captaindroid.roomdbtutorial.databinding.ActivityMainBinding;
import com.captaindroid.roomdbtutorial.databinding.RowBookBinding;
import com.captaindroid.roomdbtutorial.db.DbClient;
import com.captaindroid.roomdbtutorial.db.tables.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private List<Book> books = new ArrayList<>();
    private Adapter adapter;
    private DbClient dbClient;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd EEE yy, hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbClient = DbClient.getInstance(this);
        adapter = new Adapter();

        books.addAll(dbClient.getAppDatabase().getBookDao().getAllBooks());

        binding.rvBookList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBookList.setAdapter(adapter);

        binding.btAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("Book " + System.currentTimeMillis());
                book.setAuthor("Author " + System.currentTimeMillis());
                book.setPublishDate(System.currentTimeMillis());
                book.setCreatedAt(System.currentTimeMillis());
                book.setUpdatedAt(System.currentTimeMillis());

                dbClient.getAppDatabase().getBookDao().insertBook(book);

                books.clear();
                books.addAll(dbClient.getAppDatabase().getBookDao().getAllBooks());
                adapter.notifyDataSetChanged();
            }
        });

    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(RowBookBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.rowBookBinding.tvId.setText(books.get(position).getId() + "");
            holder.rowBookBinding.tvBookName.setText(books.get(position).getName());
            holder.rowBookBinding.tvAuthorName.setText(books.get(position).getAuthor());
            holder.rowBookBinding.tvPublishDate.setText(simpleDateFormat.format(new Date(books.get(position).getPublishDate())));

            if(position == books.size() - 1){
                holder.rowBookBinding.vLastDiv.setVisibility(View.VISIBLE);
            }else {
                holder.rowBookBinding.vLastDiv.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        RowBookBinding rowBookBinding;
        public ViewHolder(RowBookBinding rowBookBinding) {
            super(rowBookBinding.getRoot());
            this.rowBookBinding = rowBookBinding;
        }
    }
}