package com.lamagroup.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Guest 3 on 10/14/2017.
 */

public class Config {
    // the base url for loading image
    String imageBaseUrl;
    // the postersize to use the fetching image
    String posterSize;
    // the backdrop size using when fetching image
   String backDropSize;

    public  Config(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        // get the image base url
        imageBaseUrl = images.getString("secure_base_url");
        // get the poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        // get the option at index 3 or w342 as a fullback
        posterSize = posterSizeOptions.optString(3,"w342");
        // pass the backdrop size and use option at index 1 or w780
        JSONArray backDropSizeOptions = images.getJSONArray("backdrop_sizes");
        backDropSize = backDropSizeOptions.optString(1,"w780");
    }
    // helper method for creating url
    public String getImageUrl(String size,String path){
        return String.format("%s%s%s", imageBaseUrl,size, path);
    }
    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackDropSize() {
        return backDropSize;
    }
}
