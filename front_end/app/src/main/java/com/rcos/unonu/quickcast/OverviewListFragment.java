package com.rcos.unonu.quickcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

public class OverviewListFragment extends ListFragment {

	private ListProvider mListProvider;
    private int sortMethod;

    public OverviewListFragment() {
        sortMethod = 1;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_overview_list, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Construct the Provider
		mListProvider = new ListProvider(getActivity(), new ArrayList<ListElement>());

		try {
			mListProvider.setElements( "{ \"abcdefg\" : { \"start time\" : 1111111, \"sport\" : \"DOTA2\", \"teams\" : [ \"Evil Geniuses\", \"111111\", \"The Losers\", \"222222\"], \"score\" : [99, 0],\"series\" : [ 1, 0, 3]}}"/*RESPONSE*/ );
		} catch (JSONException e) {
			e.printStackTrace();
		}
        Log.d(" QUICKCAST!!!!", "This happens " + mListProvider.getCount());

		setListAdapter(mListProvider);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), DrilldownActivity.class);
		startActivity(intent);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((HubActivity) activity).onSectionAttached(1);
	}

	public void refreshMatchList() throws JSONException {
		/* MAKE API CALL TO GET_RECENT_MATCHES */
		mListProvider.setElements("{ \"abcdefg\" : { \"start time\" : 1111111, \"sport\" : \"DOTA2\", \"teams\" : [ \"Evil Geniuses\", \"111111\", \"The Losers\", \"222222\"], \"score\" : [99, 0],\"series\" : [ 1, 0, 3]}}"/*RESPONSE*/);
	}
}