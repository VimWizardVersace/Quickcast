package com.rcos.quickcast.dota2;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rcos.quickcast.DrilldownElement;
import com.rcos.quickcast.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Dan on 10/25/2016.
 */

public class TowerElement extends DrilldownElement {
    private String radiantName;
    private String direName;
    private ArrayList<String> radiantTowers;
    private ArrayList<String> direTowers;

    public TowerElement(JSONObject details) throws JSONException {
        mSport = "DOTA2";
        JSONObject radiant  = (JSONObject) details.get("radiant_team");
        JSONObject dire     = (JSONObject) details.get("dire_team");

        radiantName = radiant.getString("team_name");
        direName    = dire.getString("team_name");

        //we're guaranteed to have 10 players with id 0 or 1, so we do this:
        int j = 0;
        for (int i=0; i < 10; i++) {
            while (j > -1) {
                //JSONObject tower = (JSONObject) towers.get(j);
                //get tower info
            }
            j++;
        }
        Log.d("QUICKCAST!!!!", "constructed tower element");
    }

    public View getView(Context context, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View element = inflater.inflate(R.layout.dota2_team_element, parent, false);

        TextView tableRadiant = (TextView) element.findViewById(R.id.tableRadiant);
        tableRadiant.setText(String.format("Radiant: %s", radiantName));

        TextView tableDire = (TextView) element.findViewById(R.id.tableDire);
        tableDire.setText(String.format("Dire: %s", direName));

        //add tower stuff

        return element;
    }

    TowerElement(Parcel in){
        //...
    }


    public static final Parcelable.Creator<TowerElement> CREATOR = new Parcelable.Creator<TowerElement>() {
        public TowerElement createFromParcel(Parcel in) {
            return new TowerElement(in);
        }
        public TowerElement[] newArray(int size) {
            return new TowerElement[size];
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
