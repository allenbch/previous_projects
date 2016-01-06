/* Name: Victoria Mannina
 * PID: A10076744
 * login: cs12efp
 * */

import java.util.*;

/**
 * Title: class Queue12
 * Description: A java class that implements the BoundedQueue interface using
 * an instance of Deque12
 * @author Victoria Mannina
 * @version 1.0 29-April-2014
 * */
public class Queue12<E> implements BoundedQueue<E> {

  private BoundedDeque<E> dQueue; // instance of a double-ended queue

  /**
   * Constructor with a sinlge int argument to specify the capacity of the
   * Queue12. Initializes the instance of the Deque12 at this capacity to use
   * as a stack.
   * @param setCap the capacity of the Queue12
   * */
  public Queue12(int setCap)
  {
    dQueue = new Deque12<E>(setCap);
  }

  /**
   * Returns the capacity of this Queue12, that is, the maximum number of
   * elements it can hold.
   * @return the capacity of this Queue12
   * */
  public int capacity()
  {
    return dQueue.capacity();
  }

  /**
   * Returns the element at the head of this Queue12, or null if there was no
   * such element.
   * @return the element at the head, or null if size was zero
   * */
  public E peek()
  {
    return dQueue.peekBack();
  }

  /**
   * Removes the element at the head of this Queue12. Returns the element
   * removes, or null if there was no such element.
   * @return the element removed, or null if the size was zero
   * */
  public E dequeue()
  {
    return dQueue.removeBack();
  }

  /**
   * Adds the specified element to the tail of this Queue12. Returns true if
   * the operation succeeded, else false. If the element trying to be enqueued
   * is null, it throws a NullPointerException within the method called on the
   * instance of the Deque12.
   * @param e the element to add to the queue
   * @return true if the element was added, else false
   * */
  public boolean enqueue(E e)
  {
    return dQueue.addFront(e);
  }

  /**
   * Returns the number of elements in this Queue12.
   * @return the number of elements in this Queue12
   * */
  public int size()
  {
    return dQueue.size();
  }
}
