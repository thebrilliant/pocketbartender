package android.assimilated.pocketbartender;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    MainApp app;
    int day;

    ImageView drinkImg;
    TextView descr;

    EditText search;
    RadioGroup filter;
    Button goSearch;
    RadioButton byName;
    RadioButton byIngredient;
    RadioButton byCost;
    String searchText;
    String filterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (MainApp) getApplication();
        day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        /*if (day != app.today) {
            //update drink of the day
            app.today = day;
        }*/

        drinkImg = (ImageView) findViewById(R.id.drinkPic);
        descr = (TextView) findViewById(R.id.description);
        search = (EditText) findViewById(R.id.userText);
        filter = (RadioGroup) findViewById(R.id.options);
        goSearch = (Button) findViewById(R.id.btnGo);
        byName = (RadioButton) findViewById(R.id.name);
        byIngredient = (RadioButton) findViewById(R.id.ingredient);
        byCost = (RadioButton) findViewById(R.id.cost);

        drinkImg.setImageResource(R.drawable.moscowmule);
        descr.setText("TESTING!!!!!");

        // Gets all the JSON goodiez
        // Has to be done in its own thread or shit hits the fan
//        Thread thread = new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    // grabbing JSON files from student server
//                    String ingredientsJSON = getJSON("http://students.washington.edu/ghirme/info498c/ingredients.json");
//                    String recipesJSON = getJSON("http://students.washington.edu/ghirme/info498c/recipes.json");
//
//                    try{
//                        JSONArray ingredientsObject = new JSONArray(ingredientsJSON);
//                        JSONObject recipesObject = new JSONObject(recipesJSON);
//                    } catch(Exception e){
//                        e.printStackTrace();
//                    } finally {
//                        // Do stuff with ingredients/recipes JSON objects
//                    };
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();

        filter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == byName.getId()) {
                    filterType = (String) byName.getText();
                } else if (checkedId == byIngredient.getId()){
                    filterType = (String) byIngredient.getText();
                } else {
                    filterType = (String) byCost.getText();
                }
            }
        });

        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = search.getText().toString();
                if (filterType == null) {
                    Toast.makeText(MainActivity.this, "Please select what filter you want to search with", Toast.LENGTH_LONG).show();
                } else if (searchText == null) {
                    Toast.makeText(MainActivity.this, "Please enter the text you want to search", Toast.LENGTH_LONG).show();
                } else {
                    Intent next = new Intent(MainActivity.this, ResultActivity.class);
                    next.putExtra("search", searchText);
                    next.putExtra("type", filterType);

                    startActivity(next);
                }
            }
        });

    }

    // takes a URL String and returns the JSON as a string
    public String getJSON(String address){
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(address);
        try{
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }
            } else {
                Log.e(MainActivity.class.toString(), "Failedet JSON object");
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return builder.toString();
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
