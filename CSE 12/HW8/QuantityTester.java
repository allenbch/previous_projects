/*  CSE 12 Homework 8
 *  Victoria Mannina and Yutong Gou
 *  A10076744 and A09810097
 *  Section A00, A00
 *  5/26/14
 * */

import junit.framework.*;
import java.util.*;

/**
 *  Title: class QuantityTester
 *  Description: A JUnit tester for the Quantity class.
 *  @author Victoria Mannina
 *  @version 1.0
 * */
public class QuantityTester extends TestCase
{
  private Quantity SixtyHz;
  private Quantity ThirtyS;
  private Quantity TwentyS;
  private Quantity TwoHz;
  private Quantity two;
  private Quantity three;
  private Quantity eight;
  private List<String> emp;
  private Map<String,Quantity> db;

  /** Constructor with only call to super */
  public QuantityTester()
  {
    super();
  }

  /** Standard test fixture. */
  public void setUp()
  {
    emp = new ArrayList<String>();
    SixtyHz = new Quantity(60,Arrays.asList("Hz"),emp);
    ThirtyS = new Quantity(30,Arrays.asList("s"),emp);
    TwentyS = new Quantity(20,Arrays.asList("s"),emp);
    TwoHz = new Quantity(2,Arrays.asList("Hz"),emp);
    two = new Quantity(0.111112, emp, emp);
    three = new Quantity(0.111113, emp, emp);
    eight = new Quantity(0.111118, emp, emp);
    db = new HashMap<String,Quantity>();
    db.put("km", new Quantity(1000, Arrays.asList("meter"),emp));
    db.put("day", new Quantity(24, Arrays.asList("hour"),emp));
    db.put("hour", new Quantity(60, Arrays.asList("minute"),emp));
    db.put("minute", new Quantity(60, Arrays.asList("second"),emp));
    db.put("hertz", new Quantity(1, emp, Arrays.asList("second")));
    db.put("kph", new Quantity(1, Arrays.asList("km"), Arrays.asList("hour")));
  }

  /** Test to check that the no-arg constructor is working properly */
  public void testZeroConstructor()
  {
    Quantity test0 = new Quantity();
    assertEquals("Check that the no-arg constructor is correct",test0.toString(),"1.0");
  }

  /** Test to check that the 1-arg constructor is working properly */
  public void testOneConstructor()
  {
    Quantity test1 = new Quantity(SixtyHz);
    assertEquals("Check that the 1-arg constructor is correct 1", test1, SixtyHz);
    assertEquals("Check that the 1-arg constructor is correct 2", test1.hashCode(), SixtyHz.hashCode());
  }

  /** Test to check that the 3-arg constructor is working properly */
  public void testThreeConstructor()
  {
    Quantity test3 = new Quantity(9.8, Arrays.asList("m"), Arrays.asList("s","s"));
    assertEquals("Check that the 3-arg constructor is correct",test3.toString(),"9.8 m s^-2");
  }

  /** Test to check that the 3-arg constructor throws an
   * IllegalArgumentException if the numerator or denominator lists is null */
  public void testThreeConstructorException()
  {
    try
    {
      Quantity test3 = new Quantity(9.8, Arrays.asList("m"), null);
      fail("Should have generated an exception");
    }
    catch(IllegalArgumentException e)
    {
      // normal
    }
  }

  /** Test to check that the mul(tiply) method is working properly */
  public void testMul()
  {
    Quantity product = SixtyHz.mul(ThirtyS);
    Quantity expected = new Quantity(1800.0,Arrays.asList("Hz","s"),emp);
    assertEquals("Check that the mul(tiply) method is correct",product.toString(),expected.toString());
  }

  /** Test to check that the mul(tiply) method throws an
   * IllegalArgumentException if the argument is null */
  public void testMulException()
  {
    try
    {
      Quantity product = SixtyHz.mul(null);
      fail("Should have generated an exception");
    }
    catch(IllegalArgumentException e)
    {
      // normal
    }
  }

  /** Test to check that the div(ision) method is working properly */
  public void testDiv()
  {
    Quantity quotient = SixtyHz.div(ThirtyS);
    Quantity expected = new Quantity(2.0,Arrays.asList("Hz"),Arrays.asList("s"));
    assertEquals("Check that the div(ision) method is correct",quotient,expected);
  }

  /** Test to check that the div(ision) method throws an
   * IllegalArgumentExceptionArgument if the argument is null */
  public void testDivNull()
  {
    try
    {
      Quantity quotient = SixtyHz.div(null);
      fail("Should have generated an exception");
    }
    catch(IllegalArgumentException e)
    {
      // normal
    }
  }

  /** Test to check that the div(ision) method throws an
   * IllegalArgumentExceptionArgument if the argument is zero */
  public void testDivZero()
  {
    try
    {
      Quantity quotient = SixtyHz.div(new Quantity(0,Arrays.asList("m"),emp));
      fail("Should have generated an exception");
    }
    catch(IllegalArgumentException e)
    {
      // normal
    }
  }

  /** Test to check that the add(ition) method is working properly */
  public void testAdd()
  {
    Quantity sum = ThirtyS.add(TwentyS);
    Quantity expected = new Quantity(50,Arrays.asList("s"),emp);
    assertEquals("Check that the add(ition) method is correct",sum,expected);
  }

  /** Test to check that the add(ition) method throws an
   * IllegalArgumentExceptionArgument if the argument is null */
  public void testAddNull()
  {
    try
    {
      Quantity sum = SixtyHz.add(null);
      fail("Should have generated an exception");
    }
    catch(IllegalArgumentException e)
    {
      // normal
    }
  }

  /** Test to check that the add(ition) method throws an
   * IllegalArgumentExceptionArgument if the two Quantity objects do not have
   * the same units */
  public void testAddDifUnits()
  {
    try
    {
      Quantity sum = SixtyHz.add(TwentyS);
      fail("Should have generated an exception");
    }
    catch(IllegalArgumentException e)
    {
      // normal
    }
  }

  /** Test to check that the sub(traction) method is working properly */
  public void testSub()
  {
    Quantity difference = ThirtyS.sub(TwentyS);
    Quantity expected = new Quantity(10,Arrays.asList("s"),emp);
    assertEquals("Check that the sub(traction) method is correct",difference,expected);
  }

  /** Test to check that the sub(traction) method throws an
   * IllegalArgumentExceptionArgument if the argument is null */
  public void testSubNull()
  {
    try
    {
      Quantity difference = SixtyHz.sub(null);
      fail("Should have generated an exception");
    }
    catch(IllegalArgumentException e)
    {
      // normal
    }
  }

  /** Test to check that the sub(traction) method throws an
   * IllegalArgumentExceptionArgument if the two Quantity objects do not have
   * the same units */
  public void testSubDifUnits()
  {
    try
    {
      Quantity difference = SixtyHz.sub(TwentyS);
      fail("Should have generated an exception");
    }
    catch(IllegalArgumentException e)
    {
      // normal
    }
  }

  /** Test to verify that the negate method works properly */
  public void testNegate()
  {
    Quantity negation = SixtyHz.negate();
    Quantity expected = new Quantity(-60,Arrays.asList("Hz"),emp);
    assertEquals("Check that the negate method is correct",negation,expected);
  }

  /** Test to verify that the normalizedUnit method works properly */
  public void testNormalizedUnit()
  {
    Quantity kmNormalized = Quantity.normalizedUnit("km",db);
    Quantity expected = new Quantity(1000,Arrays.asList("meter"),emp);
    assertEquals("Check that the normalizedUnit method is correct 1", kmNormalized, expected);
    Quantity hourNormalized = Quantity.normalizedUnit("hour",db);
    expected = new Quantity(3600,Arrays.asList("second"),emp);
    assertEquals("Check that the normalizedUnit method is correct 2", hourNormalized, expected);
    Quantity kphNormalized = Quantity.normalizedUnit("kph",db);
    expected = new Quantity(0.277777,Arrays.asList("meter"),Arrays.asList("second"));
    assertEquals("Check that the normalizedUnit method is correct 3", kphNormalized, expected);
    Quantity smootNormalized = Quantity.normalizedUnit("smoot", db);
    expected = new Quantity(1, Arrays.asList("smoot"),emp);
    assertEquals("Check that the normalizedUnit method is correct 4", smootNormalized, expected);
  }

  /** Test to verify that the normalize method works properly */
  public void testNormalize()
  {
    Quantity nonNormalized = new Quantity(60, Arrays.asList("kph"), emp);
    Quantity normalized  = nonNormalized.normalize(db);
    Quantity expected = new Quantity(16.666666, Arrays.asList("meter"), Arrays.asList("second"));
    assertEquals("Check that the normalize method is correct", normalized, expected);
  }

  /** Test to verify that the pow(er) method works properly */
  public void testPow()
  {
    Quantity powTwo = TwoHz.pow(2);
    Quantity expected = new Quantity(4,Arrays.asList("Hz","Hz"), emp);
    assertEquals("Check that the pow(er) method is correct 1",powTwo,expected);
    Quantity powThree = TwoHz.pow(3);
    expected = new Quantity(8,Arrays.asList("Hz","Hz","Hz"),emp);
    assertEquals("Check that the pow(er) method is correct 2",powThree,expected);
    Quantity powZero = TwoHz.pow(0);
    expected = new Quantity(1,emp,emp);
    assertEquals("Check that the pow(er) method is correct 3",powZero,expected);
    Quantity powNegOne = TwoHz.pow(-1);
    expected = new Quantity(0.5,emp,Arrays.asList("Hz"));
    assertEquals("Check that the pow(er) method is correct 4",powNegOne,expected);
  }

  /** Test to verify that the equals method works properly */
  public void testEquals()
  {
    assertEquals("Check that the equals method is correct 1", two.equals(three), true);
    assertEquals("Check that the equals method is correct 2", two.equals(eight), false);
  }
  
  /** Test to verify that the hashCode method works properly */
  public void testHashCode()
  {
    Quantity copy = new Quantity(TwoHz);
    assertEquals("Check that the hashCode method is correct 1", copy.hashCode(), TwoHz.hashCode());
  }
}
