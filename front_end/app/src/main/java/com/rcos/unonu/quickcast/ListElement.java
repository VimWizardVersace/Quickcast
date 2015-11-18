package com.rcos.unonu.quickcast;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

public class ListElement implements Parcelable {

	public static int timestamp;
	public static int endStamp;
	public static int score1;
	public static int score2;
	public static int [] series;
	public static String matchID;
	public String sport;
	public static String team1;
	public static String teamID1;
	public static String team2;
	public static String teamID2;
    public Map<String, String> sorts;

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

        sorts = new HashMap<>();
        sorts.put("sport", sport);
        sorts.put("timestamp", Integer.toString(timestamp));
        sorts.put("teamID1", teamID1);
        sorts.put("teamID2", teamID2);
        sorts.put("finished", endStamp == -1 ? "false" : "true");

	}


	public ListElement(Parcel in) {
		super();
		readFromParcel(in);
	}

	public static final Parcelable.Creator<ListElement> CREATOR = new Parcelable.Creator<ListElement>() {
		public ListElement createFromParcel(Parcel in) {
			return new ListElement(in);
		}
		public ListElement[] newArray(int size) {
			return new ListElement[size];
		}
	};

	public void readFromParcel(Parcel in) {
		 timestamp	= in.readInt();
		 endStamp	= in.readInt();
		 score1 	= in.readInt();
		 score2 	= in.readInt();
                      in.readIntArray(series);
		 matchID	= in.readString();
		 sport 		= in.readString();
		 team1 		= in.readString();
		 teamID1	= in.readString();
		 team2 		= in.readString();
		 teamID2	= in.readString();

        sorts.put("sport", sport);
        sorts.put("timestamp", Integer.toString(timestamp));
        sorts.put("teamID1", teamID1);
        sorts.put("teamID2", teamID2);
        sorts.put("finished", endStamp == -1 ? "false" : "true");

	}
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(timestamp);
		dest.writeInt(endStamp);
		dest.writeInt(score1);
		dest.writeInt(score2);
		dest.writeIntArray(series);
		dest.writeString(matchID);
		dest.writeString(sport);
		dest.writeString(team1);
		dest.writeString(teamID1);
		dest.writeString(team2);
		dest.writeString(teamID2);
	}
}