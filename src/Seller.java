public class Seller{
 
  public Seller(){
	  login(); 
  }
  
  private void register(){
	  
  }
  
  private void login(){
	System.out.println("logged in as Seller");
	 
	String help = "Enter a command below (without the quotes) to continue.\n\n";
	  
		
	help += "\"view\" to view item inventory\n";
	help += "\"add\" to add item to inventory\n";
	help += "\"search\" to search for items\n";
	help += "\"history\" to view sales history\n";
	help += "\"update\" to update credentials\n";
	help += "\"logout\" to logout\n\n";
	help += "\"help\" anytime to see this message again\n";
	
	System.out.println(help);
  }
  
  private void updateInventory(){
    
  }
  
  private void updateName(){
    
  }
  
  private void updateEmail(){
    
  }
  
  private void addInvItem(){
    
  }
  
  private void delInvItem(){
    
  }
  private void updInvItem(){
    
  }
  
  private void updateCreds(){
    
  }
  
  private String search(){
	return null;
    
  }
}