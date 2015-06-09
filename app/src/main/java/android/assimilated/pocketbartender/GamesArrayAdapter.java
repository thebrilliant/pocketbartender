package android.assimilated.pocketbartender;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by iguest on 6/8/15.
 */


public class GamesArrayAdapter extends ArrayAdapter<Game> {
    private final Context context;
    private final Game[] values;

    public GamesArrayAdapter(Context context, Game[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.games_row, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.title);
        title.setText((values[position]).getName());

        return rowView;
    }
}
