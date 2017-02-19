import java.io.*;

// Gets messages from client and puts them in a queue, for another
// thread to forward to the appropriate client.

public class ServerReceiver extends Thread {

	private String myClientsName;
	private BufferedReader myClient;
	private ClientTable clientTable;
	private ScoreboardTable scoreboardTable;

	/**
	 * 
	 * @param myClientsName
	 *            - the name of the user
	 * @param myClient
	 *            - used to close the client when needed
	 * @param clientTable
	 *            - table with users
	 * @param scoreboardTable
	 *            - scoreboard
	 */
	public ServerReceiver(String myClientsName, BufferedReader myClient, ClientTable clientTable,
			ScoreboardTable scoreboardTable) {
		this.myClientsName = myClientsName;
		this.myClient = myClient;
		this.clientTable = clientTable;
		this.scoreboardTable = scoreboardTable;
	}

	public void run() {
		try {

			while (true) {

				// Reads the two lines that the user passed from the
				// ClientSender
				String opponent = myClient.readLine();
				String cellActive = myClient.readLine();

				/*
				 * if user passes Yes - the message is offered to the queue of
				 * the user opponent
				 */
				if (cellActive.equals("Yes")) {
					Message msg = new Message(myClientsName, cellActive + " " + myClientsName);
					MessageQueue opponentQueue = clientTable.getQueue(opponent);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + opponent + ": " + cellActive);

				}
				/*
				 * if user passes No - the message is offered to the queue of
				 * the user opponent
				 */
				else if (cellActive.equals("No")) {
					Message msg = new Message(myClientsName, cellActive);
					MessageQueue opponentQueue = clientTable.getQueue(opponent);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + opponent + ": " + cellActive);

				}
				/*
				 * if user passes GetUsers - the message is offered to his queue
				 */
				else if (cellActive.equals("GetUsers")) {
					Message msg = new Message(myClientsName, cellActive + myClientsName);
					MessageQueue opponentQueue = clientTable.getQueue(myClientsName);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + myClientsName + ": " + cellActive);

				}
				/*
				 * if user passes Increase - method that increases the score of
				 * the user is being called
				 */
				else if (cellActive.equals("Increase")) {
					scoreboardTable.updateScore(myClientsName);

				}
				/*
				 * if user passes Scoreboard - the message is offered to his
				 * queue
				 */
				else if (cellActive.equals("Scoreboard")) {
					Message msg = new Message(myClientsName, cellActive);
					MessageQueue scoreboardQueue = clientTable.getQueue(myClientsName);
					if (scoreboardQueue != null)
						scoreboardQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + myClientsName + ": " + cellActive);

				}
				/*
				 * if user passes Exit - the message is offered to the queue of
				 * the user opponent
				 */
				else if (cellActive.equals("Exit")) {
					Message msg = new Message(myClientsName, cellActive);
					MessageQueue opponentQueue = clientTable.getQueue(opponent);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + opponent + ": " + cellActive);
				}
				/*
				 * if not a specific text is send - this means that the user is
				 * in game
				 */
				else {

					// checks if user passes an integer
					boolean number = true;
					try {
						int a = Integer.parseInt(cellActive);
					} catch (NumberFormatException e) {
						number = false;
					}

					/*
					 * if both opponent and number are passed correctly - the
					 * number is offered to the queue of the user opponent
					 */
					if (opponent != null && number) {
						Message msg = new Message(myClientsName, cellActive);
						MessageQueue opponentQueue = clientTable.getQueue(opponent);
						if (opponentQueue != null)
							opponentQueue.offer(msg);
						else
							System.err.println("Message for unexistent client " + opponent + ": " + cellActive);
					}

					/*
					 * if the passed number is not an Integer - but the opponent
					 * is correct - then it has been made a request for game -
					 * the question with the name of the user are passed
					 */
					else if (opponent != null) {

						String question = "Do you want to play?" + myClientsName;
						Message msg = new Message(myClientsName, question);
						MessageQueue opponentQueue = clientTable.getQueue(opponent);
						if (opponentQueue != null) {
							opponentQueue.offer(msg);
						} else {
							System.err.println("Message for unexistent client " + opponent + ": " + question);
						}
					} else {
						myClient.close(); // the client must close
						return;
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Something went wrong with the client " + myClientsName + " " + e.getMessage());
			clientTable.remove(myClientsName);
			// No point in trying to close sockets. Just give up.
			// We end this thread (we don't do System.exit(1)).
		} catch (NullPointerException e) { // catch NullPointerException to
											// remove the user from the table
			System.err.println("Something went wrong with the client " + myClientsName + " " + e.getMessage());
			clientTable.remove(myClientsName);
		}
	}
}
