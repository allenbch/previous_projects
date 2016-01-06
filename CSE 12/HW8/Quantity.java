/*  CSE 12 Homework 8
 *  Victoria Mannina and Yutong Gou
 *  A10076744 and A09810097
 *  Section A00, A00
 *  5/26/14
 * */

import java.util.*;
import java.text.*;

/**
 *  Title: class Quantity
 *  Description: A class that represents the quantities with their units. It
 *  contains methods to manipulate these quantities, including doing arithmetic
 *  with them and converting them into other units.
 *  @author Victoria Mannina, Yutong Gou
 *  @version 1.0
 * */
public class Quantity
{
  private double value;
  private double uncertainty; // not necissary, did not finish optional #1
  private Map<String,Integer> units;

  /**
   *  A no-argument constructor that creates a default quantity of value 1 and
   *  no units. (Note that even when there are no units, the object should
   *  still contain a map object; it will just be a map with no entries.)
   * */
  public Quantity(){
    this(1.0);
  }

  /** 
   *  A one-argument constructor that creates a quantity of a given value with
   *  all other variables set to default. Prviate because only used within the
   *  public constructors.
   *  @param value the value of the new quantity
   * */
  private Quantity(double value){
    this(value, 0.0);
  }

  /** 
   *  Constructor that takes a single Quantity argument, and creates a deep
   *  copy.
   *  @param other The Quantity to make a deep copy of
   * */
  public Quantity(Quantity other){
    this.value = other.value;
    this.uncertainty = other.uncertainty;
    this.units = other.units;
  }

  /**
   *  A two-argument constructor that creates a quantity of a given value and
   *  uncretainty and sets other variables to default. Private because it is
   *  only called within public constructors
   *  @param value the value of the new quantity
   *  @param uncertainty the uncertainty of the new quantity
   * */
  private Quantity(double value, double uncertainty){
    this.value = value;
    this.uncertainty = uncertainty;
    this.units = new HashMap();
  }

  /** 
   *  A 3-argument constructor: a double, a List<String>, and a List<String>.
   *  If either of the list arguments is null, this method throws an
   *  IllegalArgumentException when it makes  a call to the 4-arg private
   *  constructor.
   *  @param value the numeric value
   *  @param numerator the units of the numerator
   *  @param denominator the units of the denominator
   * */
  public Quantity(double value, List<String> numerator, List<String> denominator){
    this(value, 0.0, numerator, denominator);
  }

  /** 
   *  A 4-arg constructor that is private because it is called within the
   *  public constructors.
   *  @param value the numeric value
   *  @param uncertainty the uncertainty of the quantity
   *  @param numerators the units of the numerator
   *  @param denominators the units of the  denominators
   *  @throws IllegalArgumentException when either the numerators or
   *  denominators are null
   * */
  private Quantity(double value, double uncertainty, List<String> numerators, List<String> denominators){
    if(numerators == null)
    {
      throw new IllegalArgumentException("Numerator unit list cannot be null");
    }
    if(denominators == null)
    {
      throw new IllegalArgumentException("Denominator unit list cannot be null");
    }
    this.value = value;
    this.uncertainty = uncertainty;
    this.units = new HashMap();
    for (String unitName : numerators) 
    {
      constHelper(unitName, 1);
    }
    for (String unitName : denominators) 
    {
      constHelper(unitName, -1);
    }
  }

  /** 
   * Multiply method. Takes a single Quantity argument, multiplies this byt
   * the argument, and returns the result. The returned value is a brand new
   * Quantity object; neither this nor the argument quantity is changed.
   * @param input the Quantity object to multiply with this
   * @return the new Quantity
   * @throws IllegalArgumentException if the argument is null
   * */
  public Quantity mul(Quantity input)
  {
    if(input == null)
    {
      throw new IllegalArgumentException("Cannot multiple null");
    }
    Quantity ans = new Quantity();
    ans.value = this.value * input.value;
    //ans.uncertainty = answer.value * Math.sqrt() + Math.pow()
    ans.units = new HashMap();
    // first add all the units from the first Quantity
    for (String ou : this.units.keySet()) 
    {
      ans.units.put(ou, Integer.valueOf(((Integer)this.units.get(ou)).intValue()));
    }
    // next add in the units from the input Quantity
    for (String uo : input.units.keySet()) 
    {
      // if the unit is already in the answer, either remove if they cancel or
      // combine with number in second Quantity
	    if(ans.units.containsKey(uo))
	    {
	      if(((Integer)input.units.get(uo)).intValue() + (Integer)ans.units.get(uo).intValue() == 0)
	      {
		     ans.units.remove(uo);
	      }
	      else
	      {
	       ans.units.put(uo, ((Integer)input.units.get(uo)).intValue() + (Integer)ans.units.get(uo).intValue() );
	      }
	    }
	    else
	    {
      	ans.units.put(uo, Integer.valueOf(((Integer)input.units.get(uo)).intValue()));
    	}
	  }
    return ans;
  }

  /** A method that takes a single Quantity argument, divides this by the
   * argument, and returns the result. The returned value is a brand new
   * Quantity object; neither this quantity nor the argument quantity changes.
   * @param input the Quantity object to divide this by
   * @return the new Quantity object
   * @throws IllegalArgumentException if the argument is null or the value of
   * the argument is zero
   * */
  public Quantity div(Quantity input)
  {
    if(input == null)
    {
      throw new IllegalArgumentException("Divisor cannot be null");
    }
    if(input.value == 0.0)
    {
      throw new IllegalArgumentException("Divisor cannot be 0");
    }
    Quantity ans = new Quantity();
    ans.value = this.value/input.value;
    //ans.uncertainty = 
    ans.units = new HashMap();
    // first add the units from this Quantity
    for (String ou : this.units.keySet()) 
    {
      ans.units.put(ou, Integer.valueOf(((Integer)this.units.get(ou)).intValue()));
    }
    // now combine with the units from the input Quantity
    for (String uo : input.units.keySet()) 
    {
      // if the units are already in the answer units, either remove if they
      // cancel out or combine them for the new unit power
	    if(ans.units.containsKey(uo))
	    {
		    if((Integer)input.units.get(uo).intValue() - (Integer)ans.units.get(uo).intValue() == 0)
		    {
			    ans.units.remove(uo);
		    }
		    else
		    {
			    ans.units.put(uo,(Integer)input.units.get(uo).intValue() - (Integer)ans.units.get(uo).intValue());
		    }
	    }
	    else
        ans.units.put(uo, -Integer.valueOf(((Integer)input.units.get(uo)).intValue()));
    }
    return ans;
  }

  /** 
   *  Addition method. Takes a single Quantity argument, adds this to it, and
   *  returns the result.
   *  @param input the Quantity argument to add to this
   *  @return the new Quantity argument
   *  @throws IllegalArgumentException if the argument is null of ir the two
   *  Quantity objects do not have the same units.
   * */
  public Quantity add(Quantity input)
  {
    if (input == null) 
    {
      throw new IllegalArgumentException("Cannot add to null");
    }
    Quantity ans = new Quantity();
    ans.value = this.value + input.value;
    ans.uncertainty = Math.sqrt(Math.pow(this.uncertainty, 2.0) + Math.pow(input.uncertainty, 2.0));
    // Quantities must have the same units
    if(this.units.equals(input.units))
    {
      ans.units = new HashMap(this.units);
    }
    else 
    {
      throw new IllegalArgumentException("Unit mismatch in addition");
    }
    return ans;
  }

  /** 
   *  Subtraction method. Takes a single Quantity argument, subtracts it from
   *  this, and returns the result. The result is a brand new Quantity object;
   *  neither this quantity nor the argument is changed. Does this by calling
   *  the add method on a negated version of the input.
   *  @param input the Quantity to subtract from this
   *  @return the new Quantity object
   *  @throws IllegalArgumentException if the argument is null or if the two
   *  Quantity objects do not have the same units
   * */
  public Quantity sub(Quantity input)
  {
    if (input == null) 
    {
      throw new IllegalArgumentException("Cannot subtract null");
    }
    return add(input.negate());
  }

  /** 
   *  Negation method. Takes no arguments and returns a new Quantity which is
   *  the negation of this Quantity. The result is a new Quantity object; this
   *  quantity does not change.
   *  @return the new Quantity object
   * */
  public Quantity negate()
  {
    Quantity ans = new Quantity(this);
    ans.value = (-ans.value);
    return ans;
  }

  /** 
   *  Creates a new normalized Quantity equivalent to 1 of the given unit. If
   *  the unit does not appear as a key in the database then this method
   *  returns a new Quantity representing 1 of the argument unit.
   *  @param unit the name of a unit as a String
   *  @param db a units database as a Map<String,Quantity>
   *  @return the new normalized Quantity
   * */
  public static Quantity normalizedUnit(String unit, Map<String, Quantity> db)
  {
    if(db.containsKey(unit))
    {
      Quantity ans = (Quantity)db.get(unit);
      return ans.normalize(db);
    }
    return new Quantity(1.0, 0.0, Arrays.asList(new String[] { unit }), new ArrayList<String>());
  }

  /** 
   *  Takes in a database and returns a copy of this but in normalized form
   *  (with all defined units expanded out into primitive units)
   *  @param db a units database as a Map<String,Quantity>
   *  @return the new normalized Quantity
   * */
  public Quantity normalize(Map<String, Quantity> db)
  {
    Quantity ans = new Quantity(this.value, this.uncertainty);
    for (String unit : this.units.keySet())
    {
      int exp = ((Integer)this.units.get(unit)).intValue();
      Quantity nUnit = normalizedUnit(unit, db);
      Quantity nUnitPower = nUnit.pow(exp);
      ans = ans.mul(nUnitPower);
    }
    return ans;
  }

  /** 
   *  Power method. Takes a single int argument (pos, neg, or zero), raises
   *  this to the given power, and returns the result as a new Quantity. Does
   *  not change this quantity.
   *  @param power the int of the power to raise this to
   *  @return the new Quantity
   * */
  public Quantity pow(int power)
  {
    Quantity ans = new Quantity();
    ans.value = Math.pow(this.value, power);
    //ans.uncertainty = (ans.value * Math.abs(power) * (this.uncertainty / this.value));
    ans.units = new HashMap();
    // remove the units if to the power of zero, otherwise just modify to the
    // power
    for (String ou : this.units.keySet()) 
    {
      if(Integer.valueOf(power * ((Integer)this.units.get(ou)).intValue()) == 0)
      {
        ans.units.remove(ou);
      } 
      else
      {
        ans.units.put(ou, Integer.valueOf(power * ((Integer)this.units.get(ou)).intValue()));
      }
    }
    return ans;
  } 

  /** 
   * Returns the Quantity as a String. Rounds to 5 places after the decimal.
   * This method was given to us. 
   * @return the Quantity as a String
   * */
  public String toString()
  { 
    // We fixed these lines to match our fields! 
    double valueOfTheQuantity = this.value;
    Map<String,Integer> mapOfTheQuantity = this.units; 
    // Ensure we get the units in order 

    TreeSet<String> orderedUnits = new TreeSet<String>(mapOfTheQuantity.keySet());
    StringBuffer unitsString = new StringBuffer();

    for (String key : orderedUnits) { 
      int expt = mapOfTheQuantity.get(key);
      unitsString.append(" " + key);
      if (expt != 1)
        unitsString.append("^" + expt); 
    } 

    // Used to convert doubles to a string with a  
    //   fixed maximum number of decimal places. 
    DecimalFormat df = new DecimalFormat("0.0####"); 
    // Put it all together and return. 
    return df.format(valueOfTheQuantity)  
           + unitsString.toString();
  }

  /** 
   * Takes any single Object, and returns true if and only if that object if a
   * Quantity whose units are exactly the same as the calling object and whose
   * value is the same when rounded to five places after the decimal point.
   * @param other Object that is an instance of Quantity to compare to this
   * @return true if the Quantity objects are equal up to 5 decimal places,
   * otherwise false
   * */
  public boolean equals(Object other)
  {
    if(other!=null && other instanceof Quantity)
    {
      Quantity o = (Quantity)other;
      return toString().equals(o.toString());
    }
    return false;
  }

  /** 
   *  Returns an integer such that equal Quantities always return the same
   *  integer.
   *  @return the hashCode int value
   * */
  public int hashCode(){
    return toString().hashCode();
  }

  // HELPER METHODS

  /** 
   *  Used to create and combine hashMaps for the units.
   *  @param unit the name of the unit to add/modify
   *  @param pos the power of the unit to add/subtract
   * */
  private void constHelper(String unit, int pos)
  {
    if (this.units.containsKey(unit))
    {
      int oldExponent = ((Integer)this.units.get(unit)).intValue();
      int newExponent = oldExponent + pos;
      if (newExponent == 0) 
      {
        this.units.remove(unit);
      } 
      else 
      {
        this.units.put(unit, Integer.valueOf(newExponent));
      }
    }
    else
    {
      this.units.put(unit, Integer.valueOf(pos));
    }
  }

}//end of class
