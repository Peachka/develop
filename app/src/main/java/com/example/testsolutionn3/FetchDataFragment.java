package com.example.testsolutionn3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchDataFragment extends Fragment {
    private ArrayList<String> jsonData;
    private ArrayList<URL> URLList;
    private OnDataFetchedListener onDataFetchedListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fetch_data, container, false);

        rootView.findViewById(R.id.buttonFetchData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataFromAPI();
            }
        });

        return rootView;
    }

    private void fetchDataFromAPI() {
        new FetchJsonData(new FetchJsonData.OnDataFetchedListener() {
            @Override
            public void onDataFetched(ArrayList<String> jsonDataList) {
                jsonData = jsonDataList;

                URLList = new ArrayList<>();

                for (String i : jsonData) {
                    try {
                        URLList.add(new URL(i.replace("[", "").replace("\"", "").replace("]", "")));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (onDataFetchedListener != null) {
                    onDataFetchedListener.onDataFetched(URLList);
                }
                Toast.makeText(getContext(), "Data fetched successfully", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    public interface OnDataFetchedListener {
        void onDataFetched(ArrayList<URL> URLList);
    }

    public void setOnDataFetchedListener(OnDataFetchedListener listener) {
        onDataFetchedListener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDataFetchedListener) {
            onDataFetchedListener = (OnDataFetchedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDataFetchedListener");
        }
    }
}
