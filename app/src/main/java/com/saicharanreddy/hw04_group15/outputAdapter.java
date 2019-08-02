/*
Assignment : Homework 04
File Name : HW04_Group15.zip
Full Name: Sai Charan Reddy Vallapureddy, Manideep Reddy Nukala
*/
package com.saicharanreddy.hw04_group15;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;


/**
 * Created by valla on 25-03-2019.
 */
public class outputAdapter extends RecyclerView.Adapter<outputAdapter.MyViewHolder> {
    ArrayList<RecipeResults> mData;
    RecipeResults rec;
    private Context context;

    public outputAdapter(ArrayList<RecipeResults> mData, Context context) {
        super();
        this.mData = mData;
        this.context = context;
    }

    @Override
    public outputAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_adapter, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(outputAdapter.MyViewHolder holder, int position) {

        rec = mData.get(position);
        Log.d("demooutput",rec.toString());
        holder.title.setText(rec.getTitle());
        holder.ingredients.setText(rec.ingredients);
        if(!rec.getHref().equals("")){
            Picasso.with(context).load(rec.getHref()).into(holder.image);
        }


        holder.href.setText(rec.getThumbnail());
        holder.href.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //recipeFragment.openURl(rec.Url);
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(rec.getThumbnail()));
                context.startActivity(intent);


            }
        });

       // RecipeResults results = mData.get(position);
      //  holder.title.setText(results.getTitle());
       // holder.ingredients.setText(results.getIngredients());
      //  holder.href.setText(results.getHref());
        //holder.image.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        TextView ingredients;
        TextView href;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleOutput);
            image = (ImageView) itemView.findViewById(R.id.displayOutput);
            ingredients = (TextView) itemView.findViewById(R.id.ingreOutput);
            href = (TextView) itemView.findViewById(R.id.urlOutput);
        }
    }
}
