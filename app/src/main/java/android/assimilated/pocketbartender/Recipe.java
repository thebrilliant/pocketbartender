package android.assimilated.pocketbartender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by christina3135 on 5/27/2015.
 */
public class Recipe {
    private static List<Ingredient> ingredientList = new ArrayList<Ingredient>();
    private String name;
    private String type;
    private double totalCost;
    private Map<Ingredient, Double> ingredientToQuantity;
    private List<String> instructions;
    //caloriesPerOz required only for the optional user story
    private int totalCalories;

    public Recipe() {
        this(null, null, 0, null, null, 0);
    }

    //this is probably the only constructor that matters; constructs a recipe object using a given
    //JSON object. Assumes that the Ingredients list has already been populated.
    public Recipe(JSONObject jsonObject) throws JSONException{
        //to be completed once structure of JSON file is determined

        this.name = jsonObject.getString("name");
        this.type = jsonObject.getString("type");

        JSONArray instructionsArray = jsonObject.getJSONArray("instructions");
        this.instructions = jsonArrayToArrayList(instructionsArray);

        //create ingredient to quantity mapping
        ingredientToQuantity = new HashMap<Ingredient, Double>();
        JSONArray ingredientArray = jsonObject.getJSONArray("ingredients");
        JSONArray quantityArray = jsonObject.getJSONArray("quantity");
        for (int i = 0; i < ingredientArray.length(); i++) {
            //find the ingredient object with the given name
            String ingredientName = ingredientArray.get(i).toString();
            Ingredient currentIngredient = stringToIngredient(ingredientName);

            //throws exception if no such ingredient exists
            if (currentIngredient == null) {
                throw new IllegalArgumentException("No such ingredient exists: " + ingredientName);
            }

            double quantity = Double.parseDouble(quantityArray.get(i).toString());
            ingredientToQuantity.put(currentIngredient, quantity);
        }

        //calculates total calories and total cost of the given recipe
        double totalCalories = 0;
        double totalCost = 0;
        for (Ingredient currentIngredient : ingredientToQuantity.keySet()) {
            double amountOfCurrentIngredient = ingredientToQuantity.get(currentIngredient);
            totalCost += currentIngredient.getPricePerUnit() * amountOfCurrentIngredient;
            totalCalories += currentIngredient.getCaloriesPerUnit() * amountOfCurrentIngredient;
        }
        this.totalCalories = (int) totalCalories;
        this.totalCost = totalCost;
    }

    //converts a JSON array to an array list
    private List<String> jsonArrayToArrayList(JSONArray jsonArray) throws JSONException {

        List<String> newArray = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            newArray.add(jsonArray.get(i).toString());
        }

        return newArray;
    }

    //returns the Ingredient with the given String name
    private Ingredient stringToIngredient(String ingredientName) {
        for (Ingredient currentIngredient : ingredientList) {
            if (currentIngredient.getName().equalsIgnoreCase(ingredientName)) {
                return currentIngredient;
            }
        }

        return null;
    }

    public Recipe (String name, String type, Map<Ingredient, Double> ingredientToQuantity,
                   List<String> instructions) {
        //recalculates cost and calories each time a new Recipe object is created

        double unroundedTotalCalories = 0;
        double totalCost = 0;
        for (Ingredient currentIngredient : ingredientToQuantity.keySet()) {
            double amountOfCurrentIngredient = ingredientToQuantity.get(currentIngredient);
            totalCost += currentIngredient.getPricePerUnit() * amountOfCurrentIngredient;
            unroundedTotalCalories += currentIngredient.getCaloriesPerUnit() * amountOfCurrentIngredient;
        }
        int totalCalories = (int) unroundedTotalCalories;

        setValues(name, type, totalCost, ingredientToQuantity, instructions, totalCalories);
    }

    public Recipe(String name, String type, int totalCost, Map<Ingredient, Double> ingredientToQuantity,
                  List<String> instructions, int totalCalories) {
        setValues(name, type, totalCost, ingredientToQuantity, instructions, totalCalories);
    }

    private void setValues(String name, String type, double totalCost, Map<Ingredient, Double> ingredientToQuantity,
                           List<String> instructions, int totalCalories) {
        this.name = name;
        this.type = type;
        this.totalCost = totalCost;
        this.ingredientToQuantity = ingredientToQuantity;
        this.instructions = instructions;
        this.totalCalories = totalCalories;
    }

    //getters and setters
    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void addIngredient(Ingredient newIngredient) {
        ingredientList.add(newIngredient);
    }

    public void addIngredient(JSONObject jsonIngredientObject) throws JSONException{
        Ingredient newIngredient = new Ingredient(jsonIngredientObject);
        ingredientList.add(newIngredient);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Map<Ingredient, Double> getIngredientToQuantity() {
        return ingredientToQuantity;
    }

    public void setIngredientToQuantity(Map<Ingredient, Double> ingredientToQuantity) {
        this.ingredientToQuantity = ingredientToQuantity;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }
}
