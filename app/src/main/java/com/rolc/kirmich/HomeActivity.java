package com.rolc.kirmich;

import com.rolc.kirmich.contentdb.*;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rolc.kirmich.contentdb.TagDB;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.database.DatabaseUtils.dumpCursorToString;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListView list = (ListView) findViewById(R.id.starredList);

        /*
        List<String> array_list = new ArrayList<String>();
        array_list.add("Sample 1");
        array_list.add("Sample 2");
        array_list.add("Sample 3");

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.list_item, array_list);
        list.setAdapter(listAdapter);
        */

        ContentDB database = new TagDB(getApplicationContext());
        //TODO: Replace the next line with search query for starred content.
        Cursor cursor = database.Search(new String[]{"addition_maths".trim()}, 0);
        Log.v("CURSOR =======", dumpCursorToString(cursor));
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
        String queryString = query.getText().toString();
        if (queryString == null || queryString.length() == 0) {
            Toast toast = new Toast(getApplicationContext());
            toast.setText("Please type a query!");
            toast.show();
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        } else {
            Log.v("SEARCHED FOR ====", queryString);
            Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);
            resultIntent.putExtra("QUERY", queryString);
            startActivity(resultIntent);
        }
    }
}
