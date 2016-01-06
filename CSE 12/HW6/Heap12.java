/*  Victoria Mannina
 *  PID: A10076744
 *  login: cs12efp
 * */

import java.util.ArrayList;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
/** Heap12 class that implements an unbounded array-backed heap structure and is
 * an extension of the Java Collections AbstractQueue class 
 * <p>
 * The elements of the heap are ordered according to their natural 
 * ordering,  Heap12 does not permit null elements. 
 * The top of this heap is the minimal or maximal element (called min/max)  
 * with respect to the specified natural ordering.  
 * If multiple elements are tied for min/max value, the top is one of 
 * those elements -- ties are broken arbitrarily. 
 * The queue retrieval operations poll and  peek 
 * access the element at the top of the heap.
 * <p>
 * A Heap12 is unbounded, but has an internal capacity governing the size of 
 * an array used to store the elements on the queue. It is always at least as 
 * large as the queue size. As elements are added to a Heap12, its capacity 
 * grows automatically. The details of the growth policy are not specified.
 * <p>
 * This class and its iterator implements the optional methods of the 
 * Iterator interface (including remove()). The Iterator provided in method 
 * iterator() is not guaranteed to traverse the elements of the Heap12 in 
 * any particular order. 
 * <p>
 * Note that this implementation is not synchronized. Multiple threads 
 * should not access a Heap12 instance concurrently if any of the 
 * threads modifies the Heap12. 
 */
public class Heap12<E extends Comparable <? super E>> extends 
	AbstractQueue<E> 
{
    private boolean isMin; // private instance variable to track if 
                           //we are using a min Heap or max Heap
    private ArrayList<E> myHeap; // our ArrayList used for the Heap
    private int defCap = 5; // the default capacity of the heap
    private int currentCap; // the current capacity of the heap
    private int elements = 0; // to keep track of how many elements are
                              //in the heap

	/** O-argument constructor. Creates and empty Heap12 with unspecified
 	 *  initial capacity, and is a min-heap 
 	 */ 
	public Heap12()
	{
        myHeap = new ArrayList<E>(defCap);
        // add null elements to the heap so we can set them later
        for(int i=0; i<defCap; i++)
        {
            myHeap.add(null);
        }
        isMin = true;
        currentCap = defCap;
	}

	/** 
 	 * Constructor to build a min or max heap
 	 * @param isMaxHeap 	if true, this is a max-heap, else a min-heap 
 	 */ 
	public Heap12( boolean isMaxHeap)
	{
        myHeap = new ArrayList<E>(defCap);
        // add null elements to the heap so we can set them later
        for(int i=0; i<defCap; i++)
        {
            myHeap.add(null);
        }
        isMin = !isMaxHeap;
        currentCap = defCap;
	}

	/** 
 	 * Constructor to build a with specified initial capacity 
 	 *  min or max heap
 	 * @param capacity  	initial capacity of the heap.	
 	 * @param isMaxHeap 	if true, this is a max-heap, else a min-heap 
 	 */ 
	public Heap12( int capacity, boolean isMaxHeap)
	{
        myHeap = new ArrayList<E>(capacity);
        // add null elements to the heap so we can set them later
        for(int i=0; i<capacity; i++)
        {
            myHeap.add(null);
        }
        isMin = !isMaxHeap;
        currentCap = capacity;
	}
	/** Copy constructor. Creates Heap12 with a deep copy of the argument
 	 * @param toCopy      the heap that should be copied
 	 */
	public Heap12 (Heap12<E> toCopy)
	{
        myHeap = new ArrayList<E>(toCopy.size());
        for(int i=0; i<toCopy.size(); i++)
        {
            myHeap.add(i,toCopy.myHeap.get(i));
        }
        isMin = toCopy.isMin;
        elements = toCopy.elements;
        currentCap = elements;
	}

	/* The following are defined "stub" methods that provide degenerate
	 * implementations of methods defined as abstract in parent classes.
	 * These need to be coded properly for Heap12 to function correctly
	 */

	/** Size of the heap
 	 * @return the number of elements stored in the heap
	*/
	public int size()
	{
		return elements; 
	}

	/** 
 	 * @return an Iterator for the heap 
	*/
	public Iterator<E> iterator()
	{
		return new Heap12Iterator(); 
	}

	/** peek - see AbstractQueue for details 
 	 * @return Element at top of heap. Do not remove 
	*/
	public E peek()
	{
        return myHeap.get(0);
	}

	/** poll - see AbstractQueue for details 
 	 * @return Element at top of heap. And remove it from the heap. 
 	 * 	return <tt>null</tt> if the heap is empty
	*/
	public E poll()
	{
        // if heap is empty return null
        if(elements == 0)
        {
            return null;
        }
        // save element to be removed for returning later
        E root = myHeap.get(0);
        // take the last element and put it at the root
        myHeap.set(0,myHeap.get(elements-1));
        // change the element that was last to null
        myHeap.set(elements-1, null);
        elements--;
        // no need to trickle down if we only have 1 element
        if(elements > 1)
        {
            // trickle down to restore heap ordering property
            trickleDown(0);
        }
        return root;
	}
   
	/** offer -- see AbstractQueue for details
	 * insert an element in the heap
	 * @return true
	 * @throws NullPointerException 
	 * 	if the specified element is null	
	 */
	public boolean offer (E e)
	{
        // cannot offer a null element to the heap
        if(e==null)
        {
            throw new NullPointerException();
        }
        // if the heap is at the backing array capacity, create new backing
        // array that has twice the capacity
        if(currentCap==elements)
        {
            ArrayList<E> temp = new ArrayList<E>(currentCap*2);
            // copy the elements from the current heap
            for(int i=0; i<currentCap; i++)
            {
                temp.add(i,myHeap.get(i));
            }
            // add null elements to the second half
            for(int j=currentCap; j<currentCap*2;j++)
            {
                temp.add(j,null);
            }
            // change the pointer for myHeap
            myHeap = temp;
            currentCap = currentCap*2;
        }
        // add to end of heap
        myHeap.set(elements,e);
        elements++;
        // bubble up to restore heap ordering property
        bubbleUp(elements-1);

        return true;
	}

       /* ------ Private Helper Methods ----
     *  DEFINE YOUR HELPER METHODS HERE
     */
    
     /**
      * Allows elements to move up in the heap if it is greater than/less than
      * the parent depending on if we are using a max or min heap.
      * @param index the index at which we want to bubble up from
      *
      * */
     private void bubbleUp(int index)
    {
        // base case #1: index at root
        if(index==0)
        {
            return;
        }

        int parentIndex = (index-1)/2;
        E parent = myHeap.get(parentIndex);
    
        // base case #2
        // if the parent is smaller and min Heap
        // if parent is larger and max Heap
        if((myHeap.get(index).compareTo(parent)>0 && isMin == true)
        ||(myHeap.get(index).compareTo(parent)<0 && isMin == false))
        {
            return;
        }

        swap(parentIndex,index); // if neither base case satisfied
        bubbleUp(parentIndex);
    }

    /** 
     *  Allows elements to move down in the heap if the element is greater
     *  than/less than it's children depending on if we are uing a max or min
     *  heap.
     *  @param index the index at which we want to trickle down from
     *  @throws IndexOutOfBoundsException if we try to trickle down from an
     *  index greater than or equal to the number of elements in the heap
     * */
     private void trickleDown(int index)
    {
        // just in case
        if(index>=elements)
        {
            throw new IndexOutOfBoundsException();
        }

        boolean hasRC = false; // if the parent has a right child
        boolean leftSmaller = false; // if the left child < parent
        boolean rightSmaller = false; // if right child < parent
        E rChild = null; // used to store right child element
        int rIndex = 2*index+2; // index of right child

        // base case if index is a leaf node
        if(2*index+1 >= elements)
        {
            return;
        }
        
        int lIndex = 2*index+1; // index of left child
        E lChild = myHeap.get(lIndex); // left child element
        if(myHeap.get(index).compareTo(lChild)<0)
        {
            leftSmaller = false;
        }
        else
        {
            leftSmaller = true;
        }
        // check to see if there is a right child
        if(2*index+2 < elements)
        {
            hasRC = true;
            rChild = myHeap.get(rIndex);
            if(myHeap.get(index).compareTo(rChild)<0)
            {
                rightSmaller = false;
            }
            else
            {
                rightSmaller = true;
            }
        }

        // base case if both children are larger for min Heap
        // or if both children are smaller for max Heap
        if( ((hasRC==false || rightSmaller==false) && leftSmaller==false 
            && isMin==true) || ((hasRC==false || rightSmaller==true) 
            && leftSmaller == true && isMin==false))
        {
            return;
        }

        // check to see if left or right child is the one that needs to be
        // swapped
        if(hasRC==false || (lChild.compareTo(rChild)<0 && isMin==true) 
            || (lChild.compareTo(rChild)>0 && isMin==false))
        {
            swap(lIndex,index);
            trickleDown(lIndex);
        }
        else
        {
            swap(rIndex,index);
            trickleDown(rIndex);
        }
    }

    /**
     *  Swap method for swapping the elements at two indexes of the heap
     *  @param childIndex the index of the child node to be swapped with
     *  parent
     *  @param index the index of the element to swap with childIndex
     * */
    private void swap(int childIndex, int index)
    {
        // save child element in temp variable
        E child = myHeap.get(childIndex);
        // move parent to child index and temp child to parent index
        myHeap.set(childIndex,myHeap.get(index));
        myHeap.set(index,child);
    }

    /** Inner Class for an Iterator **/
    /* stub routines */
	private class Heap12Iterator implements Iterator<E>
	{
        private int itIndex; // iterator index
        private boolean canRemove=false; // to track state

        // initialize to -1 because call to next() increments index before
        // finding next
        private Heap12Iterator()
        {
            itIndex = -1;
        }

        /* hasNext() to implement the Iterator<E> interface */
		public boolean hasNext()
		{
            if(itIndex+1<=elements-1)
            {
                return true;
            }
            else
                return false;
		}

        /* next() to implement the Iterator<E> interface */
		public E next() throws NoSuchElementException
        {
            if(this.hasNext()==false)
            {
                throw new NoSuchElementException();
            }
            canRemove = true;
            itIndex++;
            return myHeap.get(itIndex);   
		}
        /* remove() to implement the Iterator<E> interface */
		public void remove() throws IllegalStateException
		{
            // must have called next() before you can call remove()
            if(canRemove==false)
            {
                throw new IllegalStateException();
            }
            myHeap.set(itIndex,myHeap.get(elements-1));
            myHeap.set(elements-1,null);
            elements--;
            // cant know if it will need to move up or down in the heap
            // call both bubble up and trickle down to be certain it will find
            // it's place without disrupting the ordering property of the heap
            bubbleUp(itIndex);
            trickleDown(itIndex);
            canRemove = false;
		}	
    }
} 
// vim:ts=4:sw=4:tw=78:et
