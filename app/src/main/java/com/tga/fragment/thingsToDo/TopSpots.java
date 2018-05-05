package com.tga.fragment.thingsToDo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tga.R;
import com.tga.Response.PlaceResponse;
import com.tga.Response.RequestInterface;
import com.tga.adapter.PlacesAdapter;
import com.tga.adapter.ThingsToDoAdpater;
import com.tga.model.PlaceModel;
import com.tga.model.place;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopSpots extends Fragment {


    private ArrayList<place> ArrayList;
    private RecyclerView recyclerView;
    private ThingsToDoAdpater mAdapter;
    RequestInterface request;

    public TopSpots() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top_spots, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.top_spots_recyclerview);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/textsearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
        loadJSON(request,request.getTopSpots());

//        mAdapter = new ThingsToDoAdpater(getActivity(),ArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);

        return v;
    }


    private void loadJSON(RequestInterface request, Call<PlaceResponse> getJSON) {
        Call<PlaceResponse> call = getJSON;
//        ArrayList = new ArrayList<>();
        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {

                PlaceResponse jsonResponse = response.body();
                ArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                mAdapter = new ThingsToDoAdpater(getContext(),ArrayList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

}
