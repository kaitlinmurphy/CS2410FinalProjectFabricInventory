package com.example.cs2410_finalproject_fabricinventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2410_finalproject_fabricinventory.models.FabricEntry;

public class FabricEntriesAdapter extends RecyclerView.Adapter<FabricEntriesAdapter.ViewHolder>{
    ObservableArrayList<FabricEntry> entries;

    public interface OnFabricEntryClicked {
        public void onClick(FabricEntry entry);
    }

    OnFabricEntryClicked listener;

    public FabricEntriesAdapter(ObservableArrayList<FabricEntry> entries, OnFabricEntryClicked listener) {
        this.entries = entries;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public FabricEntriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fabric_entry_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FabricEntriesAdapter.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.fabric_name);
        textView.setText(entries.get(position).fabricName);
        holder.itemView.setOnClickListener(view -> {
            listener.onClick(entries.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }
}
