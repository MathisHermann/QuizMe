package com.example.quizme;

import android.content.Context;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.database.DBHandler;
import com.example.quizme.database.FlatfileDatabase;
import com.example.quizme.entity.QuizQuestion;
import com.example.quizme.entity.QuizSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public class CreateOrEditSetActivity extends AppCompatActivity {
    //enum Categories {SPORTS, GEOGRAPHY,MUSIC, FILMS, TV, HISTORY, LITERATURE, LANGUAGE, SCIENCE, GAMING, ENTERTAINMENT, RELIGION, FUN, PEOPLE}
    private Button newQuestion;
    private QuizSet set;
    private EditText setName;
    private Spinner category;
    private FlatfileDatabase fdb;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_set);
        fdb = new FlatfileDatabase(new DBHandler(getApplicationContext()));
        //check if intent extras exists
        try {
            String s = getIntent().getStringExtra("theQuestion");

            //get the new question
            String quest = getIntent().getStringExtra("theQuestion");
            String[] answers = (String[]) getIntent().getSerializableExtra("theAnswers");
            Log.e("Noël", quest);
            Log.e("Noël", Arrays.toString(answers));
            //create new question object
            QuizQuestion question = new QuizQuestion(UUID.randomUUID().toString(), quest, answers[0]);
            question.wrongAnswers.add(answers[1]);
            question.wrongAnswers.add(answers[2]);
            question.wrongAnswers.add(answers[3]);

            //add the object to the questionsList
            DataHolder.getInstance().arrayOfQuestions.add(question);
        } catch (NullPointerException ex) {
            Log.e("Noel", "Probleme mit Extra, eeeegal!");
        }

        //count chars entered for name of set and change name
        TextView textView = findViewById(R.id.numberCharsOfSetName);
        setName = findViewById(R.id.nameCreateNew);
        TextView tv = findViewById(R.id.createNew);

        //create a set
        set = QuizSet.createNewSet("Dummy", "dummy");

        //ListView all questions by https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

        // Create the adapter to convert the array to views
        QuestionAdapter adapterList = new QuestionAdapter(this, DataHolder.getInstance().arrayOfQuestions);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.listOfCreateQuestions);
        listView.setAdapter(adapterList);


        setName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = setName.length();
                String convert = String.valueOf(length) + "/30";
                textView.setText(convert);
                tv.setText(setName.getText());
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

        //button to go to activity_create_set
        Button buttonNewSet = (Button) findViewById(R.id.buttonAddNewSet);
        buttonNewSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { createNewSet(); }
        });

        //choose category from the list
        category = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        //spinner.setOnItemClickListener(this);

        //
        Button bt_back= findViewById(R.id.btnBackToAllSetsFromOrEditCreateSet);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Log.e("Back", "back"); backToShowAllSets(); }
        });

    }

    private void openNewQuestionActivity() {
        Intent intent = new Intent(this, NewQuestion.class);
        startActivity(intent);
        finish();
    }

    private void backToShowAllSets() {
        Intent intent = new Intent(this, ShowAllSetsActivity.class);
        startActivity(intent);
        finish();
    }

    public void createNewSet() {
        if(setName.getText().toString().equals("")) {
            error("No title set.");
            return;
        }
        if(listView.getChildCount() == 0) {
            error("Please add some questions.");
            return;
        }
        for (QuizSet set : fdb.getAllSets()) {
            if(set.getName().equals(setName.getText().toString())) {
                error("This set name is taken.");
                return;
            }
        }
        QuizSet quizSet = QuizSet.createNewSet(setName.getText().toString(), category.getSelectedItem().toString());
        quizSet.questions.addAll(DataHolder.getInstance().arrayOfQuestions);

        Set<QuizSet> allSetsInDB = fdb.getAllSets();
        allSetsInDB.add(quizSet);
        fdb.overrideSetsToDB(allSetsInDB);

        //delete arrayList
        DataHolder.getInstance().arrayOfQuestions.clear();

        //go to ShowAllSetsActivity
        Intent intent = new Intent(this, ShowAllSetsActivity.class);
        startActivity(intent);
        finish();

    }

    private void error(String error) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, error, duration);
        toast.show();

    }
}
