import java.awt.BorderLayout;

import javax.swing.JPanel;

public class ControlPanelComponent extends JPanel {
	
	/*
	 * get the BoardViewControl functionality to the component
	 */
	public ControlPanelComponent(ControlPanelModel model){
		super();
		
		BoardViewControl board = new BoardViewControl(model);
		
		model.addObserver(board);
		
		setLayout(new BorderLayout());
		
		add(board, BorderLayout.CENTER);
	}

}
