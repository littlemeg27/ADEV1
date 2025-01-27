package com.example.asynctimer;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private EditText minutesField;
    private EditText secondsField;
    private TimerAsyncTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minutesField = findViewById(R.id.edit_minutes);
        secondsField = findViewById(R.id.edit_seconds);

        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startTimer();
            }
        });

        findViewById(R.id.button_stop).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });
    }

    private void startTimer() {
        String minutesInput = minutesField.getText().toString();
        String secondsInput = secondsField.getText().toString();

        int minutes = minutesInput.isEmpty() ? 0 : Integer.parseInt(minutesInput);
        int seconds = secondsInput.isEmpty() ? 0 : Integer.parseInt(secondsInput);

        if (minutes == 0 && seconds == 0)
        {
            Toast.makeText(this, R.string.invalid_time_message, Toast.LENGTH_SHORT).show();
            return;
        }

        int totalSeconds = (minutes * 60) + seconds;

        if (timerTask != null)
        {
            timerTask.cancel(true);
        }

        timerTask = new TimerAsyncTask();
        timerTask.execute(totalSeconds);
    }

    private void stopTimer()
    {
        if (timerTask != null)
        {
            timerTask.cancel(true);
        }
        minutesField.setText("00");
        secondsField.setText("00");
    }

    private class TimerAsyncTask extends AsyncTask<Integer, Integer, Void>
    {
        private long startTime;
        private int totalSeconds;

        @Override
        protected void onPreExecute()
        {
            Toast.makeText(MainActivity.this, R.string.timer_started_message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Integer... params)
        {
            totalSeconds = params[0];
            startTime = System.currentTimeMillis();
            long endTime = startTime + (totalSeconds * 1000);

            while (System.currentTimeMillis() < endTime && !isCancelled())
            {
                long elapsedTime = System.currentTimeMillis() - startTime;
                int remainingSeconds = totalSeconds - (int) (elapsedTime / 1000);

                if (remainingSeconds < 0)
                {
                    remainingSeconds = 0;
                }

                publishProgress(remainingSeconds);

                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    break;
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            int remainingSeconds = values[0];
            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;

            minutesField.setText(String.format("%02d", minutes));
            secondsField.setText(String.format("%02d", seconds));
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(R.string.timer_expired_message)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }

        @Override
        protected void onCancelled()
        {
            long elapsedMillis = System.currentTimeMillis() - startTime;
            int elapsedSeconds = (int) (elapsedMillis / 1000);
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(getString(R.string.timer_stopped_message, elapsedSeconds))
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    }
}