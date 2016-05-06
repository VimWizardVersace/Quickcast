package com.rcos.quickcast;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HubActivity extends AppCompatActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private String mRequestURL;
    private Boolean refresh = true;
	private SharedPreferences mPreferences;

	private OverviewListPager mOverviewListPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		updatePreferences();

		setContentView(R.layout.activity_hub);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
				getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                mDrawerLayout);
        mTitle = mNavigationDrawerFragment.getCurrentTitle();

        mDrawerToggle = new ActionBarDrawerToggle( this, mDrawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
				updatePreferences();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        refresh = false;
    }

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getFragmentManager();

        Bundle args = new Bundle();
		// update the main content by replacing fragments
		args.putString("requestURL", mRequestURL);
		args.putInt("position", position);
		if (refresh) {
			mOverviewListPager = new OverviewListPager();
			mOverviewListPager.setArguments(args);
		} else {
			mOverviewListPager = (OverviewListPager) fragmentManager.findFragmentById(R.id.container);
			mOverviewListPager.setCurrentItem(position);
		}
		refresh = false;
		mOverviewListPager.updateURL();
		fragmentManager.beginTransaction()
						.replace(R.id.container, mOverviewListPager)
						.commit();
	}

	public void updateOverviewList(){
		if (mOverviewListPager != null)
			mOverviewListPager.makeRequest();
	}

    public void showPreferences(View view ) {
		FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new SettingsFragment();
        fragmentManager.beginTransaction()
						.replace(R.id.container, fragment)
						.commit();
		setTitle("Settings");
		mNavigationDrawerFragment.selectItem(-1);
        mDrawerLayout.closeDrawers();
        refresh = true;
    }

	public void updatePreferences() {
		mRequestURL = mPreferences.getString("server","localhost");
	}

    public void showAbout( View view ) {
		FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new AboutFragment();
        fragmentManager.beginTransaction()
						.replace(R.id.container, fragment)
						.commit();
		setTitle("About");
		mNavigationDrawerFragment.selectItem(-1);
        mDrawerLayout.closeDrawers();
        refresh = true;
    }

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(mTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.hub, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }

		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

    @Override
    public void setTitle (CharSequence nTitle) {
        mTitle = nTitle;
        getSupportActionBar().setTitle(mTitle);
    }

    public void selectNavigationItem( int position ) {
        mNavigationDrawerFragment.selectItem(position);
    }
}
