package com.example.testsolutionn1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FirstFragment.DataPassListener {
    private static final String KEY_FRAGMENT_COUNTER = "fragment_counter";

    TextView textView;
    int fragmentClicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textV);
        textView.setText("Here will be shown how many times you clicked in fragment");

        Button button1 = findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment();
            }
        });

        if (savedInstanceState != null) {
            fragmentClicks = savedInstanceState.getInt(KEY_FRAGMENT_COUNTER);
            updateTextView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_FRAGMENT_COUNTER, fragmentClicks);
    }

    @Override
    public void onDataPass(String data) {
        int clicks = Integer.parseInt(data.trim());
        fragmentClicks += clicks;
        updateTextView();
    }

    private void updateTextView() {
        textView.setText("Button in fragment was clicked " + fragmentClicks + " times");
    }

    private void setFragment() {
        FirstFragment firstFragment = new FirstFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, firstFragment);
        ft.commit();
    }
}
