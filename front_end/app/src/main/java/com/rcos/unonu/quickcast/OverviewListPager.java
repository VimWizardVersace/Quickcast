package com.rcos.unonu.quickcast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class OverviewListPager extends Fragment {

//	private int sortMethod;
	private String requestURL;
	private ArrayList<ListElement> elements;
	ViewPager listPager;

    public OverviewListPager() {
//		sortMethod = 1;
		requestURL = "localhost";
        elements = new ArrayList<>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.pager_overview_list, container, false);
        Bundle args = getArguments();
        requestURL = args.getString("requestURL");
        PagerAdapter pagerAdapter = new OverviewListAdapter(getFragmentManager());
		listPager = (ViewPager)rootView.findViewById(R.id.pager);
		listPager.setAdapter(pagerAdapter);

//		makeRequest();
        refreshMatchList("a");
        pagerAdapter.notifyDataSetChanged();
        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip)rootView.findViewById(R.id.pager_title_strip);
        pagerTitleStrip.refreshDrawableState();
		return rootView;
	}

	private class OverviewListAdapter extends FragmentPagerAdapter {
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

            ArrayList<String> filter = new ArrayList<>();
            switch (position) {
                case 0:
                    //args.putString("filter", "");
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
                    //args.putString("filter", "");
            }
			fragment.setArguments(args);
			return fragment;
		}

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Live Now";
                case 1:
                    return "Following";
                case 2:
                    return "Popular";
                case 3:
                    return "DOTA 2";
                default:
                    return "???";
            }
        }
	}

	public void makeRequest() {
		new JsonObjectRequest
		(Request.Method.GET, requestURL, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				refreshMatchList(response.toString());
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.d(" QUICKCAST!!!!", "Bad response from makeRequest");
			}
		});
	}

	public int refreshMatchList(String blob) {
        blob = "{ \"abcdefg\" : { \"start time\" : 1111111, \"sport\" : \"DOTA2\", \"teams\" : [ \"Evil Geniuses\", \"111111\", \"The Losers\", \"222222\"], \"score\" : [99, 0],\"series\" : [ 1, 0, 3]}}";
		Log.d(" QUICKCAST!!!!", "heyyyyyo " + this.elements.size());
		this.elements.clear();
		JSONObject matches = null;
		try {
			matches = new JSONObject(blob);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Iterator<String> ids = matches.keys();
		while (ids.hasNext()) {
			String id = ids.next();
			try {
				this.elements.add(new ListElement(id, matches.getJSONObject(id)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		Log.d(" QUICKCAST!!!!", "helloooo " + this.elements.size());
		return this.elements.size();
	}
}