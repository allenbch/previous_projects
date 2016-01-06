/*  Victoria Mannina
 *  PID: A10076744
 *  login: cs12efp
 * */

import java.lang.Comparable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
/** This is a Heap Sort
 *  @author Victoria Mannina 
 *  @date 14 May 2014
 */
public class HeapSort12 implements Sort12
{
    /**
     *  Sort method to sort a list of comparable T using the HeapSort 
     *  algorithm.
     *  @param list the List<T> to be sorted
     *  @throws NullPointerException if the list given is empty
     * */
	public  <T extends Comparable<? super T>> void sort(List<T> list)
	{
        // cannot sort a null List
        if(list==null)
        {
            throw new NullPointerException("Null argument to sort");
        }
        
        // create a temp Heap12 to move to in order to sort
        Heap12<T> tempHeap = new Heap12<T>(list.size(),false);
        for(int i=0; i<list.size(); i++)
        {
            tempHeap.offer(list.get(i));
        }
        // now that elements are sorted on the Heap12, move them back to the
        // list in that order
        for(int j=0; j<list.size(); j++)
        {
            list.set(j,tempHeap.poll());
        }
	}
}
// vim:ts=4:sw=4:sw=78
