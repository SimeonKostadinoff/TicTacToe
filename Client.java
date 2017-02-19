// Usage:
//        java Client user-nickname hostname
//
// After initializing and opening appropriate sockets, we start two
// client threads, one to send messages, and another one to get
// messages.
//
// A limitation of our implementation is that there is no provision
// for a client to end after we start it. However, we implemented
// things so that pressing ctrl-c will cause the client to end
// gracefully without causing the server to fail.
//
// Another limitation is that there is no provision to terminate when
// the server dies.
import java.io.*;
import java.net.*;

class Client {

  public static void main(String[] args) {

    // Check correct usage:
    if (args.length != 3) {
      System.err.println("Usage: java Client user-nickname port hostname");
      System.exit(1); // Give up.
    }
    
    // Initialize information:
    String nickname = args[0];
    String port = args[1];
    String hostname = args[2]; // machine 
    

    // Open sockets:
    PrintStream toServer = null;
    BufferedReader fromServer = null;
    Socket server = null;

    try {
      //server = new Socket(hostname, Port.number);
    	server = new Socket(hostname, Integer.parseInt(port));
    	toServer = new PrintStream(server.getOutputStream());
    	fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
    } 
    catch (UnknownHostException e) {
      System.err.println("Unknown host: " + hostname);
      System.exit(1); // Give up.
    } 
    catch (IOException e) {
      System.err.println("The server doesn't seem to be running " + e.getMessage());
      System.exit(1); // Give up.
    }

    // Create objects of different classes
    
    Opponent opp = new Opponent(); // used to save the opponent
    
    Respond rsp = new Respond(); // used to save the respond to a request
    
    Answer answer = new Answer(); // used to check the answer of a request
    
    NoughtsCrossesModel model = new NoughtsCrossesModel(new NoughtsCrosses()); // the model of the TicTacToe game
    
    NoughtsCrossesGUI nottGUI = new NoughtsCrossesGUI(model); // GUI of the game
    
    Thread thread = new Thread(nottGUI); // thread for the game
    
    ControlPanelModel controlModel = new ControlPanelModel(new ControlPanel()); // model for the control panel (lobby)
    
    Thread threadControl = new Thread(new ControlGUI(controlModel)); // thread for the control panel
    
    
    // objects ClientSender and ClientReceiver
    ClientSender sender = new ClientSender(nickname,toServer, model, opp, rsp, thread, controlModel, threadControl, answer, nottGUI);
    ClientReceiver receiver = new ClientReceiver(fromServer, model, opp, rsp, controlModel, answer);

    // Run them in parallel:
    sender.start();
    receiver.start();
    
    // Wait for them to end and close sockets.
    try {
      sender.join();
      toServer.close();
      receiver.join();
      fromServer.close();
      server.close();
    }
    catch (IOException e) {
      System.err.println("Something wrong " + e.getMessage());
      System.exit(1); // Give up.
    }
    catch (InterruptedException e) {
      System.err.println("Unexpected interruption " + e.getMessage());
      System.exit(1); // Give up.
    }
  }
}
