package com.example.testsolutionn2;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView name, age;
    RecyclerViewInterface recyclerViewInterface;
    Switch isStudent;
    MyAdapter myAdapter; // Add a field to hold the MyAdapter instance

    public MyViewHolder(@NonNull View itemView, MyAdapter adapter, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        age = itemView.findViewById(R.id.age);
        isStudent = itemView.findViewById(R.id.isStudent);
        myAdapter = adapter;
        // Initialize the MyAdapter instance

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewInterface != null){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(pos);
                    }

                }
            }
        });
    }

    public void bind(Item item) {
        name.setText(item.getName());
        age.setText(item.getAge());

        // Get the saved state of the checkbox from SharedPreferences
        boolean isChecked = item.getIsStudent();
        isStudent.setChecked(isChecked);

        isStudent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                item.setStudent(isChecked);

                // Update the item state in SharedPreferences
                myAdapter.updateItemInSharedPreferences(item);
            }
        });
    }
}
