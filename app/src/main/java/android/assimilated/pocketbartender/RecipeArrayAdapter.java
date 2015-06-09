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


public class RecipeArrayAdapter extends ArrayAdapter<Recipe> {
    private final Context context;
    private final Recipe[] values;

    public RecipeArrayAdapter(Context context, Recipe[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.results_row, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.firstLine);
        TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView1.setText((values[position]).getName());
        textView2.setText((values[position]).getDescription());

        String drawableName = (values[position]).getName().toLowerCase().replace(" ", "");
        int resID = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());

        imageView.setImageResource(resID);

        return rowView;
    }
}
