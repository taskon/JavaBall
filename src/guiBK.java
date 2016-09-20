
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class guiBK extends JFrame {
    
    /* Frame width and height */
    private int appWidth;
    private int appHeight;
    
    /* LAYOUTS *****/
    GridLayout headerLayout = new GridLayout(1,1);
    GridLayout QuantityLayout = new GridLayout(1,2);
    GridLayout boxSizeLayout = new GridLayout(3,2);
    GridLayout gradeOfCardLayout = new GridLayout(1,2);
    GridLayout PrintingLayout = new GridLayout(2,2);
    GridLayout ReinforcingLayout = new GridLayout(2,2);
    GridLayout SealableLayout = new GridLayout(1,2);
    GridLayout Actions1Layout = new GridLayout(1,2);
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
    private JButton ConfirmOrderButton,exitButton,calculateButton,OrderButton; 
    
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
    private Order order;
    private FlexBox box;
    private OrderItem orderDetail; 
    private double boxCost,orderCost;
    
    
    
    /**
     * Creates a new instance of GUI
     */
    public GUI(String title) {
        super(title);
       
    }
    
    /* gets the dimensions of the user's screen and then
     * calculates and ajusts the size of the Jframe 
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
        
         String[] GradeOfCard = {null,"1","2","3","4"};
         String[] Labels = {"Order Details","Box Size","Width","Height","Length","Grade Of Card",
                               "Printing","Yes","No","Type Of Printing","Choose Reinforcing",
                               "Sealable","Status","Customer Name"," ","Choose Box Quantity"};
         String[] Printing = {null,"1","2","3"};
         String[] Reinforcing = {"Reinforced Bottom","Reinforced Corners",};
        


        //
        // Create GUI Componentns - Sliders
        //

        /* Choose Box Quantity Slider */
        SlChooseQuantity = new JSlider(JSlider.HORIZONTAL,
                                     boxMin,boxMax,boxInit);

        /* get initial values */
        boxQuantity = SlChooseQuantity.getValue();
        SlChooseQuantity.addChangeListener(new ChangeListener(){
            public void stateChanged ( ChangeEvent e ){

                try{
                    boxQuantity = SlChooseQuantity.getValue();
                    //System.out.println("->"+boxQuantity);
                    txtQuantity.setText(Integer.toString(SlChooseQuantity.getValue()));

                }
                catch(Exception warning){
                    status = warning.toString();
                    textAreaStatus.append(status + newLine);
                }


            }
        });



        //
        // Create GUI Componentns - Buttons
        //
        
        /* Exit  Button */
        exitButton = new JButton("Exit");
        //Add the action Listener - and the code that will hamdle the events
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                System.exit(0);
            } 
        } );

        
       /* Calculate  Button */
        calculateButton = new JButton("Calculate");
        //The action Listener - and the code that will hamdle the events
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
        ConfirmOrderButton = new JButton("Confirm Order");
        ConfirmOrderButton.setEnabled(false);
        //The action Listener - and the code that will hamdle the events
        ConfirmOrderButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                if (ConfirmOrderButton.isEnabled()){
                    
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
        // Create GUI Compnents - Combo Boxes
        //
        
        /* Choose CARD GRADE Combo Box */
        chooseGrade = new JComboBox(GradeOfCard);
        chooseGrade.setSelectedIndex(0);
        //Add the action Listener - and the code that will hamdle the events
        chooseGrade.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                //System.out.println(chooseGrade.getSelectedItem());
            }
        } );
        
        
        /* Choose PRINTING Combo Box */
        choosePrinting = new JComboBox(Printing);
        choosePrinting.setSelectedIndex(0);
        choosePrinting.setVisible(false);
        //Add the action Listener - and the code that will hamdle the events
        choosePrinting.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
            }
        } );
        
        
        
        //
        // Create GUI Componentns - RADIO BUTTONS
        //
        
        /* Radio Button YES for the Choose Printing Panel */
        radioYesP = new JRadioButton("YES"); //create the radio button
        radioYesP.setMnemonic(KeyEvent.VK_Y); 
        radioYesP.setActionCommand("Yes"); //set the command
        radioYesP.setSelected(false); // select it by default
        //Add the action Listener - and the code that will hamdle the events
        radioYesP.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){


                /* if the user chose to have Printing then enable the combo box
                 * to choose the type of printing */
                if ( radioYesP.isSelected() )
                    choosePrinting.setVisible(true);
                else
                    choosePrinting.setVisible(false);
                
            }
        } );
       
        
        /* Radio Button NO for the Choose Printing Panel */
        radioNoP = new JRadioButton("No"); //create the radio button
        radioNoP.setMnemonic(KeyEvent.VK_N); 
        radioNoP.setActionCommand("No"); //set the command
        radioNoP.setSelected(true); // select it by default
        //Add the action Listener - and the code that will hamdle the events
        radioNoP.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                if ( radioNoP.isSelected() )
                    choosePrinting.setVisible(false);
                else
                    choosePrinting.setVisible(true);
                
            }
        } );

        /* Radio Button NO for the Choose Reinforcing Panel */
        radioNoR = new JRadioButton("No"); //create the radio button
        radioNoR.setMnemonic(KeyEvent.VK_N);
        radioNoR.setActionCommand("No"); //set the command
        radioNoR.setSelected(true); // select it by default
        //The action Listener - and the code that will hamdle the events
        radioNoR.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                if ( radioNoR.isSelected() ){
                    
                    LstReinforcing.setVisible(false);
                    reinforcment = false;
                    reinforcedBottom = false;
                    reinforcedCorners = false;
                }
                else{
                    LstReinforcing.setVisible(true);
                    //reinforcment = true;
                }

            }
        } );

        /* Radio Button YES for the Choose Reinforcing Panel */
        radioYesR = new JRadioButton("YES"); //create the radio button
        radioYesR.setMnemonic(KeyEvent.VK_Y);
        radioYesR.setActionCommand("Yes"); //set the command
        radioYesR.setSelected(false); // select it by default
        //The action Listener - and the code that will hamdle the events
        radioYesR.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){


                /* if the user chose to have Reinforcing then enable the list
                 * to choose the type of reinforcing */
                if ( radioYesR.isSelected() ){
                    LstReinforcing.setVisible(true);
                    reinforcment = true;
                }
                else{
                    LstReinforcing.setVisible(false);
                    //reinforcment = false;
                }
                    

            }
        } );


        /* Radio Button YES for the Choose Sealable Panel */
        radioYesS = new JRadioButton("YES"); //create the radio button
        radioYesS.setMnemonic(KeyEvent.VK_Y); 
        radioYesS.setActionCommand("Yes"); //set the command
        radioYesS.setSelected(false);
        //Add the action Listener - and the code that will hamdle the events
        radioYesS.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
            }
        } );
       
        
        /* Radio Button NO for the Choose Sealable Panel */
        radioNoS = new JRadioButton("No"); //create the radio button
        radioNoS.setMnemonic(KeyEvent.VK_N); 
        radioNoS.setActionCommand("No"); //set the command
        radioNoS.setSelected(true); // select it by default
        //Add the action Listener - and the code that will hamdle the events
        radioNoS.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
            }
        } );
        
        
        /*
         *   Radio Button Groups
         */
        
        // the group for the choose Printing Panel
        groupForPrinting = new ButtonGroup();
        groupForPrinting.add(radioYesP);
        groupForPrinting.add(radioNoP);
        
        // the group for the choose Sealable Panel
        groupForSealable = new ButtonGroup();
        groupForSealable.add(radioYesS);
        groupForSealable.add(radioNoS);

        // the group for the choose Reinforcing Panel
        groupForReinforcing = new ButtonGroup();
        groupForReinforcing.add(radioYesR);
        groupForReinforcing.add(radioNoR);
        

        //
        // Create GUI Componentns - Lists
        //
        
        /* List for choosing type of reinforcing */
        LstReinforcing = new JList(Reinforcing); //create the list
        LstReinforcing.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        LstReinforcing.setVisible(false);
        /* and the Selection Listener */
        LstReinforcing.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            
            selection = LstReinforcing.getSelectedIndex();
            System.out.println("Selection from List's event-> "+selection);

                
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
            textAreaStatus.append("Welcome to the FlexBox Ordering Sytem" + newLine);
            textAreaStatus.append( newLine );
        }
        catch(Exception e){
            msg.showMessageDialog(null,"There was an internal error",
                                  "Error",msg.ERROR_MESSAGE);
        }
        
        /**
         *  Create - Populate the JPanels
         *
         *  All the components will be added to JPanels - assosiated with certain
         *  layouts - and then added to the Frame 
         *
         */
        
         /* Panel Header */
         JPanel panelHeader = new JPanel(); //create panel
         headerLayout.setHgap(3);
         headerLayout.setVgap(10);
         panelHeader.setLayout(headerLayout);
         
         lblOrderDetails = new JLabel(Labels[0]);
         lblOrderDetails.setFont(headerFont1);
         panelHeader.add(lblOrderDetails);
         /* ----------------------------------------------------- */


         /* Panel Choose Box Quantity  */
         JPanel panelQuantity = new JPanel(); //create panel
         QuantityLayout.setHgap(3);
         QuantityLayout.setVgap(10);
         panelQuantity.setLayout(QuantityLayout);
         //set the Border and Title
         title =  BorderFactory.createTitledBorder(blackline,
                                              "Box Quantity",TitledBorder.LEFT,
                                               TitledBorder.TOP,headerFont2
                                                                ,titleFColor);

         panelQuantity.setBorder(title); //set the title of the layout
         panelQuantity.add(lblChooseQuantity = new JLabel(Labels[15]));
         panelQuantity.add(SlChooseQuantity); // add the slider to choose quantity
         panelQuantity.add(txtQuantity = new JTextField());
         /* ----------------------------------------------------- */



         /* Panel Box Size  */
         JPanel panelBoxSize = new JPanel(); //create panel
         boxSizeLayout.setHgap(10);
         boxSizeLayout.setVgap(3);
         panelBoxSize.setLayout(boxSizeLayout); 
         //set the Border and Title 
         title =  BorderFactory.createTitledBorder(blackline,
                                              "Box Size",TitledBorder.LEFT,
                                               TitledBorder.TOP,headerFont2
                                                                ,titleFColor);
         panelBoxSize.setBorder(title);
         panelBoxSize.add(lblWidth = new JLabel(Labels[2]));
         panelBoxSize.add(txtWidth = new JTextField());
         panelBoxSize.add(lblHeight = new JLabel(Labels[3]));
         panelBoxSize.add(txtHeight = new JTextField());         
         panelBoxSize.add(lblLength = new JLabel(Labels[4]));
         panelBoxSize.add(txtLength = new JTextField());
         /* ----------------------------------------------------- */

 
         /* Panel Grade Of Card */
         JPanel panelGradeOfCard = new JPanel();
         gradeOfCardLayout.setHgap(3);
         gradeOfCardLayout.setVgap(10);
         panelGradeOfCard.setLayout(gradeOfCardLayout);
         //set the Border and Title 
         title =  BorderFactory.createTitledBorder(blackline,
                                              "Box Card",TitledBorder.LEFT,
                                               TitledBorder.TOP,headerFont2
                                                                ,titleFColor);
         panelGradeOfCard.setBorder(title);
         panelGradeOfCard.add(lblGradeOfCard = new JLabel(Labels[5]));
         panelGradeOfCard.add(chooseGrade);
         /* ----------------------------------------------------- */

         
         
         /* Panel Choose Printing */
         JPanel panelChoosePrinting = new JPanel();
         PrintingLayout.setVgap(3);
         PrintingLayout.setHgap(10);
         panelChoosePrinting.setLayout(PrintingLayout);
         //set the Border and Title 
         title =  BorderFactory.createTitledBorder(blackline,
                                              "Printing",TitledBorder.LEFT,
                                               TitledBorder.TOP,headerFont2
                                                                ,titleFColor);
         panelChoosePrinting.setBorder(title);
         panelChoosePrinting.add(radioYesP);
         panelChoosePrinting.add(radioNoP);
         panelChoosePrinting.add(lblGradeOfCard = new JLabel(Labels[9]));
         panelChoosePrinting.add(choosePrinting);
         /* ----------------------------------------------------- */
         
         

         /* Panel Reinforcing  */
         JPanel panelReinforcing = new JPanel(); //create panel
         ReinforcingLayout.setHgap(3);
         ReinforcingLayout.setVgap(10);
         panelReinforcing.setLayout(ReinforcingLayout); 
         //set the Border and Title 
         title =  BorderFactory.createTitledBorder(blackline,
                                              "Reinforcing",TitledBorder.LEFT,
                                               TitledBorder.TOP,headerFont2
                                                                ,titleFColor);
         
         panelReinforcing.setBorder(title);
         panelReinforcing.add(radioYesR);
         panelReinforcing.add(radioNoR);
         panelReinforcing.add(lblReinforcing = new JLabel(Labels[10]));
         panelReinforcing.add(LstReinforcing);
         /* ----------------------------------------------------- */

         
         /* Panel Choose Sealable */
         JPanel panelSealable = new JPanel();
         SealableLayout.setVgap(3);
         SealableLayout.setHgap(10);
         panelSealable.setLayout(SealableLayout);
         //set the Border and Title 
         title =  BorderFactory.createTitledBorder(blackline,
                                              "Sealable",TitledBorder.LEFT,
                                               TitledBorder.TOP,headerFont2
                                                                ,titleFColor);
         panelSealable.setBorder(title);
         panelSealable.add(radioYesS);
         panelSealable.add(radioNoS);
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
         panelActions1.add(OrderButton);
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
         
         
         /* Panel Actions 2 */
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
         panelActions2.add(ConfirmOrderButton);
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
        //cp.add(panelHeader);
        //cp.add(new JSeparator());
        cp.add(panelQuantity);
        cp.add(new JSeparator());
        cp.add(panelBoxSize);
        cp.add(new JSeparator());
        cp.add(panelGradeOfCard);
        cp.add(new JSeparator());
        cp.add(panelChoosePrinting);
        cp.add(new JSeparator());
        cp.add(panelReinforcing);
        cp.add(new JSeparator());
        cp.add(panelSealable);
        cp.add(new JSeparator());
        cp.add(panelActions1);
        cp.add(new JSeparator());
        cp.add(panelStatus);
        cp.add(new JSeparator());
        cp.add(panelActions2);
        cp.add(new JSeparator());
        
        

        /* Set the Size of the window and then Display it */
        
        setWindow();
        this.setVisible(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /* 
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
                order = new Order();               
            }
            
            
            /* create the box */
            box = new FlexBox(width,height,length,grade,colourPrint,
                    typeOfPrint,
                    reinforcedBottom,
                    sealableTops,
                    reinforcedCorners);
            /* get its type */
            boxType = box.returnBoxType();
            
            /* check the returned type */
            if (boxType.equals("0")){ // NOT A VALID BOX
                
                msg.showMessageDialog(null, "This Type of box cannot " +
                        " be created.Please choose another type!",
                        "Warning", msg.WARNING_MESSAGE);
                
            } else{  // Box type is valid ( 1 to 6 )
                
                /* get the cost of the Box */
                boxCost = box.resolveCost();
                
                /* show the status to the user */
                //showStatus(boxType,boxCost,orderCost,OrderID);
                
                
                /* enable the place order button */
                ConfirmOrderButton.setEnabled(true);
                
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
            checkW_H_L(width,height,length);
            
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
        catch ( InvalidInputException iie ){ // handle invalid Width height length values
            
            msg.showMessageDialog(null, "Invalid Input! " + newLine +
                        "Width Height and Length can be between Min 20 cm " +
                        "and Maximum 2 meters.",
                        "ERROR", msg.ERROR_MESSAGE);
        }
        catch (Exception e ){  // handling any other type of mistake that might occur
                        
            msg.showMessageDialog(null, "Unexpected Error: " + e.toString(),
                        "ERROR", msg.ERROR_MESSAGE);
            
        }
        
        return dataStatus;

    }
    
    /**
     * This method simply prints to the text area 
     * the complete order information to the user
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
    
    /* 
     * This method check's if width - height - and length are valid (between 10 cm and 1 meters)
     * and if they are not throws a custom exception ( InvalidInputException )
     *
     * The validation of empty fields that are required and non nueric input 
     * is done using NumberFormatException.  
     */
    private void checkW_H_L(double width,double height,double length)
                                                 throws InvalidInputException { 
    
        
        // check the correct boundaries of the user input
        Boolean invalidW_H_L = ( ( width < 10 || width > 200 )  ||
                                   ( height < 10 || height > 200 ) || 
                                   ( length < 10 || length > 200 ) ) ? true : false;
        
        // if they are invalid throw a custom exception
        if ( invalidW_H_L )
            throw new InvalidInputException();
    
    }
    
    /* 
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
                    orderID = order.orderID();
                    firstOrder = false;
                }
            
                /* create an OrderDetail object to hold the first group of boxes ordered */
                orderDetail = new OrderItem(box,status,boxQuantity);
        
                /* add the order detail to the order */
                order.addOrderItem(orderDetail);
                
                /* calculate the new cost */
                orderCost = order.getTotalOrderCost();
                
                /* show the status to the user */
                showStatus(boxType,boxCost,orderCost,orderID);
                              
            }
            
              
            
        }
        
        
        
        
        
        
                
   
    
    

            
}

