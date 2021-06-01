package com.example.quizme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.database.DBHandler;
import com.example.quizme.database.FlatfileDatabase;
import com.example.quizme.entity.QuizQuestion;
import com.example.quizme.entity.QuizSet;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // GUI control
    Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing list view with the custom adapter
        //ArrayList<Player> playerList = new ArrayList<>();
        buttonStart = findViewById(R.id.buttonAddNewQuestion);

        FlatfileDatabase fdb = new FlatfileDatabase(new DBHandler(getApplicationContext()));
        QuizSet set = QuizSet.createNewSet("Java", "IT");
        QuizQuestion q1 = new QuizQuestion(UUID.randomUUID().toString(), "On which systems does Java run?",
                "On all systems with JVM");
        q1.wrongAnswers.add("Windows only");
        q1.wrongAnswers.add("Linux only");
        q1.wrongAnswers.add("Unix-like systems");
        set.questions.add(q1);
        QuizQuestion q2 = new QuizQuestion(UUID.randomUUID().toString(), "What is a static method?",
                "A method not bound to an object");
        q2.wrongAnswers.add("A method which must be run from an object");
        q2.wrongAnswers.add("A multithreaded method");
        q2.wrongAnswers.add("A fast-running method");
        set.questions.add(q2);
        QuizQuestion q3 = new QuizQuestion(UUID.randomUUID().toString(), "What is the codename for Android 2.2?",
                "Froyo");
        q3.wrongAnswers.add("Gingerbread");
        q3.wrongAnswers.add("Base");
        q3.wrongAnswers.add("Gingerbread");
        set.questions.add(q3);
        if (fdb.getAllSets().isEmpty()) {
            ArrayList<QuizSet> sets = new ArrayList<>();
            sets.add(set);
            fdb.overrideSetsToDB(sets);
        }
        // Set up list item onclick listener
        setUpListItemClickListener();
    }


    private void setUpListItemClickListener() {

        buttonStart.setOnClickListener((View v) -> {


           Intent intent = new Intent(MainActivity.this, ShowAllSetsActivity.class);

            EditText editText = findViewById(R.id.editTextTextPersonName);
            if (editText.getText().toString().equals("")) {
                error("Please type in a name.");
            } else {
                intent.putExtra("PLAYER_NAME", editText.getText().toString());
                DataHolder.getInstance().playerName = editText.getText().toString();
                startActivity(intent);
                finish();
            }
        });
    }

    private void error(String error) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, error, duration);
        toast.show();

    }
}