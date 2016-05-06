package com.rcos.quickcast;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by unonu on 5/6/16.
 */
public class OverviewSwipeFragment extends ListFragment {
	private SwipeRefreshLayout mSwipeRefreshLayout;
	//private int sortMethod;
	private ArrayList<String> mFilters;
	private ArrayList<ListElement> mElements;
	private ListProvider mListProvider;
	private Handler mHandler;
	private Runnable mUpdateMatchTimers;

	public OverviewSwipeFragment() {
		//sortMethod = 1;
		mFilters = new ArrayList<>();
		mElements = new ArrayList<>();
		mHandler = new Handler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_overview_swipe, container, false);

		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
		mSwipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
					((HubActivity) getActivity()).updateOverviewList();
					update(getArguments());
			}
		});
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Construct the Provider
        mListProvider = new ListProvider(getActivity(), mElements);

		setListAdapter(mListProvider);
		init();
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
		intent.putExtra("requestURL", getArguments().getString("requestURL"));

		mHandler.removeCallbacks(mUpdateMatchTimers);
		startActivity(intent);
	}

	public void init() {
		Bundle args = getArguments();
		mFilters = args.getStringArrayList("filter");
		ArrayList<ListElement> all = args.getParcelableArrayList("data");

		if (mFilters == null) mFilters = new ArrayList<>();
		if (all == null) all = new ArrayList<>();

		mElements.clear();
		for ( ListElement element: all) {
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
			for (int i=0; i < mFilters.size(); i += 2) {
				if (element.sorts.get( mFilters.get(i) ).equals(mFilters.get(i+1)) ) {
					mElements.add(element);
					break;
				}
			}
		}
//		mEmptyText.setVisibility(mElements.size() == 0 ? View.VISIBLE : View.INVISIBLE);
		mListProvider.notifyDataSetChanged();
		mSwipeRefreshLayout.setRefreshing(false);
	}

	public void setRefreshing(boolean b) {
		Log.d("ALDFSDLF","SLIDFH");
		mSwipeRefreshLayout.setRefreshing(b);
	}

	public void swipeRefreshEnabled(boolean b){
		mSwipeRefreshLayout.setEnabled(b);
	}
}
