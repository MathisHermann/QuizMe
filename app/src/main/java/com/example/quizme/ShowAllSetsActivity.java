package com.example.quizme;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


public class ShowAllSetsActivity extends AppCompatActivity {

    private TextView headerQuizMeSets;
    private Button btExit;
    private ListView lvDisplayAllSets;
    private List<String> allSets = new ArrayList<String>();
    private String playerName;
    FlatfileDatabase fdb = new FlatfileDatabase(new DBHandler(this));

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_sets);

        Intent intent = getIntent();
        String playerName = intent.getStringExtra("PLAYER_NAME");


        headerQuizMeSets = findViewById(R.id.tvHeaderQuizMeSets);
        lvDisplayAllSets = findViewById(R.id.lvDisplayAllSets);
        btExit = findViewById(R.id.btExit);
        //allSets.addAll(fdb.getAllSets());


        fdb.getAllSets().forEach(quizSet -> {
            allSets.add(quizSet.getName());

            System.out.println(quizSet.getCategory());

            System.out.println(quizSet.getUUID());

            quizSet.questions.forEach(quizQuestion -> {

                System.out.println(quizQuestion.getQuestion());
                System.out.println(quizQuestion.getCorrectAnswer());

                System.out.println(quizQuestion.wrongAnswers);
                System.out.println(quizQuestion.uuid);

            });
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                allSets);

        lvDisplayAllSets.setAdapter(arrayAdapter);

        if (getIntent().hasExtra("PLAYER_NAME"))
            playerName = getIntent().getStringExtra("PLAYER_NAME");

        headerQuizMeSets.setText(playerName);


        lvDisplayAllSets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivityPlayQuiz(position);
            }


        });

        //button to go to activity_create_set
        Button button = (Button) findViewById(R.id.buttonGoToCreateEditActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateOrEditSetActivity();
            }
        });
    }

    public void openCreateOrEditSetActivity() {
        Intent intent2 = new Intent(this, CreateOrEditSetActivity.class);
        startActivity(intent2);
    }

    private void openActivityPlayQuiz(int position) {
        Intent intent = new Intent(this, playSetActivity.class);
        intent.putExtra("position", position);
        //based on item add info to intent
        startActivity(intent);
    }


    public void finishActivity(View view) {
        finish();
    }


}
