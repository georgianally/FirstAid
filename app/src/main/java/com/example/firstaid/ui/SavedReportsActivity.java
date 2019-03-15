package com.example.firstaid.ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firstaid.R;
import com.example.firstaid.model.DBHelper;
import com.example.firstaid.model.Report;

import java.util.ArrayList;

public class SavedReportsActivity extends AppCompatActivity {

    ListView locationList;
    Button deleteAllButton;
    ArrayList<String> locationData;

    private DBHelper mydb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_reports);


        mydb = new DBHelper(this);
        Toast toast = Toast.makeText(getApplicationContext(), "" + mydb.numberOfRows(), Toast.LENGTH_SHORT); toast.show();

        locationList = (ListView) findViewById(R.id.locationsListView);
        deleteAllButton = (Button) findViewById(R.id.deleteAllButton);
        locationData = new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locationData);
        locationList.setAdapter(adapter);

        initList(adapter);
        adapter.notifyDataSetChanged();

        locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedLocation = locationData.get(position);

                for (int i = 0; i < mydb.numberOfRows(); i++){
                    Cursor rs = mydb.getData(i);
                    if(rs.moveToFirst()) {
                        String dbName = mydb.getSavedLocations().get(i);

                        if(clickedLocation.equals(dbName)){
                            Intent intent = new Intent(SavedReportsActivity.this, ReportActivity.class);
                            Report savedReport = new Report(mydb.getStart().get(i));
                            savedReport.setFinish(mydb.getFinish().get(i));
                            savedReport.setLocation(mydb.getLocation().get(i));
                            savedReport.setSafe(mydb.getSafe().get(i));
                            savedReport.setDanger(mydb.getDanger().get(i));
                            savedReport.setResponsive(mydb.getResponsive().get(i));
                            savedReport.setBleed(mydb.getBleed().get(i));
                            savedReport.setCpr(mydb.getCPR().get(i));
                            intent.putExtra("Report", savedReport);
                            startActivity(intent);
                            break;
                        }
                    }
                    rs.close();
                }
            }
        });


        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.deleteAll();
                locationData.clear();
                adapter.notifyDataSetChanged();
                initList(adapter);
            }
        });


    }

    private void initList(ArrayAdapter adapter) {
        for (int i = 0; i < mydb.numberOfRows(); i++){
            Cursor rs = mydb.getData(i);
            if(rs.moveToFirst()) {
                locationData.add(mydb.getSavedLocations().get(i));
            }
            rs.close();
        }
        adapter.notifyDataSetChanged();
    }
}
