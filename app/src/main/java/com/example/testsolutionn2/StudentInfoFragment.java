package com.example.testsolutionn2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StudentInfoFragment extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);

        textView = view.findViewById(R.id.student_info);
        textView = view.findViewById(R.id.student_info);

        // Retrieve data from arguments
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name");
            String age = args.getString("age");
            boolean isStudent = args.getBoolean("isStudent");

            String result;
            if (isStudent) {
                result = "Student name: "+name + "\n" + "Age: "+ age + "\n" + name + " is a student";
            } else {
                result = "Student name: "+name + "\n" + "Age: "+ age + "\n" + name + " is not a student";
            }

            textView.setText(result);
        }

        return view;
    }
}
