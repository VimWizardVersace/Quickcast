package com.rcos.quickcast;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class OverviewListFragment extends ListFragment {

    //private int sortMethod;
    private ArrayList<String> mFilters;
	private ArrayList<ListElement> mElements;
    private ListProvider mListProvider;
    private Handler mHandler;
    private Runnable mUpdateMatchTimers;

    public OverviewListFragment() {
        //sortMethod = 1;
        mFilters = new ArrayList<>();
        mElements = new ArrayList<>();
        mHandler = new Handler();
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
        mListProvider = new ListProvider(getActivity(), mElements);

		setListAdapter(mListProvider);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), DrilldownActivity.class);
        ListElement element = mElements.get(position);
        intent.putExtra("sport", element.sport);
        intent.putExtra("matchid", element.matchID);
        intent.putExtra("team1", element.team1);
        intent.putExtra("team2", element.team2);
        intent.putExtra("score1", element.score1);
        intent.putExtra("score2", element.score2);

        mHandler.removeCallbacks(mUpdateMatchTimers);
		startActivity(intent);
        // finish?
	}

	public void init() {
		Bundle args = getArguments();
		mFilters = args.getStringArrayList("filter");
		ArrayList<ListElement> all = args.getParcelableArrayList("data");
//
		if (mFilters == null) mFilters = new ArrayList<>();
		if (all == null) all = new ArrayList<>();

		mElements.clear();
		for ( ListElement element: all) {
//            Log.d(" QUICKCAST!!!!", "Added match " + element.matchID);
			for (int i=0; i < mFilters.size(); i += 2) {
				if (element.sorts.get( mFilters.get(i) ).equals(mFilters.get(i+1)) ) {
					mElements.add(element);
					break;
				}
			}
		}

		mUpdateMatchTimers = new Runnable() {
			@Override
			public void run() {
				long millis = SystemClock.uptimeMillis();

				for (ListElement element : mElements) {
					if (element.duration > 0) {
						double curTime = (millis-element.refTime)/1000.;
						int seconds = (int) (element.duration+curTime)%60;
						int minutes = (int) (element.duration+curTime)/60;
						if (seconds < 10)
							element.niceTime = String.format("%d:0%d", minutes, seconds);
						else
							element.niceTime = String.format("%d:%d", minutes, seconds);
					}
				}

				mListProvider.updateTimes();
				mHandler.postDelayed(this, 200);
			}
		};

		mHandler.removeCallbacks(mUpdateMatchTimers);
		mHandler.postDelayed(mUpdateMatchTimers, 100);
		Log.d(" QUICKCAST!!!!", "Made new OLF");
	}

    public void update( Bundle args ) {
        mFilters = args.getStringArrayList("filter");
        ArrayList<ListElement> all = args.getParcelableArrayList("data");

        if (mFilters == null) mFilters = new ArrayList<>();
        if (all == null) all = new ArrayList<>();

        mElements.clear();
        for ( ListElement element: all) {
//            Log.d(" QUICKCAST!!!!", "Added match " + element.matchID);
            for (int i=0; i < mFilters.size(); i += 2) {
                if (element.sorts.get( mFilters.get(i) ).equals(mFilters.get(i+1)) ) {
                    mElements.add(element);
                    break;
                }
            }
        }
        mListProvider.notifyDataSetChanged();
    }

}