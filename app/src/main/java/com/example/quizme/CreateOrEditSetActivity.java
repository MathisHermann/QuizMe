package com.example.quizme;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.entity.QuizQuestion;
import com.example.quizme.entity.QuizSet;

import java.util.ArrayList;
import java.util.UUID;

public class CreateOrEditSetActivity extends AppCompatActivity {
    //enum Categories {SPORTS, GEOGRAPHY,MUSIC, FILMS, TV, HISTORY, LITERATURE, LANGUAGE, SCIENCE, GAMING, ENTERTAINMENT, RELIGION, FUN, PEOPLE}
    private Button newQuestion;
    private QuizSet set;
    private ArrayList<QuizQuestion> arrayOfQuestions = new ArrayList<QuizQuestion>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_set);

        //check if intent extras exists
        try {
            String s = getIntent().getStringExtra("theQuestion");

            //get the new question
            String quest = getIntent().getStringExtra("theQuestion");
            String[] answers = (String[]) getIntent().getSerializableExtra("theAnswers");

            //create new question object
            QuizQuestion question = new QuizQuestion(UUID.randomUUID().toString(), quest, answers[0]);
            question.wrongAnswers.add(answers[1]);
            question.wrongAnswers.add(answers[2]);
            question.wrongAnswers.add(answers[3]);

            Log.e("Noel", question.getQuestion());
            arrayOfQuestions.add(question);

            //add the object to the questionsList
            arrayOfQuestions.add(question);
        } catch (NullPointerException ex) {
            Log.e("Noel", "Probleme mit Extra, eeeegal!");
        }

        //count chars entered for name of set and change name
        TextView textView = findViewById(R.id.numberCharsOfSetName);
        EditText editText = findViewById(R.id.nameCreateNew);
        TextView tv = findViewById(R.id.createNew);

        //create a set
        set = QuizSet.createNewSet("Dummy", "dummy");

        //ListView all questions by https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

        // Create the adapter to convert the array to views
        QuestionAdapter adapterList = new QuestionAdapter(this, arrayOfQuestions);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listOfCreateQuestions);
        listView.setAdapter(adapterList);


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

        //choose category from the list
        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemClickListener(this);


    }

    private void openNewQuestionActivity() {
        Intent intent = new Intent(this, NewQuestion.class);
        startActivity(intent);
    }

    //todo
    public void createNewSet() {

    }


}
