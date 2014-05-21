/* Locality Sensitive Hashing: Collector
* Main method
*Author:Sandaruwan Gunasinghe
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class collector {
	
	public static ArrayList<String> lines = new ArrayList<String>();
	private static Scanner s;
    static long time=0;
	public static String f_time;
	
    public static String[] collect(String e) throws FileNotFoundException{
    	
    	long start_time=System.currentTimeMillis();
    	
        int k=3;//Shingle size
        int hash_funcs=24;//Number of Hash Functions
        int band=6;//Number of band
        int rows=4;//Number of rows
        double jaccard=1;//Threshold value
        String line;
        String[] holder = null;
        
        ArrayList<String> hold=new ArrayList<String>();
        ArrayList<String> lines = new ArrayList<String>();
        HashSet<ArrayList<Integer>> hs = new HashSet<ArrayList<Integer>>();
        
        s = new Scanner(new File(e));
        
        
        
        while(s.hasNextLine()){
             line=s.nextLine();
             line=line.substring(line.indexOf(':')+1, line.length());
             lines.add(line);
        }
        
        String[] str_arr=lines.toArray(new String[lines.size()]);
        
        if(str_arr.length>1000){
        	hash_funcs=24;
        	band=8;
        	rows=3;
        }
       
        
        shingle shing=new shingle();
        minhash min=new minhash();
        
        long test1=System.currentTimeMillis();
        ArrayList<ArrayList<Integer>> shingle_set = shing.construct_sets(str_arr, k);
        System.out.println(System.currentTimeMillis()-test1+" ms construct_sets");
        
        long test2=System.currentTimeMillis();
        int[][] sorted_mat = shing.sort_shingles(str_arr, shingle_set);
        System.out.println(System.currentTimeMillis()-test2+" ms sort_shingles");
        
        long test3=System.currentTimeMillis();
        int[][] SIG = min.minhash_signatuers(sorted_mat,hash_funcs);
        System.out.println(System.currentTimeMillis()-test3+" ms minhash_sig");
        
        long test4=System.currentTimeMillis();
        ArrayList<ArrayList<Integer>> candidates = min.LS_Hashing(SIG, band, rows, jaccard);
        System.out.println(System.currentTimeMillis()-test4+" ms LS_hash");
        
        hs.addAll(candidates);
        candidates.clear();
        candidates.addAll(hs);
        
        for(int i=0;i<candidates.size();i++){
            if(editDistance.editDist(str_arr[(Integer)candidates.get(i).get(0)],str_arr[(Integer)candidates.get(i).get(1)])<=1){
                String temp="S"+((Integer)candidates.get(i).get(0)+1)+" S"+((Integer)candidates.get(i).get(1)+1);
                hold.add(temp);
                
            }
            
        }
        
        long end_time=System.currentTimeMillis()-start_time;
        String time=Long.toString(end_time);
        time=time+" ms";
        hold.add(time);
        holder=hold.toArray(new String[hold.size()]);
        return holder;
        
       
    }

    
    
}
