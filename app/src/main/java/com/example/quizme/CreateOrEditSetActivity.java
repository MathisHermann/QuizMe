package com.example.quizme;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateOrEditSetActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Fabian","Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_set);

        //count chars entered for name of set
        TextView textView;
        EditText editText;
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.numberCharsOfSetName);
        editText = findViewById(R.id.nameCreateNew);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int length = editText.length();
                String convert = String.valueOf(length);
                textView.setText(convert);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


}
