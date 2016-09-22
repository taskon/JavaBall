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
    //GridLayout QuantityLayout = new GridLayout(1,2);
    GridLayout boxSizeLayout = new GridLayout(3,2);
    //GridLayout gradeOfCardLayout = new GridLayout(1,2);
    //GridLayout PrintingLayout = new GridLayout(2,2);
    GridLayout deleteTeamLayout = new GridLayout(2,2);
    //GridLayout SealableLayout = new GridLayout(1,2);
    GridLayout Actions1Layout = new GridLayout(1,2);
    GridLayout resultLayout = new GridLayout(1,1);
    GridLayout Actions2Layout = new GridLayout(1,2);
    
    /* ---------------------- GUI Components ------------------------ */
    
    /* LABELS *****/
    private JLabel lblCustName,lbHeader,lblBoxSize,lblGradeOfCard,
                               lblPrinting,lblTypeOfPrint,lblDeleteTeam,
                               lblSealable,lblStatus,lblWidth,lblHeight,
                               lblLength,lblTypeOfPrinting,lblPlaceHolder,
                               lblChooseQuantity;
    
    /* BUTTONS *****/
    private JButton loadTeams,exitButton,calculateButton,delete; 
    
    /* FONTS - COLORS *****/
    private Font TextFont = new Font("serif", Font.BOLD, 12);
    private Font headerFont1 = new Font("Helvetica",Font.BOLD, 20);
    private Font headerFont2 = new Font("Times",Font.PLAIN, 18);
    private Color titleFColor = new Color(153,153,0);
    
    /* COMBO BOX *****/
    private JComboBox chooseGrade,choosePrinting;
    
    /* TEXT FIELDS *****/
    private JTextField txtWidth,txtHeight,txtLength,txtQuantity;
    
    /* RADIO BUTTONS *****/
    private JRadioButton radioYesP,radioNoP,radioYesS,radioNoS,radioYesR,radioNoR;
    
    /* RADIO BUTTON GROUPS *****/
    private ButtonGroup groupForPrinting,groupForSealable,groupForReinforcing;
    
    /* J LISTS ****/
    final DefaultListModel<String> teamNamesModel = new DefaultListModel();
    private JList teamsToDelete;
    
    /* TEXT AREA ****/
    private TextArea textAreaResults;
    private String newLine = "\n";
    
    /* BORDERS ****/
    private Border blackline = BorderFactory.createLineBorder(Color.orange);
    private Border greenLine = BorderFactory.createLineBorder(Color.green);
    private TitledBorder title = null;
    private Border compound,raisedbevel, loweredbevel;

    /* SLIDERS */
    private JSlider SlChooseQuantity;
    private int boxMin = 0;
    private int boxMax = 100;
    private int boxInit = 0;

    /* MESSAGES */
    private JOptionPane msg;

    /* BOX MODELING FIELDS */
    private double width,height,length;
    private int grade,typeOfPrint,boxQuantity;
    private boolean colourPrint,reinforcedBottom = false,sealableTops,
                reinforcedCorners = false,reinforcment = false;
    private String boxType;
    
    /* MISC FIELDS */
    private String status = " ",type,reinfChoise[],orderID;
    private String selectedTeamForDeletion; // will hold the team chosen for deletion
    private int selectedTeamIndexForDeletion; // will hold the team's index in the Jlist chosen for deletion
    private int confirmation;
    private Dimension screenSize,frameSize;
    private boolean dataStatus,teamDeleted = true;
    private double boxCost,orderCost;
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
    		
   		 	msg.showMessageDialog(null,"The filename or directory name is incorrect or does not exist, "
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
        
    	
         
         String[] labels = {" Results Processing of JavaBall Matches","Box Size","Width","Height","Length","Grade Of Card",
                               "Sealable","Status","Customer Name"," ","Choose a team to Delete"};
         String[] printing = {null,"1","2","3"};
        
        //
        // Create GUI Components - Buttons
        //
        
        /* Exit  Button */
        exitButton = new JButton("Exit");
        //Add the action Listener - and the code that will handle the events
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                System.exit(0);
            } 
        } );

        
       /* Calculate  Button */
        calculateButton = new JButton("Calculate");
        //The action Listener - and the code that will handle the events
        calculateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               
                calculateButton.setCursor(Cursor.getPredefinedCursor(
                                                        Cursor.WAIT_CURSOR));
                /* calculate the order */
                //calculate();

                calculateButton.setCursor(Cursor.getPredefinedCursor(
                                                        Cursor.DEFAULT_CURSOR));

             }
        } );
        
        
        /* Delete teams Button */
        delete = new JButton("Delete Team");
        delete.setEnabled(false);
        //Add the action Listener - and the code that will hamdle the events
        delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                                
                    /* delete teams */
                    deleteTeam();
                                     
            } 
        } );
        
        /* Confirm Order  Button */
        loadTeams = new JButton("Confirm Order");
        loadTeams.setEnabled(false);
        //The action Listener - and the code that will hamdle the events
        loadTeams.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                if (loadTeams.isEnabled()){
                    
                    /* get final confirmation */
                        confirmation = JOptionPane.showConfirmDialog(null,
                                    "Are you sure you want to proceed with the "
                                     + "order?",
                                     "Final Confirmation",
                                     JOptionPane.YES_NO_OPTION);
                        
                        /* if the user chosses to proceed with the order */
                        if (confirmation == 0){
                            
                            teamDeleted = true;
                                                 
                        }       
                }                     
            } 
            
        } );
        
        //
        // Create GUI Componentns - JLists
        //
        
        /* A lList for choosing which team to delete */  
        
        for(int i=0; i<allTeamNames.size(); i++)  //fill the list
        	teamNamesModel.addElement(allTeamNames.get(i)); 
        
        teamsToDelete = new JList(teamNamesModel);  //associate with the model    
        teamsToDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        teamsToDelete.setVisible(true);
        /* and the Selection Listener */
        teamsToDelete.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            
        	// track selections
            selectedTeamForDeletion = (String) teamsToDelete.getSelectedValue();
            selectedTeamIndexForDeletion = teamsToDelete.getSelectedIndex();
            System.out.println("Selection from List's event-> "+selectedTeamForDeletion);
            // enable delete button as somethong was chosen
            delete.setEnabled(true);
            

                
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
         panelDeleteTeam.add(teamsToDelete);
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
         panelActions1.add(calculateButton);
         panelActions1.add(delete);
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
         panelActions2.add(loadTeams);
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
     * this Method Calculates all the values entered by the user 
     * calculates the correctnes of the order. Is used in the 
     * Calculate button and the next order button
     */
    private boolean calculate(){
        
        boolean temp;
        
        /* collect the data */
        boolean ok = getData();
        boolean flag;
        
        /* if the getData method returned true, create the Box,
         * display the status
         * and proceed with the order
         */
        if (ok){
            
            if (teamDeleted){
                
                /* create the Order */
                //order = new Order();               
            }
            
            
            /* create the box */
            
            
            /* check the returned type */
            if (boxType.equals("0")){ // NOT A VALID BOX
                
                msg.showMessageDialog(null, "This Type of box cannot " +
                        " be created.Please choose another type!",
                        "Warning", msg.WARNING_MESSAGE);
                
            } else{  // Box type is valid ( 1 to 6 )
                
                /* get the cost of the Box */
               // boxCost = box.resolveCost();
                
                /* show the status to the user */
                //showStatus(boxType,boxCost,orderCost,OrderID);
                
                
                /* enable the place order button */
                loadTeams.setEnabled(true);
                
            }
            
            temp =  true;           
        }
        else{
            
            temp = false;
        }
        
        return temp;
        
    }
    
    
    /**
     * The method of the GUI class that will collect all data and then
     * validate them and if all is collected and valid returns true.
     */
    @SuppressWarnings("static-access")
	private boolean getData(){

         /* ======================= Collect Data =============================== */

        try {
        	
            // ******************* User Chosen a Team to Delete? ********************
            
            //int flag =0;
            // get the users selection which team to delete
            selectedTeamForDeletion = (String)teamsToDelete.getSelectedValue();
      
            dataStatus = (selectedTeamForDeletion != null) ? true : false; //check if OK
            
            
        }
        catch (Exception e ){ 
                        
            	msg.showMessageDialog(null, "Unexpected Error: " + e.toString(),
                        "ERROR", msg.ERROR_MESSAGE);
            
        }
        
        
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
     * This method check's if width - height - and length are valid (between 10 cm and 1 meters)
     * and if they are not throws a custom exception ( InvalidInputException )
     *
     * The validation of empty fields that are required and non nueric input 
     * is done using NumberFormatException.  
     */
    private void checkW_H_L(double width,double height,double length)
                                               //  throws InvalidInputException
    { 
    
        
        // check the correct boundaries of the user input
        Boolean invalidW_H_L = ( ( width < 10 || width > 200 )  ||
                                   ( height < 10 || height > 200 ) || 
                                   ( length < 10 || length > 200 ) ) ? true : false;
        
        // if they are invalid throw a custom exception
      //  if ( invalidW_H_L )
          //  throw new InvalidInputException();
    
    }
    
    /** 
     * This method will delete the teams chosen by the user
     */
    @SuppressWarnings({ "static-access", "unchecked" })
	private void deleteTeam(){
    	
    	Helper hlp = new Helper();// contains all methods to search and delete 
    	         
        if ( getData() ){ // if all data are collected and valid 
        	
        	/* get final confirmation */
            confirmation = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete: "
                         + selectedTeamForDeletion,
                         "Final Confirmation",
                         JOptionPane.YES_NO_OPTION);
            
            /* if the user chooses to proceed delete */
            if (confirmation == 0){
                         
            	if (hlp.searchDelete(teams, selectedTeamForDeletion)){ // success
            		
            		msg.showMessageDialog(null, selectedTeamForDeletion + " was deleted.");
            		delete.setEnabled(false);          		
            		// TODO: Update everything
            		//update the list of teams
            		teamNamesModel.remove(selectedTeamIndexForDeletion); 
            		
            		
            		
            		// TODO: update matchList
            		//showResults(matchList); ---------------- with the new matchlist
            	}
            	else
            		msg.showMessageDialog(null," Something went wrong.... nothing was changed, Please try again...");      	
            } 
        }
        else{	
        	msg.showMessageDialog(null,"Please Choose a team to Delete....");
        }             
    }
        
             
} // end class

