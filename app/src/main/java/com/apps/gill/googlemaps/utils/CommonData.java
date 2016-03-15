package com.apps.gill.googlemaps.utils;

import com.apps.gill.googlemaps.models.Geocoding.GeoCodeMain;
import com.apps.gill.googlemaps.models.PlaceAutoComplete.MyLocation;
import com.apps.gill.googlemaps.models.Route.SourceDestinationRoute;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gill on 22-02-2016.
 */
public class CommonData {
    public static MyLocation myLocation=null;
    public static GeoCodeMain geoCodeMain=null;
    public static SourceDestinationRoute sourceDestinationRoute=null;


    public static MyLocation getMyLocation() {
        return myLocation;
    }

    public static void setMyLocation(MyLocation myLocation) {
        CommonData.myLocation = myLocation;
    }

    public static GeoCodeMain getGeoCodeMain() {
        return geoCodeMain;
    }

    public static void setGeoCodeMain(GeoCodeMain geoCodeMain) {
        CommonData.geoCodeMain = geoCodeMain;
    }


    public static SourceDestinationRoute getSourceDestinationRoute() {
        return sourceDestinationRoute;
    }

    public static void setSourceDestinationRoute(SourceDestinationRoute sourceDestinationRoute) {
        CommonData.sourceDestinationRoute = sourceDestinationRoute;
    }

    public static List<LatLng> decoder(String encoded)
    {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

}
