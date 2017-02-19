import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ScoreboardTable {
	
	  /**
	   * table that stores all users and their results after the games
	   */
	  private ConcurrentMap<String,Integer> scoreboardTable  = new ConcurrentHashMap<String,Integer>();
	  
	  private String scoreboardUsers = "";
	  
	  /*
	   * initially every user enters the table with 0 points
	   */
	  public void add(String nickname) {
			  scoreboardTable.put(nickname, 0);
	  }
	  
	  // get the score
	  public int getScore(String nickname){
		  return scoreboardTable.get(nickname);
	  }
	  
	  // update the score - add 1 if the user won a game
	  public void updateScore(String nickname){
		  scoreboardTable.replace(nickname, this.getScore(nickname), this.getScore(nickname) + 1);
	  }
	  
	  // transform all data from the table into a string - and return it
	  public String getScoreboard(){
		  scoreboardUsers = "";
		  for ( String key : scoreboardTable.keySet() ) {
			  scoreboardUsers+=key + ": " + this.getScore(key) + " point(s);";
	  }
		  return scoreboardUsers;
}
	  


}
