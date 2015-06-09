package android.assimilated.pocketbartender;

import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    MainApp app;
    int day;

    ImageView drinkImg;
    TextView descr;
    String description;

    EditText search;
    RadioGroup filter;
    Button goSearch;
    Button gamesButton;
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
        int size = app.recipeList.size();
        int index = day % size;
        Recipe drinkOfTheDay = app.recipeList.get(index);
        description = drinkOfTheDay.getName() + ": " + drinkOfTheDay.getDescription();

        drinkImg = (ImageView) findViewById(R.id.drinkPic);
        descr = (TextView) findViewById(R.id.description);
        search = (EditText) findViewById(R.id.userText);
        filter = (RadioGroup) findViewById(R.id.options);
        goSearch = (Button) findViewById(R.id.btnGo);
        byName = (RadioButton) findViewById(R.id.name);
        byIngredient = (RadioButton) findViewById(R.id.ingredient);
        byCost = (RadioButton) findViewById(R.id.cost);
        gamesButton = (Button) findViewById(R.id.gamesButton);

        String imageName = drinkOfTheDay.getName().toLowerCase().replace(" ", "");
        int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());

        drinkImg.setImageResource(resID);
        descr.setText(description);


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

        gamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(MainActivity.this, GameResultsActivity.class);
                startActivity(next);
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
