package com.rolc.kirmich;

import com.rolc.kirmich.contentdb.ContentDB;
import com.rolc.kirmich.contentdb.TagDB;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.support.design.widget.FloatingActionButton;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String queryString = getIntent().getExtras().getString("QUERY");
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

        ContentDB database = new TagDB(getApplicationContext());
        String stringArray[] = new String[10];
        // TODO: Add all queries into the stringArray (Parse the queryString)
        stringArray[0] = "addition_maths".trim(); //DUMMY
        ListAdapter ladapter = database.searchTags(stringArray);
        ListView list = (ListView) findViewById(R.id.resultList);
        list.setAdapter(ladapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
                // TODO: detailIntent.putExtra();
                startActivity(detailIntent);
            }
        });
    }
}
