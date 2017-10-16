package com.lamagroup.flicks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamagroup.flicks.models.Movie;

import java.util.ArrayList;

/**
 * Created by Guest 3 on 10/15/2017.
 */

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    // list of movie
    ArrayList<Movie> movies;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the context and create the inflater
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        // return a new viewHolder
        return new ViewHolder(movieView);
    }

    // create and inflate a new view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // get the movie data at the specified position
        Movie movie = movies.get(position);
        // populate the view with movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

    }

    // return the total number of item in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // initialize with list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        // track view objects
        ImageView imageView;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);
            // look up view object by id
            imageView = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
        }
    }
}
