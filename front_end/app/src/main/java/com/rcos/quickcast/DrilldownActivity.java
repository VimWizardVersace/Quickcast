package com.rcos.quickcast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class DrilldownActivity extends AppCompatActivity {

    private String mMatchID;
    private String mSport;
    private DrilldownSwipeFragment mListFragment;
    private String mTeamUno;
    private String mTeamDos;
    private int mScoreUno;
    private int mScoreDos;

	public DrilldownActivity() {
		mListFragment = new DrilldownSwipeFragment();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drilldown);
        Intent intent = getIntent();
        Bundle args = intent.getExtras();

        mMatchID = args.getString("matchid");
        mSport = args.getString("sport");
        mTeamUno = args.getString("team1");
        mTeamDos = args.getString("team2");
        mScoreUno = args.getInt("score1");
        mScoreDos = args.getInt("score2");

		Bundle fragArgs = new Bundle();
		fragArgs.putString("requestURL",args.getString("requestURL","localhost"));
		mListFragment.setArguments(fragArgs);
		mListFragment.setDetails(mSport, mMatchID);

		// Set up action bar.
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		getFragmentManager().beginTransaction().replace(R.id.drilldown_container, mListFragment).commit();

		View toolbarView = inflater.inflate(R.layout.drilldown_toolbar, null);

        TextView v = (TextView) toolbarView.findViewById(R.id.match_label);
        v.setText(String.format("Match ID: %s", mMatchID));
        v = (TextView) toolbarView.findViewById(R.id.team_uno);
        v.setText(mTeamUno);
        v = (TextView) toolbarView.findViewById(R.id.team_dos);
        v.setText(mTeamDos);
        v = (TextView) toolbarView.findViewById(R.id.score_uno);
        v.setText(String.format("%d",mScoreUno));
        v = (TextView) toolbarView.findViewById(R.id.score_dos);
        v.setText(String.format("%d",mScoreDos));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(toolbarView);
            actionBar.setDisplayShowCustomEnabled(true);
        }

    }

    public String getMatchID() {
        return mMatchID;
    }



}
