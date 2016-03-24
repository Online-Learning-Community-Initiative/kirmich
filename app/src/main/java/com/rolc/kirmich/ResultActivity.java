package com.rolc.kirmich;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.homeFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        ListView list = (ListView) findViewById(R.id.resultList);
        List<String> array_list = new ArrayList<String>();
        array_list.add("Sample 1");
        array_list.add("Sample 2");
        array_list.add("Sample 3");

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.list_item, array_list);
        list.setAdapter(listAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
                //TODO: detailIntent.putExtra();
                startActivity(detailIntent);
            }
        });
    }

}
