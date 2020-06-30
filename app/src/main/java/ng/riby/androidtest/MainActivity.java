package ng.riby.androidtest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnGPSStatusChangedListener {

    private static final int PERMISSION_REQUEST_CODE = 12456;

    private LocationListener locationListener;

    private UserLocation userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPermission()) {
            userLocation = new UserLocation();
            locationListener = new LocationListener(this, getLifecycle(), this);

            initClickListeners();

        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);
    }


    private void initClickListeners() {
        final Button startButton = findViewById(R.id.btn_start);

        final Button stopButton  = findViewById(R.id.btn_stop);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopButton.setVisibility(View.VISIBLE);
                locationListener.addOnLocationChangedListener(new OnLocationRetrievedListener() {
                    @Override
                    public void locationRecieved(Location location) {
                        Double roundedLat = Math.round(location.getLatitude() * 10000.0)/ 10000.0;
                        Double  roundedLon = Math.round(location.getLongitude() * 10000.0)/ 10000.0;

                        userLocation.setOriginLat(roundedLat);
                        userLocation.setOriginLong(roundedLon);

                        locationListener.removeOnLocationChangedListener();

                        Log.d("testing2", userLocation.toString());
                    }
                });
                locationListener.getUserLocation();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationListener.addOnLocationChangedListener(new OnLocationRetrievedListener() {
                    @Override
                    public void locationRecieved(Location location) {
                        Double roundedLat = Math.round(location.getLatitude() * 10000.0)/ 10000.0;
                        Double  roundedLon = Math.round(location.getLongitude() * 10000.0)/ 10000.0;

                        userLocation.setCurrentLat(roundedLat);
                        userLocation.setCurrentLong(roundedLon);

                        locationListener.removeOnLocationChangedListener();

                        Log.d("testing1", userLocation.toString());
                    }
                });
                locationListener.getUserLocation();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationListener = new LocationListener(this, getLifecycle(), this);
                    initClickListeners();

                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void gpsStatusChanged(String message) {


        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
