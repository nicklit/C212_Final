import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MarketPlace{
 
  
  public MarketPlace() throws FileNotFoundException{
	  //******MAKE SURE TO CHANGE THIS TO WHEREVER YOUR FILE IS*******************
	  Scanner scanner = new Scanner(new File("/Users/littreln/Github/C212_Final/bin/creds.csv"));
	  scanner.useDelimiter(",");
      while(scanner.hasNext()){
          System.out.print(scanner.next()+"|");
      }
      scanner.close();
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