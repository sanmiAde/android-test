package ng.riby.androidtest.ui;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ng.riby.androidtest.database.UserLocationRepository;
import ng.riby.androidtest.model.UserLocation;

public class MainViewModel extends AndroidViewModel {

    UserLocationRepository mUserLocationRepo;

    private UserLocation userLocation;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mUserLocationRepo = new UserLocationRepository(application);
        userLocation = new UserLocation();
    }


    /***
     * This method gets the userlocation stored in the ROOM database.
     * @return livedata containting previously stored userLocation.
     */
   public LiveData<UserLocation> getLocation(){
        return mUserLocationRepo.getUserLocation();
    }

    /***
     * THis method saves the user location after both the origin location and the final location has been gotten.
     */
    public void saveUserLocation(){
        mUserLocationRepo.inserUserLocation(userLocation);
    }

    /***
     * THis method is used to check if both the origin location and the final location has been gotten.
     */
    public boolean hasLocationBeenGotten(){
        return  userLocation.hasLocationBeenGotten();
    }

    /***
     * THis method return a User Location instance with it's field set to minimum double value
     * @return a UserLocationObject
     */
    public UserLocation getUserLocation(){
        userLocation = new UserLocation();
        return userLocation;
    }

    /***
     * THis method sets the origin location of the user. User origin location is gotten after the user clicks the start button and a GPS connection has been established.
     * @param location the location of user.
     */
    public void setOrginLocation(Location location){
        Double roundedLat = Math.round(location.getLatitude() * 10000.0) / 10000.0;
        Double roundedLon = Math.round(location.getLongitude() * 10000.0) / 10000.0;

        userLocation.setOriginLat(roundedLat);
        userLocation.setOriginLong(roundedLon);
    }

    /***
     * This method sets the current location of the user. User current location is gotten after the user clicks the stop button and a GPS connection has been established.
     * @param location the location of user.
     */
    public void  setCurrentLocation(Location location){
        Double roundedLat = Math.round(location.getLatitude() * 10000.0) / 10000.0;
        Double roundedLon = Math.round(location.getLongitude() * 10000.0) / 10000.0;

        userLocation.setCurrentLat(roundedLat);
        userLocation.setCurrentLong(roundedLon);
    }

}
