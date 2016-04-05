package com.rcos.quickcast;

import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rcos.quickcast.dota2.TeamElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by unonu on 3/24/16.
 */
public class DrilldownListFragment extends ListFragment {

    private ArrayList<DrilldownElement> mElements;
    private DrilldownProvider mDrilldownProvider;
    private JSONObject mJSONData;
    private String mMatchID;
    private String mRequestURL;
    private String mSport;
    private Context mContext;

    public DrilldownListFragment( ) {
        mElements = new ArrayList<>();
        mMatchID = "";
        mRequestURL = "http://quickcast.farkinator.c9users.io";
        mSport = "unknown";
        mJSONData = null;
        mContext = getContext();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drilldown_list, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mRequestURL = sharedPreferences.getString("server", "");
        return rootView;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        makeRequest();

		// Construct the Provider
        mDrilldownProvider = new DrilldownProvider(getActivity(), mElements);

		setListAdapter(mDrilldownProvider);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
        //...
	}

    private void makeRequest() {
        RequestQueue mRequestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        // Start the queue
        mRequestQueue.start();

        Log.d(" QUICKCAST!!!!", "making request ...");
        JsonObjectRequest request = new JsonObjectRequest
                (String.format("%s/%s/live/%s", mRequestURL, "dota", mMatchID), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" QUICKCAST!!!!", "got response??????");
                        mJSONData = response;
                        try {
                            setDrilldownElementArray(mSport);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("QUICKAST!!!!", "elements " + mElements.size());
                        mDrilldownProvider.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(" QUICKCAST!!!!", "Drilldown, Bad response from makeRequest.");
                    }
                });
        Log.d(" QUICKCAST!!!!", "finished making request to " + String.format("%s/%s/live/%s", mRequestURL, "dota", mMatchID));
        mRequestQueue.add(request);
    }

    public void setDrilldownElementArray(String sport) throws JSONException{
        mElements.clear();
        switch (sport) {
            case "DOTA2":
                Log.d("QUICKCAST!!!!", "making dota list");
                mElements.add(new TeamElement(mJSONData));
                break;
            default:
        }
    }

    public void setDetails(String sport, String matchID) {
        mSport = sport;
        mMatchID = matchID;
    }
}
