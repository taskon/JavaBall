/**
 * Holds the main GUI functionality:
 * 
 *  - Load a TeamsIn file
 *  - Load a Results file
 *  - 
 * 
 * **/

import java.awt.*;
import java.awt.event.*;
import java.io.Console;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI extends JFrame {
    
    /* Frame width and height */
    private int appWidth;
    private int appHeight;
    
    /* LAYOUTS *****/
    GridLayout headerLayout = new GridLayout(1,1);
    GridLayout deleteTeamLayout = new GridLayout(2,2);
    GridLayout Actions1Layout = new GridLayout(1,2);
    GridLayout resultLayout = new GridLayout(1,1);
    GridLayout Actions2Layout = new GridLayout(1,2);
    
    /* ---------------------- GUI Components ------------------------ */
    
    /* LABELS *****/
    private JLabel lbHeader,lblDeleteTeam,
                               lblStatus;
    
    /* BUTTONS *****/
    private JButton saveResultsButton,exitButton,loadButton,deleteButton; 
    
    /* FONTS - COLORS *****/
    private Font TextFont = new Font("serif", Font.BOLD, 12);
    private Font headerFont1 = new Font("Helvetica",Font.BOLD, 20);
    private Font headerFont2 = new Font("Times",Font.PLAIN, 18);
    private Color titleFColor = new Color(153,153,0);
    
    /* J LISTS ****/
    final DefaultListModel<String> teamNamesModel = new DefaultListModel();
    private JList teamsToDeleteList;
    
    /* TEXT AREA ****/
    private TextArea textAreaResults;
    private String newLine = "\n";
    
    /* BORDERS ****/
    private Border blackline = BorderFactory.createLineBorder(Color.orange);
    private Border greenLine = BorderFactory.createLineBorder(Color.green);
    private TitledBorder title = null;
    private Border compound,raisedbevel, loweredbevel;

    /* MESSAGES */
    private JOptionPane msg;

    
    /* MISC FIELDS */
    private String status = " ",type,reinfChoise[],orderID;
    private String selectedTeamForDeletion; // will hold the team chosen for deletion
    private int selectedTeamIndexForDeletion; // will hold the team's index in the Jlist chosen for deletion
    private int confirmation;
    private Dimension screenSize,frameSize;
    private boolean dataStatus,teamDeleted = true;
    private LinkedList<Team> teams = new LinkedList<Team>(); // will hold all the team objects
    private ArrayList<String> matchList = new ArrayList<String>(); //will hold all matches
    private ArrayList<String> allTeamNames; //will hold all team names
    
    
    
    /**
     * Creates a new instance of GUI
     */
    public GUI(String title) {
        super(title);
       
    }
    
    
    
    /**
     * Initalise:
     *  - Create Gui
     *  - Load Files 
     *  - Show Match List 
     *  
     */
    @SuppressWarnings("static-access")
	protected void init(){
    	
    	Helper hlp = new Helper();  
    	
    	/* load and prepare all necessary data */
    	try{
    		
    		teams = hlp.createTeams(); //create team list 
    		allTeamNames = hlp.createTeamNamesList(teams); // create allTeamNames list
    		matchList = hlp.createMatchList(teams); //create match list 
    		
    		/* create the GUI */
    		createGui();
    		showResults(matchList);
    	}
    	catch (FileNotFoundException e) { // the TeamsIn file cannot be found
    		
   		 	msg.showMessageDialog(null,"The input File name is incorrect or does not exist, Please try agai "
   		 			+ "The program will now exit!");
   		 	System.exit(1);//exit with status 1 as further clean up is needed (close bufferReader)
   	 }
    	
    }
    
    /**
     * Gets the dimensions of the user's screen and then
     * calculates and adjusts the size of the Jframe 
     */
    private void setWindow(){
        
      /* Get the dimensions of the screen and the frame */
      screenSize = Toolkit.getDefaultToolkit().getScreenSize();      
      
      /* and set it's size depending on the resolution of the users screen */
      if ( screenSize.width == 1024 && screenSize.height == 768 ){
          appWidth = 490;
          appHeight = 738;
      }
      else{
          appWidth = 490;
          appHeight = 894;
      }
      
      /* set the application size */
      this.setSize(appWidth,appHeight);
      
      /* For debuging purposes - get the dimensions of the application frame */
      //frameSize = getSize();
      
      int x = (screenSize.width - appWidth) / 2;
      int y = (screenSize.height - appHeight) / 2;
      
      /* center the window on the users screen */
      setLocation(x, y);
      this.setResizable(false);
  
    }
    
    /**
     * Creates the GUI  
     */
    @SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
     protected void createGui(){
        
    	
         
         String[] labels = {" Results Processing of JavaBall Matches","","","","","",
                               "","",""," ","Choose a team to Delete"};
         String[] printing = {null,"1","2","3"};
        
        //
        // Create GUI Components - Buttons
        //
               
       /* Load Results  Button */
        loadButton = new JButton("Load Results");
        //The action Listener - and the code that will handle the events
        loadButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               
                loadButton.setCursor(Cursor.getPredefinedCursor(
                                                        Cursor.WAIT_CURSOR));
                
                // disable deletion of teams
                deleteButton.setEnabled(false);
                teamsToDeleteList.setEnabled(false);
                /* load results file */
                //TODO: create method to load results from file

                loadButton.setCursor(Cursor.getPredefinedCursor(
                                                        Cursor.DEFAULT_CURSOR));

             }
        } );
        
        
        /* Delete teams Button */
        deleteButton = new JButton("Delete Team");
        deleteButton.setEnabled(false);
        //Add the action Listener - and the code that will handle the events
        deleteButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                                
                    /* delete teams */
                    deleteTeam();
                                     
            } 
        } );
        
        /* Save  Button */
        saveResultsButton = new JButton("Save Results");
        saveResultsButton.setEnabled(false);
        //The action Listener - and the code that will hamdle the events
        saveResultsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                if (saveResultsButton.isEnabled()){
                    
                    /* get final confirmation */
                        confirmation = JOptionPane.showConfirmDialog(null,
                                    "Are you sure you want to save "
                                     + "results entered?",
                                     "Final Confirmation",
                                     JOptionPane.YES_NO_OPTION);
                        
                        /* if the user chosses to proceed with the order */
                        if (confirmation == 0){                            
                            //TODO: add save method                                                 
                        }       
                }                     
            } 
            
        } );
        
        /* Exit  Button */
        exitButton = new JButton("Exit");
        //Add the action Listener - and the code that will handle the events
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                System.exit(0);
            } 
        } );
        
        //
        // Create GUI Componentns - JLists
        //
        
        /* A lList for choosing which team to delete */  
        
        for(int i=0; i<allTeamNames.size(); i++)  //fill the list
        	teamNamesModel.addElement(allTeamNames.get(i)); 
        
        teamsToDeleteList = new JList(teamNamesModel);  //associate with the model    
        teamsToDeleteList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        teamsToDeleteList.setVisible(true);
        /* and the Selection Listener */
        teamsToDeleteList.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            
        	// track selections
            selectedTeamForDeletion = (String) teamsToDeleteList.getSelectedValue();
            selectedTeamIndexForDeletion = teamsToDeleteList.getSelectedIndex();
            System.out.println("Selection from List's event-> "+selectedTeamForDeletion);
            // enable delete button as somethong was chosen
            deleteButton.setEnabled(true);
            

                
         }
        } );
                 
        //
        // Create GUI Componentns - Text Area
        //
        textAreaResults = new TextArea(7, 30);
        textAreaResults.setBackground(Color.WHITE);
        textAreaResults.setEditable(false);
        
        //add a scrollbar
        JScrollPane scrollPane = new JScrollPane(textAreaResults);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
         
        textAreaResults.setFont( new Font("Serif",Font.ITALIC, 16));
        //textAreaResults.setLineWrap(true);
        //textAreaResults.setWrapStyleWord(true);
        /* ------------------------------------------------------- */
        
        //
        //  Create - Populate the JPanels
        //
        
         /* Panel Header */
         JPanel panelHeader = new JPanel(); //create panel
         headerLayout.setHgap(3);
         headerLayout.setVgap(10);
         panelHeader.setLayout(headerLayout);
         
         lbHeader = new JLabel(labels[0]);
         lbHeader.setFont(headerFont1);
         panelHeader.add(lbHeader);
         /* ----------------------------------------------------- */
     
         /* Panel Results */
         JPanel panelResults = new JPanel();
         resultLayout.setVgap(3);
         resultLayout.setHgap(10);
         panelResults.setLayout(resultLayout);
         //set the Border and Title 
         title =  BorderFactory.createTitledBorder(blackline,
                                              "JavaBall Matches & Results",TitledBorder.TOP,
                                               TitledBorder.TOP,headerFont2
                                                                ,titleFColor);
         panelResults.setBorder(title);
         JScrollPane scrollPane2 = new JScrollPane(panelResults);
         panelResults.add(textAreaResults);
         /* ----------------------------------------------------- */
         
         /* Panel Delete Team  */
         JPanel panelDeleteTeam = new JPanel(); //create panel
         deleteTeamLayout.setHgap(3);
         deleteTeamLayout.setVgap(10);
         panelDeleteTeam.setLayout(deleteTeamLayout); 
         //set the Border and Title 
         title =  BorderFactory.createTitledBorder(blackline,
                                              "Delete Team",TitledBorder.LEFT,
                                               TitledBorder.TOP,headerFont2
                                                                ,titleFColor);
         
         panelDeleteTeam.setBorder(title);
         panelDeleteTeam.add(lblDeleteTeam = new JLabel(labels[10]));
         panelDeleteTeam.add(teamsToDeleteList);
         /* ----------------------------------------------------- */
         
         /* Panel Actions 1 */
         JPanel panelActions1 = new JPanel();
         Actions1Layout.setHgap(3);
         Actions1Layout.setVgap(10);
         panelActions1.setLayout(Actions1Layout);
         //set the Border 
         raisedbevel = BorderFactory.createRaisedBevelBorder();
         loweredbevel = BorderFactory.createLoweredBevelBorder();
         compound = BorderFactory.createCompoundBorder(
			  raisedbevel, loweredbevel);
         //add an extra line
         compound = BorderFactory.createCompoundBorder(
                                  greenLine, compound);
         
         panelActions1.setBorder(compound);
         panelActions1.add(loadButton);
         panelActions1.add(deleteButton);
         /* ----------------------------------------------------- */
         
         /* Panel Actions */
         JPanel panelActions2 = new JPanel();
         Actions2Layout.setHgap(3);
         Actions2Layout.setVgap(10);
         panelActions2.setLayout(Actions2Layout);
         //set the Border 
         raisedbevel = BorderFactory.createRaisedBevelBorder();
         loweredbevel = BorderFactory.createLoweredBevelBorder();
         compound = BorderFactory.createCompoundBorder(
			  raisedbevel, loweredbevel);
         //add an extra line
         compound = BorderFactory.createCompoundBorder(
                                  greenLine, compound);
         
         panelActions2.setBorder(compound);
         panelActions2.add(saveResultsButton);
         panelActions2.add(exitButton);
         /* ----------------------------------------------------- */
         
         
        //
        // Container and Layout 
        //
       
        /* Create the Container and assosiate a layout*/
        Container cp = this.getContentPane(); 
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        

        //
        // Place the Components into the Pane - Create the User Interface
        //
        
        cp.add(panelHeader);
        cp.add(new JSeparator());
        cp.add(panelDeleteTeam);
        cp.add(new JSeparator());
        cp.add(panelActions1);
        cp.add(new JSeparator());
        cp.add(panelResults);
        cp.add(new JSeparator());
        cp.add(panelActions2);
        cp.add(new JSeparator());

        /* Set the Size of the window and then Display it */       
        setWindow();
        this.setVisible(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * The method of the GUI class that will collect all data and then
     * validate them and if all is collected and valid returns true.
     */
    @SuppressWarnings("static-access")
	private boolean getData(){

         /* ======================= Collect Data =============================== */

        try {
        	
            // ******************* User Chosen a Team to Delete? and which ********************
            
            //int flag =0;
            // get the users selection which team to delete
            selectedTeamForDeletion = (String)teamsToDeleteList.getSelectedValue();
      
            dataStatus = (selectedTeamForDeletion != null) ? true : false; //check if OK
            
            
        }
        catch (Exception e ){ 
                        
            	msg.showMessageDialog(null, "Unexpected Error: " + e.toString(),
                        "ERROR", msg.ERROR_MESSAGE);
            
        }
        
        // ******************* Collect results entered by user ********************
        
        //TODO: Collect team match results entered by the user
        
        return dataStatus;

    }
    
    /**
     * Prints information about matches
     * 
     * @param ArrayList<String> a list containing all teams
     **/
    @SuppressWarnings("static-access")
	private void showResults(ArrayList<String> matchList){
    	
    	
    	 try{
        
      
    		 textAreaResults.append(" Match List and Results - Number of Matches: " + matchList.size()/2 + newLine);
    		 textAreaResults.append("-----------------------------------------------------------------------" + newLine);
    		 //textAreaResults.append(newLine);
    		 /* print the match list */
    		 for (int i=0; i < matchList.size(); i+=2){
    			 
    			 textAreaResults.append(matchList.get(i) + " V ");
    			 textAreaResults.append(matchList.get(i+1) + " ");
    			 textAreaResults.append(" * * * No Results Yet * * * " + newLine);
    			 
    		 }
    		 textAreaResults.append("-------------------------------------------------------------------------");
    		 textAreaResults.append(newLine);
        
    	 }
    	 catch(Exception e){
             msg.showMessageDialog(null,"There was an internal error",
                                   "JavaBall-Error!",msg.ERROR_MESSAGE);
             System.out.print(e.getMessage().toString());
         }        
    }
    
    /** 
     * This method will delete the teams chosen by the user
     */
    @SuppressWarnings({ "static-access", "unchecked" })
	private void deleteTeam(){
    	/*
    	ArrayList<String> test = new ArrayList<String>();
    	test.add("Bayern");
    	test.add("Neacastle");
    	test.add("Bayern");
    	test.add("Portsmouth");
    	test.add("Newcastle");
    	test.add("Portmouth");
    	*/
    	
    	
    	Helper hlp = new Helper();// contains all methods to search and delete 
    	boolean dataCollected, enoughTeams;
    	String temp=null; //will hold the team to be deleted 
    	
    	// check that all data has been collected and there are enough teams for the tournament
    	dataCollected = getData();
    	enoughTeams = (teamNamesModel.size() >=3) ? true : false;
    	         
        if ( dataCollected && enoughTeams ){ // if all data are collected and valid 
        	
        	/* get final confirmation */
            confirmation = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete: "
                         + selectedTeamForDeletion,
                         "Final Confirmation",
                         JOptionPane.YES_NO_OPTION);
            
            /* if the user chooses to proceed delete */
            if (confirmation == 0){
                         
            	if (hlp.searchDelete(teams, selectedTeamForDeletion)){ // success item was found and deleted
            		
            		/*  ---- Update everything ----- */
            		
            		temp = selectedTeamForDeletion;
            		msg.showMessageDialog(null, selectedTeamForDeletion + " was deleted.");
            		deleteButton.setEnabled(false);          		
   
            		//update the list of teams
            		teamNamesModel.remove(selectedTeamIndexForDeletion); 
            		
            		//check for number of teams in the league
            		enoughTeams = (teamNamesModel.size() >=3) ? true : false;
            		
            		if (! enoughTeams){ // if not enough exit        			
            			msg.showMessageDialog(null,"Less than 3 teams remain - the league is canceled, "
               		 			+ "The program will now exit!");
               		 	System.exit(1);//exit with status 1 as further clean up is needed (close bufferReader)       			
            		}
            		
            		// create new match list and update update matchList
            		textAreaResults.setText(null);
            		showResults(hlp.updateMatchList(matchList, temp));
            	}
            	else
            		msg.showMessageDialog(null," Something went wrong.... nothing was changed, Please try again...");      	
            } 
        }
        else if ( !dataCollected ){	// no team chosen to delete
        	msg.showMessageDialog(null,"Please Choose a team to Delete....");
        }
    }
        
             
} // end class

