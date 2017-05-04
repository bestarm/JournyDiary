package entity;

import java.io.Serializable;

public class Journy implements Serializable{
	
	private String nameJourny;
	private String placeJourny;
	private String coverImage;
	private String timeJourny;
	private int journyID;
	private int distance;
	private int userID;
	private boolean isSync;

	public Journy(String nameJourny, String placeJourny,String timeJourny,
				  String coverImage, int journyID,int distance, int userID, boolean isSync) {
		super();
		this.nameJourny = nameJourny;
		this.placeJourny = placeJourny;
		this.timeJourny = timeJourny;
		this.coverImage = coverImage;
		this.journyID = journyID;
		this.distance = distance;
		this.userID = userID;
		this.isSync = isSync;
	}

	public String getNameJourny() {
		return nameJourny;
	}

	public String getPlaceJourny() {
		return placeJourny;
	}

	public int getJournyID() {
		return journyID;
	}

	public int getDistance() {
		return distance;
	}

	public int getUserID() {
		return userID;
	}

	public void setNameJourny(String nameJourny) {
		this.nameJourny = nameJourny;
	}

	public void setPlaceJourny(String placeJourny) {
		this.placeJourny = placeJourny;
	}

	public void setJournyID(int journyID) {
		this.journyID = journyID;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getTimeJourny() {
		return timeJourny;
	}

	public void setTimeJourny(String timeJourny) {
		this.timeJourny = timeJourny;
	}

	public boolean isSync() {
		return isSync;
	}

	public void setSync(boolean sync) {
		isSync = sync;
	}
}
