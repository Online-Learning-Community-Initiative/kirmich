package com.rolc.kirmich;

import com.rolc.kirmich.contentdb.ContentDB;
import com.rolc.kirmich.contentdb.TagDB;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private ContentDB database;

    public HomeActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        database = new TagDB(getApplicationContext());

        ListAdapter l_adapter = database.getStarred();
        ListView list = (ListView) findViewById(R.id.resultList);
        list.setAdapter(l_adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("ITEM CLICKED ==== ", "YESSSSSSS");
                Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
                // TODO: detailIntent.putExtra();
                startActivity(detailIntent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.starredContent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView list = (ListView) findViewById(R.id.resultList);
                ListAdapter l_adapter = database.getStarred();
                list.setAdapter(l_adapter);
            }
        });

        final EditText et = (EditText) findViewById(R.id.searchField);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean gotfocus) {
                if (gotfocus) {
                    et.setCompoundDrawables(null, null, null, null);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // TODO: Launch in-app settings here!
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View view) {
        ListView list = (ListView) findViewById(R.id.resultList);
        EditText query = (EditText) findViewById(R.id.searchField);
        String queryString = query.getText().toString();
        if (queryString == null || queryString.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please type a query!", Toast.LENGTH_LONG).show();
        } else {
            Log.v("SEARCHED FOR ====", queryString);

            String stringArray[] = new String[10];
            // TODO: Add the real queries into the stringArray (Parse the queryString)
            stringArray[0] = "addition_maths".trim(); //DUMMY
            ListAdapter l_adapter = database.searchTags(stringArray);
            list.setAdapter(l_adapter);

        }
    }
}
