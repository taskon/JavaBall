import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Has functionality shared all through the application including:
 * 
 * - FIle I/O
 * - Creates Team objects
 * - 
 * 
 * */
public class Helper {
	
	/**
	 * Reads a text file line by line into a String and returns a String Array holding all the team names read from the file
	 * 
	 * @param String 
	 * @return String[]
	 * @throws FileNotFoundException 
	 * 
	 **/
	
	private String teamsIn="TeamsIn.txt";
	
	private String[] readInputFile(String inputFile) throws FileNotFoundException{ 
				
		String everything = null; //will hold the file contents
		String[] names = null; //will hold each team name
		BufferedReader br = new BufferedReader(new FileReader(inputFile)); // Exception is handled in the caller method
		
		
		try {
			
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				
				line = line.substring(0, Math.min(line.length(), 10)); // trim everything beyond length 10
				line = line.replaceAll("\\s+",""); // remove whitespace and non-visible characters 
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			//add all read content into a string that holds all valid team names
			everything = sb.toString();
			br.close();
			
			//break each line of the string into an array of strings
			names = everything.split(System.getProperty("line.separator"));
			
		}catch (IOException e) {
			System.out.println("Something went wrong");			
		}
		
		return names;

	}
	
	/**
	 * Creates and returns an ArrayList containing objects of Class Team. Each object 
	 * is created with a team name read from the input file and represents a single football team.
	 * 
	 * @return ArrayList<Team>
	 * @throws FileNotFoundException
	 * 
	 * */
	private ArrayList<Team> createTeams() throws FileNotFoundException{
		
		String[] names = null; //holds all the team names
		//Map<String, Team> teams = new HashMap<>();
		ArrayList<Team> allTeams = new ArrayList<Team>(); //will hold all the team objects created from the names 
		
		// load team names from file into a string array
		names = readInputFile(teamsIn); // exception in hanled from the caller method at the GUI level
		
		int i = 0;
		// create team objects dynamically depending on the size of the array holding the names
		for (String name: names){
			allTeams.add(new Team(names[i]));
			i++;			
		}
			
		return allTeams;
						
	}

	/**
	 * Creates and returns an Alphabetically sorted ArrayList holding all matches
	 * 
	 * @return ArrayList<String> a list of all possible matches 
	 * @throws FilenotFoundException
	 **/
	public ArrayList<String> createMatchList() throws FileNotFoundException{
		
		ArrayList<Team> teams = new ArrayList<Team>(); // will hold all the team objects
		ArrayList<String> teamNames = new ArrayList<String>(); //will hold all team names
		ArrayList<String> allMatches = new ArrayList<String>(); //will hold all matches
		
		//create teams
		teams = createTeams();
		
		//create a list containing all the team names	
		for (Team team: teams){	
			teamNames.add(team.getName());
		}
		
		//sort it alphabetically
		Collections.sort(teamNames);
		
		//build a match list and print it
		for (int i = 0; i < teamNames.size(); i++) {
			
			for (int j = i+1; j < teamNames.size(); j++) {
								
				//create the match list
				allMatches.add(teamNames.get(i));
				allMatches.add(teamNames.get(j));
				
				//print the match list
				System.out.print(teamNames.get(i) + " Vs ");
				System.out.print(teamNames.get(j));
				System.out.println("     * * * No Resutls Yet * * * ");
				
			}			
		}
		
		return allMatches;		
	}
	
}
		

