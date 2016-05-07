package com.rcos.quickcast.dota2;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
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

/**
 * Created by unonu on 3/22/16.
 */
public class TeamElement extends DrilldownElement {

    private String radiantName;
    private String direName;
    // add player class eventually? I hate java.
    private ArrayList<String> radiantPlayers;
    private ArrayList<String> direPlayers;

    public TeamElement(JSONObject details) throws JSONException {
        mSport = "DOTA2";
        //...
        JSONObject radiant  = (JSONObject) details.get("radiant_team");
        JSONObject dire     = (JSONObject) details.get("dire_team");
        JSONArray players   = (JSONArray) details.get("players");

        radiantName = radiant.getString("team_name");
        direName    = dire.getString("team_name");
        radiantPlayers = new ArrayList<>();
        direPlayers = new ArrayList<>();

        //we're guaranteed to have 10 players with id 0 or 1, so we do this:
        int j = 0;
        for (int i=0; i < 10; i++) {
            while (j > -1) {
                JSONObject player = (JSONObject) players.get(j);
                if (player.getInt("team") == 0){
                    Log.d("QUICKCAST!!!!", "found a player " + j + player.getString("name"));
                    radiantPlayers.add(player.getString("name"));
                    break;
                }
                else if (player.getInt("team") == 1) {
                    Log.d("QUICKCAST!!!!", "found a player " + j + player.getString("name"));
                    direPlayers.add(player.getString("name"));
                    break;
                }
                j++;
            }
            j++;
        }
        Log.d("QUICKCAST!!!!", "constructed team element");
    }

    public View getView(Context context, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View element = inflater.inflate(R.layout.dota2_team_element, parent, false);

        TextView tableRadiant = (TextView) element.findViewById(R.id.tableRadiant);
        tableRadiant.setText(String.format("Radiant: %s", radiantName));

        TextView tableDire = (TextView) element.findViewById(R.id.tableDire);
        tableDire.setText(String.format("Dire: %s", direName));

            TextView rPlayer = (TextView) element.findViewById(R.id.r_player_0_name);
            TextView dPlayer = (TextView) element.findViewById(R.id.d_player_0_name);
                rPlayer.setText(radiantPlayers.get(0));
                dPlayer.setText(direPlayers.get(0));
            rPlayer = (TextView) element.findViewById(R.id.r_player_1_name);
            dPlayer = (TextView) element.findViewById(R.id.d_player_1_name);
                rPlayer.setText(radiantPlayers.get(1));
                dPlayer.setText(direPlayers.get(1));
            rPlayer = (TextView) element.findViewById(R.id.r_player_2_name);
            dPlayer = (TextView) element.findViewById(R.id.d_player_2_name);
                rPlayer.setText(radiantPlayers.get(2));
                dPlayer.setText(direPlayers.get(2));
            rPlayer = (TextView) element.findViewById(R.id.r_player_3_name);
            dPlayer = (TextView) element.findViewById(R.id.d_player_3_name);
                rPlayer.setText(radiantPlayers.get(3));
                dPlayer.setText(direPlayers.get(3));
            rPlayer = (TextView) element.findViewById(R.id.r_player_4_name);
            dPlayer = (TextView) element.findViewById(R.id.d_player_4_name);
                rPlayer.setText(radiantPlayers.get(4));
                dPlayer.setText(direPlayers.get(4));

        return element;
    }

    TeamElement(Parcel in){
        //...
    }


	public static final Parcelable.Creator<TeamElement> CREATOR = new Parcelable.Creator<TeamElement>() {
		public TeamElement createFromParcel(Parcel in) {
			return new TeamElement(in);
		}
		public TeamElement[] newArray(int size) {
			return new TeamElement[size];
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
