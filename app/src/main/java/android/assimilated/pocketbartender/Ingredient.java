package android.assimilated.pocketbartender;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by christina3135 on 5/27/2015.
 */
public class Ingredient {

    private String name;
    private String type;
    private double pricePerUnit;
    private String unit;
    //caloriesPerUnit required only for the optional user story
    private int caloriesPerUnit;

    public Ingredient(JSONObject jsonObject) throws JSONException {
        //to be completed once structure of JSON file is determined
        this.name = jsonObject.getString("name");
        this.type = jsonObject.getString("type");
        this.pricePerUnit = Double.parseDouble(jsonObject.getString("cost"));
        this.caloriesPerUnit = Integer.parseInt(jsonObject.getString("calories"));
        //this.pricePerUnit = jsonObject.getDouble("cost");
        //this.caloriesPerUnit = jsonObject.getInt("calories");
        this.unit = jsonObject.getString("unit");
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public void setCaloriesPerUnit(int caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }

    /*
    // these constructors turned out to be unnecessary since JSON object will always be passed into Recipe
    public Ingredient() {
        setValues(null, null, 0, 0, "");
    }

    public Ingredient(String name, String type, double pricePerUnit, int caloriesPerUnit, String unit) {
        setValues(name, type, pricePerUnit, caloriesPerUnit, unit);
    }

    private void setValues(String name, String type, double pricePerUnit, int caloriesPerUnit, String unit) {
        this.name = name;
        this.type = type;
        this.pricePerUnit = pricePerUnit;
        this.caloriesPerUnit = caloriesPerUnit;
        this.unit = unit;
    }
    */
}
