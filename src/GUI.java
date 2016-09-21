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
    //GridLayout ReinforcingLayout = new GridLayout(2,2);
    //GridLayout SealableLayout = new GridLayout(1,2);
    GridLayout Actions1Layout = new GridLayout(1,2);
    GridLayout resultLayout = new GridLayout(1,1);
    GridLayout Actions2Layout = new GridLayout(1,2);
    
    /* ---------------------- GUI Components ------------------------ */
    
    /* LABELS *****/
    private JLabel lblCustName,lbHeader,lblBoxSize,lblGradeOfCard,
                               lblPrinting,lblTypeOfPrint,lblReinforcing,
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
    
    /* LISTS ****/
    private JList LstReinforcing;
    
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
    private int selection,confirmation;
    private Dimension screenSize,frameSize;
    private boolean dataStatus,firstOrder = true;
    private double boxCost,orderCost;
    private LinkedList<Team> teams = new LinkedList<Team>(); // will hold all the team objects
    private ArrayList<String> matchList = new ArrayList<String>(); //will hold all matches
    
    
    
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
    	
    	
    	/* create the teams and the match list */
    	Helper hlp = new Helper(); 
    	
    	/* create the GUI */
    	createGui();
    	/* show initial results */
    	try{
    		
    		teams = hlp.createTeams(); //create teams
    		matchList = hlp.createMatchList(teams); //create match list 
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
    @SuppressWarnings("static-access")
     protected void createGui(){
        
    	
         
         String[] Labels = {" Results Processing of JavaBall Matches","Box Size","Width","Height","Length","Grade Of Card",
                               "Printing","Yes","No","Type Of Printing","Choose Reinforcing",
                               "Sealable","Status","Customer Name"," ","Choose Box Quantity"};
         String[] Printing = {null,"1","2","3"};
         
        
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
        delete = new JButton("Order");
        delete.setEnabled(false);
        //Add the action Listener - and the code that will hamdle the events
        delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                                
                    /* delete teams the order */
                    deleteTeams();
                                     
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
                            
                            firstOrder = true;
                                                 
                        }       
                }                     
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
         
         lbHeader = new JLabel(Labels[0]);
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
            
            if (firstOrder){
                
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

         /* =========== Collect Data ============= */

        try {

            /* collect all data to model the box */

            // ************ Width - Height - Length - Grade **************
            
            width = Double.parseDouble(txtWidth.getText());
            height = Double.parseDouble(txtHeight.getText());
            length = Double.parseDouble(txtLength.getText());
            
            /* Check if width - height - and length are valid (between 10 cm and 1 meters)
             * and if they are not throw a custom exception
             *
             * The validation of empty fields that are required and non nueric input 
             * is done using NumberFormatException.  
             */
            //checkW_H_L(width,height,length);
            
            grade = Integer.parseInt((String) chooseGrade.getSelectedItem());
            

            /*  -*************** Printing ***********************************
             * if there was a choice for Printing get the type of
             * printing the user requiers */
            if (radioYesP.isSelected()) {

                colourPrint = true;
                typeOfPrint = Integer.parseInt((String) choosePrinting.getSelectedItem());
            }

            /* ************************** Box Quantity ************************
             * If there was no box quantity set,a message is
             * dispayed and the value is set to 1 */
            if (boxQuantity == 0) {

                msg.showMessageDialog(null, "The Quantity was not set. " +
                        "Default Order size set to 1 Box!",
                        "Warning", msg.WARNING_MESSAGE);
                boxQuantity = 1;
                txtQuantity.setText("1");

            }

            //  ************************* Sealable ****************************
            if (radioYesS.isSelected()) {
                sealableTops = true;
            } else {
                sealableTops = false;
            }

            
            // ************************* Reinforcing **************************
            
            int flag =0;
            // get the selection of the user if he requires reinforcing or not
            if (radioYesR.isSelected()){   // if the user chose to have reinforcing
                     
                // get his selection from the list
                selection = LstReinforcing.getSelectedIndex();
                
                if (selection == -1) {  // if the index is -1 then there was no selection yet
                    
                    //No selection yet
                    flag = 1;
                }
                else { // There was a selection
                    
                    
                    reinforcment = true;
                    flag = 0;

                    // If the user chose more than one type of reinforcing
                    if (LstReinforcing.getSelectedIndices().length > 1){
                                               
                        /* In our case, because we have only 2 choices it is 
                         * safe to asume that he chose both.
                         * but in other cases we should use the code below to
                         * check all of the users selections -if more than one-
                         */  
                        reinforcedBottom = true;
                        reinforcedCorners = true;
                        
                        // get the users choices and store them in an array
                        /*Object temp[] = LstReinforcing.getSelectedValues();
                        for (int i=0;i<LstReinforcing.getSelectedIndices().length;i++)
                            reinfChoise[i] = (String)temp[i];*/
                                               
                    }
                    else{
                        // If the user chose one of 2 types of reinforcing 
                        switch (selection) {

                            case 0:{
                                reinforcedBottom = true;
                                reinforcedCorners = false;
                                break;
                            }
                            case 1:{
                                reinforcedCorners = true;
                                reinforcedBottom = false;
                                break;
                            }
                            default:{
                                reinforcedCorners = false;
                                reinforcedBottom = false;
                            }
                        } // Switch
                    }
                }
            }
            
            /* if the user chose to have Reinforcing but didn't choose what type
             * pop up a warning
             */
            if (flag == 1 && radioYesR.isSelected()) {
                
                msg.showMessageDialog(null, "The Reinforcing option was set, " +
                        "but a type of reinforcing was not chosen!",
                        "Warning", msg.WARNING_MESSAGE);
                reinforcment = false;
            }
            
            /* check if all requred data was 
             * completed for the order to take place
             */
            Boolean dimensionsOK  = ( width != 0  && height != 0 && length != 0 ) ? true : false;
            Boolean quantityOK = ( boxQuantity != 0 ) ? true : false;
            Boolean gradeOK = ( grade != 0 ) ? true : false;
            
            dataStatus = (dimensionsOK && quantityOK && gradeOK) ? true : false;
            
        } // Try
        catch (NumberFormatException nfe) {  // handling input errors (null , numeric format)

            status = nfe.toString();
            System.err.println(nfe);
            
            Boolean noInputW_H_L = ( status.equals("java.lang.NumberFormatException: empty String")) ? true : false;
            Boolean noInputGrade = (status.equals("java.lang.NumberFormatException: null")) ? true : false;
            Boolean nonNumericInput = (!(status.equals("java.lang.NumberFormatException: empty String"))) ? true : false;

            if ( noInputW_H_L ){
                msg.showMessageDialog(null, "Fields Width - Height - Length " +
                        "must be filed !",
                        "Warning", msg.WARNING_MESSAGE);

            } else if ( noInputGrade ) {
                msg.showMessageDialog(null, "A Type Of Grade " +
                        "must be chosen.",
                        "Warning", msg.WARNING_MESSAGE);
                
            }else if ( nonNumericInput ) {
                msg.showMessageDialog(null, "There was an Error " +
                        status.substring(32, status.length()) + newLine +
                        "Please Enter Only Numeric " +
                        "data in Fields Width - Height - Length!",
                        "ERROR", msg.ERROR_MESSAGE);
            }  

        } 
        /*
        catch ( InvalidInputException iie ){ // handle invalid Width height length values
            
            msg.showMessageDialog(null, "Invalid Input! " + newLine +
                        "Width Height and Length can be between Min 20 cm " +
                        "and Maximum 2 meters.",
                        "ERROR", msg.ERROR_MESSAGE);
        }*/
        catch (Exception e ){  // handling any other type of mistake that might occur
                        
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
    private void deleteTeams(){
        
        
            
            /* if everything is OK */
            if ( calculate() ){
                
                
                if (firstOrder){
                    /* create an orderID */
                   // orderID = order.orderID();
                    firstOrder = false;
                }
            
                /* create an OrderDetail object to hold the first group of boxes ordered */
              //  orderDetail = new OrderItem(box,status,boxQuantity);
        
                /* add the order detail to the order */
              //  order.addOrderItem(orderDetail);
                
                /* calculate the new cost */
               // orderCost = order.getTotalOrderCost();
                
                /* show the status to the user */
                //showResults(boxType,boxCost,orderCost,orderID);
                              
            }
            
              
            
        }
        
        
        
        
        
        
                
   
    
    

            
}

