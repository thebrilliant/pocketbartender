package android.assimilated.pocketbartender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Eden on 6/8/15.
 */
public class GameResultsActivity extends ActionBarActivity {
    MainApp app;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_results_layout);

        app = (MainApp) getApplication();

        list = (ListView) findViewById(R.id.lstResult);

        final Game[] games = listToArray(app.gameList);

        GamesArrayAdapter adapter = new GamesArrayAdapter(this, games);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent next = new Intent(GameResultsActivity.this, GamesActivity.class);

                String gameName = games[position].getName();
                String gameInstructions = games[position].getDirections();

                next.putExtra("name", gameName);
                next.putExtra("instructions", gameInstructions);

                startActivity(next);
            }
        });
    }

    public Game[] listToArray(List<Game> list) {
        Game[] result = new Game[list.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }

        return result;
    }
}
