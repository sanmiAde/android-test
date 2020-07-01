package ng.riby.androidtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_location_db")
public class UserLocation implements Parcelable {

    public static final Creator<UserLocation> CREATOR = new Creator<UserLocation>() {
        @Override
        public UserLocation createFromParcel(Parcel source) {
            return new UserLocation(source);
        }

        @Override
        public UserLocation[] newArray(int size) {
            return new UserLocation[size];
        }
    };
    @PrimaryKey
   public int primaryKey = 0;
    @ColumnInfo(name = "origin_lat")
    Double originLat;
    @ColumnInfo(name = "origin_long")
    Double originLong;
    @ColumnInfo(name = "current_lat")
    Double currentLat;
    @ColumnInfo(name = "current_long")
    Double currentLong;

    public UserLocation() {
        this.originLat = Double.MIN_VALUE;
        this.originLong =Double.MIN_VALUE;
        this.currentLat = Double.MIN_VALUE;
        this.currentLong = Double.MIN_VALUE;
    }


    /***
     * THis method checks if both the user current and orgin location has been gotten.
     * @return Boolean used to determine if user location has been gotten completely.
     */
    public boolean hasLocationBeenGotten() {
        return originLat != Double.MIN_VALUE && originLong != Double.MIN_VALUE && currentLat != Double.MIN_VALUE && currentLong != Double.MIN_VALUE;
    }


    protected UserLocation(Parcel in) {
        this.originLat = (Double) in.readValue(Double.class.getClassLoader());
        this.originLong = (Double) in.readValue(Double.class.getClassLoader());
        this.currentLat = (Double) in.readValue(Double.class.getClassLoader());
        this.currentLong = (Double) in.readValue(Double.class.getClassLoader());
    }

    public Double getOriginLat() {
        return originLat;
    }

    public void setOriginLat(Double originLat) {
        this.originLat = originLat;
    }

    public Double getOriginLong() {
        return originLong;
    }

    public void setOriginLong(Double originLong) {
        this.originLong = originLong;
    }

    public Double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(Double currentLat) {
        this.currentLat = currentLat;
    }

    public Double getCurrentLong() {
        return currentLong;
    }

    public void setCurrentLong(Double currentLong) {
        this.currentLong = currentLong;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.originLat);
        dest.writeValue(this.originLong);
        dest.writeValue(this.currentLat);
        dest.writeValue(this.currentLong);
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "originLat=" + originLat +
                ", originLong=" + originLong +
                ", currentLat=" + currentLat +
                ", currentLong=" + currentLong +
                '}';
    }
}
