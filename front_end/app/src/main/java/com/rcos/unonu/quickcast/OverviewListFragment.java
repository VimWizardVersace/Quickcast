package com.rcos.unonu.quickcast;

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

    //private int sortMethod;
    private ArrayList<String> filters;
	private ArrayList<ListElement> elements;

    public OverviewListFragment() {
        //sortMethod = 1;
        filters = new ArrayList<>();
        elements = new ArrayList<>();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_overview_list, container, false);
        Bundle args = getArguments();
        filters = args.getStringArrayList("filter");
        ArrayList<ListElement> all = args.getParcelableArrayList("data");

        if (filters == null) filters = new ArrayList<>();
        if (all == null) all = new ArrayList<>();

        elements.clear();
        for ( ListElement element: all) {
            for (int i=0; i < filters.size(); i += 2) {
                if (element.sorts.get( filters.get(i) ).equals(filters.get(i+1)) ) {
                    elements.add(element);
                    break;
                }
            }
        }
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

}