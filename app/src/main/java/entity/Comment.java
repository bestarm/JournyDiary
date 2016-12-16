package entity;

public class Comment {
	
	private String content;
	private int userID;
	private int diaryID;
	private int journyID;
	
	public Comment(String content, int userID, int diaryID, int journyID) {
		super();
		this.content = content;
		this.userID = userID;
		this.diaryID = diaryID;
		this.journyID = journyID;
	}

	public String getContent() {
		return content;
	}

	public int getUserID() {
		return userID;
	}

	public int getDiaryID() {
		return diaryID;
	}

	public int getJournyID() {
		return journyID;
	}
	
	
}
