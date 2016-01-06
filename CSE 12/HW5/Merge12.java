/* 
 * Victoria Mannina
 * A10076744
 * cs12efp
 * 5/6/14
 * */

import java.lang.Comparable;
import java.util.List;
import java.util.ArrayList;
/**
 *  Title: class Merge12
 *  Description: A java class that implements Sort12 using a "work/scratch"
 *  array and sorting and merging objects of type Comparable through the
 *  MergeSort algorithm.
 *  @author Victoria Mannina
 *  @version 1.0 6-May-2014
 * */
public class Merge12 implements Sort12
{
    /**
     *  Sorts the given list by calling helper methods and using an ArrayList
     *  to copy the list elements into.
     *  @param list The list to be sorted of Comparable type T
     * */
	public  <T extends Comparable<? super T>> void  sort(List<T> list)
	{
		if ( list == null )
			 throw new NullPointerException("Null argument to sort");

		// Create the arrayList to insert into
		ArrayList<T> inputArray = new ArrayList<T>(list.size());
		ArrayList<T> tempArray = new ArrayList<T>(list.size()/2+1);

        inputArray.addAll(list); // copy list to the ArrayList inputArray
        // call the MergeSort method
        internalMergeSort(inputArray, tempArray, 0, list.size()-1);
        // copy the sorted list in inputArray back to the list
        for(int i=0; i<list.size(); i++)
        {
            list.set(i,inputArray.get(i));
        }
	}

    /**
     *  Helper method that splits the inputArray recursively and then merges
     *  them together when they are down to size one and up.
     *  @param inputArray the ArrayList to be sorted
     *  @param tempArray the ArrayList to use for temporarily storing half
     *  inputArray
     *  @param first the first element in the section to merge/sort
     *  @param last the last element in the section to merge/sort
     * */
	private  <T extends Comparable<? super T>> void 
		internalMergeSort(ArrayList<T> inputArray, ArrayList<T> tempArray,
					int first, int last)
	{
        if( first<last )
        {
            int mid = first + ((last-first)/2);
            internalMergeSort( inputArray, tempArray, first, mid );
            internalMergeSort( inputArray, tempArray, mid+1, last );
            merge( inputArray, tempArray, first, mid, last );
        }
	} // internalMergeSort

    /**
     *  Helper method that actually sorts the elements given from first to last
     *  in the inputArray.
     *  @param inputArray the ArrayList that contains the elements to sort
     *  @param tempArray the ArrayList used to store half of the inputArray
     *  elements we want sorted
     *  @param first the first element to be sorted
     *  @param mid the middle of the range to be sorted
     *  @param last the last element to be sorted
     * */
	private  <T extends Comparable<? super T>> void 
		merge(ArrayList<T> inputArray, ArrayList<T> tempArray,
					int first, int mid, int last)
	{
        int inputIndex = mid+1;
        int tempIndex = 0;
        int newIndex= first;
        // add the elements from the first half of the range of inputArray into
        // tempArray
        for(int i=first; i<=mid; i++)
           {
            tempArray.add(inputArray.get(i));
           }
        // int comparison;
        while(newIndex<=last)
        {
            // if we reach the last element in the second half of inputArray,
            // that means the only elements left to add are the remaining ones
            // in the tempArray
            if(inputIndex>last)
            {
                inputArray.set(newIndex, tempArray.get(tempIndex));
                tempIndex++;
            }
            // same thing for the last if statement but vice versa (if we
            // reached the end of the tempArray then all we have left to add
            // are the elements at the end of inputArray). This might be
            // unnecessary/redundant, but I did it just to be safe.
            else if(tempIndex>(mid-first))
            {
                inputArray.set(newIndex, inputArray.get(inputIndex));
                inputIndex++;
            }
            // if the element at the current index of the second half of the
            // inputArray is smaller than the element at the current index of
            // the tempArray, place the inputArray element at inputIndex into
            // the inputArray at newIndex
            else if(inputArray.get(inputIndex).compareTo(tempArray.get(tempIndex)) <= 0 )
            {
                inputArray.set(newIndex,inputArray.get(inputIndex));
                inputIndex++;
            }
            // otherwise the element at the current index in tempArray must be
            // the smallest and therefor add it to the inputArray at the
            // newIndex
            else
            {
                inputArray.set(newIndex,tempArray.get(tempIndex));
                tempIndex++;
            }
            // increment the newIndex each time we move an element in
            newIndex++;
        }
        // remove the elements from temp so that the next time this is called
        // when we add we don't go above capacity
        for(int j=tempArray.size(); j>0; j--)
        {
            tempArray.remove(j-1);
        }
	} // Merge
}
// vim:ts=4:sw=4:sw=78
