package com.example.testsolutionn2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Handler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressLint("ke")
public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{
    FragmentManager fragmentManager;
    StudentInfoFragment studentInfoFragment;
    SharedPreferences sharedPreferences;
    List<Item> items;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("Students", Context.MODE_PRIVATE);

        if (!sharedPreferences.contains("itemsList")) {
            Log.wtf("Re","orient");


            items = createDefaultItems();

            Gson gson = new Gson();
            String itemsJson = gson.toJson(items);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("itemsList", itemsJson);
            editor.apply();
        } else {
            // If the app is not reloaded, retrieve the items from SharedPreferences
            String itemsJsonFromPrefs = sharedPreferences.getString("itemsList", null);
            Type type = new TypeToken<List<Item>>() {}.getType();
            items = new Gson().fromJson(itemsJsonFromPrefs, type);
        }

        adapter = new MyAdapter(getApplicationContext(), items, sharedPreferences, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        studentInfoFragment = new StudentInfoFragment();
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the items list in the bundle to be restored later (e.g., on screen orientation change)
        Gson gson = new Gson();
        String itemsJson = gson.toJson(adapter.getItems());
        outState.putString("itemsList", itemsJson);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore the items list from the bundle
        String itemsJson = savedInstanceState.getString("itemsList");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Item>>() {}.getType();
        List<Item> restoredItems = gson.fromJson(itemsJson, type);

        if (restoredItems != null) {
            items.clear();
            items.addAll(restoredItems);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the updated items list to SharedPreferences when the activity is paused
        Gson gson = new Gson();
        String itemsJson = gson.toJson(adapter.getItems());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("itemsList", itemsJson);
        editor.apply();
    }

    private List<Item> createDefaultItems() {
        List<Item> defaultItems = new ArrayList<>();
        defaultItems.add(new Item("0", "Anastasia", 20, false));
        defaultItems.add(new Item("1", "Maks", 22, false));
        defaultItems.add(new Item("2", "Nikita", 19, false));
        defaultItems.add(new Item("3", "John", 21, false));
        defaultItems.add(new Item("4", "Mike", 20, false));
        defaultItems.add(new Item("5", "Loren", 18, false));
        defaultItems.add(new Item("6", "Sofia", 22, false));
        defaultItems.add(new Item("7", "Kate", 18, false));
        return defaultItems;
    }

    @Override
    public void onItemClick(int position) {
        List<Item> students = adapter.getItemsFromSharedPreferences();
        String name = students.get(position).getName();
        String age = students.get(position).getAge();
        boolean isStudent = students.get(position).getIsStudent();

        // Create a new instance of StudentInfoFragment with data arguments
        studentInfoFragment = new StudentInfoFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("age", age);
        args.putBoolean("isStudent", isStudent);
        studentInfoFragment.setArguments(args);

        // Replace the fragment with the new instance
        setFragment(studentInfoFragment);
    }

    public void setFragment(StudentInfoFragment studentInfoFragment){
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, studentInfoFragment, null)
                .setReorderingAllowed(true)
                .commit();
    }
}




