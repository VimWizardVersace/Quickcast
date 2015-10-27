package com.rcos.unonu.quickcast;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
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

public class OverviewListFragment extends ListFragment {

	private ListProvider mListProvider;

	public OverviewListFragment newInstance(int sectionNumber) {
		OverviewListFragment fragment = new OverviewListFragment();
		// Bundle args = new Bundle();
		// args.putInt(mNumber, sectionNumber);
		// fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Construct the Provider
		mListProvider = new ListProvider(getActivity());

		try {
			mListProvider.setElements( "{ \"abcdefg\" : { \"start time\" : 1111111, \"sport\" : \"DOTA2\", \"teams\" : [ \"Evil Geniuses\", \"111111\", \"The Losers\", \"222222\"], \"score\" : [99, 0],\"series\" : [ 1, 0, 3]}}"/*RESPONSE*/ );
		} catch (JSONException e) {
			e.printStackTrace();
		}

		setListAdapter(mListProvider);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((HubActivity) activity).onSectionAttached(1);
	}

	public void refreshMatchList() throws JSONException {
		/* MAKE API CALL TO GET_RECENT_MATCHES */
		mListProvider.setElements( "{ \"abcdefg\" : { \"start time\" : 1111111, \"sport\" : \"DOTA2\", \"teams\" : [ \"Evil Geniuses\", \"111111\", \"The Losers\", \"222222\"], \"score\" : [99, 0],\"series\" : [ 1, 0, 3]}}"/*RESPONSE*/ );
	}
}