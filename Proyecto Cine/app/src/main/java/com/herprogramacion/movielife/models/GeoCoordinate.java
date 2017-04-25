package com.herprogramacion.movielife.models;

public class GeoCoordinate {
    private double latitude;
    private double longitude;

    //Constructor
    public GeoCoordinate(){
        //Empty constructor for Firebase data read
    }
    public GeoCoordinate (double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //Getters and Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double distance (GeoCoordinate other){
        final double EARTH_RADIUS = 6371000;

        double latDistance = Math.toRadians(this.getLatitude() - other.getLatitude());
        double longDistance = Math.toRadians(this.getLongitude() - other.getLongitude());

        double a = Math.sin(latDistance/2) * Math.sin(latDistance/2)
                + Math.sin(longDistance/2) * Math.sin(longDistance/2)
                * Math.cos(Math.toRadians(this.getLatitude()))
                * Math.cos(Math.toRadians(other.getLatitude()));
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        return c * EARTH_RADIUS;
    }

    @Override
    public String toString() {
        return "GeoCoordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
