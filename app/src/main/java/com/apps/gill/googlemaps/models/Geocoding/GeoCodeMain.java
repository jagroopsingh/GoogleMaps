
package com.apps.gill.googlemaps.models.Geocoding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GeoCodeMain {
    public List<Result> results = new ArrayList<Result>();
    public String status;

    public class Result {

        public List<AddressComponent> addressComponents = new ArrayList<AddressComponent>();
        public String formattedAddress;
        public Geometry geometry;
        public String placeId;
        public List<String> types = new ArrayList<String>();

        public class AddressComponent {
            public String longName;
            public String shortName;
            public List<String> types = new ArrayList<String>();
        }

        public class Geometry {
            public Location location;
            public String locationType;
            public ViewPort viewPort;

            public class Location {
                public Double lat, lng;
            }

            public class ViewPort {
                Direction southwest, northwest;

                class Direction {
                    double lat, lng;
                }
            }

        }
    }
}
