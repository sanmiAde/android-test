package ng.riby.androidtest;

import android.annotation.SuppressLint;


import android.content.Context;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

class LocationListener implements LifecycleObserver, android.location.LocationListener {

    private Lifecycle lifecycle;

   private OnLocationRetrievedListener onLocationRetrievedListener;

   private OnGPSStatusChangedListener onGPSStatusChangedListener;

   private LocationManager locationManager;

    public LocationListener(Context context, Lifecycle lifecycle, OnGPSStatusChangedListener onGPSStatusChangedListener) {
        this.lifecycle = lifecycle;
        this.lifecycle.addObserver(this);
        this.onGPSStatusChangedListener = onGPSStatusChangedListener;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
     void start() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            locationManager.registerGnssStatusCallback(new GnssStatus.Callback() {
                @Override
                public void onStarted() {
                    super.onStarted();
                    onGPSStatusChangedListener.gpsStatusChanged("Searching For GPS");
                }

                @Override
                public void onStopped() {
                    super.onStopped();
                }

                @Override
                public void onFirstFix(int ttffMillis) {
                    super.onFirstFix(ttffMillis);
                    onGPSStatusChangedListener.gpsStatusChanged("GPS found");
                }

                @Override
                public void onSatelliteStatusChanged(GnssStatus status) {
                    super.onSatelliteStatusChanged(status);
                }
            });
        } else {
            locationManager.addGpsStatusListener(new GpsStatus.Listener() {
                @Override
                public void onGpsStatusChanged(int i) {
                    switch (i) {
                        case GpsStatus.GPS_EVENT_STARTED:
                            onGPSStatusChangedListener.gpsStatusChanged("Searching For GPS");
                            break;
                        case GpsStatus.GPS_EVENT_FIRST_FIX:
                            onGPSStatusChangedListener.gpsStatusChanged("GPS found");
                    }
                }
            });
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        locationManager.removeUpdates(this);
        removeOnLocationChangedListener();
    }

    /***
     * Afer getting the first location remove the listener to stop recieving locatoin updates.
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        onLocationRetrievedListener.locationRecieved(location);

        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @SuppressLint("MissingPermission")
    public void getUserLocation(){

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public void addOnLocationChangedListener(OnLocationRetrievedListener onLocationRetrievedListener){
        this.onLocationRetrievedListener = onLocationRetrievedListener;
    }

    public void removeOnLocationChangedListener(){
        this.onLocationRetrievedListener = null;
    }

}
