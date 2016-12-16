package entity;

public class Diary {
	
	private int diaryID;
	private String timeDiary;
	private String placeDiary;
	private String description;
	private String image;
	private String mp3;
	private String video;
	private int journyID;
	
	public Diary(int diaryID, String timeDiary, String placeDiary,String description,
			String image, String mp3, String video, int journyID) {
		super();
		this.diaryID = diaryID;
		this.timeDiary = timeDiary;
		this.placeDiary = placeDiary;
		this.description = description;
		this.image = image;
		this.mp3 = mp3;
		this.video = video;
		this.journyID = journyID;
	}

	public int getDiaryID() {
		return diaryID;
	}

	public String getTimeDiary() {
		return timeDiary;
	}

	public String getPlaceDiary() {
		return placeDiary;
	}

	public String getImage() {
		return image;
	}

	public String getMp3() {
		return mp3;
	}

	public String getVideo() {
		return video;
	}

	public int getJournyID() {
		return journyID;
	}

	public String getDescription() {
		return description;
	}
}
