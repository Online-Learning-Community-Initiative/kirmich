package com.rolc.kirmich;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListView list = (ListView) findViewById(R.id.starredList);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //TODO: Launch in-app settings here!
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View view) {
        EditText query = (EditText) findViewById(R.id.searchField);
        String queryString = query.toString();

        //Query the db.

        Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);
        //TODO: resultIntent.putExtra();
        startActivity(resultIntent);
    }
}
