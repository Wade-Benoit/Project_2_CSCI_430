//ClientMenuState
//Wade Benoit

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Source_Code.*;

public class ClientMenuState{
	private static Warehouse warehouse;
	private static ClerkMenuState clerkMenuState;
  final static String MAINMENU =  ""+
        "CLIENT MENU OPTIONS                                                  \n\t"+
          "a. Show Client detail                                        (DISPLAYCLIENTDETAILS)\n\t"+
          "b. Show list of products                                     (DISPLAYPRODUCTSLIST)\n\t"+
          "c. Show client transactions                                  (DISPLAYCLIENTTRANSACTIONS)\n\t"+
          "d. Add client shopping cart                                  (ADDSHOPPINGCART)\n\t"+
	  "e. Edit client shopping cart                                  (EDITSHOPPINGCART)\n\t"+  //Switches states to ShoppingCartState
          "f. Display the client waitlist                               (DISPLAYCLIENTWAITLIST)\n\t"+
          "g. Logout                                                         \n\n";


	//getProductId (Helper Function)
	//Prompts user for product id, retrieves it and returns it
	public static int getProductId(){
		System.out.print("Please enter a product id: ");
		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}//end getProductId()

	
	//displayClientDetails
	//Displays all Client objects in the system.
	private static void displayClientsDetails(){
		try {
			int clientId = clerkMenuState.getClientId();
			Iterator it = warehouse.getClients();
			while(it.hasNext() )
				System.out.println(it.next().toString());
		}
		catch (Exception e){ System.out.println("ERROR: displayClientDetails() in ClientMenuState " + e);}
	}//end displayAllProducts()


	//displayProductList
	//Displays all Product objects in the system along with each objects sale price (.toString() method)
	private static void displayProductList(){
		try {
			Iterator it = warehouse.getProducts();
			while(it.hasNext() )
				System.out.println(it.next().toString());
		}
		catch (Exception e){ System.out.println("ERROR: getProducts() in ClientMenuState " + e);}
	}//end displayProductList()


  //displayClientTransactions()
  //Prompts the user for a client id.
  //Asks the user if they'd like to display detailed data (This includes the items that were charged for)
  //Displays the date and relevant data for each invoice in the client's history
  private void displayClientTransaction(){
    int clientId = clerkMenuState.getClientId();
    Iterator invoiceIt;
    boolean choice;
    
    if(!warehouse.verifyClient(clientId)){
      System.out.println("Error, invalid client id. Aborting operation");
      return;
    }
    
    System.out.print("Would you like to display detailed transactions? (Y|N) ");
    choice = new Scanner(System.in).next().equals("Y"); //true if Y
    invoiceIt = warehouse.getInvoiceIt(clientId);
    
    if(choice)
      while(invoiceIt.hasNext() )
        System.out.println(((invoiceIt.next())).toString());
    else
      while(invoiceIt.hasNext() )
        System.out.println(((invoiceIt.next())).toString());
  }//end displayClientTransactions()




  //showWaitList
  //Gets the product id, then displays its wait list
  private static void showWaitList(){
			try {
	     int productID = getProductId();
	     Iterator it;
	     if(!warehouse.verifyProduct(productID)){
	        System.out.println("Error, invalid product id. Aborting operation");
	        return;
	     }
	     it = warehouse.getProductWaitList(productID);
	     System.out.println("Product: \n" + warehouse.findProduct(productID).toString());
	     if(!it.hasNext())
	        System.out.println("Product has no wait list currently");
	     else{
	        System.out.println("Wait list:\n"+
	                           "__________________");
	        while(it.hasNext())
	           System.out.println(((WaitListItem)it.next()).toString());
	     }
		 }
		 catch (Exception e){ System.out.println("ERROR: showWaitList() in ClientMenuState " + e);}
  }//end showWaitList()
  
  
  //Running of the ClientMenuState Implementation
  public static void processInput(Warehouse warehouse){
		Scanner input = new Scanner(System.in);
		String inputStr = "";
		System.out.println(MAINMENU);
		while(!inputStr.equals("exit") && !inputStr.equals("g") && !inputStr.equals("G")){
			inputStr = input.next();

			switch(inputStr.toUpperCase()){
				case "EXIT":
					System.out.println("Exiting warehouse operations\n");
          break;
					
        case "A":
				case "DISPLAYCLIENTDETAILS":
				case "SHOWCLIENTDETAIL  ":
					displayClientsDetails();
          break;
					
        case "B":
        case "DISPLAYPRODUCTSLIST":
        case "SHOWLISTOFPRODUCTS":
          displayProductList();
          break;
					
        case "C":
        case "DISPLAYCLIENTTRANSACTIONS":
        case "SHOWCLIENTTRANSACTIONS":
					System.out.println("WARNING: Show client transactions unavailable");
          break;

        case "D":
					System.out.println("WARNING: Add client shopping cart unavailable");
					break;
        case "E":
        case "ADD":
        case "DISPLAYTHECLIENTWAITLIST":
          showWaitList();
          break;
					
        case "F":
	case "EDIT":
	case "SHOPPINGCART":
		        ShoppingCartState.processInput(w); //Switches State to the ShoppingCartState()
          break;
					
        case "G":
					System.out.println("Logging out of client\n");
          break;
					
					
        default:
          System.out.print("ERROR: Invalid option\n" + MAINMENU);
          break;
      }
    }
  }
}//end ClientMenuState class
