package edu.brown.cs.student.sup;

/** Exception thrown when transform fails */
public class QueryParserFailureException extends Exception {

  final String expr;

  public QueryParserFailureException(String expr) {
    super(expr);
    this.expr = expr;
  }
}
