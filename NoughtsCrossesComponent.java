import javax.swing.JPanel;
import java.awt.BorderLayout;

public class NoughtsCrossesComponent extends JPanel
{
	public NoughtsCrossesComponent(NoughtsCrossesModel model)
	{
		super();
		
		BoardView board = new BoardView(model); // sets the BoardView to the component
		
		model.addObserver(board);
		
		setLayout(new BorderLayout());
		
		add(board, BorderLayout.CENTER);
	}
}
