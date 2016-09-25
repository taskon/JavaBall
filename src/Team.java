/**
 * Represents a Team taking part in the tournament holds information regarding 
 * team name, goals scored, matches won.
*
 * */
public class Team {
	
	private String name;
	private int wins; // indicates the matches the team won
	
	// constructor for class team
	Team(String name){
		
		this.name=name; 	
	}
	
	// ----------------- set methods -----------
	
	/**
	 * Sets the matches won
	 * */
	public void setWon(String name){
		this.wins++;
	}
	
	// --------------  get methods ---------------
	
	/**
	 * Gets the name of the team
	 * */
	
	public int getWins(){
		return this.wins;
	}
	
	/**
	 * Gets the name of the team
	 * */
	
	public String getName(){
		return this.name;
	}	
	
		
}
