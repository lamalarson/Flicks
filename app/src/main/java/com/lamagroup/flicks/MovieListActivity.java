package com.lamagroup.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.lamagroup.flicks.models.Config;
import com.lamagroup.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    // Constant
    // The base url for the API
    public final static String API_BASE_URL ="https://api.themoviedb.org/3";
    // The parameter name for the API KEY
    public final static String API_KEY_PARAM ="api_key";
    // Tag of this activity
    public static final String TAG = "MovieListActivity";

    // instance field
    AsyncHttpClient client;
    // the base url for loading image
    String imageBaseUrl;
    // the poster size use when fetching images, part of the url
    String posterSize;
    // the list of currently playing movies
    ArrayList<Movie> movies;
    // the recycle view
    RecyclerView rvMovies;
    // theadapter wired to the recycle view
    MovieAdapter adapter;
    // config image
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        // initialize the client
        client = new AsyncHttpClient();
        // initialize the list of movie
        movies = new ArrayList<>();
        // initialize the adapter
        adapter = new MovieAdapter(movies);

        // resolve the recycle view and connect a layout manager and the adapter
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies) ;
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        // get configuration on app creation
        getConfiguration();

    }

    // get the list of currently playing movie from the API
    public void getNowPlaying(){
// create the url
        String url = API_BASE_URL + "/movie/now_playing";
        //set the request parameter
        RequestParams param = new RequestParams();
        param.put(API_KEY_PARAM,getString(R.string.api_key)); // api always required
        // execute a GET request  expecting JSON object response
        client.get(url, param, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    // load the results on movie list
                    JSONArray results = response.getJSONArray("results");
                    // iterate through result set and create movie object
                    for(int i = 0; i < results.length(); i++){
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);
                        // notify adapter that a row was added
                        adapter.notifyItemInserted(movies.size()-1);
                    }

                    Log.i(TAG, String.format("Loaded %s movies", results.length()));
                } catch (JSONException e) {
                    logError("Failed to param now playing movie", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from now playing movie", throwable, true);
            }
        });
    }


    public void getConfiguration(){
        // create the url
        String url = API_BASE_URL + "/configuration";
        //set the request parameter
        RequestParams param = new RequestParams();
        param.put(API_KEY_PARAM,getString(R.string.api_key)); // api always required
        // execute a GET request  expecting JSON object response
        client.get(url, param, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    config = new Config(response);
                    Log.i(TAG ,
                            String.format("Loaded configuration from imageBaseUrl %s and poster Size %s",
                            config.getImageBaseUrl(), config.getPosterSize()));
                   //  pass config to adapter
                    adapter.setConfig(config);

                    // get now playing list
                    getNowPlaying();
                } catch (JSONException e) {
                    logError("Failed parsing configuration", e,true );
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable,true );
            }
        });

    }
    public void logError(String message, Throwable error, boolean alertUser){
        Log.e(TAG, message, error);
        if(alertUser){
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
