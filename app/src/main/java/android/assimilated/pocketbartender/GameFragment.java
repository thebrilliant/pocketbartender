package android.assimilated.pocketbartender;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Eden on 6/9/15.
 */
public class GameFragment extends Fragment {
    private String name;
    private String instructions;
    private Activity hostActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.games_layout, container, false);
        container.removeAllViews();

        TextView title = (TextView) rootView.findViewById(R.id.txtGameName);
        TextView instructions = (TextView) rootView.findViewById(R.id.txtInstructions);

        // Sets text for views
        title.setText(this.name);
        instructions.setText(this.instructions);


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.hostActivity = activity;
    }
}


