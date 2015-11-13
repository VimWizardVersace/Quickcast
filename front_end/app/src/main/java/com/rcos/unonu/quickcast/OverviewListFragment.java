package com.rcos.unonu.quickcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class OverviewListFragment extends ListFragment {

    private int sortMethod;
    private String requestURL;
	private ArrayList<ListElement> elements;

    public OverviewListFragment() {
        sortMethod = 1;
        requestURL = "localhost";
        elements = new ArrayList<>();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_overview_list, container, false);
        Bundle args = getArguments();
        elements = args.getParcelableArrayList("data");
				Log.d(" QUICKCAST!!!!", "Made new OLF");
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Construct the Provider
        ListProvider mListProvider = new ListProvider(getActivity(), elements);

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
}