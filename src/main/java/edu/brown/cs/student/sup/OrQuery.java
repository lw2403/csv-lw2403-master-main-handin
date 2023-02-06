package edu.brown.cs.student.sup;

import java.util.List;

public class OrQuery implements Query {

  private final Query subQuery1;
  private final Query subQuery2;

  /**
   * or (subquery)
   *
   * @param expr expression
   * @throws QueryParserFailureException when transform fails
   */
  public OrQuery(String expr) throws QueryParserFailureException {
    String subQueryExpr = QueryParser.readSubQuery(expr); // expr1,expr2
    String[] exprArr = QueryParser.splitToTwoSubExpr(subQueryExpr);
    subQuery1 = QueryParser.parse(exprArr[0]);
    subQuery2 = QueryParser.parse(exprArr[1]);
  }

  @Override
  public boolean isMatch(List<String> row) {
    return subQuery1.isMatch(row) || subQuery2.isMatch(row);
  }
}
