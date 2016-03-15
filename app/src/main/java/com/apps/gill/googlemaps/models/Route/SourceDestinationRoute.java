
package com.apps.gill.googlemaps.models.Route;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SourceDestinationRoute {
    public List<GeocodedWaypoint> geocodedWaypoints = new ArrayList<GeocodedWaypoint>();
    public List<Route> routes = new ArrayList<Route>();
    public String status;

    public class GeocodedWaypoint {
        public String geocoderStatus;
        public String placeId;
        public List<String> types = new ArrayList<String>();
    }

    public class Route {
        public Bounds bounds;
        public String copyrights;
        public List<Leg> legs = new ArrayList<Leg>();
        public OverviewPolyline overviewPolyline;
        public String summary;
        public List<Object> warnings = new ArrayList<Object>();
        public List<Object> waypointOrder = new ArrayList<Object>();

        public class Bounds {
            public Northeast northeast;
            public Southwest southwest;

            public class Northeast {
                public Double lat, lng;
            }

            public class Southwest {
                public Double lat, lng;
            }
        }

        public class Leg {
            public Distance distance;
            public Duration duration;
            public String endAddress;
            public EndLocation endLocation;
            public String startAddress;
            public StartLocation startLocation;
            public List<Step> steps = new ArrayList<Step>();
            public List<Object> viaWaypoint = new ArrayList<Object>();

            public class Distance {
                public String text;
                public Integer value;
            }

            public class Duration {

                public String text;
                public Integer value;
            }

            public class EndLocation {
                public Double lat, lng;
            }

            public class StartLocation {

                public Double lat, lng;
            }
        }

        public class OverviewPolyline {
            public String points;
        }

        public class Step {
            public Distance_ distance;
            public Duration_ duration;
            public EndLocation_ endLocation;
            public String htmlInstructions;
            public Polyline polyline;
            public StartLocation_ startLocation;
            public String travelMode;
            public String maneuver;

            public class Distance_ {

                public String text;
                public Integer value;
            }

            public class Duration_ {
                public String text;
                public Integer value;
            }

            public class EndLocation_ {

                public Double lat, lng;
            }

            public class Polyline {
                public String points;
            }

            public class StartLocation_ {
                public Double lat, lng;
            }

        }

    }

}