/* Name: Victoria Mannina
 * PID: A10076744
 * login: cs12efp
 * */

import java.util.*;

/**
 * Title: class Stack12
 * Description: A java class that implements the BoundedStack interface using
 * an instance of Deque12
 * @author Victoria Mannina
 * @version 1.0 29-April-2014
 * */
public class Stack12<E> implements BoundedStack<E> {

  private BoundedDeque<E> dStack; // instance of a double-ended queue

  /** 
   * Constructor with single int argument to specify the capacity of the
   * Stack12. Initializes instance of a Deque12 at this capacity to use as a
   * stack.
   * @param setCap the capacity of the Stack12
   * */
  public Stack12(int setCap)
  {
    dStack = new Deque12<E>(setCap);
  }

  /**
   * Returns the capacity of this Stack12, that is, the maximum number of
   * elements it can hold.
   * @return the capacity of this Stack12
   * */
  public int capacity()
  {
    return dStack.capacity();
  }

  /**
   * Returns the element at the top fot his Stack12, or null if there was no
   * such element. Uses call on the instance of the Deque12
   * @return the element at the top, or null if the size was zero
   * */
  public E peek()
  {
    return dStack.peekFront();
  }

  /**
   * Removes the element at the top of this Stack12. Returns the element
   * removed, or null if there was no such element.
   * @return the element removed, or null if the size was zero
   * */
  public E pop()
  {
    return dStack.removeFront();
  }

  /**
   * Adds the specified element to the top of this Stack12. Returns true if the
   * operation succeeded, else false. Throws NullPointerException using the
   * call to the Deque12 method if the element trying to be added is null.
   * @param e the element to be added, else false
   * @return true if the element was added, else false
   * */
  public boolean push(E e)
  {
    return dStack.addFront(e);
  }

  /**
   * Returns the number of elements in this Stack12.
   * @return the number of elements in this Stack12
   * */
  public int size()
  {
    return dStack.size();
  }
}
