import java.io.PrintStream;

// Continuously reads from message queue for a particular client,
// forwarding to the client.

public class ServerSender extends Thread {
  private MessageQueue queue;
  private PrintStream client;
  private ClientTable table;
  private ScoreboardTable scoreboardTable ;
  
  /**
   * 
   * @param queue - message queue where all data is stored
   * @param client - the client
   * @param table - the table with all users
   * @param scoreboardTable - scoreboard table 
   */
  public ServerSender(MessageQueue queue, PrintStream client, ClientTable table, ScoreboardTable scoreboardTable) {
    this.queue = queue;   
    this.client = client;
    this.table = table;
    this.scoreboardTable = scoreboardTable;
  }

  public void run() {
	  
    while (true) {
    // take the message from the queue
      Message msg = queue.take();
      
      /*
       * if message contains yes - 
       * split the message and send the opponent and the message itself to the client
       */
      if (msg.toString().contains("Yes")) {
    	  String opponent = msg.toString().substring(4);
    	  client.println(msg);
    	  client.println(opponent);
      } 
      /*
       * if message is no - 
       * only print the message to the client
       */
      else if(msg.toString().equals("No")){
    	  client.println(msg);
      }
      /*
       * if the message contains GetUsers - 
       * split the message and send the message and the string with all users
       */
      else if(msg.toString().contains("GetUsers")){
    	  String nickname = msg.toString().substring(8);
    	  client.println(msg);
    	  client.println(table.getUsers(nickname));
      }
      /*
       * if message is scoreboard - 
       * send the message and the string with the results of the scoreboard
       */
      else if(msg.toString().equals("Scoreboard")){
    	  client.println(msg);
    	  client.println(scoreboardTable.getScoreboard());
      }
      /*
       * if message is exit - 
       * send only the message
       */
      else if(msg.toString().equals("Exit")){
    	  client.println(msg);
      }
      /*
       * if the message contains the question - 
       * send the opponent and the message itself
       */
      else if (msg.toString().contains("Do you want to play?")) {
    	  System.out.println("Question send");
    	  String opponent = msg.toString().substring(20);
          client.println(msg);
          client.println(opponent);
      } 
      /*
       * if the message is not a specific one - 
       * send only the message
       */
      else {
          client.println(msg);
          client.println("Already given.");  
      }
    }
  }
}
