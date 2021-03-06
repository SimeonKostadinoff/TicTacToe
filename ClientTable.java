// Each nickname has a different incomming-message queue.

import java.util.ArrayList;
import java.util.concurrent.*;

public class ClientTable {

  private ConcurrentMap<String,MessageQueue> queueTable  = new ConcurrentHashMap<String,MessageQueue>();
  
  private String users;

  // The following overrides any previously existing nickname, and
  // hence the last client to use this nickname will get the messages
  // for that nickname, and the previously exisiting clients with that
  // nickname won't be able to get messages. Obviously, this is not a
  // good design of a messaging system. So I don't get full marks:
  
  public ClientTable(){
	  this.users = "";
  }

  public void add(String nickname) {
	 queueTable.put(nickname, new MessageQueue());
		  
  }
  
  // remove a user from the table
  public void remove(String nickname){
	  queueTable.remove(nickname);
  }

  // Returns null if the nickname is not in the table:
  public MessageQueue getQueue(String nickname) {
    return queueTable.get(nickname);
  }
  
  /*
   * method that put every user in a string, except the user who enquire the table
   */
  public String getUsers(String nickname){
	  users = "";
	  for ( String key : queueTable.keySet() ) {
		  if(!key.equals(nickname)){
		    users+= key +  ",";
		  }
		}
	  return users;
  }

}
