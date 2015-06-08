package android.assimilated.pocketbartender;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

/**
 * Created by iguest on 5/28/15.
 */
public class ResultActivity extends ActionBarActivity {

    MainApp app;

    TextView userText;

    String userSearch;
    String type;
    Ligst<Recipe> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_layout);

        app = (MainApp) getApplication();

        Intent launchingIntent = getIntent();
        userSearch = launchingIntent.getStringExtra("search");
        type = launchingIntent.getStringExtra("type");

        userText = (TextView) findViewById(R.id.txtSearch);

        userText.setText(userSearch);

        if (type.equalsIgnoreCase("name")) {
            app.searchByName(userSearch);
        } else if (type.equalsIgnoreCase("ingredients")) {
            app.searchByIngredient(userSearch);
        } else {
            double cost = Double.parseDouble(userSearch);
            app.searchByAmount(cost);
        }
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
