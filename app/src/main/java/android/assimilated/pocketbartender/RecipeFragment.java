package android.assimilated.pocketbartender;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by christina3135 on 5/31/2015.
 */
public class RecipeFragment extends Fragment {
    private String recipeName;
    private Activity hostActivity;
    private MainApp app;
    private static int currentStepNum;
    private static List<String> instructions;
    private static ArrayList<Recipe> recipeList;
    private static Map<Ingredient, Double> ingredientMap;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
        app = (MainApp) hostActivity.getApplication();
        recipeList = app.recipeList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentView = inflater.inflate(R.layout.recipe_fragment, container, false);
        container.removeAllViews();

        if (getArguments() != null) {

            recipeName = getArguments().getString("recipe");
            //temporary filler value for recipe
            //final Recipe currentRecipe = null;
            final Recipe currentRecipe = findRecipe(recipeName);

            if (currentRecipe == null) {
                throw new IllegalArgumentException("Given recipe does not exist. Recipe name: " + recipeName);
            }

            final Button btnNext = (Button) hostActivity.findViewById(R.id.btnNext);
            final TextView txtDescr = (TextView) fragmentView.findViewById(R.id.txtDescr);

            if (btnNext.getText().toString().equalsIgnoreCase("Start")) {
                txtDescr.setText(descriptionToString(currentRecipe));
            }

            if (btnNext.getText().toString().equalsIgnoreCase("Start")) {
                btnNext.setText(R.string.next);
                currentStepNum = -1;

                instructions = currentRecipe.getInstructions();
            }

            if (currentStepNum == -1) {
                String ingredients = "Ingredients required for this recipe: \n";

                ingredientMap = currentRecipe.getIngredientToQuantity();
                for (Ingredient currentIngredient : ingredientMap.keySet()) {
                    String ingredientString = ingredientMap.get(currentIngredient) + " " +
                            currentIngredient.getUnit() + "s " + currentIngredient.getName();

                    ingredients += ingredientString + "\n";
                }

                txtDescr.setText(ingredients);
                currentStepNum++;
            } else if (currentStepNum < instructions.size()) {
                String currentInstruction = instructions.get(currentStepNum);

                txtDescr.setText(currentInstruction);
                currentStepNum++;
            } else {
                txtDescr.setText(descriptionToString(currentRecipe));
                btnNext.setText(R.string.start);

                currentStepNum = 0;
            }

            if (currentStepNum == instructions.size()) {
                btnNext.setText(R.string.finish);
            }
        }

        return fragmentView;
    }

    private String descriptionToString(Recipe currentRecipe) {
        return "Description: " + currentRecipe.getDescription() + "\nCost: $" +
                Math.round(100 * currentRecipe.getTotalCost()) / 100.0 +
                "\nCalories: " + currentRecipe.getTotalCalories();
    }

    private Recipe findRecipe(String desiredRecipeName) {
        for (Recipe currentRecipe : recipeList) {
            if (currentRecipe.getName().equalsIgnoreCase(desiredRecipeName)) {
                return currentRecipe;
            }
        }

        return null;
    }
}
