import java.util.ArrayList;
import java.util.Arrays;


public class ControlPanel {
	
	private String usersString; // all users in a string
	private ArrayList<String> users; // array of the users 
	private String selectedUser = null; // the selected user
	private String answer; // the answer of the opponent
	private boolean no; // check for answer no
	private boolean usersSend = false; // check if the user is send
	private String nickname; // the nickname of the user
	private boolean scoreboard = false; // check if the scoreboard is enquired
	private String scoreboardUsers; // user and result in a string
	private ArrayList<String> scoreboardArray; // array of users and results
	
	
	/**
	 * initialize the arrays 
	 */
	public ControlPanel(){
		
		users = new ArrayList<String>();
		scoreboardArray = new ArrayList<String>();
	}

	/*
	 * get the strings of users
	 */
	public String getUsersString() {
		return usersString;
	}

	/*
	 * set the string of users
	 */
	public void setUsersString(String usersString) {
		this.usersString = usersString;
	}

	/*
	 * get the array of users
	 */
	public ArrayList<String> getUsers() {
		return users;
	}
	
	/*
	 * get the count of the users
	 */
	public int getUsersCount(){
		return users.size();
	}
	
	/*
	 * set the users to an array 
	 */
	public void setUsers() {
		if(usersString != null && usersString != ""){
			users = new ArrayList<String>(Arrays.asList(usersString.split(",")));
		}
	}
	
	/*
	 * get a particular user
	 */
	public String getUser(int i){
		String user = null;
		if(!users.isEmpty()){
			user = users.get(i);
		}
		return user;
	}

	/*
	 * get the selected user
	 */
	public String getSelectedUser() {
		return selectedUser;
	}

	/*
	 * set the selected user
	 */
	public void setSelectedUser(String selectedUser) {
		this.selectedUser = selectedUser;
	}

	/*
	 * get the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/*
	 * set the answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	/*
	 * if no answer
	 */
	public boolean getAnswerNo(){
		return this.no;
	}
	
	/*
	 * set if no answer
	 */
	public void setAnswerNo(boolean no){
		this.no = no;
	}

	/*
	 * chekck if user send
	 */
	public boolean isUsersSend() {
		return usersSend;
	}

	/*
	 * set if user send
	 */
	public void setUsersSend(boolean usersSend) {
		this.usersSend = usersSend;
	}

	/*
	 * get the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/*
	 * set the nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/*
	 * check if scoreboard is enquired
	 */
	public boolean isScoreboard() {
		return scoreboard;
	}
	/*
	 * set if scoreboard is enquired
	 */
	public void setScoreboard(boolean scoreboard) {
		this.scoreboard = scoreboard;
	}

	/*
	 * get string of the users and the results in the scoreboard
	 */
	public String getScoreboardUsers() {
		return scoreboardUsers;
	}

	/*
	 * set string of the users and the results in the scoreboard
	 */
	public void setScoreboardUsers(String scoreboardUsers) {
		this.scoreboardUsers = scoreboardUsers;
	}
	
	/*
	 * get an array of the scoreboards data
	 */
	public ArrayList<String> getScoreboardArray() {
		return scoreboardArray;
	}

	/*
	 * set the string of scoreboard data into array
	 */
	public void setScoreboardArray() {
		if(scoreboardUsers != null && scoreboardUsers != ""){
			scoreboardArray = new ArrayList<String>(Arrays.asList(scoreboardUsers.split(";")));
		}
	}
	
	/*
	 * get the array of scoreboard size
	 */
	public int getScoreboardArraySize(){
		return scoreboardArray.size();
	}
	
	/*
	 * get a particular user 
	 */
	public String getScoreboardUser(int i){
		String user = null;
		if(!scoreboardArray.isEmpty()){
			user = scoreboardArray.get(i);
		}
		return user;
	}
	
}
