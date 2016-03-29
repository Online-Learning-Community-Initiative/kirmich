package com.rolc.kirmich;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ContentDB database;

    public HomeActivity() {
        super();
    }

    public void setListView(ListAdapter l_adapter) {
        ListView list = (ListView) findViewById(R.id.resultList);
        list.setAdapter(l_adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        database = new TagDB(getApplicationContext());

        ListAdapter l_adapter = database.getStarred();
        setListView(l_adapter);

        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.searchField);
        ArrayAdapter<String> tv_adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<>(database.getAllTags()));
        textView.setAdapter(tv_adapter);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.starredContent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListAdapter l_adapter = database.getStarred();
                setListView(l_adapter);
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
            setListView(l_adapter);
        }
    }
}
