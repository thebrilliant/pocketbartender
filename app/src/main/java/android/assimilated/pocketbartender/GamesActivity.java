package android.assimilated.pocketbartender;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by iguest on 5/28/15.
 */
public class GamesActivity extends ActionBarActivity {

    MainApp app;
    Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games_layout);

        app = (MainApp) getApplication();

        Intent launcher = getIntent();
        String name = launcher.getStringExtra("name");
        String instructions = launcher.getStringExtra("instructions");

        TextView titleView = (TextView) findViewById(R.id.txtGameName);
        TextView instructionsView = (TextView) findViewById(R.id.txtInstructions);

        // Sets text for views
        titleView.setText(name);
        instructionsView.setText(instructions);

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
        }

        return super.onOptionsItemSelected(item);
    }
}
