package ng.riby.androidtest.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import ng.riby.androidtest.R;
import ng.riby.androidtest.model.UserLocation;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        viewModel.getUserLocation().observe(this, new Observer<UserLocation>() {
            @Override
            public void onChanged(UserLocation userLocation) {

                if (userLocation != null) {

                    LatLng firstLocation = new LatLng(userLocation.getOriginLat(), userLocation.getOriginLong());
                    mMap.addMarker(new MarkerOptions().position(firstLocation).title("Previous Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(firstLocation));

                    LatLng secondLocation = new LatLng(userLocation.getCurrentLat(), userLocation.getCurrentLong());
                    mMap.addMarker(new MarkerOptions().position(secondLocation).title("Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(firstLocation));

                    final Double distance = distance(userLocation.getOriginLat(), userLocation.getOriginLong(), userLocation.getCurrentLat(), userLocation.getCurrentLong());

                    Polyline polyline1 = googleMap.addPolyline(
                            new PolylineOptions()
                                    .clickable(true)
                                    .add(
                                            firstLocation,
                                            secondLocation
                                    ));

                    Toast.makeText(MapsActivity.this, "You have travelled " + String.format("%.2f", distance) + " Kilometers", Toast.LENGTH_SHORT).show();

                    googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener(){

                        @Override
                        public void onPolylineClick(Polyline polyline) {
                            Toast.makeText(MapsActivity.this, "You have travelled " +String.format("%.2f", distance) + " Kilometers", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(MapsActivity.this, "You don't have any location saved currently", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}