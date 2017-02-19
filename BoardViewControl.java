import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class BoardViewControl extends JPanel implements Observer{
	
	private ControlPanelModel model;
	private JButton connect, refresh, scoreboard;
	private DefaultListModel<String> defaultList;
	private JList<String> list;
	private JPanel panel;
	
	/**
	 * sets the buttons and adds events to them
	 * @param model
	 */
	public BoardViewControl(ControlPanelModel model){
		
		super();
		this.model = model;
		
		panel = new JPanel(); // JPanel for the buttons
		connect = new JButton("Connect"); // connect button
		refresh = new JButton("Refresh"); // refresh button
		scoreboard = new JButton("Scoreboard"); // scoreboard button
		
		defaultList = new DefaultListModel<>(); // JList to show the users 
		list = new JList<String>(defaultList);
		
		/*
		 * get the elements from an array and list them in the JList
		 */
		refresh.addActionListener(e -> {
			model.setUsersSend(true);
			try {
				//wait to receive the required information.
				Thread.sleep(1000); 
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			defaultList.removeAllElements();
			for(int i = 0; i < model.getUsersCount(); i++){
				defaultList.addElement(model.getUser(i));
			}
		});
		
		/*
		 * selects the current user 
		 */
		connect.addActionListener(e -> {
			String selectedUser = list.getSelectedValue();
			model.setSelectedUser(selectedUser);
		});
		
		/*
		 * gets the scoreboard array and list all users results in a JList
		 */
		scoreboard.addActionListener(e -> {
			model.setScoreboard(true);
			try {
				//wait to receive the required information.
				Thread.sleep(1000); 
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			defaultList.removeAllElements();
			for(int i = 0; i < model.getScoreboardArraySize(); i++){
				defaultList.addElement(model.getScoreboardUser(i));
			}
		});
		
		panel.add(connect);
		panel.add(refresh);
		panel.add(scoreboard);
		
		setLayout(new BorderLayout());
		
		add(list);
		add(panel, BorderLayout.NORTH);
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		repaint();
		
	}

}
