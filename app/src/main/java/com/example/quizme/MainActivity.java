package com.example.quizme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // GUI control
    Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing list view with the custom adapter
        //ArrayList<Player> playerList = new ArrayList<>();
        buttonStart = findViewById(R.id.btnStart);

        // Set up list item onclick listener
        setUpListItemClickListener();
    }

    private void setUpListItemClickListener() {

        buttonStart.setOnClickListener((View v) -> {

                //Toast.makeText(getApplicationContext(), "Player " + position + " clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ShowAllSetsActivity.class);
                intent.putExtra("PLAYER_NAME", "playerName");

                startActivity(intent );
        });
    }

}