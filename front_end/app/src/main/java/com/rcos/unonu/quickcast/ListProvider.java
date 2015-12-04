package com.rcos.unonu.quickcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

	public ListProvider(Context context, ArrayList<ListElement> list) {
		super(context, -1, list);
		this.elements = list;
		this.context = context;
	}

	static class ViewHolder {
		public TextView sportLabel;
		public TextView timeLabel;
		public TextView team_1Label;
		public TextView team_2Label;
		public TextView score_1Label;
		public TextView score_2Label;
		// public ImageView logo1;
		// public ImageView logo2;
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// View element = inflater.inflate(R.layout.main_list_element, parent, false);
		View element = convertView;
		ViewHolder holder;

		if (element == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			element = inflater.inflate(R.layout.main_list_element, parent, false);
			holder = new ViewHolder();

			/* View elements to inject */
			holder.sportLabel = (TextView) element.findViewById(R.id.sport);
			holder.timeLabel = (TextView) element.findViewById(R.id.sport_timer);
			holder.team_1Label = (TextView) element.findViewById(R.id.name_uno);
			holder.team_2Label = (TextView) element.findViewById(R.id.name_dos);
			holder.score_1Label = (TextView) element.findViewById(R.id.score_uno);
			holder.score_2Label = (TextView) element.findViewById(R.id.score_dos);
			//holder.logo1 = (ImageView) element.findViewById(R.id.logo_uno);
			//holder.logo2 = (ImageView) element.findViewById(R.id.logo_dos);

			element.setTag(holder);
		}

		holder = (ViewHolder) element.getTag();

		ListElement elementData = elements.get(position);

		holder.sportLabel.setText(elementData.sport);
		holder.timeLabel.setText("XX:XX");
		holder.team_1Label.setText(elementData.team1);
		holder.team_2Label.setText(elementData.team2);
		holder.score_1Label.setText(Integer.toString(elementData.score1));
		holder.score_2Label.setText(Integer.toString(elementData.score2));

		return element;
	}
}
