package com.example.quizme;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowAllSetsActivity extends AppCompatActivity {

    private TextView headerQuizMeSets;
    private Button btExit;
    private String playerName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_sets);


        headerQuizMeSets = findViewById(R.id.tvHeaderQuizMeSets);
        btExit = findViewById(R.id.btExit);

        if (getIntent().hasExtra("PLAYER_NAME"))
            playerName = getIntent().getStringExtra("PLAYER_NAME");

        headerQuizMeSets.setText(playerName);

    }

    public void finishActivity(View view) {
        finish();
    }


}