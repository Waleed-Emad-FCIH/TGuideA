package com.tga.Response;




import com.tga.R;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RequestInterface {



    @GET("json?query=Top+spots+in+Cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getTopSpots();

    @GET("json?query=jewelry+or+casino+or+spa+in+Cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getPlacesA_Z();

    @GET("json?query=zoo+park+in+Cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getOutDoors();

    @GET("json?query=museum+and+mall+in+Cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getInDoors();

    @GET("json?query=restaurant+in+Cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getResturants();

    @GET("json?query=cafe+in+Cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getCafe();

    @GET("json?query=bar+in+Cairo&key=AIzaSyB_7KprS66Hcih9Rfnu05ssVPRdvOdVVy4")
    Call<PlaceResponse> getBar();

    @GET("json?")
    Call<PlaceDetailsResponse> getPlaceDetails(@Query("placeid") String id,
                                               @Query("key") String key);

}
