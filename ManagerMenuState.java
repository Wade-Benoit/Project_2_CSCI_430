//ManagerMenuState
//Wade Benoit 


import Source_Code.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class ManagerMenuState{
	private final static String MENUOPTIONS = "		MANAGER MENU	\n" +
									"_____________________________________\n" +
									"a. to add a product          (ADDPRODUCT)\n" +
									"s. to add a supplier         (ADDSUPPLIER)\n" +
									"v. to view all suppliers         (VIEWALLSUPPLIERS)\n" +
									"l. to view suppliers of a product          (VIEWSUPPLIERSOFPRODUCT)\n" +
									"p. to view all products of a supplier          (VIEWPRODUCTSOFSUPPLIER)\n" +
									"n. to add a supplier for a product         (ADDSUPPLIERFORPRODUCT)\n" +
									"m. to update the purchase price of a product from a supplier         (MODIFYPURCHASEPRICE)\n" +
									"c. to login as a salesclerk          (LOGINASCLERK)\n" +
									"q. to logout\n";
	private static Warehouse warehouse;
	private static ManagerMenuState ManagerMenuState;
	

	//performMenu                                                           DIFFERENT MENU METHOD AND PROCESSING THAN OTHER TWO STATES IN STAGE 1
	//Continuously displays the menu for the user and receives their choice.
	//Sends that input to processUserChoice()
	public static void performMenu(Warehouse w){
		boolean getNextOption = true;		
		Scanner s = new Scanner(System.in);
		warehouse = w;
		char choice;
		while(getNextOption){
			//Print the menu
			System.out.println(MENUOPTIONS);
			//Get the user's selection
			choice = s.nextLine().charAt(0);
			//Process that chosen option
			getNextOption = processUserChoice(choice);
		}
	}//end performMenu()
	
	private static boolean processUserChoice(char choice){
		boolean iterateAgain = true;
		switch(Character.toLowerCase(choice)){
			case 'a':
				addProduct(); break;
			case 's':
				addSupplier(); break;
			case 'v':
				viewAllSuppliers(); break;
			case 'l':
				viewSuppliersOfProduct(); break;
			case 'p':
				viewProductsOfSupplier(); break;
			case 'n':
				addSupplierForProduct(); break;
			case 'm':
				modifyPurchasePrice(); break;
			case 'c':
				loginAsClerk(); break;
			case 'q':
				System.out.println("Logging out");
				iterateAgain = false; break;
			default:
				System.out.println("Invalid input; Try again");
		}
		return iterateAgain;
	}//end processUserChoice()
	

	//addProduct
	//Provides necessary prompts for the user to add a product to the Warehouse
	//Allows user to repeat as many times as needed
	private static void addProduct(){
	  boolean adding = true;
      Scanner input;   
      while(adding){
   		input = new Scanner(System.in);
   		System.out.print("Enter a description for the product: ");
   		String description = input.nextLine();
   		System.out.print("Enter an sale price for the product: ");
   		String salePrice = input.nextLine();
   		System.out.print("Enter a stock for the product: ");
   		int stock = input.nextInt();
   		warehouse.addProduct(description, Double.valueOf(salePrice), stock);
   		System.out.println("Product added successfully");
         //Check to see if they want to add another product
         System.out.print("Add another product? (Y|N) ");
         input = new Scanner(System.in);
         adding = (input.next().charAt(0) == 'Y');
      }
	}//end addProduct()
	

	//addSupplier
	//Code to prompt user for necessary information to add a new supplier to the warehouse
	private static void addSupplier(){
		String description;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter a description for the supplier:");
		description = s.nextLine();
		warehouse.addSupplier(description);
		System.out.println("Supplier added successfully");
	}//end addSupplier()

  
  
	//viewAllSuppliers
	//Displays all suppliers registered in the warehouse
	private static void viewAllSuppliers(){
		Iterator it = warehouse.getSuppliers();
		System.out.println("Suppliers:");
		System.out.println("____________________");
		while(it.hasNext())
			System.out.println(it.next().toString());
	}//end viewAllSuppliers
	

	//viewSuppliersOfProduct
	//User passes in the id of the product.
	//Interface displays all suppliers that supply that product, along with
	//their sale price for the product.
	private static void viewSuppliersOfProduct(){
		Scanner s = new Scanner(System.in);
		int productId;
		String output = "";
		System.out.print("Please enter the product id to search for: ");
		productId = s.nextInt();
		if(!warehouse.verifyProduct(productId)){
			System.out.println("No product found with given id. Aborting\n");
			return;
		}
		Iterator it = warehouse.getSuppliers();
		Supplier currSupplier;
		while(it.hasNext()){
			currSupplier = (Supplier)it.next();
			output += currSupplier.searchProduct(productId) + '\n';
		}
		System.out.println(output);
	}//end viewSuppliersOfProduct
	
  //(Similar to viewSuppliersOfProduct but reverse)
	private static void viewProductsOfSupplier(){
		Scanner s = new Scanner(System.in);
		int supplierId;
		System.out.print("Enter a supplier id: ");
		supplierId = s.nextInt();
		Supplier supp = warehouse.findSupplier(supplierId);
		if(supp == null){
			System.out.println("Error, no supplier found with given id. Aborting.\n");
			return;
		}
		System.out.println("\n" + supp.toString() + "\n" + "___________" + "\n");
		Iterator it = supp.getProducts();
		while(it.hasNext())
			System.out.println(((SupplierItem)it.next()).toString());
	}//end viewProductsOfSupplier
	

	//addSupplierForProduct
	//Prompts the user for a supplier id, product id and price. If the supplier
	//does not already stock the given product, the product will be added to the list
	//of products that the supplier provides, and will be assigned the given price..
    private static void addSupplierForProduct(){
      Scanner s = new Scanner(System.in);
      int supplierId, productId;
      double price;
      Supplier supplier;
      Product product;
      System.out.print("Enter the id of the supplier who will stock this product: ");
      supplierId = s.nextInt();
      supplier = warehouse.findSupplier(supplierId);
		    if(supplier == null){
          System.out.println("Error, no supplier found with given id. Aborting");
          return;
		    }
      System.out.print("Enter the id of the product to be added: ");
      productId = s.nextInt();
      product = warehouse.findProduct(productId);
      if(product == null){
        System.out.println("Error, no product found with given id. Aborting");
        return;
      }
      if(supplier.hasProduct(productId)){
        System.out.println("Error, Product already supplied by supplier. Aborting");
        return;
      }
      s = new Scanner(System.in);
      System.out.print("Enter the purchase price for this product: ");
      price = Double.valueOf(s.nextLine());
      supplier.addProduct(product, price);
	}//end addSupplierForProduct()
	

	//modifiyPurchasePrice()
	//Prompts the user for a supplier id, product id and price. If the supplier
	//stocks the given product, this method will reassign the price of the product
	//to the new one specified
	private static void modifyPurchasePrice(){
		Scanner s = new Scanner(System.in);
		int supplierId, productId;
		double price;
		Supplier supplier;
		Product product;
		System.out.print("Enter the id of the supplier who stocks this product: ");
		supplierId = s.nextInt();
		supplier = warehouse.findSupplier(supplierId);
		if(supplier == null){
			System.out.println("Error, no supplier found with given id. Aborting");
			return;
		}
		System.out.print("Enter the id of the product: ");
		productId = s.nextInt();
		product = warehouse.findProduct(productId);
		if(product == null){
			System.out.println("Error, no product found with given id. Aborting");
			return;
		}
		if(!supplier.hasProduct(productId)){
			System.out.println("Error, Product not supplied by supplier. Aborting");
			return;
		}
		s = new Scanner(System.in); //flush input buffer
		System.out.print("Enter the purchase price for this product: ");
		price = Double.valueOf(s.nextLine());
		supplier.setPurchasePrice(product, price);
	}//end modifyPurchasePrice()
	
	private static void loginAsClerk(){
		ClerkMenuState.processInput(warehouse);
	}//end loginAsClerk()

  
  
	//instance()
	//Used to implement class as singleton. Returns an instance of the ManagerMenuState
	public static ManagerMenuState instance() {
		if(ManagerMenuState == null)
			return ManagerMenuState = new ManagerMenuState();
		else
			return ManagerMenuState;
	}//end instance()
} //end class
