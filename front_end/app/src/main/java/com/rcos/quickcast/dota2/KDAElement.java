package com.rcos.quickcast.dota2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rcos.quickcast.DrilldownElement;
import com.rcos.quickcast.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by unonu on 3/22/16.
 */
public class KDAElement extends DrilldownElement {

	private HashMap<Long, String> mPlayerMap;
	private HashMap<String,int[]> mRadiantKDA;
	private HashMap<String,int[]> mDireKDA;
	private ArrayList<String> mDireList;
	private ArrayList<String> mRadiantList;

	public KDAElement(JSONObject details) throws JSONException {
		mSport = "dota2";
		mPlayerMap = new HashMap<>();
		mRadiantKDA = new HashMap<>();
		mDireKDA = new HashMap<>();
		mDireList = new ArrayList<>();
		mRadiantList = new ArrayList<>();

		JSONObject scoreboard = details.getJSONObject("scoreboard");
		JSONObject dire = scoreboard.getJSONObject("dire");
		JSONObject radiant = scoreboard.getJSONObject("radiant");
		JSONArray players = details.getJSONArray("players");
		JSONArray dPlayers = dire.getJSONArray("players");
		JSONArray rPlayers = radiant.getJSONArray("players");

		// Map player player IDs to player Names
		int j = 0;
        for (int i=0; i < 10; i++) {
            while (j > -1) {
                JSONObject player = (JSONObject) players.get(j);
                if (player.getInt("team") == 0 || player.getInt("team") == 1){
                    mPlayerMap.put(player.getLong("account_id"), player.getString("name"));
					if (player.getInt("team") == 0)
						mRadiantList.add(player.getString("name"));
					else
						mDireList.add(player.getString("name"));
                    break;
                }
                j++;
            }
            j++;
        }

		for (int i=0; i < 5; i++) {
			JSONObject player = (JSONObject) dPlayers.get(i);
			int [] a = {player.getInt("kills"),
						player.getInt("death"),
						player.getInt("assists")};
			mDireKDA.put(mPlayerMap.get(player.getLong("account_id")),a);
			player = (JSONObject) rPlayers.get(i);
			int [] b = {player.getInt("kills"),
					player.getInt("death"),
					player.getInt("assists")};
			mRadiantKDA.put(mPlayerMap.get(player.getLong("account_id")),b);
		}
	}

	public View getView(Context context, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View element = inflater.inflate(R.layout.dota2_kda_element, parent, false);

		String name;
		TextView t;

		t = (TextView) element.findViewById(R.id.name0);
		name = mRadiantList.get(0);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills0);
		t.setText(String.format("%d", mRadiantKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths0);
		t.setText(String.format("%d", mRadiantKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists0);
		t.setText(String.format("%d", mRadiantKDA.get(name)[2]));

		t = (TextView) element.findViewById(R.id.name1);
		name = mRadiantList.get(1);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills1);
		t.setText(String.format("%d", mRadiantKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths1);
		t.setText(String.format("%d", mRadiantKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists1);
		t.setText(String.format("%d", mRadiantKDA.get(name)[2]));

		t = (TextView) element.findViewById(R.id.name2);
		name = mRadiantList.get(2);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills2);
		t.setText(String.format("%d", mRadiantKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths2);
		t.setText(String.format("%d", mRadiantKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists2);
		t.setText(String.format("%d", mRadiantKDA.get(name)[2]));

		t = (TextView) element.findViewById(R.id.name3);
		name = mRadiantList.get(3);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills3);
		t.setText(String.format("%d", mRadiantKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths3);
		t.setText(String.format("%d", mRadiantKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists3);
		t.setText(String.format("%d", mRadiantKDA.get(name)[2]));

		t = (TextView) element.findViewById(R.id.name4);
		name = mRadiantList.get(4);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills4);
		t.setText(String.format("%d", mRadiantKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths4);
		t.setText(String.format("%d", mRadiantKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists4);
		t.setText(String.format("%d", mRadiantKDA.get(name)[2]));

		t = (TextView) element.findViewById(R.id.name5);
		name = mDireList.get(0);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills5);
		t.setText(String.format("%d", mDireKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths5);
		t.setText(String.format("%d", mDireKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists5);
		t.setText(String.format("%d", mDireKDA.get(name)[2]));

		t = (TextView) element.findViewById(R.id.name6);
		name = mDireList.get(1);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills6);
		t.setText(String.format("%d", mDireKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths6);
		t.setText(String.format("%d", mDireKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists6);
		t.setText(String.format("%d", mDireKDA.get(name)[2]));

		t = (TextView) element.findViewById(R.id.name7);
		name = mDireList.get(2);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills7);
		t.setText(String.format("%d", mDireKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths7);
		t.setText(String.format("%d", mDireKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists7);
		t.setText(String.format("%d", mDireKDA.get(name)[2]));

		t = (TextView) element.findViewById(R.id.name8);
		name = mDireList.get(3);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills8);
		t.setText(String.format("%d", mDireKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths8);
		t.setText(String.format("%d", mDireKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists8);
		t.setText(String.format("%d", mDireKDA.get(name)[2]));

		t = (TextView) element.findViewById(R.id.name9);
		name = mDireList.get(4);
		t.setText(name);
		t = (TextView) element.findViewById(R.id.kills9);
		t.setText(String.format("%d", mDireKDA.get(name)[0]));
		t = (TextView) element.findViewById(R.id.deaths9);
		t.setText(String.format("%d", mDireKDA.get(name)[1]));
		t = (TextView) element.findViewById(R.id.assists9);
		t.setText(String.format("%d", mDireKDA.get(name)[2]));

		return element;
	}

    KDAElement(Parcel in){
        //...
    }


	public static final Parcelable.Creator<KDAElement> CREATOR = new Parcelable.Creator<KDAElement>() {
		public KDAElement createFromParcel(Parcel in) {
			return new KDAElement(in);
		}
		public KDAElement[] newArray(int size) {
			return new KDAElement[size];
		}
	};

	public int describeContents() {
		return 0;
	}

    private void readFromParcel(Parcel in) {
        //...
    }

    public void writeToParcel(Parcel dest, int flags) {
        //...
    }
}