package com.rcos.quickcast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HubActivity extends AppCompatActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private String mRequestURL;
    private Boolean refresh = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hub);

        mRequestURL = "http://quickcast.farkinator.c9users.io";
        mNavigationDrawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mTitle = mNavigationDrawerFragment.getCurrentTitle();

        refresh = false;
    }

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment;
        Bundle args = new Bundle();
		// update the main content by replacing fragments
        if (position > -1 && position < 4) {
            if (refresh) {
                args.putString("requestURL", mRequestURL);
                args.putInt("position", position);
                fragment = new OverviewListPager();
                fragment.setArguments(args);
//                if (mNavigationDrawerFragment != null)
//                    mTitle = mNavigationDrawerFragment.getItemTitle(position);
//                getSupportActionBar().setTitle(mTitle);
            } else {
                fragment = fragmentManager.findFragmentById(R.id.container);
                ((OverviewListPager) fragment).setCurrentItem(position);
            }
            refresh = false;
        } else {
            fragment = new AboutFragment();
            refresh = true;
            mTitle = getString(R.string.title_section6);
//            getSupportActionBar().setTitle(mTitle);
		}

		fragmentManager.beginTransaction()
						.replace(R.id.container, fragment)
						.commit();
	}

//	public void onSectionAttached(int number) {
//        OverviewListPager fragment = (OverviewListPager) getSupportFragmentManager().findFragmentById(R.id.container);
//
//        ActionBar actionBar = getSupportActionBar();
//		switch (number) {
//			case 1:
//				mTitle = getString(R.string.title_section1);
////                fragment.setCurrentItem(number-1);
//				break;
//            case 2:
//                mTitle = getString(R.string.title_section2);
////                fragment.setCurrentItem(number-1);
//                break;
//            case 3:
//                mTitle = getString(R.string.title_section3);
////                fragment.setCurrentItem(number-1);
//                break;
//            case 4:
//                mTitle = getString(R.string.title_section4);
////                fragment.setCurrentItem(number - 1);
//                break;
////			case 5:
////				mTitle = getString(R.string.title_section5);
////				break;
////			case 6:
////				mTitle = getString(R.string.title_section6);
////				break;
//        }
//        actionBar.setTitle(mTitle);
//    }

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(mTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
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
