package com.lamagroup.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Guest 3 on 10/14/2017.
 */

public class Movie {
    private String title;
    private String overview;
    private String posterpath;

    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterpath = object.getString("poster_path");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterpath() {
        return posterpath;
    }
}
