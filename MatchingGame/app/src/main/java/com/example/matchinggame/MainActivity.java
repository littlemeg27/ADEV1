package com.example.matchinggame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.ArrayList;

// Brenna Pavlinchak
// Term C202501
// Main Activity

public class MainActivity extends AppCompatActivity
{
    private TextView textGuesses;
    private Button buttonRestart;
    private GridLayout gridButtons;
    private Button[] buttons;
    private ArrayList<Character> letterPairs;
    private int guesses = 0;
    private Button firstButton = null;
    private boolean isProcessing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textGuesses = findViewById(R.id.text_guesses);
        buttonRestart = findViewById(R.id.button_restart);
        gridButtons = findViewById(R.id.grid_buttons);

        buttonRestart.setOnClickListener(v -> restartGame());

        initializeGame();
    }

    private void initializeGame()
    {
        guesses = 0;
        textGuesses.setText(String.valueOf(guesses));
        buttonRestart.setVisibility(View.GONE);

        generateLetterPairs();

        setupButtons();
    }

    private void generateLetterPairs()
    {
        letterPairs = new ArrayList<>();

        for (char letter = 'A'; letter <= 'H'; letter++)
        {
            letterPairs.add(letter);
            letterPairs.add(letter);
        }
        Collections.shuffle(letterPairs);
    }

    private void setupButtons()
    {
        gridButtons.removeAllViews();
        buttons = new Button[16];

        for (int i = 0; i < 16; i++)
        {
            final int index = i;
            Button button = new Button(this);
            button.setLayoutParams(new GridLayout.LayoutParams());
            button.setText("");
            button.setOnClickListener(v -> handleButtonClick(index));
            buttons[i] = button;
            gridButtons.addView(button);
        }
    }

    private void handleButtonClick(int index)
    {
        if (isProcessing || buttons[index].getText().length() > 0)
        {
            return;
        }

        buttons[index].setText(String.valueOf(letterPairs.get(index)));

        if (firstButton == null)
        {
            firstButton = buttons[index];
        }
        else
        {
            guesses++;
            textGuesses.setText(String.valueOf(guesses));

            if (firstButton.getText().equals(buttons[index].getText()))
            {
                firstButton.setVisibility(View.INVISIBLE);
                buttons[index].setVisibility(View.INVISIBLE);
                firstButton = null;
                checkGameCompletion();
            }
            else
            {
                isProcessing = true;

                buttons[index].postDelayed(() ->
                {
                    firstButton.setText("");
                    buttons[index].setText("");
                    firstButton = null;
                    isProcessing = false;
                }, 1000);
            }
        }
    }

    private void checkGameCompletion()
    {
        for (Button button : buttons)
        {
            if (button.getVisibility() == View.VISIBLE)
            {
                return;
            }
        }
        buttonRestart.setVisibility(View.VISIBLE);
    }

    private void restartGame() {
        initializeGame();
    }
}