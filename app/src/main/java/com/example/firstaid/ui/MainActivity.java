package com.example.firstaid.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstaid.R;
import com.example.firstaid.ui.FirstAidActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(R.id.startButton);
        Button viewReportsButton = (Button) findViewById(R.id.viewReportsButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFirstAid();
            }
        });

        viewReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SavedReportsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startFirstAid() {
        Intent intent = new Intent(this, FirstAidActivity.class);
        startActivity(intent);
    }
}
