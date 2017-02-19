// Usage:
//        java Server
//
// There is no provision for ending the server gracefully.  It will
// end if (and only if) something exceptional happens.


import java.net.*;
import java.io.*;

public class Server {
	

   public static void main(String [] args) {
	   
   if (args.length != 1) {
	      System.err.println("Usage: java Server port");
	      System.exit(1); // Give up.
	    }
	    
	    // Initialize information:
	    String port = args[0];

    // This will be shared by the server threads:
    ClientTable clientTable = new ClientTable();
    
    // scoreboardTable
    ScoreboardTable scoreboardTable = new ScoreboardTable();

    // Open a server socket:
    ServerSocket serverSocket = null;

    // We must try because it may fail with a checked exception:
    try {
      //serverSocket = new ServerSocket(Port.number);
    	serverSocket = new ServerSocket(Integer.parseInt(port));
    } 
    catch (IOException e) {
      System.err.println("Couldn't listen on port " + Port.number);
      System.exit(1); // Give up.
    }

    // Good. We succeeded. But we must try again for the same reason:
    try { 
      // We loop for ever, as servers usually do:

      while (true) {
        // Listen to the socket, accepting connections from new clients:
        Socket socket = serverSocket.accept();

        // This is so that we can use readLine():
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // We ask the client what its name is:
        String clientName = fromClient.readLine();
        // For debugging:
       
        // Repeating users - if a user with the same name enters the game he receives a name with added "1" in the back
        if(clientTable.getQueue(clientName) == null){
        	clientTable.add(clientName);
            scoreboardTable.add(clientName);
        }else{
        	clientName = clientName + "1";
        	clientTable.add(clientName);
            scoreboardTable.add(clientName);
        }
        System.out.println(clientName + " connected");

        // We create and start a new thread to read from the client:
        (new ServerReceiver(clientName, fromClient, clientTable, scoreboardTable)).start();

        // We create and start a new thread to write to the client:
        PrintStream toClient = new PrintStream(socket.getOutputStream());
        (new ServerSender(clientTable.getQueue(clientName), toClient, clientTable, scoreboardTable)).start();
      }
    } 
    catch (IOException e) {
      // Lazy approach:
      System.err.println("IO error " + e.getMessage());
      // A more sophisticated approach could try to establish a new
      // connection. But this is beyond this simple exercise.
    }
  }
  
}
