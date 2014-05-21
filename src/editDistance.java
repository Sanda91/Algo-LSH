/* Locality Sensitive Hashing: Edit Distance
 * Check for the edit distance of 1
 *Author: Sandaruwan Gunasinghe
 */

public class editDistance{
	
	public static double editDist(String a,String b){//Get the strings from the candidate matrix
		a.toLowerCase();
		b.toLowerCase();
		
		String a_hold[]=a.split(" ");//Get each word separately and add them to an array
		String b_hold[]=b.split(" ");
		int alength=a_hold.length;//Get the length of each string array that hold the words
		int blength=b_hold.length;
		
		int maxLen=Math.max(alength,blength);//Get the maximum length and minimum length of the strings
		int minLen=Math.min(alength,blength);
		int counter=2;
		int m=0;
		//if the difference between the two arrays is one and array b is shorter than array a.
			if(maxLen-minLen==1&&alength>blength){	
				point:
				for(int i=0;i<minLen;i++){  
					//System.out.println(m);
					counter=0;//Assign counter variable to 0
					if(!b_hold[i].equals(a_hold[m])){
						//Check if the words in the given location match if not increase counter by 1
						counter++;
						if(b_hold[i].equals(a_hold[m+1])){
							//Check if the word infront of that matches with the respective b[] string
							m++;
							continue point;
							//If they match start the loop again and make the counter 0
							}
						else{
							/*If not break the loop and increase counter because there have been
							two mismatches*/
							counter++;
							break;
							}
						
						}
					
					
				m++;	
					
				}
				
			if(counter<=1){
				return counter;
		}
				
	}
			
			
			m=0;
			
			if(maxLen-minLen==1&&alength<blength){/*As above but b[] length is greater
			than the length of a[]*/
				point:
				for(int i=0;i<minLen;i++){
					counter=0;
					if(!a_hold[i].equals(b_hold[m])){
						counter++;
						if(a_hold[i].equals(b_hold[m+1])){
							m++;
							continue point;
							}
						else{
							counter++;
							break;
							}
						}
					m++;
				}
			if(counter<=1){
				return counter;
		}
	}
			
			if(alength==blength){//If the lengths of the arrays are equal
				int count2=0;
				for(int i=0;i<alength;i++){
					if(a_hold[i].equals(b_hold[i])){
						//Increase the counter while the words match
						count2++;
					}
				}
				if(count2==alength-1){
					//If all the words match or there is only one mismatch then return 1
					return 1;
				}
			}
			
		return counter;
	}
	


}
