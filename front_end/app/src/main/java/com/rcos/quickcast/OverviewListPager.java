package com.rcos.quickcast;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class OverviewListPager extends Fragment {

	private String mRequestURL;
	private ArrayList<ListElement> elements;
    private FragmentStatePagerAdapter pagerAdapter;
	public ViewPager listPager;

    public OverviewListPager() {
		mRequestURL = "http://128.113.209.217:5000/";
        elements = new ArrayList<>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.pager_overview_list, container, false);
        pagerAdapter = new OverviewListAdapter(getFragmentManager());
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
                getActivity().setTitle( pagerAdapter.getPageTitle(position) );
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });

//        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip)rootView.findViewById(R.id.pager_title_strip);
//        pagerTitleStrip.refreshDrawableState();


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
    }

	private class OverviewListAdapter extends FragmentStatePagerAdapter {
		public OverviewListAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public ListFragment getItem(int position) {
			OverviewListFragment fragment = new OverviewListFragment();
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
                    filter.add("sport"); filter.add("Dota2");
                    args.putStringArrayList("filter", filter);
                    break;
                default:
                    args.putString("filter", "");
            }
			fragment.setArguments(args);
			return fragment;
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
	}

	public void makeRequest() {
        RequestQueue mRequestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();
        Log.d(" QUICKCAST!!!!", "making request " + this.elements.size());
		JsonObjectRequest request = new JsonObjectRequest
		(this.mRequestURL +"recent_matches" , null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
                Log.d(" QUICKCAST!!!!", "got response??????");
				refreshMatchList(response);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.d(" QUICKCAST!!!!", "Bad response from makeRequest");
			}
		});
        Log.d(" QUICKCAST!!!!", "finished making request " + this.mRequestURL + "recent_matches");
        mRequestQueue.add(request);
	}

	public int refreshMatchList(JSONObject matches) {
		Log.d(" QUICKCAST!!!!", "match list has " + this.elements.size());
        Log.d(" QUICKCAST!!!!", "response body:\n\n " + matches.toString() + "\n\n");
		this.elements.clear();

		Iterator<String> ids = matches.keys();
		while (ids.hasNext()) {
			String id = ids.next();
            ListElement x = null;
            try {
                x = new ListElement(id, matches.getJSONObject(id));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (x != null) {
                this.elements.add(0, x);
                Log.d(" QUICKCAST!!!!", "refresh " + this.elements.get(0).matchID + " : " + x.matchID);
                if (elements.size() > 1)
                    Log.d(" QUICKCAST!!!!", "also " + this.elements.get(1).matchID);
            }
        }
//        for (ListElement e : elements) {
        for (int i=0; i < this.elements.size(); i++) {
            ListElement e = this.elements.get(i);
            Log.d(" QUICKCAST!!!!", "recap "+e.matchID);
            e.matchID = e.matchID + 'r';
            e.sport = e.sport + '3';
        }
        pagerAdapter.notifyDataSetChanged();
        return this.elements.size();
	}

    public void setCurrentItem(int position) {
        listPager.setCurrentItem( position );
    }
}