package com.rcos.quickcast;

import android.content.Context;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OverviewListPager extends Fragment {

	private String mRequestURL;
	private ArrayList<ListElement> elements;
    private OverviewSwipeAdapter pagerAdapter;
	public ViewPager listPager;
    private SparseArray<OverviewSwipeFragment> mFragments;
	private int mCurrentPosition;

    public OverviewListPager() {
		mRequestURL = "http://localhost";
        elements = new ArrayList<>();
        mFragments = new SparseArray<>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
        mRequestURL = getArguments().getString("requestURL", mRequestURL);

		View rootView = inflater.inflate(R.layout.pager_overview_list, container, false);
        pagerAdapter = new OverviewSwipeAdapter(getFragmentManager());
        pagerAdapter.notifyDataSetChanged();

        listPager = (ViewPager)rootView.findViewById(R.id.pager) ;
        listPager.setAdapter(pagerAdapter);
        listPager.setSaveEnabled(true);
        listPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                HubActivity ha = (HubActivity) getActivity();
                ha.setTitle( pagerAdapter.getPageTitle(position) );
                ha.selectNavigationItem(position);
				mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
				mFragments.get(mCurrentPosition).swipeRefreshEnabled(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });

		return rootView;
	}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        makeRequest();


    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        setCurrentItem(args.getInt("position",0));
        mRequestURL = args.getString("requestURL", mRequestURL);
    }

	public void updateURL() {
        mRequestURL = getArguments().getString("requestURL", mRequestURL);
	}

	private class OverviewSwipeAdapter extends FragmentStatePagerAdapter {
		public OverviewSwipeAdapter (FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public OverviewSwipeFragment getItem(int position) {
			OverviewSwipeFragment fragment = new OverviewSwipeFragment();
            Bundle args = new Bundle();

            args.putParcelableArrayList("data", elements);
                Log.d(" QUICKCAST!!!!", "len o elements " + elements.size());
            for (ListElement e : elements) {
                Log.d(" QUICKCAST!!!!", "in elements "+e.matchID);
            }

            ArrayList<String> filter = new ArrayList<>();
            switch (position) {
                case 0:
                    filter.add("finished"); filter.add("false");
                    args.putStringArrayList("filter", filter);
                    break;
                case 1:
                    //args.putString("filter", "");
                    break;
                case 2:
                    filter.add("sport"); filter.add("CSGO");
                    args.putStringArrayList("filter", filter);
                    break;
                case 3:
                    filter.add("sport"); filter.add("DOTA2");
                    args.putStringArrayList("filter", filter);
                    break;
                default:
                    args.putString("filter", "");
            }
			fragment.setArguments(args);
            mFragments.put(position, fragment);
			return fragment;
		}

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return getString(R.string.title_section1);// (" + elements.size() + ")";
                case 1:
                    return getString(R.string.title_section2);
                case 2:
                    return getString(R.string.title_section3);
                case 3:
                    return getString(R.string.title_section4);
                default:
                    return "???";
            }
        }

        public OverviewSwipeFragment getFragment(int position) {
            return mFragments.get(position);
        }
	}

	public void makeRequest() {
		// Make sure we're up to date
		updateURL();

        RequestQueue mRequestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();
        Log.d(" QUICKCAST!!!!", "making request ...");
		JsonArrayRequest request = new JsonArrayRequest
		(String.format("%s/live", mRequestURL), new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
                Log.d(" QUICKCAST!!!!", "got response??????");
				refreshMatchList(response);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.d(" QUICKCAST!!!!", "Bad response from makeRequest.");
            }
		});
        Log.d(" QUICKCAST!!!!", "finished making request to " + mRequestURL + "/live");
        mRequestQueue.add(request);
	}

	public int refreshMatchList(JSONArray matches) {
		Log.d(" QUICKCAST!!!!", "match list has " + elements.size());
        Log.d(" QUICKCAST!!!!", "response body:\n\n " + matches.toString() + "\n\n");
		elements.clear();

        ListElement x = null;
        int j = 0;
        try {
            x = new ListElement( (JSONObject) matches.get(j) );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        while (x != null) {
            elements.add(x);

            j++;

            try {
                x =  new ListElement( (JSONObject) matches.get(j) );
            } catch (JSONException e) {
                x = null;
            }
        }

        pagerAdapter.notifyDataSetChanged();
        for (int i = 0; i < mFragments.size(); i++) {
            OverviewSwipeFragment f = pagerAdapter.getFragment(i);
            if (f != null ) {
                Bundle args = new Bundle();
                args.putParcelableArrayList("data", elements);
				args.putString("requestURL", mRequestURL);

                ArrayList<String> filter = new ArrayList<>();
                switch (i) {
                    case 0:
                        filter.add("finished"); filter.add("false");
                        args.putStringArrayList("filter", filter);
                        break;
                    case 1:
                        //args.putString("filter", "");
                        break;
                    case 2:
                        filter.add("sport"); filter.add("CSGO");
                        args.putStringArrayList("filter", filter);
                        break;
                    case 3:
                        filter.add("sport"); filter.add("DOTA2");
                        args.putStringArrayList("filter", filter);
                        break;
                    default:
                        args.putString("filter", "");
                }
                f.update(args);
            }
        }

		for (int i = 0; i < mFragments.size(); i++) {
			OverviewSwipeFragment f = mFragments.get(i);
			if (f != null) {
				f.setRefreshing(false);
				Log.d("QUIECKAADF","stop refresh...");
			}
		}

        return elements.size();
	}

    public void setCurrentItem(int position) {
        listPager.setCurrentItem( position );
    }
}