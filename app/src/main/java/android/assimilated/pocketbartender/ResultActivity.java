package android.assimilated.pocketbartender;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by iguest on 5/28/15.
 */
public class ResultActivity extends ActionBarActivity {

    MainApp app;

    TextView userText;
    ListView list;

    String userSearch;
    String type;
    List<Recipe> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_layout);

        app = (MainApp) getApplication();

        Intent launchingIntent = getIntent();
        userSearch = launchingIntent.getStringExtra("search");
        type = launchingIntent.getStringExtra("type");

        userText = (TextView) findViewById(R.id.txtSearch);
        list = (ListView) findViewById(R.id.lstResult);

        userText.setText(userSearch);

        if (type.equalsIgnoreCase("name")) {
            results = app.searchByName(userSearch);
        } else if (type.equalsIgnoreCase("ingredient")) {
            results = app.searchByIngredient(userSearch);
        } else {
            double cost = Double.parseDouble(userSearch);
            results = app.searchByAmount(cost);
        }

        ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(this, android.R.layout.simple_list_item_1, results);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
