import java.io.FileNotFoundException;
import java.util.Scanner;



public class Buyer extends MarketPlace{

	private Scanner userInput;

  
	
	public Buyer() throws FileNotFoundException{
		login();
<<<<<<< HEAD


=======
		System.out.println("Enter your password");

>>>>>>> refs/remotes/origin/master
	}
  
	private void register(){
    
	}
  
  private void login(){
	System.out.println("\nLogged in as Buyer\n\n");
	  
	String help = "Enter a command below (without the quotes) to continue.\n\n";
	  
	
	help += "\"view\" to view items for sale\n";
	help += "\"cart\" to view your cart\n";
	help += "\"search\" to search for items\n";
	help += "\"buy\" to buy items in cart\n";
	help += "\"history\" to view purchase history\n";
	help += "\"update\" to update credentials\n";
	help += "\"logout\" to logout\n\n";
	help += "\"help\" anytime to see this message again\n";
	
	System.out.println(help);
	
	Scanner buyInput = new Scanner(System.in);
	String input = buyInput.next().toLowerCase();
	
	if(input.equals("view")){
		
	}else if(input.equals("cart")){
		
	}else if(input.equals("search")){
		
	}else if(input.equals("buy")){
		
	}else if(input.equals("history")){
		
	}else if(input.equals("update")){
		
	}else if(input.equals("logout")){
		
	}else if (input.equals("help")){
		
	}else{
		System.out.println("Sorry, I cannot recognize your command, please try again\n");
		login();
	}
  }
  
  private void buy(Item i){
    
  }
  
  private void updateCreds(){
    
  }
  
  private String viewMarketPlaceItems(){
	return null;
    
  }
  
  private String search(){
	return null;
    
  }
  
}