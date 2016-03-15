package com.apps.gill.googlemaps.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.apps.gill.googlemaps.R;
import com.apps.gill.googlemaps.models.Geocoding.GeoCodeMain;
import com.apps.gill.googlemaps.models.PlaceAutoComplete.MyLocation;
import com.apps.gill.googlemaps.models.Route.SourceDestinationRoute;
import com.apps.gill.googlemaps.retrofit.RestClient;
import com.apps.gill.googlemaps.utils.CommonData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gill on 03-03-2016.
 */
public class MapFrag extends FragmentActivity implements OnMapReadyCallback {
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> descriptionList = new ArrayList<>();

    List<LatLng> steps ;
    TextWatcher textWatcher;
    Button btPress;
    static  double  latitude,longitude;
    GoogleMap mymap;

    void init() {
        initTextWatcher();
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.tv_location);
        btPress = (Button) findViewById(R.id.bt_press);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_frag);
        init();

        autoCompleteTextView.addTextChangedListener(textWatcher);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackGeocoding();

            }
        });
    }

    private void callBackGeocoding() {
        String getAddress = autoCompleteTextView.getText().toString();
        RestClient.getApiService().geoCode(getAddress, serverKey, new Callback<GeoCodeMain>() {
            @Override
            public void success(GeoCodeMain geoCodeMain, Response response) {
                CommonData.setGeoCodeMain(geoCodeMain);

                latitude = geoCodeMain.results.get(0).geometry.location.lat;
                longitude =geoCodeMain.results.get(0).geometry.location.lng;
                Log.v("TAG", String.valueOf(latitude));
                Log.v("TAG", String.valueOf(longitude));

                if (mymap != null) {
                    Marker searchMarker = mymap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Result"));
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(searchMarker.getPosition());
                    LatLngBounds bounds = builder.build();
                    final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 150);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mymap.animateCamera(cameraUpdate);
                        }
                    }, 3000);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void initTextWatcher() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callBackForAutoComplete();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    String serverKey = "AIzaSyDHMOOh_GXANAm3TCJeG33o-QUp3D6BevE";

    private void callBackForAutoComplete() {
        Log.v("TAG",autoCompleteTextView.getText().toString());
        RestClient.getApiService().autoComplete(autoCompleteTextView.getText().toString(), serverKey, new Callback<MyLocation>() {
            @Override
            public void success(MyLocation myLocation, Response response) {
                CommonData.setMyLocation(myLocation);
                descriptionList.clear();
                int len = myLocation.predictions.size();
                for (int loop = 0; loop < len; loop++) {
                    descriptionList.add(loop, myLocation.predictions.get(0).description);
                }
                Log.v("TAGList", String.valueOf(descriptionList));
                arrayAdapter = new ArrayAdapter(MapFrag.this, R.layout.text_layout, descriptionList);
                autoCompleteTextView.setThreshold(2);
                autoCompleteTextView.setAdapter(arrayAdapter);
                autoCompleteTextView.showDropDown();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.mymap = googleMap;
        String Key="AIzaSyCN1OQ2kW1Finc3SIe4CBVJBWT73SijcOs";
        RestClient.getApiService().routePolyline("Moga","Chandigarh",Key, new Callback<SourceDestinationRoute>() {
            @Override
            public void success(SourceDestinationRoute sourceDestinationRoute, Response response) {
                CommonData.setSourceDestinationRoute(sourceDestinationRoute);
                PolylineOptions polylineOptions=new PolylineOptions();
                for(int i=0;i<sourceDestinationRoute.routes.get(0).overviewPolyline.points.length();i++)
                {
                    steps=CommonData.decoder(sourceDestinationRoute.routes.get(0).overviewPolyline.points);
                }
                polylineOptions.addAll(steps);
                mymap.addPolyline(polylineOptions);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
