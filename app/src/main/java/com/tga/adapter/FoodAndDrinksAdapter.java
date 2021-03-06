package com.tga.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tga.Activity.FoodType;
import com.tga.Activity.HomeDetails;
import com.tga.R;
import com.tga.model.PlaceModel;

import java.util.List;

/**
 * Created by Mada on 2/28/2018.
 */

public class FoodAndDrinksAdapter extends RecyclerView.Adapter<FoodAndDrinksAdapter.MyViewHolder> {

    Context context;
    List<PlaceModel> placeModels;



    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title;


        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.imgFood);
//            price = (TextView) view.findViewById(R.id.price);
            title = (TextView) view.findViewById(R.id.txtfoodType);

        }

    }


    public FoodAndDrinksAdapter(Context mainActivityContacts, List<PlaceModel> placeModels) {
        this.placeModels = placeModels;
        this.context = mainActivityContacts;
    }



    @Override
    public FoodAndDrinksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);


        return new FoodAndDrinksAdapter.MyViewHolder(itemView);


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final FoodAndDrinksAdapter.MyViewHolder holder, int position) {
        final PlaceModel placeModel = placeModels.get(position);

        holder.title.setText(placeModel.getTitle());
        holder.image.setImageResource(placeModel.getPlImg());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, FoodType.class);
                i.putExtra("type",holder.title.getText());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });




    }

    @Override
    public int getItemCount() {
        return placeModels.size();
    }


}


