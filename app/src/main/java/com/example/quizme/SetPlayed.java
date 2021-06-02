package com.example.quizme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.database.DBHandler;
import com.example.quizme.database.FlatfileDatabase;

public class SetPlayed extends AppCompatActivity {


    // GUI control
    private TextView tvAmountCorrectAnswers;
    private TextView tvAmountWrongAnswers;
    private TextView tvAmountAllAnswers;
    private TextView tvSetHighScore;
    private Button btnBackToAllSetsFromSetPlayed;
    private FlatfileDatabase fdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_played);
        fdb = new FlatfileDatabase(new DBHandler(getApplicationContext()));
        tvAmountCorrectAnswers = findViewById(R.id.tvAmountCorrectAnswers);
        tvAmountWrongAnswers = findViewById(R.id.tvAmountWrongAnswers);
        tvAmountAllAnswers = findViewById(R.id.tvAmountAllAnswers);
        tvSetHighScore = findViewById(R.id.tvSetHighScore);
        btnBackToAllSetsFromSetPlayed = findViewById(R.id.btnBackToAllSetsFromSetPlayed);

        loadContent();
        btnBackToAllSetsFromSetPlayed.setOnClickListener(backToOverview -> {
            Intent intent = new Intent(this, ShowAllSetsActivity.class);
            startActivity(intent);
            finish();
        });


    }

    // Todo
    // - Add the highscore
    // - Set new Highscore if new highscore
    @SuppressLint("SetTextI18n")
    private void loadContent() {
        //Bundle extras = getIntent().getExtras();
        int score = getIntent().getIntExtra("score", 0);
        int amountAnswers = getIntent().getIntExtra("amountAnswers", 0);
        String setUUID = getIntent().getStringExtra("setUUID");
        //int score = extras.getInt("score");
        //int amountAnswers = extras.getInt("amountAnswers");
        String highScore = String.format("%d (%s)", fdb.getHighscoreScore(setUUID),
                fdb.getHighscoreName(setUUID));
        tvAmountCorrectAnswers.setText("" + score);
        tvAmountWrongAnswers.setText("" + (amountAnswers - score));
        tvAmountAllAnswers.setText("" + amountAnswers);
        tvSetHighScore.setText(highScore);

    }
}
