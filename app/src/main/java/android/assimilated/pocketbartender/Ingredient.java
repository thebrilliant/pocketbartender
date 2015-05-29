package android.assimilated.pocketbartender;

import org.json.JSONObject;

/**
 * Created by christina3135 on 5/27/2015.
 */
public class Ingredient {

    private String name;
    private String type;
    private double pricePerUnit;
    //caloriesPerUnit required only for the optional user story
    private int caloriesPerUnit;

    public Ingredient() {
        setValues(null, null, 0, 0);
    }

    public Ingredient(JSONObject jsonObject) {
        String name = "";
        String type = "";
        double pricePerUnit = 0;
        int caloriesPerUnit = 0;

        //to be completed once structure of JSON file is determined

        setValues(name, type, pricePerUnit, caloriesPerUnit);
    }

    public Ingredient(String name, String type, double pricePerUnit, int caloriesPerUnit) {
        setValues(name, type, pricePerUnit, caloriesPerUnit);
    }

    private void setValues(String name, String type, double pricePerUnit, int caloriesPerUnit) {
        this.name = name;
        this.type = type;
        this.pricePerUnit = pricePerUnit;
        this.caloriesPerUnit = caloriesPerUnit;
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

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public void setCaloriesPerUnit(int caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }
}
