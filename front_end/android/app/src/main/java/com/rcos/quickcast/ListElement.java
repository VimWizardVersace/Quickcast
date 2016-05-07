package com.rcos.quickcast;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by unonu on 10/25/15.
 */

/*
	Okay here:
	Get Recent Matches —>
	{ match ID :
		{ "start duration",
			"sport",
			"teams" : [ team ID 1, team ID 2],
			"score" : [hero score 1, hero score 2],
			"series" : [ games won 1, games won 2, series length]
		},
		...
	}
 */

public class ListElement implements Parcelable {

	public int duration;
	public int endStamp;
	public int score1;
	public int score2;
	public int [] series;
	public String matchID;
	public String sport;
	public String team1;
	public String teamID1;
	public String team2;
	public String teamID2;
    public String niceTime;
    public Map<String, String> sorts;
    public long refTime;

	public ListElement(JSONObject details) throws JSONException {

        JSONArray teams = details.getJSONArray("teams");
        JSONArray scores = details.getJSONArray("score");
        JSONArray jsonSeries = details.getJSONArray("series");

        matchID     = details.getString("matchid");
        sport		= details.getString("sport");
		duration    = details.optInt("duration", -1);
		endStamp	= details.optInt("end_time", -1);
		series		= new int [3];
		series[0]	= jsonSeries.getInt(0);
		series[1]	= jsonSeries.getInt(1);
		series[2]	= jsonSeries.getInt(2);
		score1		= scores.getInt(0);
		score2		= scores.getInt(1);
		team1		= teams.optString(0, "a");
		teamID1		= teams.optString(1, "b");
		team2		= teams.optString(2, "c");
		teamID2		= teams.optString(3, "d");
        niceTime    = "00:00";

        sorts = new HashMap<>();
        sorts.put("sport", sport);
        sorts.put("duration", Integer.toString(duration));
        sorts.put("teamID1", teamID1);
        sorts.put("teamID2", teamID2);
        sorts.put("finished", endStamp == -1 ? "false" : "true");

        refTime = SystemClock.uptimeMillis();
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

	private void readFromParcel(Parcel in) {
		duration    = in.readInt();
		endStamp	= in.readInt();
		score1 	    = in.readInt();
		score2 	    = in.readInt();
                      in.readIntArray(series);
		matchID	    = in.readString();
		sport 		= in.readString();
		team1 		= in.readString();
		teamID1	    = in.readString();
		team2 		= in.readString();
		teamID2	    = in.readString();
        refTime     = in.readLong();

        sorts.put("sport", sport);
        sorts.put("duration", Integer.toString(duration));
        sorts.put("teamID1", teamID1);
        sorts.put("teamID2", teamID2);
        sorts.put("finished", endStamp == -1 ? "false" : "true");


	}
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(duration);
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
        dest.writeLong(refTime);
	}
}