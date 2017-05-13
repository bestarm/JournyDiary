package entity;

/**
 * Created by ThanhCong on 09/05/2017.
 */

public class Location {
    private int diaryID;
    private double lattitude, longtitude;

    public Location(int diaryID, double lattitude, double longtitude) {
        this.diaryID = diaryID;
        this.lattitude = lattitude;
        this.longtitude = longtitude;
    }

    public int getDiaryID() {
        return diaryID;
    }

    public void setDiaryID(int diaryID) {
        this.diaryID = diaryID;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
