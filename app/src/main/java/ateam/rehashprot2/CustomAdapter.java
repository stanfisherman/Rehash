package ateam.rehashprot2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Warrick
 */
public class CustomAdapter extends ArrayAdapter {
    private Activity activity;
    private final ArrayList<String> items;
    private final ArrayList<String> description;
    private final ArrayList<Integer> icon;

    public CustomAdapter(Activity context, int resource, ArrayList<String> module, ArrayList<String> description, ArrayList<Integer> icon_id) {
        super(context, resource, module);
        this.activity = context;
        this.items = module;
        this.description = description;
        this.icon = icon_id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder view;
        if (rowView == null) {
            // Get a new instance of the row layout view
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.module_row, null);
            // Hold the view objects in an object, that way the don't need to be "re- finded"
            view = new ViewHolder();
            view.item_name = (TextView) rowView.findViewById(R.id.module_title);
            view.item_description = (TextView) rowView.findViewById(R.id.module_description);
            view.item_logo = (ImageView) rowView.findViewById(R.id.module_img);
            rowView.setTag(view);
        } else {
            view = (ViewHolder) rowView.getTag();
        }
        /** Set data to your Views. */
        String m = items.get(position);
        String p = description.get(position);
        int i = icon.get(position);
        view.item_name.setText(m);
        view.item_description.setText(p);
        view.item_logo.setImageResource(i);
        return rowView;
    }

    protected static class ViewHolder
    {
        protected ImageView item_logo;
        protected TextView item_name;
        protected TextView item_description;
    }
}