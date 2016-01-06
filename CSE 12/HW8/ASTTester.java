/*  CSE 12 Homework 8
 *  Victoria Mannina and Yutong Gou
 *  A10076744 and A09810097
 *  Section A00, A00
 *  5/26/14
 * */

import junit.framework.*;
import java.util.*;

/**
 *  Title: class ASTTester
 *  Description: A JUnit tester for the AST class.
 *  @author Victoria Mannina
 *  @version 1.0
 * */
public class ASTTester extends TestCase
{
  private Map<String,Quantity> db;
  private Quantity SixtyHz;
  private Quantity ThirtyS;
  private Quantity TwentyS;
  private Quantity Minute1;
  private Quantity Lab12;
  private List<String> emp;
  private Value twentysec;
  private Value thirtysec;
  private Value sixtyhertz;
  private Value minute;
  private Value lab;

  /** Constructor with only call to super */
  public ASTTester()
  {
    super();
  }

  /** Standard test fixture. */
  public void setUp()
  {
    emp = new ArrayList<String>();
    db = QuantityDB.getDB();
    SixtyHz = new Quantity(60,Arrays.asList("Hz"),emp);
    ThirtyS = new Quantity(30,Arrays.asList("s"),emp);
    TwentyS = new Quantity(20,Arrays.asList("s"),emp);
    Minute1 = new Quantity(1,Arrays.asList("minute"),emp);
    Lab12 = new Quantity(1,Arrays.asList("lab"),emp);
    sixtyhertz = new Value(SixtyHz);
    twentysec = new Value(TwentyS);
    thirtysec = new Value(ThirtyS);
    minute = new Value(Minute1);
    lab = new Value(Lab12);
  }

  /** Test to verify that the eval method is working within the Product class */
  public void testProduct()
  {
    Product product = new Product(twentysec,thirtysec);
    assertEquals("Check to verify that product works","600.0 s^2", product.eval(db).toString());
  }

  /** Test to verify that the eval method is working within the Quotient class */
  public void testQuotient()
  {
    Quotient quotient = new Quotient(sixtyhertz,thirtysec);
    assertEquals("Check to verify that quotient works","2.0 Hz s^-1", quotient.eval(db).toString());
  }

  /** Test to verify the eval method is working within the Sum class */
  public void testSum()
  {
    Sum sum = new Sum(thirtysec,twentysec);
    assertEquals("Check to verify that the sum works","50.0 s",sum.eval(db).toString());
  }

  /** Test to verify the eval method is working within the Difference class */
  public void testDifference()
  {
    Difference difference = new Difference(thirtysec,twentysec);
    assertEquals("Check to verify that the difference works","10.0 s",difference.eval(db).toString());
  }

  /** Test to verify the eval method is working within the Power class */
  public void testPower()
  {
    Power power = new Power(twentysec,2);
    assertEquals("Check to verify that the power works","400.0 s^2",power.eval(db).toString());
  }

  /** Test to verify the eval method is working within the Negation class */
  public void testNegation()
  {
    Negation negation = new Negation(sixtyhertz);
    assertEquals("Check to verify that the negation works","-60.0 Hz",negation.eval(db).toString());
  }

  /** Test to verify the eval method is working within the Normalize class */
  public void testNormalize()
  {
    Normalize normalize = new Normalize(minute);
    assertEquals("Check to verify that the normalise works","60.0 second",normalize.eval(db).toString());
  }

  /** Test to verify the eval method is working within the Define class */
  public void testDefine()
  {
    Define define = new Define("HW8",lab);
    assertEquals("Check to verify that the define works","1.0 lab",define.eval(db).toString());
    assertEquals("Check to verify that the define works 2",db.containsKey("HW8"), true);
 }

  /** Test to verify the eval method is working within the Value class */
  public void testValue()
  {
    Value val = new Value(new Quantity(10,Arrays.asList("hour"),emp));
    assertEquals("Check to verify that the value works","10.0 hour",val.eval(db).toString());
  }
}
