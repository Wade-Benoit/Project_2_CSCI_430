
/*
ClientQueryState.java
*/


import Source_Code.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class ClientQueryState {
	private static Warehouse warehouse;
	private static ClientQueryState ClientQueryState;
 	final static String FILENAME = "WarehouseData";
  final static String MAINMENU =  ""+
        "CLERK MENU OPTIONS                                                  \n\t"+ //The Menu Display String of options for the Clerk State
          "a. Show list of clients                           (DISPLAYALLCLIENTS)\n\t"+
          "b. Show list of clients with outstanding balance  (DISPLAYALLOUTSTANDINGCLIENTS)\n\t"+
	        "c. Show list of clients with no transactions      (DISPLAYZEROTRANSACTIONCLIENTS)\n\t"+
	        "d. Logout                                                            \n\n";


  //instance()
  //Creates an instance of the ClientQueryState
	public static ClientQueryState instance() {
		if(ClientQueryState == null)
			return ClientQueryState = new ClientQueryState();
		else
			return ClientQueryState;
}//end instance()

		  

  //displayAllClients()
  //Displays all of the Client objects currently recorded in the system
	private static void displayAllClients(){
		Iterator it = warehouse.getClients();
		while(it.hasNext() )
			System.out.println(it.next().toString());
    }//end displayAllClients
		  
		  
		  
		  
  //displayAllOutstandingClients()
  //Displays all of the Client objects currently in the system with an outstanding balance
  private static void displayAllOutstandingClients(){
		Iterator it = warehouse.getClients();
		while(it.hasNext() ) {
      if(it.getClientBalance < 0)
			  System.out.println(it.next().toString());
    }
  }//end displayAllOutstandingcClients()

	
  //displayZeroTransactionClients()
  //Displays all of the Client objects currently in the system with a transaction count of zero
  private static void displayZeroTransactionClients(){
		Iterator it = warehouse.getClients();
		while(it.hasNext() ) {
      if(it.getClientTransactions == 0 || it.getClientTransactions == NULL)
			  System.out.println(it.next().toString());
    }
  }//end displayZeroTransactionClients()


	  //processInput()
    //User input and menu selection here
    public static void processInput(Warehouse w){
		warehouse = w;
		Scanner input = new Scanner(System.in);
		String inputStr = "";
		System.out.println(MAINMENU);
		while(!inputStr.equals("exit") && !inputStr.equals("d") && !inputStr.equals("D") && !inputStr.equals("logout")){ //This is where logging out is handled
			inputStr = input.next();

			
			
	switch(inputStr.toUpperCase()) { 

	case "A":
        case "DISPLAYALLCLIENTS":
        case "SHOWLISTOFCLIENTS":
          displayAllClients();
          break;


        case "B":
        case "SHOWLISTOFCLIENTSWITHOUTSTANDINGBALANCE":
          displayAllOutstandingClients();
          break;
			
	case "C":
        case "DISPLAYCLENTSWITHZEROTRANSACTIONS":
        case "DISPLAYZEROTRANSACTIONCLIENTS":
          displayZeroTransactionClients();
          break;
			
	case "D":     //THIS CASE WILL NEVER BE REACHED, WILL BE CAUGHT AS PARAMETER 
        case "LOGOUT":
        case "EXIT":
         break;
			
	}//END SWITCH-CASE	
 }//END PROCESSINPUT()
	    	    
}//END CLASS
