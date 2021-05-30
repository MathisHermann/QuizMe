package com.example.quizme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SetPlayed extends AppCompatActivity {


    // GUI control
    private TextView tvAmountCorrectAnswers;
    private TextView tvAmountWrongAnswers;
    private TextView tvAmountAllAnswers;
    private TextView tvSetPlayedScore;
    private TextView tvSetHighScore;
    private Button btnBackToAllSetsFromSetPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_played);


        tvAmountCorrectAnswers = findViewById(R.id.tvAmountCorrectAnswers);
        tvAmountWrongAnswers = findViewById(R.id.tvAmountWrongAnswers);
        tvAmountAllAnswers = findViewById(R.id.tvAmountAllAnswers);
        tvSetPlayedScore = findViewById(R.id.tvSetPlayedScore);
        tvSetHighScore = findViewById(R.id.tvSetHighScore);
        btnBackToAllSetsFromSetPlayed = findViewById(R.id.btnBackToAllSetsFromSetPlayed);

        loadContent();
        btnBackToAllSetsFromSetPlayed.setOnClickListener(backToOverview -> {
            Intent intent = new Intent(this, ShowAllSetsActivity.class);
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
        //int score = extras.getInt("score");
        //int amountAnswers = extras.getInt("amountAnswers");
        String highScore = "n.A.";

        //tvAmountCorrectAnswers.setText("1");
        tvAmountCorrectAnswers.setText("" + score);
        tvAmountWrongAnswers.setText("" + (amountAnswers - score));
        tvAmountAllAnswers.setText("" + amountAnswers);
        if (score == 0) {
            tvSetPlayedScore.setText("0%");
        } else {
            tvSetPlayedScore.setText("" + (amountAnswers / score * 100) + "%");
        }
        tvSetHighScore.setText("" + highScore);

    }
}
