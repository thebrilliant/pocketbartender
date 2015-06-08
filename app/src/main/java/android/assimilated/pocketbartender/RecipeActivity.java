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
public class RecipeActivity extends ActionBarActivity {

    MainApp app;
    Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_layout);

        //filler value; this needs to be received as an intent from the search function
        //which will determine exactly what recipe to display
        app = (MainApp) getApplication();
        Intent launcher = getIntent();
        String name = launcher.getStringExtra("recipe");
        currentRecipe = app.searchByName(name).get(0);

        //filler change
        //title text box txtRecipeName
        //description text box txtDescr
        //start button btnNext

        //uses fields from MainApp to access recipes and ingredients

        //use strings from strings.xml for button text
        TextView txtRecipeName = (TextView) findViewById(R.id.txtRecipeName);
        final Button btnNext = (Button) findViewById(R.id.btnNext);

        txtRecipeName.setText(currentRecipe.getName());
        btnNext.setText(R.string.start);

        RecipeFragment.currentStepNum = -2;

        createFragment();

        RecipeFragment.currentStepNum = -1;

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment();
            }
        });
    }

    private void createFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle recipeBundle = new Bundle();
        recipeBundle.putString("recipe", currentRecipe.getName());

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(recipeBundle);
        ft.setCustomAnimations(R.animator.flip_in_left,R.animator.flip_out_left,R.animator.flip_in_right,R.animator.flip_out_right);
        ft.add(R.id.container, recipeFragment);
        ft.commit();
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
