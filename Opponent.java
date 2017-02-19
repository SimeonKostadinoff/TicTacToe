/*
 * Opponent class that tracks the behaviour of the user and the opponent it had choosen
 */
public class Opponent {
	private String opp = "";
	private String additionalOpponent = "";
	
	public Opponent (){
		
	}
	
	public String getOpponent() {
		return this.opp;
	}
	
	public void setOpponent(String opp) {
		this.opp = opp;
	}

	public String getAdditionalOpponent() {
		return additionalOpponent;
	}

	public void setAdditionalOpponent(String additionalOpponent) {
		this.additionalOpponent = additionalOpponent;
	}
}
