package com.rcos.quickcast;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by unonu on 3/22/15.
 */

public class DrilldownProvider extends ArrayAdapter<DrilldownElement> {
    private Context context;
    private ArrayList<DrilldownElement> elements;

    public DrilldownProvider(Context context, ArrayList<DrilldownElement> list) {
        super(context, -1, list);
        this.elements = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("QUICKCAST!!!!", "get view?");
		return elements.get(position).getView(this.context, parent);
    }
}
