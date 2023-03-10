package edu.brown.cs.student.sup;

import java.util.List;

public class AndQuery implements Query {
  // and uses query
  private final Query subQuery1;
  private final Query subQuery2;

  /**
   * and(sub1, sub2)
   *
   * @param expr expression
   * @throws QueryParserFailureException transform fails
   */
  public AndQuery(String expr) throws QueryParserFailureException {
    String subQueryExpr = QueryParser.readSubQuery(expr); // expr1,expr2
    String[] exprArr = QueryParser.splitToTwoSubExpr(subQueryExpr);
    subQuery1 = QueryParser.parse(exprArr[0]);
    subQuery2 = QueryParser.parse(exprArr[1]);
  }

  // check data row match expression object or not
  @Override
  public boolean isMatch(List<String> row) {
    return subQuery1.isMatch(row) && subQuery2.isMatch(row);
  }
}
