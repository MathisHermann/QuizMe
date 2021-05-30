package com.example.quizme;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;

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
    private final List<String> allOptions = new ArrayList<String>();
    FlatfileDatabase fdb;
    String setID, rightAnser;
    private int setPotion = 0;
    private int round = 0;
    private String question;
    private int correctPosion;
    private int score = 0;
    private int amountAnswers;
    private Set<QuizQuestion> questions;
    private TextView tvPlayQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_set);
        tvPlayQuestion = findViewById(R.id.tvPlayQuestion);

        lvDisplayAllSets = findViewById(R.id.lvDisplayAllSets);
        lvPlayAnswers = findViewById(R.id.lvPlayAnswers);

        //TODO: setPotion gemäss Listenauswahl einstellen
        fdb = new FlatfileDatabase(new DBHandler(getApplicationContext()));

        questions = fdb.getAllSets().stream().collect(Collectors.toList()).get(setPotion).questions;
        roundPlay();

        lvPlayAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                select(position);
            }


        });
    }

    private void roundPlay() {
        

        QuizQuestion quizQuestion = questions.stream().collect(Collectors.toList()).get(round);


        question = quizQuestion.getQuestion();
        tvPlayQuestion.setText(question);
        allOptions.addAll(quizQuestion.wrongAnswers);
        allOptions.add(randomCorrectPosion(), quizQuestion.getCorrectAnswer());


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                allOptions);


        lvPlayAnswers.setAdapter(arrayAdapter);
    }

    private int randomCorrectPosion() {
        correctPosion = new Random().nextInt(5);
        return correctPosion;
    }




    private void select(int position) {
        if (correctPosion == position){
            score++;

            lvPlayAnswers.setBackgroundColor(Color.GREEN);
        }else {
            lvPlayAnswers.setBackgroundColor(Color.RED);
        }
        if(round + 1 == questions.size()){
            openScoreAcrivity(score);
        }else {
            round++;
            roundPlay();
        }
    }

    private void openScoreAcrivity(int score) {
        Log.e("playSet", "openScore");


        Intent intent = new Intent(this, SetPlayed.class);
        intent.putExtra("score", score);

        intent.putExtra("amountAnswers", round +1);
        startActivity(intent);

    }

    ;

}
