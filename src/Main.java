import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<Team> allTeams = new ArrayList<Team>();
		ArrayList<String> allMatches = new ArrayList<String>();		
		
		// ---------   test init and team creation -----------
		/*
		Helper hlp = new Helper();
		allTeams= hlp.createTeams();
		allMatches=hlp.createMatchList(allTeams);
		
		for (String match: allMatches){
			
			System.out.print(match);
			
		}
		
		*/
		
		GUI gui = new GUI("Java Ball"); // create a new interface
	    gui.init();// initilize it
	    
		

	}

}
