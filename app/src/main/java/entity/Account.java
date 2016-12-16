package entity;

public class Account {
//	private static final int AUTHORIZE_USER = 11;
//	private static final int AUTHORIZE_ADMIN = 22;
	
	private String userName;
	private String passWord;
	private int accountID;
	private int authorize;
	public Account(String userName, String passWord, int accountID,
			int authorize) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.accountID = accountID;
		this.authorize = authorize;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public int getAccountID() {
		return accountID;
	}
	public int getAuthorize() {
		return authorize;
	}
	
	public void setPassWord(String newPass){
		this.passWord = newPass;
	}
}
