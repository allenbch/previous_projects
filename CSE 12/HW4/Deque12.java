/* Name: Victoria Mannina
 * PID: A10076744
 * login: cs12efp
 * */

import java.util.*;
/** 
 * Title: class Deque12
 * Description: A Java class that implements the BoundedDeque interface using
 * a circular array 
 * @author Victoria Mannina
 * @version 1.0 29-April-2014
 * */
public class Deque12<E> implements BoundedDeque<E> {

    private int size; // number of elements the deque is currently holding
    private int front; // index of element at the front
    private int back; // index of element at the back
    private int capacity; // current capacity of the deque
    private ArrayList<E> arrList; // the array list used as a circular array

    /** 
     * Constructor to create a Deque12 object with a max capacity at the
     * argument passes (setCap). Does not accept negative int for capacity.
     * @param setCap specifies the capacity of the Deque12
     * @throws IllegalArgumentException if the specified capacity is negative
     * */
    public Deque12(int setCap)
    {
      if(setCap<0)
      {
        throw new IllegalArgumentException();
      }
      // initialize the member variables
      size = 0;
      front = 0;
      back = 0;
      capacity = setCap;
      arrList = new ArrayList<E>(setCap);
      // in order to keep the capacity firm, we add null elements to the
      // circular array and then use the set method later to modify them
      for(int i = 0; i<capacity; i++)
      {
        arrList.add(null);
      }
    }

    /** 
     * Returns the capacity of this Deque12, that is, the maximum number
     * of elements it can hold
     * @return the capacity of this Deque12
     * */
    public int capacity()
    {
      return capacity;
    }

    /**
     * Returns the number of elements in this Deque12.
     * @return the number of elements in this Deque12
     * */
    public int size()
    {
      return size;
    }

    /**
     * Adds the specified element to the front of this Deque12. Returns true if
     * the operation succeeded, else false. The Deque12's size must be less
     * than its capacity. 
     * @param e the element to add to the front of the list
     * @return true if the element was added, else false (false if size is at
     * capacity)
     * @throws NullPointerException if teh specified element is null, and the
     * size is less than capacity
     * */
    public boolean addFront(E e)
    {
      // check first to see if size is at capacity
      if(size == capacity)
      {
        return false;
      }

      if(e == (E) null)
      {
        throw new NullPointerException();
      }

      // special case for an empty array because we want our front and back to
      // the new element. This is because we want front and back to always
      // point to the frontmost and backmost element. If we decremented front,
      // it would continue to point to an empty (null) element.
      if(size == 0)
      {
        arrList.set(front,e);
      }
      else
      {
        // because we are using a circular array, we want it to loop to the
        // back if the front is at position 0
        if(front == 0)
        {
          front = capacity-1;
        }

        else
        {
          front--;
        }

        arrList.set(front,e);
      }

      size++; // increment size because we added an element
      return true;
    }

    /**
     * Adds the specified element to the back of this Deque12. Returns true if
     * the operation succeeded, else false. The Deque12's size must be less
     * than its capacity. None of the other elements should changed, and the
     * size should be increased by 1.
     * @param e the element to add to the back of the list
     * @return true if the element was added, else false (false if the size is
     * at capacity)
     * @throws NullPointerException is the specified element is null, and size
     * is less than capacity
     * */
    public boolean addBack(E e)
    {
      // check first to see if size is at capacity
      if(size == capacity)
      {
        return false;
      }

      if(e == (E) null)
      {
        throw new NullPointerException();
      }

      // special case for an empty array because we want the front and back
      // pointer to always point to the frontmost and backmost element(s)
      // respectively. If we incremented the back pointer, this would no longer
      // be the case.
      if(size == 0)
      {
        arrList.set(back,e);
      }

      else
      {
        // because we are using a circular array, we want the back to wrap
        // around to the front if it goes to the end of the array
        if(back == capacity-1)
        {
          back = 0;
        }

        else
        {
          back++;
        }

        arrList.set(back,e);
      }

      size++; // increment size because we have added an element
      return true;
    }

  /**
   * Removes the element at the front of this Deque12. Returns the element
   * removed, or null if there was no such element.
   * @return the element removed, or null if the size was zero
   * */
  public E removeFront()
  {
    if(size == 0)
    {
      return (E) null;
    }
  
    E toRemove = arrList.get(front);
    size--; // decrement size because we remove an element

    if(size != 0)
    {
      front++;
      if(front == capacity)
      {
        front = 0;
      }
    }

    return toRemove;
  }

  /** Removes the element at the back of this Deque12. Returns the element
   * removed, or null if there was no such element.
   * @return the element removed, or null if the size was zero
   * */
  public E removeBack()
  {
    if(size == 0)
    {
      return (E) null;
    }

    E toRemove = arrList.get(back);
    size--;

    if(size != 0)
    {
      back--;
      if(back < 0)
      {
        back = capacity-1;
      }
    }
  
    return toRemove;
  }

  /**
   * Returns the element at the front of this Deque12, or null if there was no
   * such element. The Deque12 remains unchanged.
   * @return the element at the front, or null if the size was zero
   * */
  public E peekFront()
  {
    if(size == 0)
    {
      return (E) null;
    }

    return arrList.get(front);
  }

  /**
   * Returns the element at the back of this Deque12, or null if there was no
   * such element. The Deque12 remains unchanged.
   * @return the element at the back, or null if the size was zero.
   * */
  public E peekBack()
  {
    if(size == 0)
    {
      return (E) null;
    }

    return arrList.get(back);
  }

}
