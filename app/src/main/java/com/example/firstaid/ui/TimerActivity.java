package com.example.firstaid.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.firstaid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TimerActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton;
    private Button choice1Button;
    private Button choice2Button;
    private Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        final boolean responsive = getIntent().getExtras().getBoolean("isResponsive");

        timerTextView = (TextView)findViewById(R.id.timerTextView);
        startButton = (Button) findViewById(R.id.startButton);
        choice1Button = (Button)findViewById(R.id.choice1Button);
        choice2Button = (Button)findViewById(R.id.choice2Button);
        skipButton = (Button) findViewById(R.id.skipButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timerTextView.setText("Seconds Remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        timerTextView.setText("Done");
                    }
                }.start();
            }
        });

        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimer(6);
            }
        });
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(responsive)
                    endTimer(9);
                else
                    endTimer(7);
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(responsive)
                    endTimer(9);
                else
                    endTimer(7);
            }
        });
    }

    private void endTimer(int i) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("page", i);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
