package com.lamagroup.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by Guest 3 on 10/14/2017.
 */

@Parcel // annotation indicates class is Parcelable
public class Movie {
    public String title;
    public String overview;
    public String posterpath;
    public String backdroppath;

    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterpath = object.getString("poster_path");
        backdroppath = object.getString("backdrop_path");
    }
    // no-arg, empty constructor required for Parceler
    public Movie() {}
    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public String getBackdroppath() {        return backdroppath;    }
}
