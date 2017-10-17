package com.lamagroup.flicks;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lamagroup.flicks.models.Config;
import com.lamagroup.flicks.models.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Guest 3 on 10/15/2017.
 */

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    // list of movie
    ArrayList<Movie> movies;
    // config needed for image urls
    Config config;
    // context for rendering image
    Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the context and create the inflater
        context = parent.getContext();
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
        // determine the orientation
        boolean isportrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        // build url poster image
        String imageUrl = null;

        if(isportrait){
            imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterpath());
        }else{
            imageUrl = config.getImageUrl(config.getBackDropSize(), movie.getBackdroppath());
        }
        //get the correct placeholder and image view from orientation
        int placeholderid = isportrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isportrait ? holder.ivPosterImage : holder.ivBackdropimage;

        //load image using glide
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .placeholder(placeholderid)
                .error(placeholderid)
                .into(imageView);
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

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        // track view objects
        ImageView ivPosterImage;
        ImageView ivBackdropimage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);
            // look up view object by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            ivBackdropimage = (ImageView) itemView.findViewById(R.id.ivBackdropimage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
        }
    }
}
