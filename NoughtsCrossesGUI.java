import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class NoughtsCrossesGUI implements Runnable
{
	NoughtsCrossesModel model;
	private JFrame frame;
	
	public NoughtsCrossesGUI(NoughtsCrossesModel model){
		this.model = model;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		NoughtsCrossesComponent comp = new NoughtsCrossesComponent(model);
		
		// create the frame for the game
		frame = new JFrame("Noughts and Crosses");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Hiding the frame, not closing it
		
		/*
		 * add event to check if the window had changed condition
		 */
		frame.addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent e) {
				
				model.setInVisible(false); // set the user not in game
			}
		});
		
		
		frame.add(comp);
		
		frame.setVisible(false);	
		
	}
	
	/*
	 * set the visibility of the GUI - show the frame and restart the game
	 */
	public void setVisibleGUI(boolean visible){
		frame.setVisible(visible);
		model.newGame();
	}
	public boolean getVisibleGUI(){
		return frame.isVisible();
	}
	
	
}
