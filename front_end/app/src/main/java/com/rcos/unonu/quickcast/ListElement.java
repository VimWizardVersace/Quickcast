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

public class ListElement {
	public ListElement(String id, JSONObject details) throws JSONException {
		matchID = id;

		JSONArray teams = details.getJSONArray("teams");
		JSONArray scores = details.getJSONArray("score");
		JSONArray jsonSeries = details.getJSONArray("series");

		sport		= details.getString("sport");
		timestamp	= details.getInt("start time");
		endStamp	= details.optInt("end time", -1);
		series		= new int [3];
		series[0]	= jsonSeries.getInt(0);
		series[1]	= jsonSeries.getInt(1);
		series[2]	= jsonSeries.getInt(2);
		score1		= scores.getInt(0);
		score2		= scores.getInt(1);
		team1		= teams.getString(0);
		teamID1		= teams.getString(1);
		team2		= teams.getString(2);
		teamID2		= teams.getString(3);

	}

	int timestamp;
	int endStamp;
	int score1;
	int score2;
	int [] series;
	String matchID;
	String sport;
	String team1;
	String teamID1;
	String team2;
	String teamID2;

}