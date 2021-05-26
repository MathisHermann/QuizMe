package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class NewQuestion extends AppCompatActivity {
    TextView tv_em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        //button save question
        ImageButton  imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuestion();
            }
        });
        tv_em=findViewById(R.id.tv_error_message);
    }

    private void saveQuestion() {
        Intent intent = new Intent(this, CreateOrEditSetActivity.class);

        EditText question = findViewById(R.id.et_question);
        EditText rightAnswer = findViewById(R.id.et_RightAnswer);
        EditText wrongAnswer1 = findViewById(R.id.et_wrongAnswer1);
        EditText wrongAnswer2 = findViewById(R.id.et_wrongAnswer2);
        EditText wrongAnswer3 = findViewById(R.id.et_wrongAnswer3);

        //make EditText to Strings
        String quest = question.getText().toString();
        String[] answers={rightAnswer.getText().toString(),wrongAnswer1.getText().toString(),wrongAnswer2.getText().toString(),wrongAnswer3.getText().toString()};

        //test if something is empty
        if(quest.isEmpty()||answers[0].isEmpty()||answers[1].isEmpty()||answers[2].isEmpty()||answers[3].isEmpty()){
           tv_em.setText("Invalid Input");
            tv_em.setTextColor(Color.parseColor("#ab2524"));
        }else{
            intent.putExtra("theQuestion",quest);
            intent.putExtra("theAnswers",answers);
            startActivityForResult(intent,1);
            //setResult(RESULT_OK, intent);
            Log.e("Leander",quest);
            finish();
        }

    }




}