package com.example.testsolutionn2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<Item> items;
    SharedPreferences sharedPreferences;

    public MyAdapter(Context context, List<Item> items, SharedPreferences sharedPreferences, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.items = items;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user_view, parent, false), this, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item currentItem = items.get(position);
        holder.bind(currentItem);

    }
    public List<Item> getItems() {
        return items;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    void updateItemInSharedPreferences(Item item) {
        List<Item> itemList = getItemsFromSharedPreferences();
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).getId().equals(item.getId())) {
                    // Found the matching item, update it in the list
                    itemList.set(i, item);
                    break;
                }
            }
            // Convert the updated list to JSON
            Gson gson = new Gson();
            String updatedItemsJson = gson.toJson(itemList);
            // Save the updated JSON back to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("itemsList", updatedItemsJson);
            editor.apply();
        }
    }

    // Method to retrieve the list of items from SharedPreferences
    public List<Item> getItemsFromSharedPreferences() {
        String itemsJsonFromPrefs = sharedPreferences.getString("itemsList", null);
        if (itemsJsonFromPrefs != null) {
            Type type = new TypeToken<List<Item>>() {}.getType();
            return new Gson().fromJson(itemsJsonFromPrefs, type);
        }
        // If the data doesn't exist in SharedPreferences, return an empty list
        return new ArrayList<>();
    }
}
