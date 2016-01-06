/*  CSE 12 Homework 8
 *  Victoria Mannina and Yutong Gou
 *  A10076744 and A09810097
 *  Section A00, A00
 *  5/26/14
 * */

import java.io.*;
import java.util.*;
import junit.framework.TestCase;

/**
 *  Title: class UnicalcTester
 *  Description: A JUnit tester for the Unicalc class.
 *  @author Yutong Gou
 *  @version 1.0
 * */
public class UnicalcTester extends TestCase {
  private String input;
	private Unicalc unicalc;
	private Map<String,Quantity> env;
    
  /** Standard test fixture. */
  public void setUp(){
		unicalc = new Unicalc();
		env = QuantityDB.getDB();
  }

  /** Test to verify S is working properly */
  public void testS(){
    input = "def smoot 67 in";
    System.setOut(new PrintStream(new OutputStream(){
      public void write(int b) {}
    }));
    unicalc.tokenize(input);
    AST ast = unicalc.parse();
  	assertEquals( "67.0 in", ast.eval(env).toString());
  }

  /** Test to verify L is working properly */
  public void testL(){
    input = "# 60Hz * 30s";
    System.setOut(new PrintStream(new OutputStream(){
      public void write(int b) {}
    }));
    unicalc.tokenize(input);
    AST ast = unicalc.parse();
  	assertEquals( "1800.0", ast.eval(env).toString());
  }

  /** Test to verify E is working properly */
  public void testE(){
    input = "14m+9m";
    System.setOut(new PrintStream(new OutputStream(){
      public void write(int b) {
    }
    }));
    unicalc.tokenize(input);
    AST ast = unicalc.parse();
  	assertEquals( "23.0 m", ast.eval(env).toString());
  }

  /** Test to verify P is working properly */
  public void testP(){
    input = "60 Hz * 30 s";
    System.setOut(new PrintStream(new OutputStream(){
      public void write(int b) {
    }
    }));
    unicalc.tokenize(input);
    AST ast = unicalc.parse();
  	assertEquals( "1800.0 Hz s", ast.eval(env).toString());
  }

  /** Test to verify K is working properly */
  public void testK(){
    input = "-60 Hz * 30 s";
    System.setOut(new PrintStream(new OutputStream(){
      public void write(int b) {
    }
    }));
    unicalc.tokenize(input);
    AST ast = unicalc.parse();
  	assertEquals( "-1800.0 Hz s", ast.eval(env).toString());
  }

  /** Test to verify Q is working properly */
  public void testQ(){
    input = "# 60 Hz ^ 2/hour";
    System.setOut(new PrintStream(new OutputStream(){
      public void write(int b) {
    }
    }));
    unicalc.tokenize(input);
    AST ast = unicalc.parse();
  	assertEquals( "0.01667 second^-3", ast.eval(env).toString());
  }

  /** Test to verify R is working properly */
  public void testR(){
    input = "# 364.4 smoot";
    System.setOut(new PrintStream(new OutputStream(){
      public void write(int b) {
    }
    }));
    unicalc.tokenize(input);
    AST ast = unicalc.parse();
  	assertEquals( "364.4 smoot", ast.eval(env).toString());
  }
}
