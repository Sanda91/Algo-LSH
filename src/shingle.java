import java.util.ArrayList;
import java.util.Hashtable;


public class shingle{
	
  static Hashtable<Integer, String> hash_table=new Hashtable<Integer, String>(); //Initializing the Hashtable

  public String shingles(String line,int k) { //Takes the string and the value of k in k-shingles as parameters
	  
	  String sub_st = null;
	  ArrayList<String> shingles = new ArrayList<String>();
	  
	  line = line.toLowerCase(); //Converting the line in to the lower case
	  line=line.replaceAll("\\s+","");//Removing spaces from the line
	  
	  System.out.println(line);
	  
	  for (int i=0;i<line.length();i++) {
		  
		  if(i<line.length()-(k-1)){
			  
		  sub_st=line.substring(i,i+k);//Creating k-shingles
		  
		  }
		  
		  else{	  
			  break;
		  }
		  
		  shingles.add(sub_st); //Add shingles to the ArrayList
	  
	  }
	  
	  hashed_shingles(shingles); //Hash the values of the shingles
	  
    return shingles.toString();
    
  }
  
  
  public static String hashed_shingles(ArrayList<String> shingles){//Takes an ArrayList as the parameter
	  
	  ArrayList<Integer> hashed_shingles=new ArrayList<Integer>();//Initialize an ArrayList to hold the hashed shingles
	
	  for(String sub_str:shingles){
		  int key=sub_str.hashCode(); //Get the hash code of each shingle
		  
		  hashed_shingles.add(key); //Add it to an ArrayList
		  hash_table.put(key, sub_str); //Add to a Hash Table
		  
	  }
	  //System.out.println(hashed_shingle.toString());
	  return hashed_shingles.toString();
	  
  }

}
