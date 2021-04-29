package com.upce.ar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class QuizWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_welcome);


        Button btnContinue = findViewById(R.id.btnContinue);
        Button btnBackToHomeScreen = findViewById(R.id.btnBackToHomeScreen);
        ProgressBar loadingBar;
        loadingBar = findViewById(R.id.progressBarLoading);
        loadingBar.setVisibility(View.GONE);
        btnContinue.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QuizAR.class)));
        btnContinue.setOnClickListener(v -> {
            loadingBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(getApplicationContext(), QuizAR.class));
        });
        btnBackToHomeScreen.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HomeScreen.class)));
    }
}
