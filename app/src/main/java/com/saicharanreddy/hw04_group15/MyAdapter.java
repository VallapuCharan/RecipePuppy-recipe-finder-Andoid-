/*
Assignment : Homework 04
File Name : HW04_Group15.zip
Full Name: Sai Charan Reddy Vallapureddy, Manideep Reddy Nukala
*/
package com.saicharanreddy.hw04_group15;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valla on 24-03-2019.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    IData idata;
    View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton floatingActionButton;
    ArrayList<FloatingActionButton> buttons = new ArrayList<FloatingActionButton>();
    ArrayList<Ingredients> mData;
    int mDataSize;
    public MyAdapter(ArrayList<Ingredients> mData) {
        this.mData = mData;
        mDataSize = mData.size();
        this.view = view;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipeadapter, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view,new MyCustomEditTextListener());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Ingredients ingreObj = mData.get(position);
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.ingre.setText(mData.get(holder.getAdapterPosition()).getIngredients());
        //holder.ingre.setText(ingreObj.getIngredients());
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.floatingButton);
        floatingActionButton.setTag(position);
        buttons.add(floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Log.d("demobutton",String.valueOf(floatingActionButton.getTag()));

                if(holder.ingre.getText().toString().equals(""))
                {
                    Toast.makeText(view.getContext(), "Please add a ingredient", Toast.LENGTH_SHORT).show();
                }
                else {

                    ingreObj.setIngredients(holder.ingre.getText().toString());


                    if (mData.size() < 5) {
                        int set = mData.size();
                        for(int i=0;i<set;i++)
                        {
                            final int status =(Integer) v.getTag();


                        }
                        for(FloatingActionButton b: buttons) {
                            if(String.valueOf(b.getId()).equals("999")) {
                                //DO WHAT YOU WANT
                                floatingActionButton.getTag(R.drawable.close_red);
                            }
                        }
                        mData.set(position, ingreObj);
                        mData.add(new Ingredients(""));
                        notifyItemChanged(position);
                    }
                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }
    IData iDateInterface;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        EditText ingre;
        //FloatingActionButton floatingActionButton;
        public MyCustomEditTextListener myCustomEditTextListener;
        public MyViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            ingre = (EditText) itemView.findViewById(R.id.ingreInput);
            this.myCustomEditTextListener = myCustomEditTextListener;
            ingre.addTextChangedListener(myCustomEditTextListener);
           // floatingActionButton = (FloatingActionButton) itemView.findViewById(R.id.floatingButton);
        }
    }
    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mData.set(position, new Ingredients(charSequence.toString()));
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
    public static interface IData{
        public void addButton(ArrayList<Ingredients> data);
    }


}
