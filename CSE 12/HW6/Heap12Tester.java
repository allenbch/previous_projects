/*  Name: Victoria Mannina
 *  PID: A10076744
 *  login: cs12efp
 * */

import junit.framework.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  Title: class Heap12Tester
 *  Description: A JUnit tester for the Heap12 class.
 *  @author Victoria Mannina
 *  @version 1.0 
 * */
public class Heap12Tester extends TestCase
{
  // all the heaps I want to test on :D
  private Heap12<Integer> testerOne;
  private Heap12<Integer> testerTwo;
  private Heap12<Integer> testerThree;
  private Heap12<Integer> copyMin;
  private Heap12<Integer> copyMinCopy;
  private Heap12<Integer> copyMax;
  private Heap12<Integer> copyMaxCopy;
  private Heap12<Integer> testerFour;
  private Heap12<Integer> testerException;

  /** Constructor with only call to super */
  public Heap12Tester()
  {
    super();
  }

  /**
   *  Standard test fixture. Initializes the Heap12's and adds elements to
   *  some.
   * */
  public void setUp()
  {
    testerOne = new Heap12<Integer>();
    testerTwo = new Heap12<Integer>();
    testerException = new Heap12<Integer>();
    testerThree = new Heap12<Integer>(true);
    copyMin = new Heap12<Integer>();
    for(int i=0; i<5; i++)
    {
      copyMin.offer(new Integer(i+1));
    }
    copyMinCopy = new Heap12<Integer>(copyMin);
    copyMax = new Heap12<Integer>(true);
    for(int i=0; i<5; i++)
    {
      copyMax.offer(new Integer(i+1));
    }
    copyMaxCopy = new Heap12<Integer>(copyMax);
    testerFour = new Heap12<Integer>(10,false);

  }

  /** Test to check that the size() method is working */
  public void testSize()
  {
    assertEquals("Check if size method is working",5,copyMin.size());
  }

  /** Test to check that the peek() method is working */
  public void testPeek()
  {
    testerOne.offer(new Integer(5));
    assertEquals("Check if peek method is working",new Integer(5),testerOne.peek());
    testerOne.poll();
  }

  /** Test to check that the offer() method is working */
  public void testOffer()
  {
    assertEquals("Check if offer is working",0,testerOne.size());
    testerOne.offer(new Integer(3));
    assertEquals("Check if offer is working",1,testerOne.size());
  }

  /** Test to check that offer() works when called multiple times in a row on a
   * min heap*/
  public void testOfferMultiple()
  {
    testerTwo.offer(new Integer(10));
    assertEquals("Check Offer Multiple",new Integer(10),testerTwo.peek());
    testerTwo.offer(new Integer(2));
    assertEquals("Check Offer Multiple",new Integer(2),testerTwo.peek());
    testerTwo.offer(new Integer(5));
    assertEquals("Check Offer Multiple",new Integer(2),testerTwo.peek());    
  }

  /** Test to check that the offer() method is working when called multiple
   * times in a row on a max heap */
  public void testOfferMultipleMax()
  {
    testerThree.offer(new Integer(5));
    assertEquals("Check Offer Multiple Max",new Integer(5),testerThree.peek());
    testerThree.offer(new Integer(2));
    assertEquals("Check Offer Multiple Max",new Integer(5),testerThree.peek());
    testerThree.offer(new Integer(10));
    assertEquals("Check Offer Multiple Max",new Integer(10),testerThree.peek()); 
  }

  /** Test to check that the copy constructor is working properly.
   * Created Heap12 using copy constructor, modified original, checked to see
   * that new Heap is unchanged but original has changed.*/
  public void testCopyHeap()
  {
    copyMin.poll();
    assertEquals("Check Copy Heap Constructor",new Integer(2),copyMin.peek());
    assertEquals("Check Copy Heap Constructor",new Integer(1),copyMinCopy.peek());
  }

  /** Test to check that copy constructor also works on a max heap */
    public void testCopyHeapMax()
  {
    copyMax.poll();
    assertEquals("Check Copy Heap Constructor",new Integer(4),copyMax.peek());
    assertEquals("Check Copy Heap Constructor",new Integer(5),copyMaxCopy.peek());
  }

  /** Test to check that poll works when called multiple times in a row */
  public void testPollMultiple()
  {
   for(int i=0; i<9; i++)
    {
      testerFour.offer(new Integer(i+1));
    }
   assertEquals("Check size of testerFour",9,testerFour.size());
   for(int j=0; j<3; j++)
   {
    testerFour.poll();
   }
   assertEquals("Check size of testerFour after poll",6,testerFour.size());
  }

  /** Test that the iterator works when calling next() */
  public void testIteratorNext()
  {
    Iterator<Integer> iter;
    int counter = 0;
    iter = copyMinCopy.iterator();
    while(iter.hasNext())
    {
      counter++;
      assertEquals("Iterator Counting",new Integer(counter), iter.next());          
    }
  }

  /** Test that the iterator works when calling remove() */
  public void testIteratorRemove()
  {
    Iterator<Integer> iter2;
    int counter = 0;
    iter2 = copyMaxCopy.iterator();
    while(counter<3)
    {
      counter++;
      iter2.next();
    }
    iter2.remove();
    assertEquals("Iterator Remove",4, copyMaxCopy.size());          
  }

  /** Test to confirm that offer() throws an exception when trying to offer a
   * null element */
  public void testOfferException()
  {
    try
    {
      testerException.offer(null);
      fail("Should have generated an exception");
    }
    catch(NullPointerException e)
    {
      // normal
    }
  }

  /** Test to confirm that the iterator method next() throws an exception when
   * called on an empty heap */
  public void testItNextException()
  {
    try
    {
      Iterator<Integer> iter3;
      iter3 = testerException.iterator();
      iter3.next();
      fail("Should have generated an exception");
    }
    catch(NoSuchElementException e)
    {
      // normal
    }
  }

  /** Test to confirm that the iterator method remove() throws an exception
   * when called before the next() method is called */
  public void testItRemoveException()
  {
    try
    {
      Iterator<Integer> iter4;
      iter4 = testerException.iterator();
      iter4.remove();
      fail("Should have generated an exception");
    }
    catch(IllegalStateException e)
    {
      // normal
    }
  }
}
