package com.app.kiosk.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.kiosk.R;
import com.app.kiosk.databinding.ItemRowBinding;
import com.app.kiosk.model.Item;
import com.app.kiosk.ui.OnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable {

    private List<Item> items;  // Original full list
    private List<Item> filteredItems; // Filtered list
    private OnItemClickListener listener; // Click listener

    public ItemAdapter(OnItemClickListener listener) {
        // Initialize with an empty list and set the listener
        this.items = new ArrayList<>();
        this.filteredItems = new ArrayList<>();
        this.listener = listener;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.filteredItems = new ArrayList<>(items); // Initialize the filtered list
        Log.d("TAG", "setItems: " + this.items.size());
        notifyDataSetChanged(); // Notify the adapter that data has changed
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use View Binding to inflate the item layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRowBinding binding = ItemRowBinding.inflate(inflater, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = filteredItems.get(position); // Use filtered list
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return filteredItems.size(); // Return filtered list size
    }

    // ViewHolder class to hold item views
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemRowBinding binding;

        public ItemViewHolder(ItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item, OnItemClickListener listener) {
            binding.itemName.setText(item.getName());
            binding.itemDescription.setText(item.getDescription());
            binding.itemPrice.setText(String.valueOf(item.getPrice()));

            Glide.with(binding.itemImage.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.itemImage);

            // Set item click listener to open detail view
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            });
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Item> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(items);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Item item : items) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItems.clear();
                filteredItems.addAll((List<Item>) results.values);
                notifyDataSetChanged();

                // Notify if no results found
                if (filteredItems.isEmpty()) {
                    listener.toggleNoResultsMessage(true);
                } else {
                    listener.toggleNoResultsMessage(false);
                }
            }
        };
    }

}
