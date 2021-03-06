import java.util.Observable;

public class NoughtsCrossesModel extends Observable
{
	private NoughtsCrosses oxo;
	
	
	public NoughtsCrossesModel(NoughtsCrosses oxo)
	{
		super();
		this.oxo = oxo;
	}
	
/**
Get symbol at given location
@param i the row
@param j the column
@return the symbol at that location
*/
	public int get(int i, int j)
	{
		return oxo.get(i, j);
	}

	
/**
Is it cross's turn?
@return true if it is cross's turn, false for nought's turn
*/	
	public boolean isCrossTurn()
	{
		return oxo.isCrossTurn();
	}

/**
Let the player whose turn it is play at a particular location
@param i the row
@param j the column
*/
	public void turn(int i, int j)
	{
		oxo.turn(i, j);
		//changeMove.setChangeMove(String.valueOf(i) + String.valueOf(j));
		//System.out.println(changeMove.getChangeMove());
		setChanged();
		notifyObservers();
	}
	
/**
Determine who (if anyone) has won
@return CROSS if cross has won, NOUGHT if nought has won, oetherwise BLANK
*/
	public int whoWon()
	{
		return oxo.whoWon();
	}
	
	
/**
Start a new game
*/
	public void newGame()
	{
		oxo.newGame();
		setChanged();
		notifyObservers();
	}
	
	public boolean getIsMyTurn(){
		return oxo.getIsMyTurn();
	}
	
	public void setIsMyTurn(boolean turn) {
		oxo.setIsMyTurn(turn);
		setChanged();
		notifyObservers();
	}
	
	public int getTurn(){
		return oxo.getTurn();
	}
	
	public void setTurn(int turn) {
		oxo.setTurn(turn);
		setChanged();
		notifyObservers();
	}
	
	public boolean isInVisible() {
		return oxo.isInVisible();
	}


	public void setInVisible(boolean inVisible) {
		oxo.setInVisible(inVisible);
		setChanged();
		notifyObservers();
	}
	
	public boolean isUserWinner() {
		return oxo.isUserWinner();
	}
	
	public void setUserWinner(boolean userWinner){
		oxo.setUserWinner(userWinner);
		setChanged();
		notifyObservers();
	}
	
	public boolean isPlaying() {
		return oxo.isPlaying();
	}

	public void setPlaying(boolean isPlaying) {
		oxo.setPlaying(isPlaying);
		setChanged();
		notifyObservers();
	}
}