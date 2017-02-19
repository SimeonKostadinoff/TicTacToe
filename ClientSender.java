import java.io.PrintStream;
import javax.swing.JOptionPane;

// Repeatedly reads recipient's nickname and text from the user in two
// separate lines, sending them to the server (read by ServerReceiver
// thread).

public class ClientSender extends Thread {

	private String nickname;
	private PrintStream server;
	private NoughtsCrossesModel model;
	private Opponent opp;
	private Respond rsp;
	private Answer answerEle;
	private Thread threadGame, threadControl;
	private ControlPanelModel controlModel;
	private boolean count = true;
	private String answer;
	private NoughtsCrossesGUI nottGUI;

	/**
	 * 
	 * @param nickname - nickname of the logged user
	 * @param server - the server
	 * @param model - model for the game - all the methods are available from there
	 * @param opp - opponent that is chosen 
	 * @param rsp - received respond after a request
	 * @param threadGame - thread of the game GUI
	 * @param controlModel - model for the control panel (lobby) - all the methods are available from there
	 * @param threadControl - thread for the control panel GUI
	 * @param answerEle - answer
	 * @param nottGUI - the game GUI 
	 */
	ClientSender(String nickname, PrintStream server, NoughtsCrossesModel model, Opponent opp, Respond rsp,
			Thread threadGame, ControlPanelModel controlModel, Thread threadControl, Answer answerEle,
			NoughtsCrossesGUI nottGUI) {

		this.nickname = nickname;
		this.server = server;
		this.model = model;
		this.opp = opp;
		this.rsp = rsp;
		this.answerEle = answerEle;
		this.threadGame = threadGame;
		this.controlModel = controlModel;
		this.threadControl = threadControl;
		this.nottGUI = nottGUI;
	}

	public void run() {

		// start thread for control panel and the game
		threadControl.start();
		threadGame.start();

		// Tell the server what my nickname is:
		server.println(nickname);

		// set the nickname as a control panel title
		controlModel.setNickname(nickname);

		while (true) {

			String recipient = "";

			/**
			 * First loop when the respond is not Yes
			 */
			while (!rsp.getRespond().equals("Yes")) {

				// The users initially run in this loop while an opponent is not
				// chosen
				while (controlModel.getSelectedUser() == null) {

					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					/**
					 * if the answer to a request is No - prints the opponent to
					 * the server, prints "No" message, set the opponent to null
					 * - to prevent exit the loop, sets the answer value to its
					 * original
					 */
					if (controlModel.getAnswerNo()) {

						server.println(opp.getOpponent());
						server.println("No");
						opp.setOpponent(null);
						controlModel.setAnswerNo(false);
					}
					count = true;
					
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					/**
					 * if the user click "Reset" button - prints the opponent
					 * and GetUsers message to the server
					 */

					if (controlModel.isUsersSend()) {
						server.println(opp.getOpponent());
						server.println("GetUsers");
						controlModel.setUsersSend(false);
					}

					/**
					 * if the user clicks "Scoreboard" - prints the opponent and
					 * the Scoreboard message to the server
					 */
					if (controlModel.isScoreboard()) {
						server.println(opp.getOpponent());
						server.println("Scoreboard");
						controlModel.setScoreboard(false);
					}

				}

				/**
				 * if there is a selected user or the answer is Yes
				 */
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				answer = answerEle.getAnswer(); // saves the answer

				if (controlModel.getSelectedUser() != null
						|| (answerEle.getAnswer() != null && answerEle.getAnswer().equals("Yes"))) {

					recipient = controlModel.getSelectedUser(); // saves the
																// selected user

					/**
					 * first check if the answer is Yes - the game starts
					 */
					if (answer != null && answer.equals("Yes")) {

						count = true; // this variable prevents from infinity
										// loops
						server.println(opp.getOpponent()); // sends the opponent
															// to the server
						server.println("Yes"); // sends Yes to the server

						model.setIsMyTurn(true); // show that it is this user's
													// turn - used to disable
													// the buttons when needed
						nottGUI.setVisibleGUI(true); // set the game GUI to
														// visible because
														// initially it is false
						model.setInVisible(true); // indicates that the user is
													// in game

						// loop in this while when the user is in game
						while (model.isInVisible()) {

							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							if (!model.isPlaying()) {
								System.out.println("Second loop");
								String newOpponent = opp.getAdditionalOpponent();
								server.println(newOpponent);
								server.println("No");
								model.setPlaying(true);
							}

							/**
							 * if the game ended and there is a winner - prints
							 * the opponent to the server and sends Increase to
							 * the server which helps for increasing the score
							 */
							if (model.isUserWinner()) {
								model.setUserWinner(false);
								server.println(opp.getOpponent());
								server.println("Increase");
								JOptionPane.showMessageDialog(null, "Congratulations! " + nickname + " won!");
							}

							/**
							 * if a move is made sends the move to the server
							 */
							if (model.getTurn() != 0) {
								model.setIsMyTurn(false);
								String cellActiveInt = String.valueOf(model.getTurn());
								server.println(opp.getOpponent());
								server.println(cellActiveInt);
								model.setTurn(0);
							}
						}

						// if the user is not playing anymore the value of these
						// variables are set to their initial ones
						answer.equals(null);
						controlModel.setSelectedUser(null);
						answerEle.setAnswer(null);
						nottGUI.setVisibleGUI(false);
						rsp.setRespond("");
						server.println(opp.getOpponent());
						server.println("Exit");

					} else if (count) { // if the answer is not Yes then the
										// server receives the selected opponent
						server.println(recipient);
						server.println("NotYes");
						count = false;
					}
				}
			}

			/**
			 * When the respond is Yes the user loops in the below while, but
			 * first sets his turn to false, the visibility of the game GUI to
			 * true and indicates that he is playing
			 */

			model.setIsMyTurn(false);
			nottGUI.setVisibleGUI(true);
			model.setInVisible(true);

			while (rsp.getRespond().equals("Yes") && model.isInVisible()) {

				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (!model.isPlaying()) {
					System.out.println("Second loop");
					String newOpponent = opp.getAdditionalOpponent();
					server.println(newOpponent);
					server.println("No");
					model.setPlaying(true);
				}

				/**
				 * if the game ended and there is a winner - prints the opponent
				 * to the server and sends Increase to the server which helps
				 * for increasing the score
				 */

				if (model.isUserWinner()) {
					System.out.println("Second");
					model.setUserWinner(false);
					server.println(opp.getOpponent());
					server.println("Increase");
					JOptionPane.showMessageDialog(null, "Congratulations! " + nickname + " won!");
				}

				/**
				 * if a move is made sends the move to the server
				 */
				if (model.getTurn() != 0) {
					model.setIsMyTurn(false);
					String cellActiveInt = String.valueOf(model.getTurn());
					server.println(opp.getOpponent());
					server.println(cellActiveInt);
					model.setTurn(0);
				}
			}

			// if the user is not playing anymore the value of these variables
			// are set to their initial ones
			controlModel.setSelectedUser(null);
			answerEle.setAnswer(null);
			rsp.setRespond("");
			nottGUI.setVisibleGUI(false);
			server.println(opp.getOpponent());
			server.println("Exit");

		}
	}
}
