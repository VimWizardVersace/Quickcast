package com.rcos.unonu.quickcast;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

public class DrilldownActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drilldown);

        Intent intent = getIntent();
    }
}
