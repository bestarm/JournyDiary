package entity;

public class Report {
	
	private String time;
	private String content;
	private int userID;
	
	public Report(String time, String content, int userID) {
		super();
		this.time = time;
		this.content = content;
		this.userID = userID;
	}
	public String getTime() {
		return time;
	}
	public String getContent() {
		return content;
	}
	public int getUserID() {
		return userID;
	}
	
}
