package com.apps.gill.googlemaps.retrofit;

import com.apps.gill.googlemaps.models.Geocoding.GeoCodeMain;
import com.apps.gill.googlemaps.models.PlaceAutoComplete.MyLocation;
import com.apps.gill.googlemaps.models.Route.SourceDestinationRoute;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by gill on 22-02-2016.
 */
public interface ApiService {


    @POST("/place/autocomplete/json")
    public void autoComplete(@Query("input") String location, @Query("key") String key, Callback<MyLocation> callback);
    @POST("/geocode/json")
    public void geoCode(@Query("address") String placeAddress,@Query("key") String key ,Callback<GeoCodeMain> callback);
    @POST("/directions/json")
    public void routePolyline(@Query("origin") String origin,@Query("destination") String destination,@Query("key") String key,Callback<SourceDestinationRoute> callback);
}
