package android.assimilated.pocketbartender;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by christina3135 on 5/31/2015.
 */
public class RecipeFragment extends Fragment {
    private String recipeName;
    private Activity hostActivity;
    private MainApp app;
    private int currentStepNum;
    private List<String> instructions;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentView = inflater.inflate(R.layout.recipe_fragment, container, false);

        app = (MainApp) hostActivity.getApplication();

        recipeName = getArguments().getString("recipe");
        //temporary filler value for recipe
        final Recipe currentRecipe = null;
        //TODO: find current recipe from list field in MainApp
        //Recipe currentRecipe = app.recipeList

        if (getArguments() != null) {

            final Button btnNext = (Button) hostActivity.findViewById(R.id.btnNext);
            final TextView txtDescr = (TextView) hostActivity.findViewById(R.id.txtDescr);

            if (btnNext.getText().toString().equals(R.string.start)) {
                txtDescr.setText(currentRecipe.getDescription());
            }

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btnNext.getText().toString().equals(R.string.start)) {
                        btnNext.setText(R.string.next);
                        currentStepNum = 0;

                        instructions = currentRecipe.getInstructions();
                    }

                    if (currentStepNum < instructions.size()) {
                        String currentInstruction = instructions.get(currentStepNum);

                        txtDescr.setText(currentInstruction);
                        currentStepNum++;
                    } else {
                        txtDescr.setText(currentRecipe.getDescription());
                        btnNext.setText(R.string.start);

                        currentStepNum = 0;
                    }

                    if (currentStepNum == instructions.size()) {
                        btnNext.setText(R.string.finish);
                    }
                }
            });
        }


        return fragmentView;
    }
}
