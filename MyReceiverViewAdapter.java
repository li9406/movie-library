package com.example.movielibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MyReceiverViewAdapter extends RecyclerView.Adapter<MyReceiverViewAdapter.ViewHolder> {

    ArrayList<Item> data;

    // need to have a constructor for the movie list
    public MyReceiverViewAdapter(ArrayList<Item> _data) {
        data = _data;
    }

    @NonNull
    @Override
    public MyReceiverViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);    //
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyReceiverViewAdapter.ViewHolder holder, int position) {
        holder.itemText.setText((CharSequence) data.get(position));

        final int fPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Item at position " + fPosition + " was clicked", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemText = itemView.findViewById(R.id.textView);
        }
    }
}
