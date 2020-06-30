package ng.riby.androidtest.database;

import android.app.Application;
import android.database.sqlite.SQLiteCursor;

import androidx.lifecycle.LiveData;


import ng.riby.androidtest.database.LocationDao;
import ng.riby.androidtest.database.LocationDatabase;
import ng.riby.androidtest.model.UserLocation;

public class UserLocationRepository {

    private LocationDao mUserLocationDao;
    private ng.riby.androidtest.database.LocationDatabase db;

    public UserLocationRepository(Application application) {
        db = LocationDatabase.getDatabase(application);
        mUserLocationDao = db.wordDao();
    }

    public LiveData<UserLocation> getUserLocation(){
        return mUserLocationDao.getUserLocation();
    }

    public void inserUserLocation(final UserLocation userLocation){
        db.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mUserLocationDao.insertUserLocation(userLocation);
            }
        });
    }


}
