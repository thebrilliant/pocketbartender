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
import java.util.Map;
import java.util.Set;

/**
 * Created by iguest on 5/28/15.
 */
public class MainApp extends Application {
    public static int today;
    private static MainApp instance = null;

    ArrayList<Ingredient> ingredientList;
    ArrayList<Recipe> recipeList;

    HashMap<String, List<Recipe>> nameSearch;
    HashMap<String, List<Recipe>> ingredientSearch;

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

        buildSearchByIngredient();
        buildSearchByName();

            // Gets all the JSON goodiez
            // Has to be done in its own thread or shit hits the fan
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ingredientList = new ArrayList<Ingredient>();
                        recipeList = new ArrayList<Recipe>();
                        // grabbing JSON files from student server
                        String ingredientsJSON = getJSON("http://students.washington.edu/ghirme/info498c/ingredients.json");
                        String recipesJSON = getJSON("http://students.washington.edu/ghirme/info498c/recipes.json");

                        JSONArray ingredientsObject;
                        JSONArray recipesObject;

                        ingredientsObject = new JSONArray(ingredientsJSON);
                        recipesObject = new JSONArray(recipesJSON);

                        // create ingredient list
                        for (int i = 0; i < ingredientsObject.length(); i++) {
                            JSONObject jsonObj = ingredientsObject.getJSONObject(i);
                            Ingredient ingredient = new Ingredient(jsonObj);
                            ingredientList.add(ingredient);
                        }

                        // allows ingredients to be accessed statically
                        Recipe recipe = new Recipe();
                        recipe.setIngredientList(ingredientList);

                        // create recipes list
                        for (int j = 0; j < recipesObject.length(); j++) {
                            JSONObject currJSON = recipesObject.getJSONObject(j);
                            Recipe currRecipe = new Recipe(currJSON);
                            recipeList.add(currRecipe);
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
    }

    //Initializes and builds the search directory that allows user to search for a specific drink by name.
    //Allows for searching by partial matches.
    public void buildSearchByName() {
        nameSearch = new HashMap<String, List<Recipe>>();
        for(int i = 0; i < recipeList.size(); i++) {
            Recipe recipe = recipeList.get(i);
            String name = (recipe.getName()).toLowerCase();

            String[] words = name.split("\\s+");
            for(int j = 0; j < words.length; j++) {
                Set<String> indexedWords = nameSearch.keySet();
                if(indexedWords.contains(words[j])) {
                    List<Recipe> list = nameSearch.get(name);
                    list.add(recipe);
                    nameSearch.put(words[j], list);
                } else {
                    List<Recipe> list = new ArrayList<Recipe>();
                    list.add(recipe);
                    nameSearch.put(words[j], list);
                }
            }
        }
    }

    //Initializes and builds the search directory (ingredientSearch) that allows for the user to search for recipes based
    //on an ingredient. Built based on Ingredient names, so 'lime' and 'lime juice' are two different things.
    public void buildSearchByIngredient() {
        ingredientSearch = new HashMap<String, List<Recipe>>();
        for(int i = 0; i < recipeList.size(); i++) {
            Recipe recipe = recipeList.get(i);
            Map<Ingredient, Double> recipeIngredients = recipe.getIngredientToQuantity();
            Set<Ingredient> ingredients = recipeIngredients.keySet();
            for(Ingredient ingredient : ingredients) {
                String name = (ingredient.getName()).toLowerCase();
                Set<String> indexedIngredients = ingredientSearch.keySet();
                if(indexedIngredients.contains(name)) {
                    List<Recipe> recipes = ingredientSearch.get(name);
                    //Ensures no duplicate recipes
                    if(!containsRecipe(recipe, recipes)) {
                        recipes.add(recipe);
                        ingredientSearch.put(name, recipes);
                    }
                } else {
                    List<Recipe> list = new ArrayList<Recipe>();
                    list.add(recipe);
                    ingredientSearch.put(name, list);
                }
            }
        }
    }

    private boolean containsRecipe(Recipe recipe, List<Recipe> list) {
        for(Recipe entry : list) {
            if(recipe == entry) {
                return true;
            }
        }
        return false;
    }

    //Returns results for recipes that are relevant to the phrase the user typed in.
    //RELEVANCE: If there is a perfect match it goes first in the list, the rest are just partial
    //matches, no relevance for those yet.
    //PARAMETERS: String (user search phrase).
    //RETURNS: List<Recipe> Search results.
    public List<Recipe> searchByName(String phrase) {
        String keyphrase = phrase.trim().toLowerCase();
        List<Recipe> results = new ArrayList<Recipe>();

        //Attempt to find perfect match
        for(Recipe recipe : recipeList) {
            String name = recipe.getName().toLowerCase();
            if(keyphrase.equals(name)) {
                results.add(recipe);
            }
        }

        //Find partial matches
        String[] keyWords = keyphrase.split("\\s+");
        Set<String> indexedWords = nameSearch.keySet();
        for(String word : keyWords) {
            if(indexedWords.contains(word)) {
                List<Recipe> currentRecipes = nameSearch.get(word);
                for(Recipe entry : currentRecipes) {
                    if(!containsRecipe(entry, results)) {
                        results.add(entry);
                    }
                }
            }
        }
        return results;
    }

    //Returns results for recipes that are relevant to the ingredient the user typed in.
    //PARAMETERS: String (user search phrase).
    //RETURNS: List<Recipe> Search results.
    public List<Recipe> searchByIngredient(String phrase) {
        List<Recipe> results = new ArrayList<Recipe>();
        String keyIngredient = phrase.trim().toLowerCase();

        Set<String> indexedIngredients = ingredientSearch.keySet();

        if(indexedIngredients.contains(keyIngredient)) {
            results.addAll(ingredientSearch.get(keyIngredient));
        }

        return results;
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