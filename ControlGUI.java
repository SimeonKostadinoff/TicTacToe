import javax.swing.JFrame;

public class ControlGUI implements Runnable{
	
	ControlPanelModel model;
	
	/*
	 * uses the model for the control panel
	 */
	public ControlGUI(ControlPanelModel model){
		this.model = model;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		ControlPanelComponent comp = new ControlPanelComponent(model);
		
		// creates the frame and add the component (with the buttons)
		JFrame frame = new JFrame();
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(model.getNickname() + "' s lobby");
		
		frame.add(comp);
		
		frame.setVisible(true);
		
	}
}
