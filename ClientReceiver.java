import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

// Gets messages from other clients via the server (by the
// ServerSender thread).

public class ClientReceiver extends Thread {

	private BufferedReader server;
	private NoughtsCrossesModel model;
	private Opponent opp;
	private Respond rsp;
	private Answer answerEle;
	private ControlPanelModel controlModel;
	private boolean count = true;
	private String question = "Do you want to play?";

	/**
	 * 
	 * @param server - the server
	 * @param model - model of the game where all the methods can be found
	 * @param opp - keep track of the opponent
	 * @param rsp - save the respond
	 * @param controlModel - model for the control panel
	 * @param answerEle - answer 
	 */
	ClientReceiver(BufferedReader server, NoughtsCrossesModel model, Opponent opp, Respond rsp,
			ControlPanelModel controlModel, Answer answerEle) {
		this.server = server;
		this.model = model;
		this.opp = opp;
		this.rsp = rsp;
		this.answerEle = answerEle;
		this.controlModel = controlModel;

	}

	public void run() {
		try {
			while (true) {

				// Saves the first value that is sent
				String s = server.readLine();

				/*
				 * if message contains Yes - the respond of the opponent is set
				 * to Yes
				 */
				if (s.contains("Yes")) {
					rsp.setRespond("Yes");
					String oppS = server.readLine();
					opp.setOpponent(oppS);

				}
				/*
				 * if message is No - the chosen user is set to null - the
				 * answer is set to No
				 */
				else if (s.equals("No")) {
					controlModel.setSelectedUser(null);
					answerEle.setAnswer("No");
					JOptionPane.showMessageDialog(null, "Your opponent is in game or refused the offer to play!");

				}
				/*
				 * if message contains GetUsers - update the users
				 */
				else if (s.contains("GetUsers")) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String users = server.readLine();
					controlModel.setUsersString(users); // transform the string
														// users to an array
					controlModel.setUsers(); // set the new values of this array
					controlModel.setScoreboard(false);

				}
				/*
				 * if message is Scoreboard update the scoreboard
				 */
				else if (s.equals("Scoreboard")) {
					controlModel.setScoreboardUsers(server.readLine()); // transform
																		// the
																		// string
																		// scoreboard
																		// to an
																		// array
					controlModel.setScoreboardArray(); // set the new values of
														// this array
				}
				/*
				 * if message is Exit the user mustn't be playing
				 */
				else if (s.equals("Exit")) {
					model.setInVisible(false);

				}
				/*
				 * if the message is not a specific one
				 */
				else {

					String oppS = server.readLine(); // read the opponent form
														// the server
					if (!model.isInVisible()) { // the user is not in game - set
												// the opponent
						if (!oppS.equals("Already given.")) {
							this.opp.setOpponent(oppS);
						}
					}

					/*
					 * if the message is not a question, then it is a move and
					 * we play it
					 */
					if (s != null && !s.contains(question)) {
						count = true;
						try {

							model.turn(Character.getNumericValue(s.charAt(0)) - 1,
									Character.getNumericValue(s.charAt(1)) - 1);
							model.setIsMyTurn(true);

							if (model.whoWon() != 0) { // if the game ended with
														// a winner

								model.setInVisible(false);
								JOptionPane.showMessageDialog(null, "You lost!");

							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}

					}
					/*
					 * if the message is a question
					 */
					else if (s.contains(question)) {

						if (!model.isInVisible()) {

							int response = infoBox("You have been challenged by " + oppS); // Pop-up
																							// message
																							// to
																							// the
																							// opponent

							// yes answer
							if (response == JOptionPane.YES_OPTION) {
								answerEle.setAnswer("Yes"); // set the answer to
															// yes
								controlModel.setSelectedUser("User"); // set a
																		// default
																		// user
																		// in
																		// order
																		// to
																		// continue
																		// game
							}
							// no answer
							else if (response == JOptionPane.NO_OPTION) {
								opp.setOpponent(oppS); // set the opponent
								controlModel.setSelectedUser(null); // make the
																	// chosen
																	// user null
								answerEle.setAnswer("No"); // set the answer to
															// No
								controlModel.setAnswerNo(true);
							}

						} else {
							
							opp.setAdditionalOpponent(oppS);
							model.setPlaying(false);
						}

					} else {
						server.close(); // Probably no point.
						throw new IOException("Got null from server"); // Caught
																		// below.
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Server seems to have died " + e.getMessage());
			System.exit(1); // Give up.
		}
	}

	/**
	 * infoBox for the pop up
	 * 
	 * @param infoMessage
	 * @return yes or no pop up
	 */
	public static int infoBox(String infoMessage) {
		return JOptionPane.showConfirmDialog(null, infoMessage, "Request to play.", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
	}
}
