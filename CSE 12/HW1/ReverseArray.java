/* NAME: Victoria Mannina
 * PID: A10076744
 * login: cs12efp */

import java.util.*;
import java.io.*;

/** This class uses an array of strings to print the lines of a file
 *  in reverse order.*/
public class ReverseArray{

	public static void main(String[] args){
	 try{
	 if(args.length<1)
	 {
	  System.out.println("usage: ReverseArray <filename> "
	  + "(the program prints this when no file is given)");
	  return;
	 }
	 File file = new File(args[0]);
	 Scanner s1 = new Scanner(file);
	 String[] forward = new String[100];
	 int counter = 0;
	 int arraySize = 100;
	 while(s1.hasNextLine())
	 {
	  String in = s1.nextLine();
	  forward[counter] = in;
	  counter++;
	  if(counter>=arraySize)
	  {
	   arraySize = arraySize+100;
	   String[] bigger = new String[arraySize]; 
	   for(int k=0; k<arraySize-100; k++)
	   {
	    bigger[k] = forward[k];
	   }
	   forward = bigger;
	  }	  
	 }
	 for(int j = counter-1; j>=0; j--)
	 {
	  System.out.println(forward[j]);
	 }
}	
	catch (FileNotFoundException e)
	{
	 System.out.println("File Not found");
	}
}
}
