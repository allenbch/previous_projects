/*  CSE 12 Homework 8
 *  Yutong Gou, Victoria Mannina
 *  A09810097, A10076744
 *  A00, A00
 *  May 26, 2014
 */

// Original author: Chris Stone
// Extended by: Christine Alvarado

/** 
 *  Title: class AST
 *  Description: A class for representing Unicalc Abstract Syntax Tree
 *  @author Yutong Gou, Victoria Mannina
 *  @version 3.0
 * */

import java.util.*;

/** The interface for an Abstract Syntax Tree for Unicalc */
interface AST {
  /** The string representation of this AST
    * @return The String representation of this AST */
  public String   toString();
  
  /** Evaluate this AST
    * @param env The environment in which to do the evaluation (symbol map)
    * @return The value of the AST
    * */
  public Quantity eval(Map<String,Quantity> env);
  
  /** Equals
    * @param An object to compare to
    * @return true if o represents the same kind of AST with the same structure
    *         false otherwise.
    * */
  public boolean equals( Object o );
}

// The variables and methods should be self-evident
// and you do not need to add header comments to the 
// classes and methods below unless you want to or you 
// are doing something cryptic.

class Product implements AST
{
  private AST left;
  private AST right;

  public Product(AST l, AST r)
  {
    this.left = l;
    this.right = r;
  }

/** eval This method takes the left and right components and takes both of
 * them and times them by using the mul method from Quantity, eval times 
 * them together and returns the product.
 * @param env the environment.
 * @return the product of the two numbers in the form of a Quantity object.
 */
  public Quantity eval(Map<String,Quantity> env)
  {
    Quantity l = this.left.eval(env);
    Quantity r = this.right.eval(env);
    return l.mul(r);
  }  
  
  public String toString()
  {
    return ("Product(" + left + "," + right + ")");
  }  
  
  public boolean equals( Object o ) {
    if ( o instanceof Product ) {
      Product tree = (Product)o;
      return tree.left.equals( left ) && tree.right.equals( right );
    }
    else return false;
  }
}

class Quotient implements AST
{
  private AST left;
  private AST right;

  public Quotient(AST l, AST r)
  {
    this.left = l;
    this.right = r;
  }
  
/** eval This method takes the left and right components and takes both of
 * them and divides them by using the div method from Quantity, eval dovodes 
 * them together and returns the quotient.
 * @param env the environment.
 * @return the quotient of the two numbers in the form of a Quantity object.
 */
  public Quantity eval(Map<String,Quantity> env)
  {
    Quantity l = this.left.eval(env);
    Quantity r = this.right.eval(env);
    return l.div(r);  }  
  
  public String toString()
  {
    return ("Quotient(" + left + "," + right + ")");
  }
  
  public boolean equals( Object o ) {
    if ( o instanceof Quotient ) {
      Quotient tree = (Quotient)o;
      return tree.left.equals( left ) && tree.right.equals( right );
    }
    else return false;
  }
}

class Power implements AST
{
  private AST child;
  private int exponent;
  
  public Power(AST ast, int expt)
  {
    this.child = ast;
    this.exponent = expt;
  }
  
/** eval This method takes the left and right components and takes the left to
 * the power of the right number by using the pow method from Quantity, 
 * eval takes the power and returns the answer.
 * @param env the environment.
 * @return the answer of the two numbers in the form of a Quantity object.
 */
  public Quantity eval(Map<String,Quantity> env)
  {
    Quantity stuff = this.child.eval(env);
    return stuff.pow(this.exponent);
  }
  
  public String toString()
  {
    return ("Power(" + child + "," + exponent + ")");
  }  
  
  public boolean equals( Object o ) {
    if ( o instanceof Power ) {
      Power tree = (Power)o;
      return tree.child.equals( this.child ) && tree.exponent == this.exponent;
    }
    else return false;
  }
}

class Sum implements AST
{
  private AST left;
  private AST right;

  public Sum(AST l, AST r)
  {
    this.left = l;
    this.right = r;
  }
  
/** eval This method takes the left and right components and takes both of
 * them and adds them by using the add method from Quantity, eval adds 
 * them together and returns the sum.
 * @param env the environment.
 * @return the sum of the two numbers in the form of a Quantity object.
 */
  public Quantity eval(Map<String,Quantity> env)
  {
    Quantity l = this.left.eval(env);
    Quantity r = this.right.eval(env);
    return l.add(r);
  }  
  
  public String toString()
  {
    return ("Sum(" + left + "," + right + ")");
  }
  
  public boolean equals( Object o ) {
    if ( o instanceof Sum ) {
      Sum tree = (Sum)o;
      return tree.left.equals( left ) && tree.right.equals( right );
    }
    else return false;
  }
}

class Difference implements AST
{
  private AST left;
  private AST right;

  public Difference(AST l, AST r)
  {
    this.left = l;
    this.right = r;
  }
  
/** eval This method takes the left and right components and takes both of
 * them and takes the difference between them by using the sub method 
 * from Quantity, eval subtracts them and returns the difference.
 * @param env the environment.
 * @return the difference of the two numbers in the form of a Quantity object.
 */
  public Quantity eval(Map<String,Quantity> env)
  {
    Quantity l = this.left.eval(env);
    Quantity r = this.right.eval(env);
    return l.sub(r);
  }  

  public String toString()
  {
    return ("Difference(" + left + "," + right + ")");
  }
  
  public boolean equals( Object o ) {
    if ( o instanceof Difference ) {
      Difference tree = (Difference)o;
      return tree.left.equals( left ) && tree.right.equals( right );
    }
    else return false;
  }

}


class Negation implements AST
{
  private AST child;
  
  public Negation(AST ast)
  {
    this.child = ast;
  }
  
/** eval This method takes a number as the parameter and gets the negation
 * of that object by using the negate method from Quantity, eval negates 
 * the number and returns the opposite.
 * @param env the environment.
 * @return the negation of the input number and returns it as a Quantity object.
 */
  public Quantity eval(Map<String,Quantity> env)
  {
    Quantity stuff = this.child.eval(env);
    return stuff.negate();
  }
  
  public String toString()
  {
    return ("Negation(" + child + ")");
  }  
  
  public boolean equals( Object o ) {
    if ( o instanceof Negation ) {
      Negation tree = (Negation)o;
      return tree.child.equals( this.child );
    }
    else return false;
  }
}
class Value implements AST
{
  private Quantity quant;

  public Value(Quantity q)
  {
    this.quant = q;
  }
  
/** eval This method takes the input number and evaluate it by making a 
 * new Quantity object through the use of a costructor.
 * @param env the environment.
 * @return the product of the two numbers in the form of a Quantity object.
 */
  public Quantity eval(Map<String,Quantity> env)
  {
    return new Quantity(this.quant);
  }  
  
  public String toString()
  {
    return ("Value(" + quant + ")");
  }  
  
  public boolean equals( Object o ) {
    if ( o instanceof Value ) {
      Value val = (Value)o;
      return val.quant.equals( this.quant );
    }
    else return false;
  }
}

class Normalize implements AST
{
  private AST child;
  
  public Normalize(AST ast)
  {
    this.child = ast;
  }
  
/** eval This method takes the left and right components and takes both of
 * them and times them by using the mul method from Quantity, eval times 
 * them together and returns the product.
 * @param env the environment.
 * @return the product of the two numbers in the form of a Quantity object.
 */
  public Quantity eval(Map<String,Quantity> env)
  {
    Quantity stuff = this.child.eval(env);
    return stuff.normalize(env);
  }
  
  public String toString()
  {
    return ("Normalize(" + child + ")");
  }
  
  public boolean equals( Object o ) {
    if ( o instanceof Normalize ) {
      Normalize tree = (Normalize)o;
      return tree.child.equals( this.child );
    }
    else return false;
  }
  
}

class Define implements AST
{
  private String unitName;
  private AST defn;
  
  public Define(String name, AST ast)
  {
    this.unitName = name;
    this.defn = ast;
  }
  
/** eval This method takes the left and right components and takes both of
 * them and times them by using the mul method from Quantity, eval times 
 * them together and returns the product.
 * @param env the environment.
 * @return the product of the two numbers in the form of a Quantity object.
 */
  public Quantity eval(Map<String,Quantity> env)
  {
    Quantity spiderman = this.defn.eval(env);
    env.put(this.unitName, spiderman);
    return (Quantity)env.get(this.unitName);
  }
  
  public String toString()
  {
    return ("Define(" + unitName + "," + defn + ")");
  }  
  
  public boolean equals( Object o ) {
    if ( o instanceof Define ) {
      Define tree = (Define)o;
      return tree.unitName.equals( this.unitName ) 
        && tree.defn.equals( this.defn );
    }
    else return false;
  }
}
