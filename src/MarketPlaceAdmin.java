import java.io.FileNotFoundException;

public class MarketPlaceAdmin{
  
  public MarketPlaceAdmin() throws FileNotFoundException{
    login();
  }
  
  private void login(){
	System.out.println("\nLogged in as Admin\n\n");
  
	String help = "Enter a command below (without the quotes) to continue.\n\n";
		  
	help += "\"buyers\" to view all buyers\n";	
	help += "\"sellers\" to view all sellers\n";
	help += "\"items\" to view items for sale\n";
	help += "\"history_b\" to view a purchase history\n";
	help += "\"history_s\" to view a sale history\n";
	help += "\"search\" to search for items\n";
	help += "\"update\" to update credentials\n";
	help += "\"logout\" to logout\n\n";
	help += "\"help\" anytime to see this message again\n";
		
	System.out.println(help); 
  }
  
  private String listBuyers(){
	return null;
    
  }
  
  private String listSellers(){
	return null;
    
  }
  
  private void updateCreds(){
    
  }
  
  private String listItems(Seller s){
	return null;
    
  }
  
  private String purchaseHistory(Buyer b){
	return null;
    
  }
  
  private String showShipping(Item i){
	return null;
    
  }
  
  private String salesHistory(Seller s){
	return null;
    
  }
  
  private void updateUserSellerCreds(Seller s){
    
  }
  
  private void updateBuyerCreds(Buyer b){
    
  }
  
  private String search(){
	return null;
    
  }
}