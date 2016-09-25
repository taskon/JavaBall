import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Has functionality shared all through the application including:
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
	 * @return LinkedList<Team>
	 * @throws FileNotFoundException
	 * 
	 * */
	public LinkedList<Team> createTeams() throws FileNotFoundException{
		
		String[] names = null; //holds all the team names
		//Map<String, Team> teams = new HashMap<>();
		LinkedList<Team> teams = new LinkedList<Team>(); //will hold all the team objects created from the names 
		
		// load team names from file into a string array
		names = readInputFile(teamsIn); // exception in hanled from the caller method at the GUI level
		
		int i = 0;
		// create team objects dynamically depending on the size of the array holding the names
		for (String name: names){
			teams.add(new Team(names[i]));
			i++;			
		}
			
		return teams;
						
	}
	
	/**
	 * Creates and returns an Alphabetically sorted ArrayList holding all matches
	 * 
	 * @param LinkedList<Team> an arrayList holding team objects
	 * @return ArrayList<String> a list of all possible matches 
	 * @throws FilenotFoundException
	 **/
	public ArrayList<String> createMatchList(LinkedList<Team> teams) throws FileNotFoundException{
		
		ArrayList<String> allTeamNames = new ArrayList<String>(); //will hold all team names
		ArrayList<String> allMatches = new ArrayList<String>(); //will hold all matches
		
		allTeamNames = createTeamNamesList(teams); //create a list of all team names for use in the creation of the match list 
		
		//build a match list and print it
		for (int i = 0; i < allTeamNames.size(); i++) {
			
			for (int j = i+1; j < allTeamNames.size(); j++) {
								
				//create the match list
				allMatches.add(allTeamNames.get(i));
				allMatches.add(allTeamNames.get(j));
				
				//print the match list
				/*
				System.out.print(teamNames.get(i) + " Vs ");
				System.out.print(teamNames.get(j));
				System.out.println("     * * * No Resutls Yet * * * ");
				*/
			}			
		}
		
		return allMatches;		
	}
	
	/**
	 * Creates an alphabetically sorted ArrayList of all teams names from the list containing all team objects
	 * 
	 * @param LinedList<Team>
	 * @return ArrayList<String> all team names 
	 * */
	public ArrayList<String> createTeamNamesList(LinkedList<Team> teams){
		
		ArrayList<String> allTeamNames = new ArrayList<String>(); //will hold all team names
		
		//create a list containing all the team names	
		for (Team team: teams){	
			
			allTeamNames.add(team.getName());
		}
		
		//sort it alphabetically
		Collections.sort(allTeamNames);
		
		
		return allTeamNames;
		
	}
	
	/**
	 * Finds and removes from the linked list the String provided as a parameter.
	 * It uses an iterator rather than a extented for loop in order to keep iterating after the remove,
	 * without a ConcurrentModificationException.
	 * 
	 * This method assumes that the linked list contains only one occurrence of the object, i.e. valid input file that contained
	 * each team name once. Returns true of false.
	 * 
	 * @param LinkedList<Team> the list 
	 * @param String the serach term
	 * @return boolean found or not
	 * 
	 * */
	public boolean searchDelete(LinkedList<Team> teams, String searchTerm){
		
		Iterator<Team> it = teams.iterator();
		
		if (teams != null){
			
			while(it.hasNext()){
				
			    if(it.next().getName().contains(searchTerm)){	
			    	
			        it.remove();
			        //break;
			        return true; //works like break;
			        
			   }			    
			} 
		}	
		return false; //not found		
	}
	
	/**
	 * Update Match List by deleting all matches of the team that was removed by the user. The indexes in which this
	 * team is found ( along with the next index which is the team's rival in the specific match) are stored in a list
	 * then this list is sorted in descending order (this way the indices that will be removed in the match list are not affected by shifting)
	 * and finally the they are removed from the array list holding all matches 
	 * one by one.  
	 * 
	 * */
	public ArrayList<String> updateMatchList( ArrayList<String> matchList, String termToRemove ){
		
		List<Integer> indices = new ArrayList<Integer>(); //will store the pairs of indices for deletion
		
		/* search for all matches which include the team to delete and store them in indices*/	
		for (int i=0;i<matchList.size();i++){
			
			if (matchList.get(i).contains(termToRemove)){ 
				 
				 indices.add(i);
				 indices.add(i+1);
				 
			 }
		}
		// sort in descending order so as not to affect the matchList when the specific indices get deleted
		Collections.sort(indices, Collections.reverseOrder()); 
		
		//remove the all the indices from matchlist
		for (int i : indices)
		    matchList.remove(i);	
		
		return matchList;
		
	}
	
	/**
	 * Extracts goals scored from the ResultsIn txt file 
	 * 
	 * */
	
	//TODO
	
	
}
		

