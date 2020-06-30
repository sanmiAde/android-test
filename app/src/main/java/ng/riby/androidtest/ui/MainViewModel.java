package ng.riby.androidtest.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ng.riby.androidtest.database.UserLocationRepository;
import ng.riby.androidtest.model.UserLocation;

public class MainViewModel extends AndroidViewModel {

    UserLocationRepository mUserLocationRepo;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mUserLocationRepo = new UserLocationRepository(application);
    }


   public LiveData<UserLocation> getUserLocation(){
        return mUserLocationRepo.getUserLocation();
    }

    public void setUserLocation(UserLocation userLocation){
        mUserLocationRepo.inserUserLocation(userLocation);
    }
}
