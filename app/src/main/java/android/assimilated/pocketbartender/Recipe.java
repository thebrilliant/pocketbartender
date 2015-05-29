package android.assimilated.pocketbartender;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by christina3135 on 5/27/2015.
 */
public class Recipe {

    private String name;
    private String type;
    private double totalCost;
    private Map<Ingredient, Integer> ingredientToQuantity;
    private List<String> steps;
    //caloriesPerOz required only for the optional user story
    private int totalCalories;

    public Recipe() {
        this(null, null, 0, null, null, 0);
    }

    public Recipe(JSONObject jsonObject) {
        String name = "";
        String type = "";
        int totalCost = 0;
        Map<Ingredient, Integer> ingredientToQuantity = new HashMap<Ingredient, Integer>();
        List<String> steps = new ArrayList<String>();
        int totalCalories = 0;

        //to be completed once structure of JSON file is determined

        setValues(name, type, totalCost, ingredientToQuantity, steps, totalCalories);
    }

    public Recipe (String name, String type, Map<Ingredient, Integer> ingredientToQuantity,
                   List<String> steps) {
        //recalculates cost and calories each time a new Recipe object is created
        double totalCost = 0;
        int totalCalories = 0;

        for (Ingredient currentIngredient : ingredientToQuantity.keySet()) {
            int amountOfCurrentIngredient = ingredientToQuantity.get(currentIngredient);
            totalCost += currentIngredient.getPricePerUnit() * amountOfCurrentIngredient;
            totalCalories += currentIngredient.getCaloriesPerUnit() * amountOfCurrentIngredient;
        }

        setValues(name, type, totalCost, ingredientToQuantity, steps, totalCalories);
    }

    public Recipe(String name, String type, int totalCost, Map<Ingredient, Integer> ingredientToQuantity,
                  List<String> steps, int totalCalories) {
        setValues(name, type, totalCost, ingredientToQuantity, steps, totalCalories);
    }

    private void setValues(String name, String type, double totalCost, Map<Ingredient, Integer> ingredientToQuantity,
                           List<String> steps, int totalCalories) {
        this.name = name;
        this.type = type;
        this.totalCost = totalCost;
        this.ingredientToQuantity = ingredientToQuantity;
        this.steps = steps;
        this.totalCalories = totalCalories;
    }

    //getters and setters
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

    public Map<Ingredient, Integer> getIngredientToQuantity() {
        return ingredientToQuantity;
    }

    public void setIngredientToQuantity(Map<Ingredient, Integer> ingredientToQuantity) {
        this.ingredientToQuantity = ingredientToQuantity;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }
}
