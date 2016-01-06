/*  CSE 12 Homework 7
 *  Victoria Mannina
 *  PID: A10076744
 *  Section A00
 *  5/19/14
 * */

/**
 * TQTree.java
 * A Java class that supports a Binary Tree that plays the game of twenty questions
 * 
 * @author Victoria Mannina
 * @version 3.0
 * @date May 20, 2014
 */

import java.io.*;
import java.util.Scanner;
import java.text.ParseException;
import java.util.LinkedList;

public class TQTree {
  private TQNode root;
  
  /** Inner class that supports a node for a twenty questions tree.
    * You should not need to change this class. */
  class TQNode {
    /*  You SHOULD NOT add any instance variables to this class */
    TQNode yesChild;  // The node's right child
    TQNode noChild;   // The node's left child
    String data;      // A question (for non-leaf nodes) 
                      // or an item (for leaf nodes)
    
    int idx;        // index used for printing
     
    /** Make a new TWNode 
      * @param data The question or answer to store in the node. 
      */
    public TQNode( String data )
    {
      this.data = data;
    }    
    
    /** Setter for the noChild 
      * @param noChild The new left (no) child
      */
    public void setNoChild( TQNode noChild )
    {
      this.noChild = noChild;
    }
    
    
    /** Setter for the yesChild 
      * @param yesChild The new right (yes) child
      */
    public void setYesChild( TQNode yesChild )
    {
      this.yesChild = yesChild;
    }
    
        
    /** Getter for the yesChild 
      * @return The node's yes (right) child
      */
    public TQNode getYesChild()
    {
      return this.yesChild;
    }

    /** Getter for the noChild 
      * @return The node's no (left) child
      */
    public TQNode getNoChild()
    {
      return this.noChild;
    }
    
    /** Getter for the data
      * @return The data stored in this node
      */
    public String getData()
    {
      return this.data;
    }
    
    /** Setter for the index
      * @param idx index of this for printing 
      */
    public void setIndex(int idx) {
      this.idx = idx;
    }
    
    /** get the index
      * @return idx index of this for printing 
      */
    public int getIndex() {
      return this.idx;
    }
  }  // End TQNode

  
  /** Builds a starter TQ tree with 1 question and 2 answers */
  public TQTree()
  {
    // just call helper method to create default tree
    buildDefaultTree();
  }
  
  /** Builds a new TQTree by reading from a file 
    * @param filename The file containing the tree
    * @throws FileNotFoundException if the file cannot be found or read.
    * */
  public TQTree( String filename )
  {
    File f = new File( filename );
    LineNumberReader reader;
    try {
      reader = new LineNumberReader( new FileReader( f ));
    } catch ( FileNotFoundException e ) {
      System.err.println( "Error opening file " + filename );
      System.err.println( "Building default Question Tree." );
      
      buildDefaultTree();
     // DONE: Add code to build the default tree here
      
      return;
    }
    
    // DONE: Add code to read in the tree from a file here.  
    // Note: You will likely find the private method buidSubtree helpful here.  
    // Also, consider making your own private helper method to do most of the work here.
   
    try {
      root = buildSubtree(reader);
      reader.close();
    } catch ( IOException e ) {
      System.err.println( "An error occured while closing file " + filename );
    }
      catch(ParseException e)
      {
        System.err.println("Parse error");
      }
    
  }
  
  /** Play one round of the game of Twenty Questions using this game tree 
    * Augments the tree if the computer does not guess the right answer
    */ 
  public void play()
  {
    boolean playGame = true; // until you get to a leaf, continue down the tree
    boolean lastYes = true; // instance variable to track if the answer to the
                            // last question was yes or no
    TQNode parent = root; // current node, starts at root
    TQNode grand = null; // node before current (parent)
    String s; // used for saving user input
    Scanner input = new Scanner(System.in); // to scan for user input

    while(playGame==true)
    {
      // check to see if leaf
      // if it is not a leaf, it is a Question
      if(!(parent.getNoChild() == null))
      {
        grand = parent; // move the current to the grandparent
        System.out.println(parent.getData()); // ask question
        s = input.next(); // get response
        // move along the tree depending on their response
        // nonaffirmative input treated as "no" response
        if(s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes"))
        {
          parent = parent.getYesChild();
          lastYes = true;
        }
        else
        {
          parent = parent.getNoChild();
          lastYes = false;
        }
      }
      // if it is a leaf, it is an Answer
      else
      { 
        // Ask if we got it right
        System.out.println("Is it " + parent.getData() + "?");
        s = input.next(); 
        // if correct, yay! if not, modify tree
        if(s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes"))
        {
          System.out.println("Got it!");
        }
        else
        {
          input.nextLine(); // reset input buffer b/c last call to next()
          System.out.println("Ok, what was it?");
          s = input.nextLine();
          TQNode newA = new TQNode(s); // new answer node

          System.out.println("Give me a question that would distinguish " 
          + parent.getData() + " from " + newA.getData());
          s = input.nextLine();
          TQNode newQ = new TQNode(s); // new question node
          // use lastYes to change the correct grandparent pointer
          if(lastYes)
          {
            grand.setYesChild(newQ);
          }
          else
          {
            grand.setNoChild(newQ);
          }

          System.out.println("And would the answer to the question for "
          + newA.getData() + " be yes or no?");
          s = input.next();
          // change newQ pointers based on input
          if(s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes"))
          {
            newQ.setNoChild(parent);
            newQ.setYesChild(newA);
          }
          else
          {
            newQ.setNoChild(newA);
            newQ.setYesChild(parent);
          }
          // set these to default just in case
          parent.setNoChild(null);
          parent.setYesChild(null);
          newA.setNoChild(null);
          newA.setYesChild(null);
        }
        playGame = false; // we found a leaf, so stop going down tree
      }
    }
  }
  
  /** Save this Twenty Questions tree to the file with the given name
    * @param filename The name of the file to save to
    * @throws FileNotFoundException If the file cannot be used to save the tree
    */
  public void save( String filename ) throws FileNotFoundException
  {
    // DONE: Implement this method
    try{
    File newF = new File(filename); // create/overwrite file
    PrintWriter output = new PrintWriter(newF); // create writer
    saveHelper(root,output); // call helper to write to file
    output.close(); // close writer
    }
    catch(FileNotFoundException e)
    {
       throw new FileNotFoundException();
    }
  }  
  
  /** Print a level-order traversal of the tree to standard out (System.out)
    * */ 
  public void print()
  {
    // Use a LinkedList as a Queue
    LinkedList<TQNode> list = new LinkedList<TQNode>();
    list.add(root);
    TQNode current = root;
    int index = 0;

    while(list.size() != 0)
    {
      current.setIndex(index);
      // add children if they exist
      if(current.getNoChild()!=null)
      {
        list.add(0,current.getNoChild());
      }
      if(current.getYesChild()!=null)
      {
        list.add(0,current.getYesChild());
      }
      // remove current
      list.remove(list.size()-1);
      // if it is not empty, set current to next element in queue
      if(list.size()!=0)
      {
      current = list.get(list.size()-1);
      }
      index++;
    }
    
    // now list is empty, readd root
    list.add(root);
    current = root;
    String str1;
    String str2;

    while(list.size()!=0)
    {
      // if there is no child, the child's child is null so index is null
      if(current.getNoChild()==null)
      {
        str1 = "null";
      }
      // otherwise add child and create string with it's index
      else
      {
        list.add(0,current.getNoChild());
        str1 = Integer.toString(current.noChild.idx);
      }
      // same for yesChild as above
      if(current.getYesChild()==null)
      {
        str2 = "null";
      }
      else
      {
        list.add(0,current.getYesChild());
        str2 = Integer.toString(current.yesChild.idx);
      }
      System.out.format("%d: '%s' no:(%s) yes:(%s)\n", current.getIndex(),current.getData(),str1,str2);
      list.remove(list.size()-1);
      if(list.size()!=0)
      {
        current = list.get(list.size()-1);
      }
    }
  }
  
    // PRIVATE HELPER METHODS
  // You will likely want to add a few more private helper methods here.   

    /**
     *  Used to write to a file recursively for the tree.
     *  @param current the TQNode to print the data to the file
     *  @param output the PrintWriter to use to print to the file
     * */
    private void saveHelper(TQNode current, PrintWriter output)
  {
    // base case -> current node is null, fell off tree
    // otherwise continue down tree in preorder algorithm
    if(current!=null)
    {
      // if it doesn't have a child, it is an Answer
      if(current.getNoChild()==null)
      {
        output.print("A:");
      }
      // otherwise it is a Question
      else
      {
        output.print("Q:");
      }
      // print data of current then recursive calls to children
      output.println(current.getData());
      saveHelper(current.getNoChild(),output);
      saveHelper(current.getYesChild(),output);
    }
    return;
  }

  /**
   *  Used to create a tree with default values if you do not use a file or if
   *  there is a problem with a file you are trying to make the tree with. Has
   *  one question and two answers.
   * */
  private void buildDefaultTree()
  {
    root = new TQNode("Is it bigger than a breadbox?");
    TQNode nChild = new TQNode("spam");
    TQNode yChild = new TQNode("a computer scientist");

    root.setYesChild(yChild);
    root.setNoChild(nChild);
    nChild.setNoChild(null);
    nChild.setYesChild(null);
    yChild.setNoChild(null);
    yChild.setYesChild(null);
  }

  /** Recursive method to build a TQTree by reading from a file.
    * @param reader A LineNumberReader that reads from the file
    * @return The TQNode at the root of the created tree
    * @throws ParseException If the file format is incorrect
    */
  private TQNode buildSubtree( LineNumberReader reader ) throws ParseException 
  {
    
    String line;
    try {
      line = reader.readLine();
    }
    catch ( IOException e ) {
      throw new ParseException( "Error reading tree from file: " + e.getMessage(),
                               reader.getLineNumber() );
    }
    
    if ( line == null ) {
      // We should never be calling this method if we don't have any more input
      throw new ParseException( "End of file reached unexpectedly", reader.getLineNumber() );
    }
    
    String[] lineSplit = line.split( ":", 2 );
    String qOrA = lineSplit[0];
    String data = lineSplit[1];
    
    TQNode subRoot = new TQNode( data );
    if ( qOrA.equals( "Q" ) ) {
      subRoot.setNoChild( buildSubtree( reader ) );
      subRoot.setYesChild( buildSubtree( reader ) );
    }    
    return subRoot;
  }
}
