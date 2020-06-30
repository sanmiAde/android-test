package ng.riby.androidtest.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import ng.riby.androidtest.model.UserLocation;

@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserLocation(UserLocation userLocation);

    @Query("SELECT * FROM user_location_db")
    LiveData<UserLocation> getUserLocation();
}
