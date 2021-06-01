package com.example.quizme;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import java.util.Collections;

import com.example.quizme.database.FlatfileDatabase;
import com.example.quizme.database.DBHandler;
import com.example.quizme.entity.QuizSet;

import java.util.Set;
import java.util.stream.IntStream;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.quizme.database.FlatfileDatabase;
import com.example.quizme.database.DBHandler;
import com.example.quizme.database.Serializer;
import com.example.quizme.entity.QuizQuestion;
import com.example.quizme.entity.QuizSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

public class playSetActivity extends AppCompatActivity {


    // GUI control
    Button buttonStart;
    private ListView lvDisplayAllSets;
    private ListView lvPlayAnswers;
    private final List<String> allOptions = new ArrayList<>();
    FlatfileDatabase fdb;
    String setID, rightAnser;
    private int setPotion = 0;
    private int round = 0;
    private String question;
    private int correctPosion;
    private int score = 0;
    private int amountAnswers;
    private ProgressBar bar;
    private TextView tvCurrentQu;
    private List<QuizQuestion> questions;
    private TextView tvPlayQuestion;
    private final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_set);
        tvPlayQuestion = findViewById(R.id.tvPlayQuestion);
        bar = findViewById(R.id.progressBar);
        tvCurrentQu = findViewById(R.id.tvNumberCurrentQuestion);
        lvDisplayAllSets = findViewById(R.id.lvDisplayAllSets);
        lvPlayAnswers = findViewById(R.id.lvPlayAnswers);

        fdb = new FlatfileDatabase(new DBHandler(getApplicationContext()));
        fdb.getAllSets().forEach(set -> {
            if(set.getUUID().equals(getIntent().getStringExtra("positionUUID"))) {
                questions = new ArrayList<>(set.questions);
            }
        });
        roundPlay();

        lvPlayAnswers.setOnItemClickListener((parent, view, position, id) -> select(position));
    }

    @SuppressLint("SetTextI18n")
    private void roundPlay() {
        if (questions.size() == 0) {
            error("error: no question in this set");
            Log.e("PYsizeIF", "" + questions.size());
            finish();
            Log.e("PYsizeIFfinish", "" + questions.size());
        } else {
            Log.e("PYsize", "" + questions.size());
            bar.setProgress((int) (round / (double) questions.size() * 100), true);
            tvCurrentQu.setText(round + " / " + questions.size());
            QuizQuestion quizQuestion = questions.get(round);


            question = quizQuestion.getQuestion();
            tvPlayQuestion.setText(question);
            allOptions.clear();
            allOptions.addAll(quizQuestion.wrongAnswers);
            allOptions.add(randomCorrectPosion(), quizQuestion.getCorrectAnswer());


            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    allOptions);


            lvPlayAnswers.setAdapter(arrayAdapter);
        }
    }


    private void error(String error) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, error, duration);
        toast.show();

    }

    private int randomCorrectPosion() {
        correctPosion = new Random().nextInt(4);
        return correctPosion;
    }


    private void select(int position) {
        if (correctPosion == position) {
            score++;

            lvPlayAnswers.setBackgroundColor(Color.GREEN);
        } else {
            lvPlayAnswers.setBackgroundColor(Color.RED);
        }
        handler.postDelayed(() -> lvPlayAnswers.setBackgroundColor(Color.GRAY), 150);
        if (round + 1 == questions.size()) {
            openScoreAcrivity(score);
        } else {
            round++;
            roundPlay();
        }
    }

    private void openScoreAcrivity(int score) {
        Log.e("playSet", "openScore");
        fdb.writeHighscore(getIntent().getStringExtra("positionUUID"),
                DataHolder.getInstance().playerName, score);
        Intent intent = new Intent(this, SetPlayed.class);
        intent.putExtra("score", score);
        intent.putExtra("setUUID", getIntent().getStringExtra("positionUUID"));
        intent.putExtra("amountAnswers", round + 1);
        startActivity(intent);
        finish();

    }
}
