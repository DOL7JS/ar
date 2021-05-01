package com.upce.ar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        Button toQuizAR = findViewById(R.id.btnOpenQuiz);
        Button toLibraryAR = findViewById(R.id.btnOpenLibrary);
        Button toAboutApp = findViewById(R.id.btnAboutApp);

        toQuizAR.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QuizWelcome.class)));
        toLibraryAR.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ModelSelection.class)));
        toAboutApp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AboutApp.class)));
    }
}
