package com.example.firstaid.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstaid.R;
import com.example.firstaid.model.DBHelper;
import com.example.firstaid.model.Page;
import com.example.firstaid.model.Protocol;
import com.example.firstaid.model.Report;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

public class FirstAidActivity extends AppCompatActivity {


    //Sets up custom back button
    private Stack<Integer> pageStack = new Stack<Integer>();
    //Set up views
    private Protocol protocol;
    private TextView promptText;
    private Button choice1Button;
    private Button choice2Button;

    private int nextPage;
    private int currentPage;
    private boolean responsive = false;
    Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);

        Date currentTime = Calendar.getInstance().getTime();
        report = new  Report(currentTime.toString());

        //Initialise Views
        promptText = (TextView) findViewById(R.id.promptText);
        choice1Button = (Button) findViewById(R.id.choice1Button);
        choice2Button = (Button) findViewById(R.id.choice2Button);
        Button skipButton = (Button) findViewById(R.id.skipButton);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPage(currentPage+1);
            }
        });

        protocol = new Protocol();
        loadPage(0);

    }

    private void loadPage(int pageNumber) {
        //Add page number to stack (custom back button)
        Log.i("My Activity", report.getSafe() + "   " +report.getResponsive() + "   " + report.getDanger());
        pageStack.push(pageNumber);

        currentPage = pageNumber;
        final Page page = protocol.getPage(pageNumber);

        String pageText = getString(page.getTextId());
        promptText.setText(pageText);


        //If only one button
        if(pageNumber == 8){
            Date currentTime = Calendar.getInstance().getTime();
            report.setFinish(currentTime.toString());
            startReportActivity();
        }
        else if (page.isSingleButton() && pageNumber != 7) {
            singlePageUI();
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view){
                    startTimer();
                }
            });
        }
        else if (page.isSingleButton()) {
            singlePageUI();
            headCheckPageUI(page);
        } else { //else set up page as normal
            loadButtons(page, pageNumber);
        }
    }

    private void startReportActivity() {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("Report", report);
        startActivity(intent);
    }

    private void loadButtons(final Page page, final int pageNumber) {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());
        //When click choice1Button get next page and load page
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(pageNumber){
                    case 0:
                        report.setSafe("Yes");
                        break;
                    case 1:
                        report.setDanger("Yes");
                        break;
                    case 2:
                        report.setBleed("Yes");
                        break;
                    case 3:
                        report.setResponsive("Yes");
                        break;
                    case 6:
                        report.setCpr("Yes");
                        break;
                }

                if (page.getChoice1().getNextPage() == 4) {
                    responsive = true;
                }
                nextPage = page.getChoice1().getNextPage();
                loadPage(nextPage);
            }
        });

        choice2Button.setVisibility(View.VISIBLE);
        choice2Button.setText(page.getChoice2().getTextId());
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(pageNumber){
                    case 0:
                        report.setSafe("No");
                        break;
                    case 1:
                        report.setDanger("No - Call 999");
                        break;
                    case 2:
                        report.setBleed("No");
                        break;
                    case 3:
                        report.setResponsive("No");
                        break;
                }

                nextPage = page.getChoice2().getNextPage();
                loadPage(nextPage);
            }
        });
    }

    private void startTimer() {
        report.setCpr("Yes");
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("isResponsive", responsive);
        startActivityForResult(intent, 1);
    }

    private void singlePageUI() {
        choice1Button.setVisibility(View.INVISIBLE);
        choice2Button.setText(R.string.done_button_text);
    }

    //Refactor and simplify? Looping page 7 UI
    private void headCheckPageUI(final Page page) {
        final String[] headToeCheck = {"Head", "Body", "Upper Legs", "Lower Legs", "Arms"};
        promptText.setText(getString(page.getTextId(), headToeCheck[0]));
        final int[] i = {1};

        //When click done - display next word in array
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i[0] < headToeCheck.length - 1) {
                    i[0]++;
                    promptText.setText(getString(page.getTextId(), headToeCheck[i[0]]));
                } else {
                    nextPage = page.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            int page = data.getIntExtra("page", 0);
            loadPage(page);
        }
    }

    @Override
    public void onBackPressed() {
        //Removes top page from stack
        pageStack.pop();
        //Will go back to Start page if on first page
        if (pageStack.isEmpty()) {
            super.onBackPressed();
        }
        else {
            loadPage(pageStack.pop());
        }
    }
}
