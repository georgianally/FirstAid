package com.example.firstaid.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firstaid.R;
import com.example.firstaid.model.DBHelper;
import com.example.firstaid.model.Report;

public class ReportActivity extends AppCompatActivity {

    private TextView startTextView;
    private TextView finishTextView;
    private TextView locationTextView;
    private TextView safeTextView;
    private TextView dangerTextView;
    private TextView responsiveTextView;
    private TextView bleedTextView;
    private TextView cprTextView;

    private Button saveButton;
    private Button viewReportsButton;

    Report report;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        report = getIntent().getExtras().getParcelable("Report");

        mydb = new DBHelper(this);

        startTextView = (TextView)findViewById(R.id.startTextView);
        finishTextView = (TextView)findViewById(R.id.finishTextView);
        locationTextView = (TextView)findViewById(R.id.locationTextView);
        safeTextView = (TextView)findViewById(R.id.safeTextView);
        dangerTextView = (TextView)findViewById(R.id.dangerTextView);
        responsiveTextView = (TextView)findViewById(R.id.responsiveTextView);
        bleedTextView = (TextView)findViewById(R.id.bleedTextView);
        cprTextView = (TextView)findViewById(R.id.cprTextView);

        saveButton = (Button)findViewById(R.id.saveButton);
        viewReportsButton = (Button)findViewById(R.id.viewReportsButton);


        startTextView.setText(startTextView.getText() + report.getStart());
        finishTextView.setText(finishTextView.getText() + report.getFinish());
        locationTextView.setText(locationTextView.getText() + report.getLocation());
        safeTextView.setText(safeTextView.getText() + report.getSafe());
        dangerTextView.setText(dangerTextView.getText() + report.getDanger());
        responsiveTextView.setText(responsiveTextView.getText() + report.getResponsive());
        bleedTextView.setText(bleedTextView.getText() + report.getBleed());
        cprTextView.setText(cprTextView.getText() + report.getCpr());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(ReportActivity.this);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ReportActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mydb.insertLocation(userInput.getText().toString(),
                                                report.getStart(),
                                                report.getFinish(),
                                                report.getLocation(),
                                                report.getSafe(),
                                                report.getDanger(),
                                                report.getResponsive(),
                                                report.getBleed(),
                                                report.getCpr());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        viewReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, SavedReportsActivity.class);
                startActivity(intent);
            }
        });
    }
}
