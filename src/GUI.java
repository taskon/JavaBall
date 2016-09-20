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
    //GridLayout Actions1Layout = new GridLayout(1,2);
    GridLayout StatusLayout = new GridLayout(1,1);
    GridLayout Actions2Layout = new GridLayout(1,2);
    
    /* ---------------------- GUI Components ------------------------ */
    
    /* LABELS *****/
    private JLabel lblCustName,lblOrderDetails,lblBoxSize,lblGradeOfCard,
                               lblPrinting,lblTypeOfPrint,lblReinforcing,
                               lblSealable,lblStatus,lblWidth,lblHeight,
                               lblLength,lblTypeOfPrinting,lblPlaceHolder,
                               lblChooseQuantity;
    
    /* BUTTONS *****/
    private JButton loadTeams,exitButton,loadResults,OrderButton; 
    
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
    private TextArea textAreaStatus;
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
    
    
    
    /**
     * Creates a new instance of GUI
     */
    public GUI(String title) {
        super(title);
       
    }
    
    /**
     * gets the dimensions of the user's screen and then
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
     * The basic method of the GUI class that will create the GUI populate it 
     * with widjets and conponents and handle all events as well the actions that will
     * respond to these events.
     */
    @SuppressWarnings("static-access")
     protected void initialize(){
        
    	
         
         String[] Labels = {"Match Details","Box Size","Width","Height","Length","Grade Of Card",
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
        loadResults = new JButton("Calculate");
        //The action Listener - and the code that will handle the events
        loadResults.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               
                loadResults.setCursor(Cursor.getPredefinedCursor(
                                                        Cursor.WAIT_CURSOR));
                /* calculate the order */
                //calculate();

                loadResults.setCursor(Cursor.getPredefinedCursor(
                                                        Cursor.DEFAULT_CURSOR));

             }
        } );
        
        
        /* Place Order Button */
        OrderButton = new JButton("Order");
        OrderButton.setEnabled(true);
        //Add the action Listener - and the code that will hamdle the events
        OrderButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                                
                    /* place the order */
                    placeOrder();
                                     
            } 
        } );
        
        /* Confirm Order  Button */
        loadTeams = new JButton("Confirm Order");
        loadTeams.setEnabled(true);
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
        textAreaStatus = new TextArea(5, 30);
        textAreaStatus.setBackground(Color.WHITE);
        textAreaStatus.setEditable(false);
        
        //add a scrollbar
        JScrollPane scrollPane = new JScrollPane(textAreaStatus);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
         
        textAreaStatus.setFont( new Font("Serif",Font.ITALIC, 16));
        //textAreaStatus.setLineWrap(true);
        //textAreaStatus.setWrapStyleWord(true);

        try{
            textAreaStatus.append("Welcome to JavaBall" + newLine);
            textAreaStatus.append( newLine );
        }
        catch(Exception e){
            msg.showMessageDialog(null,"There was an internal error",
                                  "Error",msg.ERROR_MESSAGE);
        }
        
        //
        //  Create - Populate the JPanels
        //
        
         /* Panel Header */
         JPanel panelHeader = new JPanel(); //create panel
         headerLayout.setHgap(3);
         headerLayout.setVgap(10);
         panelHeader.setLayout(headerLayout);
         
         lblOrderDetails = new JLabel(Labels[0]);
         lblOrderDetails.setFont(headerFont1);
         panelHeader.add(lblOrderDetails);
         /* ----------------------------------------------------- */
     
         /* Panel Status */
         JPanel panelStatus = new JPanel();
         StatusLayout.setVgap(3);
         StatusLayout.setHgap(10);
         panelStatus.setLayout(StatusLayout);
         //set the Border and Title 
         title =  BorderFactory.createTitledBorder(blackline,
                                              "Status",TitledBorder.LEFT,
                                               TitledBorder.TOP,headerFont2
                                                                ,titleFColor);
         panelStatus.setBorder(title);
         JScrollPane scrollPane2 = new JScrollPane(panelStatus);
         panelStatus.add(textAreaStatus);
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
        //cp.add(new JSeparator());
        cp.add(panelStatus);
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
     * This method simply prints to the text area 
     * all relevant information to the user
     */
    private void showStatus(String boxType,double boxCost,double orderCost,String orderID){
        
      
        textAreaStatus.append("Order Details for Order Number - " + orderID +" - are: " + newLine);
        textAreaStatus.append("-----------------------------------------------------" + newLine);
        textAreaStatus.append(newLine);
        textAreaStatus.append("Number Of Boxes ordered: " + boxQuantity + newLine);
        textAreaStatus.append("Width: "+ width +" Height: " + height + " Length: " + length + newLine);
        textAreaStatus.append("Grade Of Card: Type" + grade + newLine);
        
        if ( colourPrint ){
            textAreaStatus.append("Printing: Yes" + newLine);
            textAreaStatus.append("Type Of Printing: " + typeOfPrint + newLine);
        }
        else
            textAreaStatus.append("Printing: None" + newLine);
            
        if (reinforcment)
            textAreaStatus.append("Reinforcing: Yes" + newLine);
        else
            textAreaStatus.append("Reinforcing: None "+ newLine);
        
        if (sealableTops)
            textAreaStatus.append("Sealable Tops: Yes"+ newLine);
        else
            textAreaStatus.append("Sealable Tops: No" + newLine);
        
        textAreaStatus.append("Type of Box: " + boxType + newLine);
        textAreaStatus.append("Price Of Box: " + boxCost + newLine) ;
        textAreaStatus.append("Total Cost: " + orderCost + newLine) ;
        
        textAreaStatus.append("--------------------------");
        textAreaStatus.append(newLine);
        
        
        
        
        
        
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
     * This method will create the OrderDetail class ( containing all the quotes 
     * for boxes of different type - if more than one type for a single order)
     * and place them all inside
     * the final order.
     */
    private void placeOrder(){
        
        
            
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
                showStatus(boxType,boxCost,orderCost,orderID);
                              
            }
            
              
            
        }
        
        
        
        
        
        
                
   
    
    

            
}

