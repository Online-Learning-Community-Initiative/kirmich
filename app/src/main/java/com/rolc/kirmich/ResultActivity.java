package com.rolc.kirmich;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.rolc.kirmich.contentdb.ContentDB;
import com.rolc.kirmich.contentdb.TagDB;

import java.util.ArrayList;
import java.util.List;

import static android.database.DatabaseUtils.dumpCursorToString;

public class ResultActivity extends AppCompatActivity {
    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        queryString = getIntent().getExtras().getString("QUERY");
        Log.v("QUERY ====", queryString);

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

        ContentDB database = new TagDB(getApplicationContext());
        String stringArray[] = new String[10];
        //TODO: Add all queries into the stringArray (Parse the queryString)
        stringArray[0] = "addition_maths".trim(); //DUMMY
        Cursor cursor = database.Search(stringArray, 0);
        Log.v("CURSORRESULTACT=======", dumpCursorToString(cursor));
        CursorAdapter searchAdapter = new SearchAdapter(getApplicationContext(), cursor);
        list.setAdapter(searchAdapter);

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
