/* Name: Victoria Mannina
 * PID: A10076744
 * login: cs12efp
 * */

import junit.framework.*;
/**
 * Title: class BoundedDequeTester
 * Description: A JUnit tester for the Deque12 class.
 * @author Victoria Mannina
 * @version 1.0 29-April-2014
 * */
public class BoundedDequeTester extends TestCase
{
  private Deque12<Integer> empty; // used as empty Deque12 to run tests on
  private Deque12<Integer> empty2; // another empty Deque12 for special cases
  private Deque12<Integer> empty3;  // on empty Deque12's
  private Deque12<Integer> full; // used for special tests on full Deque12's
  private Deque12<Integer> mod; // used for testing adding, deleting, peeking
  static final int FILLER = 10;
  private int CAPACITY = 10;

  /** Constructor with only call to super */
  public BoundedDequeTester()
  {
    super();
  }

  /**
   * Standard Test Fixture. Initializes the Deque12's and fills the Deque12
   * that needs to be full for testing.
   * */
  public void setUp()
  {
    empty = new Deque12<Integer>(CAPACITY);
    empty2 = new Deque12<Integer>(CAPACITY);
    empty3 = new Deque12<Integer>(CAPACITY);
    

    full = new Deque12<Integer>(CAPACITY);
    for(int i = FILLER; i>0; i--)
    {
      full.addFront(new Integer(i));
    }
    mod = new Deque12<Integer>(5);
  }
  
  /** Test to check if capacity() method is working on Deque12's of varying
   * capacities */
  public void testCapacity()
  {
    assertEquals("Check empty Capacity",10,empty.capacity());
    assertEquals("Check full Capacity",10,full.capacity());
    assertEquals("Check mod Capacity",5,mod.capacity());
  }

  /** Test to check that size returns 0 for an empty Deque12 */
  public void testSizeEmpty()
  {
    assertEquals("Check empty Size",0,empty.size());
  }

  /** Test to check that size is working on non-empty Deque12's */
  public void testSizeNonEmpty()
  {
    assertEquals("Check full Size",10,full.size());
    mod.addFront(new Integer(7));
    assertEquals("Check mod Size",1,mod.size());
    mod.removeFront();
  }

  /** Test to verify that addFront() method is working on an empty Deque12 */
  public void testAddFrontEmpty()
  {
    empty.addFront(new Integer(3));
    assertEquals("Check new empty Size",1,empty.size());
    assertEquals("Check value at front of empty",new Integer(3),
      empty.peekFront());
  }

  /** 
   * Test to verify that addFront() returns false when called on 
   * a full Deque12. 
   * */
  public void testAddFrontCap()
  {
    assertEquals("Check if we can add to full deque",
      full.addFront(new Integer(6)),false);
  }

  /** 
   * Test to check that addFront() throws a NullPointerException if the
   * element trying to be added is null. 
   * */
  public void testAddFrontException()
  {
    try
    {
      empty.addFront(null);
      fail("Should have generated an exception");
    }
    catch(NullPointerException e)
    {
      // normal
    }
  }

  /** 
   * Test that addFront() is working properly when called mutliple times in a
   * row. Verifies by using size() and peekFront()/peekBack() methods.
   * */
  public void testAddFrontMultiple()
  {
    assertEquals("Check that mod initially empty",0,mod.size());
    mod.addFront(new Integer(1));
    mod.addFront(new Integer(8));
    mod.addFront(new Integer(4));
    assertEquals("Check that mod is now size 3",3,mod.size());
    assertEquals("Check that front is 4",new Integer(4),mod.peekFront());
    assertEquals("Check that back is 1",new Integer(1),mod.peekBack());
    mod.removeFront();
    mod.removeFront();
    mod.removeFront();
  }

  /** Test to verify that addBack() is working on an empty Deque12 */
  public void testAddBackEmpty()
  {
    empty2.addBack(new Integer(3));
    assertEquals("Check new empty2 Size",1,empty2.size());
    assertEquals("Check value at front of empty2",new Integer(3),
      empty2.peekFront());
  }

  /** 
   * Test to verify that addBack() returns false if called
   * on a full Deque12 
   * */
  public void testAddBackCap()
  {
    assertEquals("Check if we can add to full deque",
      full.addBack(new Integer(6)),false);  
  }

  /** 
   * Test that addBack() throws a NullPointerException if the element trying
   * to be added is null.
   * */
  public void testAddBackException()
  {
     try
    {
      empty2.addBack(null);
      fail("Should have generated an exception");
    }
    catch(NullPointerException e)
    {
      // normal
    }

  }

  /** 
   * Test to verify that addBack() works correctly when called multiple times
   * in a row. Verifies by using size() and peekFront()/peekBack() methods.
   * */
  public void testAddBackMultiple()
  {
    assertEquals("Check that mod initially empty",0,mod.size());
    mod.addBack(new Integer(1));
    mod.addBack(new Integer(8));
    mod.addBack(new Integer(4));
    assertEquals("Check that mod is now size 3",3,mod.size());
    assertEquals("Check that front is 4",new Integer(1),mod.peekFront());
    assertEquals("Check that back is 1",new Integer(4),mod.peekBack());
    mod.removeFront();
    mod.removeFront();
    mod.removeFront();
  }

  /** Test that removeFront() returns null if called on an empty Deque12 */
  public void testRemoveFrontEmpty()
  {
    assertEquals("Check that we can't remove from empty",
      empty3.removeFront(),null);  
  }

  /** 
   * Test to verify removeFront() works properly when call on a non-empty
   * Deque12
   * */
  public void testRemoveFrontNonEmpty()
  {
    assertEquals("Check that mod initially empty",0,mod.size());
    mod.addFront(new Integer(7)); 
    assertEquals("Check that mod is now size 1",1,mod.size());
    mod.removeFront();
    assertEquals("Check that mod is empty again",0,mod.size());
  }

  /** 
   * Test that removeFront() works properly when called multiple times in a
   * row. Verified using size() method. 
   * */
  public void testRemoveFrontMultiple()
  {
    assertEquals("Check that mod initially empty",0,mod.size());
    mod.addFront(new Integer(7)); 
    mod.addFront(new Integer(3));
    mod.addFront(new Integer(4));
    assertEquals("Check that mod is now size 3",3,mod.size());
    mod.removeFront();
    mod.removeFront();
    mod.removeFront();
    assertEquals("Check that mod is empty again",0,mod.size());
  }

  /** 
   * Test to verify that removeBack() returns null if called on an empty
   * Deque12
   * */
  public void testRemoveBackEmpty()
  {
    assertEquals("Check that we can't remove from empty",
      empty3.removeBack(),null);    
  }

  /** 
   * Test to verify that removeBack() works properly 
   * on non-empty Deque12's 
   * */
  public void testRemoveBackNonEmpty()
  {
    assertEquals("Check that mod initially empty",0,mod.size());
    mod.addFront(new Integer(7)); 
    assertEquals("Check that mod is now size 1",1,mod.size());
    mod.removeBack();
    assertEquals("Check that mod is empty again",0,mod.size());
  }

  /** 
   * Test to verify that removeBack() works properly when called several times
   * in a row.
   * */
  public void testRemoveBackMultiple()
  {
    assertEquals("Check that mod initially empty",0,mod.size());
    mod.addFront(new Integer(7)); 
    mod.addFront(new Integer(3));
    mod.addFront(new Integer(4));
    assertEquals("Check that mod is now size 3",3,mod.size());
    mod.removeBack();
    mod.removeBack();
    mod.removeBack();
    assertEquals("Check that mod is empty again",0,mod.size());
  }

  /** 
   * Test to verify that peekFront() returns null when called on an empty
   * Deque12
   * */
  public void testPeekFrontEmpty()
  {
    assertEquals("Check that we can't peek at empty deque",
      empty3.peekFront(),null);  
  }

  /** 
   * Test to verify that peekFront() works correctly when called on a
   * non-empty Deque12
   * */
  public void testPeekFrontNonEmpty()
  {
    mod.addFront(new Integer(7)); 
    assertEquals("Check to peek front element",new Integer(7),
      mod.peekFront());
  }

  /** 
   * Test to verify that peekBack() returns null when called on an empty
   * Deque12
   * */
  public void testPeekBackEmpty()
  {
    assertEquals("Check that we can't peek at empty deque",
      empty3.peekBack(),null);    
  }

  /** 
   * Test to verify that peekBack() works correctly when called 
   * on a non-empty Deque12
   * */
  public void testPeekBackNonEmpty()
  {
    mod.addBack(new Integer(2));   
    assertEquals("Check to peek back element",new Integer(2),
      mod.peekBack());
  }

}
