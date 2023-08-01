package com.example.testsolutionn3;

import java.net.URL;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements FetchDataFragment.OnDataFetchedListener{
    private FragmentManager fragmentManager;
    private ArrayList<URL> URLList;
    private FetchDataFragment fetchDataFragment;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchDataFragment = new FetchDataFragment();
        fetchDataFragment.setOnDataFetchedListener(this);

        fetchDataFragment = new FetchDataFragment();
        fragmentManager = getSupportFragmentManager();
        setFragment(fetchDataFragment);

        int numColumns = 2;
        recyclerView = findViewById(R.id.pictures_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));
        imageAdapter = new ImageAdapter(this, URLList);
        recyclerView.setAdapter(imageAdapter);
    }

    public void setFragment(FetchDataFragment fetchDataFragment){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmet_layout, fetchDataFragment, null)
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public void onDataFetched(ArrayList<URL> URLList) {
        this.URLList = URLList;

        imageAdapter = new ImageAdapter(this, URLList);
        recyclerView.setAdapter(imageAdapter);

    }
}


