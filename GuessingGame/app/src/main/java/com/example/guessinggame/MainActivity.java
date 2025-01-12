package com.example.guessinggame;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

// Brenna Pavlinchak
// Term C202501
// Main Activity

public class MainActivity extends AppCompatActivity
{

    private EditText[] inputFields;
    private int[] targetNumbers;
    private int remainingGuesses;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFields = new EditText[]{
                findViewById(R.id.number1),
                findViewById(R.id.number2),
                findViewById(R.id.number3),
                findViewById(R.id.number4)
        };

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> handleSubmit());

        startNewGame();
    }

    private void startNewGame()
    {
        remainingGuesses = 4;
        targetNumbers = generateRandomNumbers();

        for (EditText input : inputFields)
        {
            input.setText("");
            input.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }

        Toast.makeText(this, "New game started! Good luck!", Toast.LENGTH_SHORT).show();
    }

    private int[] generateRandomNumbers()
    {
        Random random = new Random();
        int[] numbers = new int[4];
        for (int i = 0; i < 4; i++)
        {
            numbers[i] = random.nextInt(10); // Generate numbers between 0 and 9
        }
        return numbers;
    }

    private void handleSubmit()
    {
        if (!validateInputs())
        {
            Toast.makeText(this, "Please enter valid numbers in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        int[] userGuess = new int[4];
        for (int i = 0; i < inputFields.length; i++)
        {
            userGuess[i] = Integer.parseInt(inputFields[i].getText().toString());
        }

        boolean isCorrect = evaluateGuess(userGuess);

        if (isCorrect)
        {
            showAlert("Congratulations!", "You guessed all numbers correctly!", true);
        }
        else
        {
            remainingGuesses--;
            if (remainingGuesses == 0)
            {
                showAlert("Game Over", "You ran out of guesses. The correct numbers were: " +
                        formatTargetNumbers(), false);
            }
            else
            {
                Toast.makeText(this, "Incorrect! You have " + remainingGuesses + " guesses left.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInputs()
    {
        for (EditText input : inputFields)
        {
            if (TextUtils.isEmpty(input.getText()))
            {
                return false;
            }
        }
        return true;
    }

    private boolean evaluateGuess(int[] guess)
    {
        boolean isCorrect = true;

        for (int i = 0; i < targetNumbers.length; i++)
        {
            if (guess[i] == targetNumbers[i])
            {
                inputFields[i].setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            }
            else
            {
                isCorrect = false;
                if (guess[i] < targetNumbers[i])
                {
                    inputFields[i].setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                }
                else
                {
                    inputFields[i].setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        }

        return isCorrect;
    }

    private void showAlert(String title, String message, boolean restartGame)
    {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) ->
                {
                    if (restartGame)
                    {
                        startNewGame();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private String formatTargetNumbers()
    {
        StringBuilder sb = new StringBuilder();
        for (int num : targetNumbers)
        {
            sb.append(num).append(" ");
        }
        return sb.toString().trim();
    }
}