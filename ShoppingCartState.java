
//ShoppingCartState
//Wade Benoit

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Source_Code.*;

public class ShoppingCartState{
	private static Warehouse warehouse;
	private static ShoppingCartState ShoppingCartState;
  final static String MAINMENU =  ""+
        "SHOPPING CART OPTIONS                                                  \n\t"+
          "a. Display Current Shopping Cart                       (DISPLAYSHOPPINGCART)\n\t"+
	        "b. Add or Remove Products                              (MODIFYSHOPPINGCART)\n\t"+
          "c. Exit Shopping Cart                                  (EXITSHOPPINGCART)\n\t"+


	//Displaying the Shopping cart
	//Add and remove options in the shopping cart.
	private static void displayShopingCart(){
		Iterator it = warehouse.getProducts();
		while(it.hasNext() )
			System.out.println(it.next().toString());
	}

	
	
	
  //editing and adding the products in the shopping cart
	private static void modifyShoppingCart() {

	  Scanner sc = new Scanner(System.in);
	    ArrayList<ItemList> cart = new ArrayList<ItemList>();

	    ItemList item;
	    int itemID;
	    String itemName;
	    double itemPrice;
	    String itemDescription;
	    int itemQuantity;
	    double itemTax;
	    int ch;
	    String choice;

	    ProductList shoppingCart = new ProductList();

	    while (true) {
	        System.out.println("Menu:");
	        System.out.println("0) Exit " + "\n"
	                + "1) Add item in shopping cart" + "\n"
	                + "2) Remove item from shpping cart");
	        ch = sc.nextInt();

	        switch (ch) {
	        case 0:
	            System.out.println("\n" + "Good bye!");
	            System.exit(0);

	        case 1:
	            System.out.println("Enter item ID: ");
	            itemID = sc.nextInt();

	            System.out.println("Enter item name: ");
	            itemName = sc.next();

	            System.out.println("Enter item price: ");
	            itemPrice = sc.nextDouble();

	            System.out.println("Enter short description of item: ");
	            itemDescription = sc.next();

	            System.out.println("Enter quantity: ");
	            itemQuantity = sc.nextInt();

	            System.out.println("Enter tax rate:");
	            itemTax = sc.nextDouble();

	            shoppingCart.add(itemID, itemName, itemPrice, itemDescription, itemQuantity, itemTax);

	            break;

	        case 2:
	            System.out.println("Enter name of the item that you would like to remove: ");
	            choice = sc.next();
	            shoppingCart.remove(choice);

	            break;
	        }

	    }
	}//END modifyShoppingCart()
	
	
	
	
	
	
	
	
	
	
	  //Running of the Shopping Cart State Implementation
  public static void processInput(Warehouse warehouse){
		Scanner input = new Scanner(System.in);
		String inputStr = "";
		System.out.println(MAINMENU);
		while(!inputStr.equals("exit")){
			inputStr = input.next();

			switch(inputStr.toUpperCase()){
				case "EXIT":
					System.out.println("Exiting shopping cart...\n");
          break;
					
        case "A":
	case "DISPLAYSHOPPINGCART":
	case "DISPLAY":
	  displayShoppingCart();
          break;
					
        case "B":
        case "ADD":
        case "REMOVE":
	case "EDIT":				
          modifyShoppingCart();
          break;
					
        case "C":
	System.out.println("Exiting Shopping Cart..");
          	ClientMenuState.processInput(w); //Swtitch states back to the ClientMenuState
          break;
					
	case default :
	  System.out.println("INVALID INPUT");
	  break;
	}//END Switch-Case
 }//END WHILE			
}//End processInput();
	
	
}//End Class
