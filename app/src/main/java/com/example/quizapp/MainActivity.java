package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String[] questions = {
            "What is the purpose of an Intent in Android?",
            "Which method is used to start a new Activity in Android?",
            "What is the lifecycle method that is called when an Activity is first created?",
            "How do you declare a Button in an XML layout file?",
            "What is the difference between a Fragment and an Activity in Android?"
    };

    private String[][] options = {
            {"To send data between components", "To manage user input", "To handle network operations", "To manage UI layout"},
            {"startActivity()", "beginActivity()", "createActivity()", "openActivity()"},
            {"onCreate()", "onStart()", "onResume()", "onPause()"},
            {"<Button> Button </Button>", "<button> Button </button>", "<button id='btn'>Button</button>", "<Button android:id='@+id/button'/>"},
            {"Fragments are part of an Activity; Activities are standalone components", "Fragments and Activities are the same", "Fragments handle background tasks; Activities manage UI", "Activities are used for networking; Fragments are used for UI"}
    };

    private int[] answers = {0, 0, 0, 3, 0}; // Index of the correct answers
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    private TextView questionText;
    private RadioGroup optionGroup;
    private Button hintButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.question_text);
        optionGroup = findViewById(R.id.option_group);
        hintButton = findViewById(R.id.hint_button);
        submitButton = findViewById(R.id.submit_button);

        loadQuestion();

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display hint based on the current question
                displayHint();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the selected answer
                checkAnswer();
            }
        });
    }

    private void loadQuestion() {
        questionText.setText(questions[currentQuestionIndex]);
        for (int i = 0; i < 4; i++) {
            RadioButton optionButton = (RadioButton) findViewById(getResources().getIdentifier("option" + (i + 1), "id", getPackageName()));
            optionButton.setText(options[currentQuestionIndex][i]);
            optionButton.setTag(i); // Set tag for each option button
        }
    }

    private void checkAnswer() {
        int selectedId = optionGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedOption = findViewById(selectedId);
        int selectedOptionIndex = Integer.parseInt(selectedOption.getTag().toString());

        if (selectedOptionIndex == answers[currentQuestionIndex]) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            correctAnswers++; // Increment correct answers count
        } else {
            Toast.makeText(this, "Incorrect! The correct answer is: " + options[currentQuestionIndex][answers[currentQuestionIndex]], Toast.LENGTH_LONG).show();
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            loadQuestion();
            optionGroup.clearCheck(); // Reset the selected option
        } else {
            showResults(); // Show results when quiz is finished
        }
    }

    private void displayHint() {
        switch (currentQuestionIndex) {
            case 0:
                Toast.makeText(this, "Consider the communication between components.", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "Look for the method to initiate another Activity.", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "This method is called when the Activity is first set up.", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "Buttons in XML are declared with the <Button> tag.", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Fragments are part of Activities and not standalone.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showResults() {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("CORRECT_ANSWERS", correctAnswers); // Pass the score
        intent.putExtra("TOTAL_QUESTIONS", questions.length); // Pass the total number of questions
        startActivity(intent);
        finish(); // Optional: finish MainActivity to prevent returning to it
    }
}
