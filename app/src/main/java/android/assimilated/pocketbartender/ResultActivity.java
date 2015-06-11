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
import android.widget.Toast;

import java.util.ArrayList;
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
            // if search is empty just return empty array
            results = userSearch.isEmpty() ? new ArrayList<Recipe>() :
                    app.searchByIngredient(userSearch);
        } else {
            results = userSearch.isEmpty() ? new ArrayList<Recipe>() :
                    app.searchByAmount(Double.parseDouble(userSearch));
        }
        Recipe[] results2 = new Recipe[results.size()];
        for(int i = 0; i < results.size(); i++) {
            results2[i] = results.get(i);
        }
        RecipeArrayAdapter adapter = new RecipeArrayAdapter(this, (Recipe[]) results2);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent next = new Intent(ResultActivity.this, RecipeActivity.class);
                String recipeName = results.get(position).getName();
                next.putExtra("recipe", recipeName);

                //Toast.makeText(ResultActivity.this, "recipe: " + recipeName, Toast.LENGTH_SHORT).show();
                startActivity(next);
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
        } else if (id == R.id.action_games) {
            Intent i = new Intent(getApplicationContext(), GameResultsActivity.class);
            startActivity(i);

            return true;
        } else if (id == R.id.action_home) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
