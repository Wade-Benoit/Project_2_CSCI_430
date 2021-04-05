/*
ClerkMenuState.java
*/



import Source_Code.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class ClerkMenuState{
	private static Warehouse warehouse;
	private static ClerkMenuState ClerkMenuState;
 	final static String FILENAME = "WarehouseData";
  final static String MAINMENU =  ""+
        "CLERK MENU OPTIONS                                                  \n\t"+ //The Menu Display String of options for the Clerk State
          "a. Add A Client                                        (ADDCLIENT)\n\t"+
          "b. Show list of products                      (DISPLAYALLPRODUCTS)\n\t"+
          "c. Become a client                                                \n\t"+
          "d. Display the waitlist for a product               (SHOWWAITLIST)\n\t"+
          "e. Receive a shipment                                (ADDSHIPMENT)\n\t"+
          "f. Record a payment from a client.                                \n\t"+
	  "g. Query Clients                                                  \n\t"+ //Added for Stage 2, switches to QueryClientState
          "h. Logout                                                         \n\n";



//Generic Methods Section 


	//getClientId
	//Prompts user for client id, retrieves it and returns 
	public static int getClientId(){
		System.out.print("Please enter a Client ID: ");
		Scanner s = new Scanner(System.in);
		return s.nextInt();
  }//end getClientID()
  
  
	
	//getProductId
	//Prompts user for product id, retrieves it and returns 
	
	public static int getProductId(){
		System.out.print("Please enter a Product ID: ");
		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}//end getProductId()



  //instance()
  //Creates an instance of the ClerkMenuState
	public static ClerkMenuState instance() {
		if(ClerkMenuState == null)
			return ClerkMenuState = new ClerkMenuState();
		else
			return ClerkMenuState;
	}//end instance()

  
  
  
  //addClient
  //Prompts the user to add all information for new client in system, then adds Client object
  private static void addClient(){
    Scanner input = new Scanner(System.in);
    System.out.print("Enter a name for the client: ");
    String name = input.nextLine();
    System.out.print("Enter a phone number for the client: ");
    String phone = input.nextLine();
    System.out.print("Enter an address for the client: ");
    String address = input.nextLine();
    warehouse.addClient(name, phone, address);
    System.out.println("Client added successfully");
  }//end addClient()

  
  //displayAllProducts()
  //Displays all of the current Product objects in the system with an iterator object
	private static void displayAllProducts(){
		Iterator it = warehouse.getProducts();
		while(it.hasNext() )
			System.out.println(it.next().toString());
	}//end displayAllProducts
  
  
  
  
  //displayInvoices()
  //Prompts the user for a client id.
  //Asks the user if they'd like to display detailed data of invoice
  //Displays the date and relevant data for each invoice in the client's history
  //Displays whether the client 
  private static void displayInvoices(){
    int clientId = getClientId();
    Iterator invoiceIt;
    boolean choice;
    if(!warehouse.verifyClient(clientId)){
      System.out.println("Error, invalid client id. Aborting operation");
      return;
    }//end if
    System.out.print("Would you like to display detailed transactions? (Y|N) ");
    choice = new Scanner(System.in).next().equals("Y"); //true if Y
    invoiceIt = warehouse.getInvoiceIt(clientId);
    if(choice)
      while(invoiceIt.hasNext() )
        System.out.println(((Invoice)(invoiceIt.next())).detailedString() );
    else
      while(invoiceIt.hasNext() )
        System.out.println(((Invoice)(invoiceIt.next())).toString());

		System.out.println("WARNING: displayInvoices in ClerkMenuState testing incomplete");
  }//end displayInvoices()

  
  //showWaitList
  //Gets the product id, then displays its wait list
  private static void showWaitList(){
     int productID = getProductId();
     Iterator it;
     if(!warehouse.verifyProduct(productID)){
        System.out.println("Error, invalid product id. Aborting operation");
        return;
     }//end if
     it = warehouse.getProductWaitList(productID);
     System.out.println("Product: \n" + warehouse.findProduct(productID).toString());
     if(!it.hasNext())
        System.out.println("Product Currently Has No Waitlist");
     else{
        System.out.println("Wait list:\n"+
                           "__________________");
        while(it.hasNext())
           System.out.println(((WaitListItem)it.next()).toString());
     }
  }//end showWaitList


	//addShipment()
	//Will prompt operator for a supplier ID for the shipment being taken in.
	//Verifies that id
	//Will repeatedly prompt operator for a product id and then prompt user to adjust quantity of that product
	private static void addShipment(){
		int supplierId, productId, quantity, quantCount;
		Scanner scanner;
		boolean moreProducts = true;
		Iterator waitList;
		WaitListItem currItem;
		char choice;
		//Get supplier id:
		System.out.print("Please enter a supplier id: ");
		scanner = new Scanner(System.in);
		supplierId = scanner.nextInt();
		//Verify supplier id:
		if(!warehouse.verifySupplier(supplierId)){
			System.out.println("Error, invalid supplier id. Aborting operation");
			return;
		}//end if
		while(moreProducts){
			//Get product id:
			System.out.print("Enter the received product's id (0 to quit): ");
			scanner = new Scanner(System.in); //flush input buffer
			productId = scanner.nextInt();
         if(productId == 0) //Sentinel value to return with
            return;
			//Verify product id:
			if(!warehouse.verifyProduct(productId)){
				System.out.println("Error, invalid product id. Aborting operation");
				return;
			}//end if
			//If valid, get product quantity
			System.out.print("Enter a quantity for the product: ");
			scanner = new Scanner(System.in); //flush buffer
			quantity = scanner.nextInt();
			//Increment product's quantity
			waitList = warehouse.addShippedItem(productId, quantity);
			//Receive waitList, prompt for any quantities that can be fulfilled.
			quantCount = 0; //Reset quant count for new item
			while(waitList.hasNext() ){
				currItem = (WaitListItem)waitList.next();
				if((currItem.getQuantity() + quantCount)<= warehouse.getStock(productId)){
					System.out.println("Order " + currItem.getOrder().getId() +
						" can be fulfilled with new stock.\n" +
                  " Current stock: " + warehouse.getStock(productId) +
                  " Order quantity needed: " + currItem.getQuantity() +
						"\nFulfill? (Y|N) ");
					scanner = new Scanner(System.in);
					choice = scanner.next().charAt(0);
					if(choice == 'Y'){
                  System.out.println("Fulfilling order");
						warehouse.fulfillWaitListItem(productId, currItem);
						quantCount += currItem.getQuantity();
					}
				}
			}
			warehouse.doneAddingfulfillItems();
		}
	}//end addShipment()



  //callClient
  //calls the ClientMenuState
	private static void callClient(){
		ClientMenuState.processInput(warehouse);
		System.out.println(MAINMENU);
	}

  public static void processInput(Warehouse w){
		warehouse = w;
		Scanner input = new Scanner(System.in);
		String inputStr = "";
		System.out.println(MAINMENU);
		while(!inputStr.equals("exit") && !inputStr.equals("i") && !inputStr.equals("logout")){
			inputStr = input.next();

			
			
switch(inputStr.toUpperCase()) {
		
	case "EXIT":
		System.out.println("Exiting Clerk Operations\n");
        	break;
		
		
        case "A":
	case "ADDCLIENT":
	case "ADDACLIENT":
		addClient();
       	        break;
					
					
        case "B":
        case "DISPLAYALLPRODUCTS":
        case "SHOWLISTOFPRODUCTS":
        	displayAllProducts();
       	        break;
					
					
        case "C":
	callClient(); //Switches State to Client State -- Become A Client
        break;
					
					
        case "D":
        case "SHOWWAITLIST":
        case "DISPLAYTHEWAITLISTFORAPRODUCT":
       		 showWaitList();
                 break;
					
					
        case "E":
	case "ADDSHIPMENT":
	case "RECEIVEASHIPMENT":
		addShipment();
        	break;
					
					
        case "F":
		System.out.println("WARNING: Record a payment from a client unavailable");
       	        break;
					
	
        case "G":
	case "CLIENTQUERY":
	case "QUERY":
	 	ClientQueryState.processInput(w);  //Switches states to the ClientQueryState
		
		
         case "H":
	 	System.out.println("Logging out of client\n"); 
                break;
					
					
        default:
        	System.out.print("ERROR: Invalid option\n" + MAINMENU);
        	break;
      }//end switch
    }//end while
  }//end processInput
}//end ClerkMenuState class


