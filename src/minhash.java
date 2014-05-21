/* Locality Sensitive Hashing: Minhash and LSH
 * Add Minhashing and LSH techniques
 *Author:Sandaruwan Gunasinghe
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class minhash{
	
	static int length_bucket=101;//The length should be a prime number
	
	public ArrayList<ArrayList<ArrayList<Integer>>> init_array_buckets(int bands){//Initializing the Array Buckets
		
		//Initialize a holder for all the array buckets
		ArrayList<ArrayList<ArrayList<Integer>>> array_buckets=new ArrayList<ArrayList<ArrayList<Integer>>>();
		//Initialize a holder for buckets
		ArrayList<ArrayList<Integer>> buckets=null;
		//Initialize a bucket
		ArrayList<Integer> bucket=null;
	
		for(int i=0;i<bands;i++){
			buckets=new ArrayList<ArrayList<Integer>>();
				for(int j=0;j<length_bucket;j++){	
					bucket=new ArrayList<Integer>();
					buckets.add(bucket);
			}
			array_buckets.add(buckets);
		}
		
		return array_buckets;
	}
	
	public int hash_min(int x,int var,int cond,int length){//Get the minhash value using generated hash functions
		
		return (var*x+cond)%length;
		
	}
	
	public ArrayList<ArrayList<Integer>> generate_hash_func(int n){//Generate n number of hash functions
		
		ArrayList<Integer> temp_arr = null;
		ArrayList<ArrayList<Integer>> hash_func=new ArrayList<ArrayList<Integer>>();//Create an arraylist to hold the hash variables 
		int var=0;
		int cond=0;
		Random random=new Random();//Create a random generator
		
		for(int i=0;i<n;i++){	
			
			temp_arr=new ArrayList<Integer>();
			
			var=random.nextInt(1000);//Get a random integer
			cond=random.nextInt(1000);	
			
			temp_arr.add(var);//Add the random integers to an ArrayList as couples
			temp_arr.add(cond);	
			
		    hash_func.add(temp_arr);//Add the couples in to another ArrayList
		
		}
		return hash_func;
		
	}
	
	public int[][] minhash_signatuers(int matrix[][],int n){
		
		if(matrix[0].length<10){
			length_bucket=3;
		}
		
		if(matrix[0].length>10&&matrix[0].length<100){
			length_bucket=53;
		}
		
		if(matrix[0].length>100&&matrix[0].length<1000){
			length_bucket=503;
		}
		
		if(matrix[0].length>1000){
			length_bucket=5003;
		}
		

		int var=0;
		int cond=0;
		int mat_length=matrix.length;
			
		ArrayList<ArrayList<Integer>> hash_func=generate_hash_func(n);
		ArrayList<Integer> val=null;
		ArrayList<ArrayList<Integer>> hash_val=new ArrayList<ArrayList<Integer>>();
		
		int infinity=Integer.MAX_VALUE;//Assumed infinity to fill the signature table
		
		int SIG[][]=new int[n][matrix[0].length];//Initialize the signature matrix for size [n,number of shingle sets]
		
		for(int i=0;i<hash_func.size();i++){
			val=new ArrayList<Integer>();
			var=hash_func.get(i).get(0);
			cond=hash_func.get(i).get(1);
			for(int j=0;j<matrix.length;j++){
				val.add(hash_min(j,var,cond,mat_length));
			//Create the minhash value of each function for each row and store them in an ArrayList
			
			}
			hash_val.add(val);
			//The size of the ArrayList would be [n,matrix.length]
			//n=Number of lines Each line for each hash function	
			//inside each array that is in hash_val	2D array it stores the value of minhash value of each row
			//hash_val[0,0] is the h1 values of line 1
			//hash_val[1,0] is the h2 value of line 1 like wise	
		}
	
		
		for(int i=0;i<SIG.length;i++){
			for(int j=0;j<SIG[0].length;j++){
				SIG[i][j]=infinity;//Make all the elements in the signature matrix to infinity
			}
		}
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				
				if(matrix[i][j]!=0){//Check if the corresponding value in the characteristic matrix is 0 or 1
					for(int k=0;k<n;k++){//if it is 1 add the minimum value of minhash value of the generated minhashes to Signature Matrix
						int high=hash_val.get(k).get(i);
						SIG[k][j]=Math.min(SIG[k][j],high);
						//System.out.print(SIG[k][j]+" ");
						
					}
					//System.out.println();
				}
				
			}
		}
	    //System.out.println(SIG);
		return SIG;
		
	}
	
	
	public ArrayList<ArrayList<Integer>> LS_Hashing(int SIG[][],int bands,int rows,double jaccard) {
		
		if(bands*rows!=SIG.length){
			throw new Error("Bands * Rows must be equal to the length of the Signature Matrix");
		}
		
		ArrayList<Integer> temp=new ArrayList<Integer>();
		ArrayList<ArrayList<ArrayList<Integer>>> array_buckets=init_array_buckets(bands);
		ArrayList<ArrayList<Integer>> buckets =new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> candidates=new ArrayList<ArrayList<Integer>>();
		
		int band[][]=new int[rows][SIG[0].length];//Initialize a band to hold rows of hashed signature table
		int index=0;
		int sum=0;
		int key=0;
		int counter=0;
		
		
		for(int i=0;i<bands;i++){
				buckets= array_buckets.get(i);
			for(int m=0;m<rows;m++){
				counter++;
				for(int j=index;j<index+rows&&j<SIG.length;j++){
					//for(int k=0;k<SIG[0].length;k++){
						//band[m][k]=SIG[j][k]; //Get a line from the signature matrix and put it in the band
						band[m]=SIG[j];
					}	    		
				//}
				
				if(counter==rows){	
					counter=0;
					for(int l=0;l<band[0].length;l++){
						for(int k=0;k<band.length;k++){
							sum+=band[k][l];
							continue;
						}
						key=sum%buckets.size();
						buckets.get(key).add(l);//Get the key value using the prime number
						sum=0;
					}
					
				}
			
				
			}	
			//System.out.println(length_bucket);
		    //System.out.println(buckets);
		    
			for(int n=0;n<buckets.size();n++){
				if(buckets.get(n).toArray().length>1){
					for(int m=0;m<buckets.get(n).size();++m){
						for(int o=m+1;o<buckets.get(n).size();++o){
							temp=new ArrayList<Integer>();
							temp.add(buckets.get(n).get(m));
							temp.add(buckets.get(n).get(o));
							if(!candidates.contains(temp)){//If the pair is not in the candidate ArrayList add them to it
								Collections.sort(temp);//Sort the temp values
								candidates.add(temp);
							}
						}
					}
					//Get the pairs that are in the same bucket
					
					
				}
			}		
				
			index+=rows;	
		  }
		
	    candidates=Jaccard(candidates,SIG,jaccard);
		return candidates;
	}
	
	public ArrayList<ArrayList<Integer>> Jaccard(ArrayList<ArrayList<Integer>> candidates,int SIG[][],double thresh ){
	    
		ArrayList<ArrayList<Integer>> real_cand=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp=new ArrayList<Integer>();
		
		double counter=0;
		double thresh_val=0;
		
		for(int k=0;k<candidates.size();k++){
			
			temp=new ArrayList<Integer>();
			for(int j=0;j<SIG.length;j++){
					if(SIG[j][candidates.get(k).get(0)]==SIG[j][candidates.get(k).get(1)]){	
						counter++;
						
					}
					
				}
			
			thresh_val=counter/SIG.length;
			if(thresh_val>thresh){
				
				  temp.add(candidates.get(k).get(0));
				  temp.add(candidates.get(k).get(1));
				  
				  real_cand.add(temp);
				  
				}
			}
		return real_cand;
		
	}
	
}
