/* Name: Victoria Mannina
 * PID: A10076744
 * login: cs12efp
 * */

import java.util.*;

/**
 *  Title: class MyLinkedList
 *  Description: Homework 2 - subset of the Java Collection's Framework
 *  LinkedList, matching it's behaviors on the described subsets
 *  @author Victoria Mannina
 *  @version 1.0 15-April-2014
 * */
public class MyLinkedList<E> extends AbstractList<E> {

	private int nelems; // number of elements in the list
	private Node head; // used to track dummy head
	private Node tail; // used to track dummy tail

 /**
  * Description: Nested class within MyLinkedList to represent a node.
  * */
	protected class Node {
		E data;
		Node next;
		Node prev;

		/** Constructor to create singleton Node
         *  @param element Element to add, can be null
         * */
		public Node(E element)
		{
            data = element;
            next = null;
            prev = null;
		}
		/** Constructor to create singleton link it between previous and next
	 	*   @param element Element to add, can be null
	 	*   @param prevNode predecessor Node, can be null
	 	*   @param nextNode successor Node, can be null 
	 	*/
			public Node(E element, Node prevNode, Node nextNode)
		{
            data = element;
            prev = prevNode;
            next = nextNode;
		}
		/** Remove this node from the link. Update previous and next nodes */
		public void remove()
		{
            prev.next = next;
            next.prev = prev;
            prev = null;
            next = null;
		}
		/** Set the previous node in the list
		 *  @param p new previous node
		 */
		public void setPrev(Node p)
		{
            prev = p;
		}
		/** Set the next node in the list
		 *  @param n new next node
		 */
		public void setNext(Node n)
		{
            next = n;
		}
		   
		/** Set the element 
		 *  @param e new element 
		 */
		public void setElement(E e)
		{
            data = e;
		}
		/** Accessor to get the next Node in the list
         *  @return the pointer to the node after current one*/
		public Node getNext()
		{
            return next; // XXX-CHANGED-XXX
		}
		/** Accessor to get the prev Node in the list 
         *  @return the pointer to the node before the current one*/
		public Node getPrev()
		{
            return prev; // XXX-CHANGED-XXX
		} 
		/** Accessor to get the Nodes Element 
         *  @return the data of the current node*/
		public E getElement()
		{
            return data; // XXX-CHANGED-XXX
		} 
	}

    /** ListIterator implementation */ 
	protected class MyListIterator implements ListIterator<E> {

        private boolean forward;
        private boolean canRemove;
        private Node left,right; // Cursor sits between these two nodes
        private int idx;        // Tracks current position. what next() would
                                // return 
        /** Constructor to create a MyListIterator*/
        public MyListIterator()
        {
            left = head;
            right = head.getNext();
            idx = 0;
            forward = true;
            canRemove = false;
        }

        /** Inserts the specified element into the list. It is inserted
         * immediately before the element that would be returned by a call to
         * next(), if any, and after the element that would be returned by
         * previous(), if any.
         * @param e Element to add, cannot be null
         * @throws NullPointerException if the element trying to be added is
         * null */
		@Override
		public void add(E e) throws  NullPointerException
		{
            if(e==(E)null) //check to see if we need to throw Null exception
            {
                String nullData = new String("Cannot add null element");
                throw new NullPointerException(nullData);
            }
            Node newNode = new Node(e,left,right);
            nelems++;
            right = newNode;
            canRemove = false;
		}

        /** Returns true if this my list iterator has more elements when
         * traversing the list in the forward direction.
         * @return true if next() would return an element rather than throwing
         * an exception */
		@Override
		public boolean hasNext()
		{
            boolean hasN = false;
            if(right!=tail) // if the right variable points to the tail,
                           //there are no more elements in the MyLinkedList
            {
                hasN = true;
            }
            return hasN; // XXX-CHANGED-XXX
		}

        /** Returns true if this my list iterator has more elements when
         * traversing the list in the reverse direction.
         * @return true if previous() would return an element rather than
         * throwing an exception */
		@Override
		public boolean hasPrevious()
		{
            boolean hasP = false;
            if(left!=head)
            {
                hasP = true;
            }
            return hasP; // XXX-CHANGED-XXX
		}

        /** Returns the next element in the list and advances the cursor
         * position. This method may be called repeatedly to iterate through
         * the list, or intermixed with calls to previous() to go back and
         * forth
         * @return the next element in the list
         * @throws NoSuchElementException if the iteration has no next element 
         * */
		@Override
		public E next() throws NoSuchElementException
		{
            if(this.hasNext()==false)
            {
                String noNext = new String("No next element available");
                throw new NoSuchElementException(noNext);
            }
            left = right;
            right = right.getNext();
            idx++;
            canRemove = true; // if it can move forward, then left must
                              // point to a node other than the dummy head
            forward = true;
            return left.getElement();  // XXX-CHANGED-XXX
		}

        /** Returns the index of the element that would be returned by a
         * subsequent call to next(). Returns list size if the iterator is at
         * the end of the list 
         * @return index of next element */
		@Override
		public int nextIndex()
		{
            return idx; // XXX-CHANGED-XXX
		}

        /** Returns the previous element in the list and moves the cursor
         * position backwards. This method may be called repeatedly to iterate
         * through the list backwards, or intermixed with calls to next() to
         * go back and forth.
         * @return the previous element in the list
         * @throws NoSuchElement if the iteration has no previous element */
		@Override
		public E previous() throws NoSuchElementException
		{
            if(this.hasPrevious()==false)
            {
                String noPrev = new String("No previous element available");
                throw new NoSuchElementException(noPrev);
            }
            right = left;
            left = left.getPrev();
            idx--;
            forward = false;
            if(this.hasPrevious()==true)
            {
                canRemove = true;
            }
            else
            {
                canRemove = false;
            }
            return right.getElement(); // XXX-CHANGED-XXX
		}

        /** Returns the index of the element that would be returned by a
         * subsequent call to previous(). Returns -1 if the list iterator is
         * at the beginning of the list.
         * @return the index of the element that would be returned by a
         * subsequent call to previous, or -1 if the list iterator is at the
         * beginning of the list */
		@Override
		public int previousIndex()
		{
            return idx-1;  // XXX-CHANGED-XXX
		}

        /** Removes the element from the list that was returned by next() or
         * previous(). This can only be made once per call to next or
         * previous. It can be made only if add and remove have not been
         * called after the last call to next or previous 
         * @throws IllegalStateException if neither next nor previous have
         * been called, or remove or add have been called after the last call
         * to next or previous */
		@Override
		public void remove() throws IllegalStateException
		{
            if(canRemove==false)
            {
                String illegalState = new String("Remove cannot be " +
                "called because either next or previous has not been " +
                "called OR remove or add was called last");
                throw new IllegalStateException(illegalState);
            }
            if(forward==true)
            {
                left.remove();
            }
            else
            {
                right.remove();
            }
            nelems--;
            canRemove = false;
		}

        /** Replaces the last element returned by next or previous with the
         * specified element. This can be made only if neither remove nor add
         * have been called after the last call to next or previous 
         * @param e the element with which to replace the last element returned
         * by next or previous
         * @throws NullPointerException if the data trying to be added is null
         * @throws IllegalStateException if neither next nor previous has been
         * called, or remove or add have been called after the last call to
         * next or previous */
		@Override
		public void set(E e) throws NullPointerException,IllegalStateException
		{
            if(canRemove == false)
            {
                String illegalState = new String("Cannot set element because"+
                " either remove or add has been called after the last call"+
                " to next or previous");
                throw new IllegalStateException(illegalState);
            }
            if(e == (E) null)
            {
                String nullData = new String("Cannot add null element");
                throw new NullPointerException(nullData);
            }
            if(forward==true)
            {
                left.setElement(e);
            }
            else
            {
                right.setElement(e);
            }
		}

	}


	//  Implementation of the MyLinkedList Class


    /** Only 0-argument constructor is defined */
    public MyLinkedList()
    {
     head = new Node(null);
     tail = new Node(null);
     head.setNext(tail);
     tail.setPrev(head);
     nelems = 0;
    }

    /** Returns the number of elements in this list
     * @return the number of elements in this list */
	@Override
	public int size()
	{
	    return nelems; // XXX-CHANGED-XXX 
	}
 
    /** Returns the element at the specified position in this list
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range 
     * (index < 0 || index >= size())*/
	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
        if(nelems == 0 || index>=nelems || index<0)
        {
            String indexOutE = new String("No node at index " + index);
            throw new IndexOutOfBoundsException(indexOutE);
        }
        Node current = head.getNext();
        for(int i=0; i<index;i++)
        {
            current = current.getNext();
        }
        return current.getElement();  // XXX-CHANGED-XXX
	}

	@Override
    /** Add an element to the list 
     * @param index  where in the list to add
     * @param data data to add
     * @throws IndexOutOfBoundsException if the index is out of range:
     * (index<0 || index >size())
     * @throws NullPointerException if the data trying to be added is null
     */ 
	public void add(int index, E data) 
        throws IndexOutOfBoundsException,NullPointerException
	{
        if(index>nelems || index<0)
        {
            String indexOutE = new String("Cannot add to index "+index);
            throw new IndexOutOfBoundsException(indexOutE);
        }
        if(data== (E) null)
        {
            String nullData = new String("Cannot add node with null data");
            throw new NullPointerException(nullData);
        }
        Node forward = head.getNext();
        Node back = head;
        for(int i=0; i<index; i++)
        {
            back = forward;
            forward = forward.getNext();
        }
        Node newNode = new Node(data, back, forward);
        back.setNext(newNode);
        forward.setPrev(newNode);
        nelems++;
	}
    /** Add an element to the end of the list 
     * @param data data to add
     * @throws NullPointerException if the data to be added is null
     * @return true if successful
     */ 
    public boolean add(E data) throws NullPointerException
    {
        if(data == (E) null)
        {
            String nullData = new String("Cannot add node with null data");
            throw new NullPointerException(nullData);
        }
        Node prev = tail.getPrev();
        Node newNode = new Node(data, prev, tail);
        prev.setNext(newNode);
        tail.setPrev(newNode);
        nelems++;
        return true; // XXX-CHANGED-XXX
    }

    /** Set the element at an index in the list 
     * @param index  where in the list to add
     * @param data data to add
     * @return element perviously at the specified location
     * @throws IndexOutOfBoundsException if the index is out of range
     * @throws NullPointerException if trying to set data to null
     */ 
	public E set(int index, E data) 
        throws IndexOutOfBoundsException,NullPointerException
	{
        if(nelems==0 || index<0 || index>=nelems)
        {
            String indexOutE = new String("Cannot set element at index"
            + index);
            throw new IndexOutOfBoundsException(indexOutE);
        }
        if(data == (E) null)
        {
            String nullData = new String("Cannot add node with null data");
            throw new NullPointerException(nullData);
        }
        Node current = head.getNext();
        for(int i=0;i<index;i++)
        {
            current = current.getNext();
        }
        E oldE = current.getElement();
        current.setElement(data);
        return oldE;// current.getElement();; // XXX-CHANGED-XXX
	}

    /** Remove the element at an index in the list 
     * @param index  where in the list to add
     * @return element the data found
     * @throws IndexOutOfBoundsException
     */ 
    public E remove(int index) throws IndexOutOfBoundsException
    {
        if(nelems==0 || index>=nelems || index<0)
        {
            String indexOutE = new String("Cannot remove node at index "
            + index);
            throw new IndexOutOfBoundsException(indexOutE);
        }
        Node current = head.getNext();
        Node back = head;
        for(int i=0;i<index;i++)
        {
            back = current;
            current = current.getNext();
        }
        Node forward = current.getNext();
        back.setNext(forward);
        forward.setPrev(back);
        current.setPrev(null);
        current.setNext(null);
        nelems--;
        return current.getElement(); // XXX-CHANGED-XXX
    }

    /** Clear the linked list */
    public void clear()
    {
        head.setNext(tail);
        tail.setPrev(head);
        nelems = 0;
    }

    /** Determine if the list empty 
     *  @return true if empty, false otherwise */
    public boolean isEmpty()
    {
        boolean empty = false;
        if(nelems==0)
        {
            empty = true;
        }
        return empty;  // XXX-CHANGED-XXX
    }

    // Helper method to get the Node at the Nth index
    /** Helper method to get the Node at the Nth index
     * @param index the index of the node to find
     * @return the node at the given index */
    private Node getNth(int index) 
    {
        Node nth = new Node(null);
        if(index>=nelems)
        {
            nth = head;
            for(int i=0; i<index; i++)
            {
                nth=nth.next;
            }
        }
        return nth;  // XXX-CHANGED-XXX
    }

     
                

/*  UNCOMMENT the following when you believe your MyListIterator class is
	functioning correctly */
    /** Changes the calls to iterator to instead create a MyListIterator */
	public Iterator<E> iterator()
	{
	return new MyListIterator();
	}

    /** Changes the calls to listIterator to instead create instances
     * of MyListIterator */
	public ListIterator<E> listIterator()
	{
	return new MyListIterator();
	}

}

// VIM: set the tabstop and shiftwidth to 4 
// vim:tw=78:ts=4:et:sw=4

