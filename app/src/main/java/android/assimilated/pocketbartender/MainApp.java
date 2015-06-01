package android.assimilated.pocketbartender;

import android.app.Application;
import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by iguest on 5/28/15.
 */
public class MainApp extends Application {
    public static int today;
    private static MainApp instance = null;

    ArrayList<Ingredient> ingredientList;
    ArrayList<Recipe> recipeList;

    HashMap<String, Recipe> nameSearch;

    public MainApp() {
        //ensures that there is only one instance of QuizApp
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one MainApp");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        ingredientList = new ArrayList<Ingredient>();
        Ingredient ingredient1 = new Ingredient("Lime juice", "Citrus", 0.50, 10, "oz");
        ingredientList.add(ingredient1);
        Ingredient ingredient2 = new Ingredient("Ice cubes", "Chiller", 0.00, 2, "cube");
        ingredientList.add(ingredient2);
        Ingredient ingredient3 = new Ingredient("vodka", "hard", 1.00, 1, "oz");
        ingredientList.add(ingredient3);
        Ingredient ingredient4 = new Ingredient("ginger beer", "soda", 2.00, 3, "oz");
        ingredientList.add(ingredient4);

        recipeList = new ArrayList<Recipe>();



        Recipe recipe = new Recipe();
        recipe.setIngredientList(ingredientList);

        try {
            JSONArray jsonData = new JSONArray(loadJSONFromAsset());

            for(int i = 0; i < jsonData.length(); i++) {
                JSONObject jsonObj = jsonData.getJSONObject(i);
                Recipe addRecipe = new Recipe(jsonObj);
                recipeList.add(addRecipe);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        buildSearchByName();

        // Gets all the JSON goodiez
        // Has to be done in its own thread or shit hits the fan
/*
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        ingredientList = new ArrayList<Ingredient>();

                        // grabbing JSON files from student server
                        String ingredientsJSON = getJSON("http://students.washington.edu/ghirme/info498c/ingredients.json");
                        String recipesJSON = getJSON("http://students.washington.edu/ghirme/info498c/recipes.json");

                        JSONArray ingredientsObject;
                        JSONArray recipesObject;

                        ingredientsObject = new JSONArray(ingredientsJSON);
                        recipesObject = new JSONArray(recipesJSON);

                        for (int i = 0; i < ingredientsObject.length(); i++) {
                            JSONObject jsonObj = ingredientsObject.getJSONObject(i);
                            String name = jsonObj.getString("name");
                            String type = jsonObj.getString("type");
                            Double pricePerUnit = Double.parseDouble(jsonObj.getString("pricePerUnit"));
                            String unit = jsonObj.getString("unit");

                            Ingredient ingredient = new Ingredient(name, type, pricePerUnit, 0, unit);
                            ingredientList.add(ingredient);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                thread.start();
                thread.join();
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }

            Log.i("MainApp", ingredientList.size() + ""); */
    }

    public void buildSearchByName() {
        HashMap directory = new HashMap<String, Recipe>();
        for(int i = 0; i < recipeList.size(); i++) {
            Recipe recipe = recipeList.get(i);
            String name = recipe.getName();

            String[] words = name.split(" ");
            for(int j = 0; j < words.length; j++) {
                directory.put(words[j].toLowerCase(), recipe);
            }
        }
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

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("recipes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public List<Ingredient> getAllIngredients(){return ingredientList;}
}