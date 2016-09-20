import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<Team> allTeams = new ArrayList<Team>();
		
		
		// ---------   test init and team creation -----------
		
		Helper hlp = new Helper();
		allTeams=hlp.createTeams(); //create teams
		hlp.createMatchList(allTeams); //create match list
		

	}

}
