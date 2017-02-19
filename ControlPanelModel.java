import java.util.ArrayList;
import java.util.Observable;

public class ControlPanelModel extends Observable{
	
	private ControlPanel panel;
	
	/*
	 * ControlPanel 
	 */
	public ControlPanelModel(ControlPanel panel){
		
		super();
		this.panel = panel;
	}
	
	/*
	 * Overrides all methods form the ControlPanel class
	 */
	
	public String getUsersString() {
		return panel.getUsersString();
	}
	
	public int getUsersCount(){
		return panel.getUsersCount();
	}
	
	public void setUsersString(String usersString) {
		panel.setUsersString(usersString);
		setChanged();
		notifyObservers();
	}
	
	public void setUsers() {
		panel.setUsers();
		setChanged();
		notifyObservers();
	}
	
	public String getUser(int i){
		return panel.getUser(i);
	}
	
	public String getSelectedUser() {
		return panel.getSelectedUser();
	}

	public void setSelectedUser(String selectedUser) {
		panel.setSelectedUser(selectedUser);
		setChanged();
		notifyObservers();
	} 
	
	public String getAnswer(){
		return panel.getAnswer();
	}
	
	public void setAnswer(String answer){
		panel.setAnswer(answer);
		setChanged();
		notifyObservers();
	}
	
	public boolean getAnswerNo(){
		return panel.getAnswerNo();
	}
	public void setAnswerNo(boolean no){
		panel.setAnswerNo(no);
		setChanged();
		notifyObservers();
	}
	
	public boolean isUsersSend() {
		return panel.isUsersSend();
	}

	public void setUsersSend(boolean usersSend) {
		panel.setUsersSend(usersSend);
		setChanged();
		notifyObservers();
	}
	public String getNickname() {
		return panel.getNickname();
	}

	public void setNickname(String nickname) {
		panel.setNickname(nickname);
		setChanged();
		notifyObservers();
	}
	public boolean isScoreboard() {
		return panel.isScoreboard();
	}

	public void setScoreboard(boolean scoreboard) {
		panel.setScoreboard(scoreboard);
		setChanged();
		notifyObservers();
	}
	public String getScoreboardUsers() {
		return panel.getScoreboardUsers();
	}

	public void setScoreboardUsers(String scoreboardUsers) {
		panel.setScoreboardUsers(scoreboardUsers);
		setChanged();
		notifyObservers();
	}

	public ArrayList<String> getScoreboardArray() {
		return panel.getScoreboardArray();
	}

	public void setScoreboardArray() {
		panel.setScoreboardArray();
		setChanged();
		notifyObservers();
	}
	public int getScoreboardArraySize(){
		return panel.getScoreboardArraySize();
	}
	
	public String getScoreboardUser(int i){
		return panel.getScoreboardUser(i);
	}
	

}
