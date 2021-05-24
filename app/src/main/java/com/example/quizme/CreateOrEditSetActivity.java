package com.example.quizme;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateOrEditSetActivity extends AppCompatActivity {
    Button newQuestion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_set);

        //count chars entered for name of set and change name
        TextView textView = findViewById(R.id.numberCharsOfSetName);
        EditText editText = findViewById(R.id.nameCreateNew);
        TextView tv = findViewById(R.id.createNew);

        //todo dummy database exists or not if not create

        //todo add question to dummy database
        String quest=getIntent().getStringExtra("theQuestion");
        String[] answers= (String[]) getIntent().getSerializableExtra("theAnswers");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = editText.length();
                String convert = String.valueOf(length) + "/30";
                textView.setText(convert);
                tv.setText(editText.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //button to go to activity_create_set
        Button button = (Button) findViewById(R.id.buttonAddNewQuestion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewQuestionActivity();
            }
        });



}

    public void openNewQuestionActivity() {
        Intent intent = new Intent(this, NewQuestion.class);
        startActivity(intent);
    }


    public void createNewSet() {

    }
}
