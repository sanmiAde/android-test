package ng.riby.androidtest;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLocation implements Parcelable {
    Double originLat;

    Double originLong;

    Double currentLat;

    Double currentLong;

    public UserLocation() {
        this.originLat = 0.0;
        this.originLong = 0.0;
        this.currentLat = 0.0;
        this.currentLong = 0.0;
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

    /***
     * THis method checks if both the user current and orgin location has been gotten.
     * @return Boolean used to determine if user location has been gotten completely.
     */
    public boolean hasLocationBeenGotten(){
        return originLat != 0.0 && originLong != 0 && currentLat != 0 && currentLong != 0;
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

    protected UserLocation(Parcel in) {
        this.originLat = (Double) in.readValue(Double.class.getClassLoader());
        this.originLong = (Double) in.readValue(Double.class.getClassLoader());
        this.currentLat = (Double) in.readValue(Double.class.getClassLoader());
        this.currentLong = (Double) in.readValue(Double.class.getClassLoader());
    }

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
