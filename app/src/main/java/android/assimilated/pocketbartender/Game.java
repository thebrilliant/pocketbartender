package android.assimilated.pocketbartender;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eden on 6/9/15.
 */
public class Game {
    private String name;
    private String directions;

    public Game (JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString("name");
        this.directions = jsonObject.getString("directions");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }
}
