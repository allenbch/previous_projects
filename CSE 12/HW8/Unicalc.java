/*  CSE 12 Homework 8
 *  Yutong Gou, Victoria Mannina
 *  A09810097, A10076744
 *  A00, A00
 *  May 26, 2014
 */

// Extended by:

import java.util.*;        // Scanner, LinkedList, ...
import java.util.regex.*;  // Pattern, Matcher, ...

/** 
 *  Title: class Unicalc
 *  Description: A class that contains methods for parsing a list of tokens
 *  according to the rules of the Unicalc languagre. The ultimate goal of these
 *  parsing methods to produce an AST that can be passed to the evaluator. Also
 *  contains a main method that implements the read-eval-print loop for the
 *  Unicalc interpreter.
 *  @author Yutong Guo, Victoria Mannina
 *  @version 2.0
 * */
class Unicalc
{

  private LinkedList<String> toks;  // Used by the parser functions
                                    //    to keep track of the remaining tokens.
                                    // Initialized by tokenize(...)
                                    // Parsing functions remove the tokens
  
  public Unicalc() 
  { 
     // Nothing we need to do in the constructor.
  }

  // method tokenize
  //   Takes a string, and sets this.toks to be a linked 
  //     list of all Unicalc tokens in this string
  //     (ignoring whitespace, and other things that can't
  //     be part of a token.)
  //  
  public void tokenize(String input)
  {
    this.toks = new LinkedList<String>( Tokenizer.tokenize( input ) );
    
    System.out.println("Tokens: " + this.toks);
    
    return;
  }
  
  // method parse
  // Tries to parse the contents of this.toks
  //    using recursive descent, starting with the start symbol.
  // output: an abstract syntax tree representing the input
  //
  public AST parse()
  {
    // Begin parsing with the start symbol
    AST answer = this.S(); 
    
    // Display results  
    System.out.println("AST: " + answer);
    if (! toks.isEmpty()) {
      System.out.println("Extra tokens left over: " + toks);
    }
    System.out.println();
      
    return answer;
  }
  
  /** S this method will read and see if the input is a definition or not
    * @return calls L when the input was not a def.
    * */
  public AST S()
  {
	//here it reads the input to see if the first incoming token is
	//def and if it is def, then it will define the unit, if not
	//it will pass it on to the next method
	if ("def".equals(this.toks.peek())){
	  this.toks.pop();
	  if (isAlphabetic(this.toks.peek())){
	    String unit = this.toks.pop();
  	    return new Define(unit, L());
	  }
	  String err = "Expected units, but found:'" + (String)this.toks.peek() + "'";
	  throw new ParseError(err);
	}
    return L();
  }

  /** L this method will read and see if the input needs to be normalized
    * @return calls E when the input does not need to be noramlized.
    * */
  public AST L()
  {
	//here it reads the input to see if the first incoming token is
	//# and if it is a pound key, then it will normalize the unit, if not
	//it will pass it on to the next method
	if ("#".equals(this.toks.peek())){
	  this.toks.pop();
	  return new Normalize(E());
    }
      return E();
  }

  /** E this method will read and see if the input will require addition,
    * subtraction, or nothing so do, so it returns P().
    * @return p when the input is neither a + nor a -.
    * */
  public AST E() 
  {
	//here it reads the input to see if the first incoming token is
	//a plus or minus and if it is one  of those, then it will call 
	//the corresponding sum or difference methods and if it doesnt need
	//to, it will pass it on to the next method
        AST p = P();
	if ("+".equals(this.toks.peek())){
	  this.toks.pop();
	  return new Sum(p, E());
	}
	if ("-".equals(this.toks.peek())){
	  this.toks.pop();
	  return new Difference(p, E());
	}
    return p;
  }
  
  /** P this method will read and see if the input will require multiplication,
    * division, or nothing so do, so it returns K().
    * @return k when the input doesnt need mul or div.
    * */
  public AST P()
  {
    //   P -> K * P | K / P | K
    //   here it reads the input to see if the first incoming token is
    //   a * or a /, and it will call product and quotient if it is one
    //   of them, if it doesnt need to, then it calls and pass it on to 
    //   the next method
    AST k = K();
    if ("*".equals(this.toks.peek())){
      this.toks.pop();
      return new Product(k, P());
    }
    if ("/".equals(this.toks.peek())){
      this.toks.pop();
      return new Quotient(k, P());
    }
    return k;
  }
    
  /** K this method will read and see if the input will require negation and 
    * returns Q().
    * @return q when the input doesnt need negation.
    * */
  public AST K()
  {
	//here it reads the input to see if the first incoming token is
	//def and if it is def, then it will define the unit, if not
	//it will pass it on to the next method
    if ("-".equals(this.toks.peek())){
	  this.toks.pop();
	  return new Negation(K());
	}
    return Q();
  }

  // private method isNumber
  //   Checks that the given string is non-null
  //      and could be converted to a double
  //      (via the Double.parseDouble function)
  //      without generating an error
  //
  private static boolean isNumber(String s)
  {
    if (s == null) return false;
    
    try { 
      Double.parseDouble(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
  
  // private method isAlphabetic
  //   Checks that the given string is non-null
  //      and consists of (ASCII) letters or underscores
  //      (but not digits).
  //
  private static boolean isAlphabetic(String s)
  {
    return s != null && s.matches("[a-zA-Z_]+");
  }
  
  public AST Q()
  {
    // Q -> R | R Q 
    
    //   here it reads the input to see if the first incoming token is
    //   a alphabet or number or ( then if will call multiply, if it 
    //   doesnt need to, then it calls and pass it on to 
    //   the next method
    AST r = R();

	String toks2 = this.toks.peek();
	if ((isAlphabetic(toks2)) || ("(".equals(toks2)) || (isNumber(toks2))) {
	  return new Product(r, Q());
	}
    return r;  // I don't think I should *always* do this
               //   (e.g., if I peek and the R is followed
               //    by a number, word, or left parenthesis,
               //    I should try to recurively grab at least
               //    one more R via Q...)

  }

  public AST R()
  {
    // R -> V | V ^ J
    
    AST v = V();

	if ("^".equals(this.toks.peek())){
	  this.toks.pop();
	  return new Power(v, J());
    }
    return v;
  }

  //  --------------------------------------------------
  //   The following functions should need no changes.
 
  
  // Function for grabbing tokens corresponding to V in the grammar,
  //    and returning a corresponding abstract syntax tree.
  // Should work without changes, but you should make sure
  //    you understand how it works.    
  public AST V()
  {
    // V -> D | W | ( L )
        
    List<String> emp = Collections.<String>emptyList();  // an empty list

    String next = toks.peek();  
    
    if ( isNumber(next) ) {
      // D
      double d1 = D();
      return new Value(new Quantity(d1, emp, emp));
      
    } else if ( isAlphabetic(next) ) {
      // W 
      String unitName = toks.pop();
      return new Value(new Quantity(1.0, Arrays.asList(unitName), emp));
      
    } else if ( "(".equals(next) ) {
      // ( L )

      toks.pop();  // Skip the left parenthesis
      AST l = L(); // Recursively grab the contents, which should match L.
 
      String after_l = toks.peek();

      // Immediately following that L should be a right parenthesis. Check
      if ( ")".equals(after_l) ) {
        // It's there, so get rid of it (since we want to remove all
        //    the tokens corresponding to V)
        toks.pop();
        return l;     // A parenthesized L has the same tree as a non-parenthesized L
                      //   (Since the tree structure *already* tells us the way that
                      //    our subexpressions are grouped, keeping around these
                      //    parentheses in the AST is unnecessary; they wouldn't affect
                      //    the final answer.)
      } else {
        throw new ParseError("Expected close-parenthesis, but found: '" + next + "'");
      }  

    } else {
      // The first token can't possibly be part of a V.
      throw new ParseError("Expected number or identifier or subexpression, but found: '" 
                   + next + "'");
    }
  }
  

  // Parsing function for J in the grammar.
  //   Note: unlike most of the parsing functions,
  //         we're returning the corresponding int rather than an AST.
  //
  public int J()
  {
    // J -> -I | I

    String next = toks.peek();

    if ( "-".equals(next) ) {
      toks.pop();       // Get rid of the minus-sign token
      int i = I();      // Following it should be something matching I
      return (- i);     // Combine the minus sign and i
    } else {
      return I();       // No minus sign, so look for something matching I
    }
  }      

  // Parsing function for I in the grammar.
  //   Note: unlike most of the parsing functions,
  //         we're returning the corresponding int rather than an AST.
  //
  public int I() 
  {
    // I -> (any nonnegative integer)

    String next = toks.peek();
    try {
      toks.pop();
      return Integer.parseInt(next);
    } catch (NumberFormatException e) {
      throw new ParseError("Expected an integer, but found: '" + next + "'");
    }
  }
  
  // Parsing function for D in the grammar.
  //   Note: unlike most of the parsing functions,
  //         we're returning the corresponding double-precision
  //         floating-point number, rather than an AST.
  public double D() {
    // D -> (any nonnegative number, integer or floating-point)

    String next = toks.peek();
    try {
      toks.pop();
      return Double.parseDouble(next);
    } catch (NumberFormatException e) {
      throw new ParseError("Expected a number, but found: '" + next + "'");
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////
  
  /** method main
   * inputs: the usual
   * outputs: none, but this method has the Unicalc read-eval-print loop
   */   
  public static void main(String[] args)
  {
    Scanner console = new Scanner(System.in);
    Unicalc unicalc = new Unicalc();

    Map<String,Quantity> env = QuantityDB.getDB();

    while (true)
    {
      System.out.print("input>  ");
      String input = console.nextLine();
      
      if (input.equals("")) break;  // Quit if no input
      
      unicalc.tokenize(input);
      
      AST ast = unicalc.parse();
      
      System.out.println("Result: " + ast.eval(env) + "\n");

    }
  }

}      

// ParseError class
//    A new kind of exception to be thrown if there is
//    an error in parsing

class ParseError extends RuntimeException {
   public ParseError(String message) {
         // Create a ParseError object containing
         // the given message as its error message.
      super(message);
   }
}
