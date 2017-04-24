import java.util.Scanner;


public class Buyer{
 
  
	
	public Buyer(){
		login();
		System.out.println("Enter your password");
		System.out.println("Enter your snassword");
	}
  
	private void register(){
    
	}
  
  private void login(){
	System.out.println("logged in as Buyer");
	  
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