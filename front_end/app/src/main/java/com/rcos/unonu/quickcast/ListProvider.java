package com.rcos.unonu.quickcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by unonu on 10/25/15.
 */

/*
	Okay here:
	Get Recent Matches —>
	{ match ID :
		{ "start time",
			"sport",
			"teams" : [ team ID 1, team ID 2],
			"score" : [hero score 1, hero score 2],
			"series" : [ games won 1, games won 2, series length]
		},
		...
	}
 */

public class ListProvider extends ArrayAdapter<ListElement> {
	private Context context;
	private ArrayList<ListElement> elements;

	public ListProvider(Context context) {
		super(context, -1);
		this.elements = new ArrayList<ListElement>();
		this.context = context;
	}

	public int setElements(String blob) throws JSONException {
		this.elements = new ArrayList<ListElement>();
		JSONObject matches = new JSONObject(blob);

		Iterator<String> ids = matches.keys();
		while (ids.hasNext()) {
			String id = ids.next();
			this.elements.add(new ListElement(id, matches.getJSONObject(id)));
		}
		return this.elements.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View element = inflater.inflate(R.layout.main_list_element, parent, false);

		/* View elements to inject */
		TextView sportLabel = (TextView) element.findViewById(R.id.sport);
		TextView timeLabel = (TextView) element.findViewById(R.id.sport_timer);
		TextView team_1Label = (TextView) element.findViewById(R.id.name_uno);
		TextView team_2Label = (TextView) element.findViewById(R.id.name_dos);
		TextView score_1Label = (TextView) element.findViewById(R.id.score_uno);
		TextView score_2Label = (TextView) element.findViewById(R.id.score_dos);
		//ImageView logo1 = (ImageView) element.findViewById(R.id.logo_uno);
		//ImageView logo2 = (ImageView) element.findViewById(R.id.logo_dos);

		ListElement elementData = elements.get(position);

		sportLabel.setText(elementData.sport);
		timeLabel.setText("XX:XX");
		team_1Label.setText(elementData.team1);
		team_2Label.setText(elementData.team2);
		score_1Label.setText(elementData.score1);
		score_2Label.setText(elementData.score2);

		return element;
	}
}
