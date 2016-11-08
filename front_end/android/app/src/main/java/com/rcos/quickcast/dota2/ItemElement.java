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
 * Created by D4N on 10/4/2016.
 */

public class ItemElement extends DrilldownElement {

    private String radiantName;
    private String direName;
    private ArrayList<String> radiantPlayers;
    private ArrayList<String> direPlayers;
    private ArrayList<String> radiantItems;
    private ArrayList<String> direItems;

    public ItemElement(JSONObject details) throws JSONException {
        mSport = "DOTA2";
        //...
        JSONArray players = (JSONArray) details.get("players");

        radiantPlayers = new ArrayList<>();
        direPlayers = new ArrayList<>();
        radiantItems = new ArrayList<>();
        direItems = new ArrayList<>();

        //we're guaranteed to have 10 players with id 0 or 1, so we do this:
        int j = 0;
        for (int i = 0; i < 10; i++) {
            while (j > -1) {
                JSONObject player = (JSONObject) players.get(j);
                //JSONObject itemset = (JSONObject) items.get(j);
                if (player.getInt("team") == 0) {
                    Log.d("QUICKCAST!!!!", "found a player " + j + player.getString("name"));
                    radiantPlayers.add(player.getString("name"));
                    //add in item images to radiantItems
                    break;
                } else if (player.getInt("team") == 1) {
                    Log.d("QUICKCAST!!!!", "found a player " + j + player.getString("name"));
                    direPlayers.add(player.getString("name"));
                    //add in item images to direItems
                    break;
                }
                j++;
            }
            j++;
        }
        Log.d("QUICKCAST!!!!", "constructed item element");
    }

    public View getView(Context context, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View element = inflater.inflate(R.layout.dota2_item_element, parent, false);

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

        /*TextView item = (TextView) element.findViewById(R.id.r_player_0_item1);
        item.setBackground(picture 1 for guy 1);
        item = (TextView) element.findViewById(R.id.r_player_0_item2);
        item.setBackground(picture 2 for guy 2);*/
        //etc

        return element;
    }

    ItemElement(Parcel in){
        //...
    }


    public static final Parcelable.Creator<ItemElement> CREATOR = new Parcelable.Creator<ItemElement>() {
        public ItemElement createFromParcel(Parcel in) {
            return new ItemElement(in);
        }
        public ItemElement[] newArray(int size) {
            return new ItemElement[size];
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
