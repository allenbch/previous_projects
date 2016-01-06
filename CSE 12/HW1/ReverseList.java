/* NAME: Victoria Mannina
 * PID: A10076744
 * login: cs12efp */

import java.util.*;
import java.io.*;

/** This class uses a LinkedList of type string to print out a text file
 *  in reverse order.*/
public class ReverseList{

	public static void main(String[] args){
	 try{
	 if(args.length<1)
	 {
	  System.out.println("usage: ReverseList <filename> "
	  + "(the program prints this when no file is given)");
	  return;
	 }
	 File file = new File(args[0]);
	 Scanner s1 = new Scanner(file);
	 LinkedList<String> forwardList = new LinkedList<String>();
	 int counter = 0;
	 while(s1.hasNextLine())
	 {
	  String in = s1.nextLine();
	  forwardList.add(in);
	  counter++;
	 }
	 for(int j = counter-1; j>=0; j--)
	 {
	  System.out.println(forwardList.get(j));
	 }
	}	
	catch (FileNotFoundException e)
	{
	 System.out.println("File Not found");
	}
}
}
