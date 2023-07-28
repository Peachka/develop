package com.example.testsolutionn1;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class FirstFragment extends Fragment {
    private DataPassListener dataPassListener;
    int counter = 0;
    int clicks = 0;

    TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ImageView backArrow = view.findViewById(R.id.backArrow);
        Button btn = view.findViewById(R.id.button2);
        txt = view.findViewById(R.id.textView);
        txt.setText("Your counter");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks +=1;
                counter += 1;
                txt.setText("Counter: " + String.valueOf(counter));
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks == 0) {
                    dataPassListener.onDataPass(" 0");
                } else {
                    dataPassListener.onDataPass((String) String.valueOf(clicks));
                    clicks = 0;
                }
            }
        });

        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString("text");
            int savedCounter = savedInstanceState.getInt("counter");
            txt.setText(savedText);
            counter = savedCounter;
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", txt.getText().toString());
        outState.putInt("counter", counter);
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataPassListener = (DataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DataPassListener");
        }
    }

    public interface DataPassListener {
        void onDataPass(String data);
    }
}
