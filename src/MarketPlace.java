import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class MarketPlace{
	
	private boolean loggedin = false;

	public static void main(String[] args) throws FileNotFoundException
	{
		 MarketPlace testMarketPlace = new MarketPlace();
		 
	}
  
  public MarketPlace() throws FileNotFoundException{
	  //******MAKE SURE TO CHANGE THIS TO WHEREVER YOUR FILE IS*******************
	  /**
	   * Scanner scanner = new Scanner(new File("C:\\Users\\littreln\\Documents\\GitHub\\C212_Final\\bin\\creds.csv"));
	   
	  scanner.useDelimiter(",");
      while(scanner.hasNextLine()){
          System.out.print(scanner.next()+"|");
      }
      scanner.close();
      */
	  
	  
	  
	  Scanner reader1 = new Scanner(System.in);
	  
	  System.out.println(help());
	  
	  while (true){
		  String input = reader1.next();
		  
		  
		  
		  if(input.equals("login")){
			  System.out.println("logged in");
		  }
		  else if (input.equals("logout")){
			  System.out.println("logged out");
		  }
		  else if (input.equals("search")){
			  System.out.println("searching...");
		  }
		  else if (input.equals("help")){
			  System.out.println(help());
		  }
		  else{
			  System.out.println("Sorry, I cannot recognize your command, please try again");
		  }
	  }
	  
	  /**
	   
	  String thisLine;
	   BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\littreln\\Documents\\GitHub\\C212_Final\\bin\\creds.csv"));
	    try
	    {

	       thisLine = reader.readLine();
	       System.out.print(thisLine);

	       while((thisLine = reader.readLine()) != null)               
	           {
	               thisLine = thisLine.trim();
	               String username = thisLine.split(" ")[0];
	                if (user.displayUserName().equals(username))
	                {
	                System.out.print("\nUser <-" + user.displayUserName() + " -> exists!");
	                break;   // break the loop
	                }                    
	           }

	    }
	    catch(IOException e)
	    {
	        System.out.print("\nUser does not exist\n"); 
	        e.printStackTrace();
	    } 
	    finally {
	        try {
	            reader.close();
	        } catch (IOException e) { /* ignore */ //}
	    //} **/
  }
  
  private String help(){
	  String help = "Welcome! Enter a command below (without the quotes) to continue.\n\n";
	  help+="\"login\" to login\n";
	  help+="\"search\" to search for items\n";
	  help+="\"logout\" to logout\n";
	  help+="\"help\" anytime to see this message again\n";
	  return help;
  }
  private void register(){
    
  }
  
  private void makeID(){
    
  }
  
  private String viewStock(){
	return null;
    
  }
  
  private void login(){
    
  }
  
  private void updateCreds(){
    
  }
  
  private String search(){
	return null;
    
  }
  
  
  
}