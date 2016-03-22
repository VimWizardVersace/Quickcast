package com.rcos.quickcast;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by unonu on 3/22/16.
 */
public abstract class DrilldownElement implements Parcelable {
    public String mSport;
    public abstract View getView(Context context, ViewGroup parent);

//    DrilldownElement() {}
//
//    DrilldownElement(String sport) {
//        mSport = sport;
//    }
//
//    DrilldownElement(Parcel in) {
//        super();
//        readFromParcel(in);
//    }
//
//    private void readFromParcel(Parcel in) {
//        mSport = in.readString();
//    }
//
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(mSport);
//    }
}
