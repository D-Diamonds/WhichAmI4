package com.example.whichami4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayGameActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_game);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        TextView resultText = findViewById(R.id.result);
        System.out.println(result);
        resultText.setText("You are the game: " + result);

        Button replayBtn = findViewById(R.id.replayBtn);
        replayBtn.setOnClickListener(this);
    }

}
