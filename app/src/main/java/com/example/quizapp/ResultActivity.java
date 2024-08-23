package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView resultText;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = findViewById(R.id.result_text);
        restartButton = findViewById(R.id.btn_restart);

        // Get the score and total questions from the intent
        int correctAnswers = getIntent().getIntExtra("CORRECT_ANSWERS", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

        // Display the score
        String resultMessage = "Quiz finished! Your score: " + correctAnswers + "/" + totalQuestions;
        resultText.setText(resultMessage);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart the quiz by navigating back to the WelcomeActivity
                Intent intent = new Intent(ResultActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish ResultActivity to prevent returning to it
            }
        });
    }
}
