package edu.brown.cs.student.sup;

import java.util.List;

public class NotQuery implements Query {

  private final Query subQuery;

  /**
   * not (subquery)
   *
   * @param expr expression
   * @throws QueryParserFailureException transform fails
   */
  public NotQuery(String expr) throws QueryParserFailureException {
    String subQueryExpr = QueryParser.readSubQuery(expr);
    subQuery = QueryParser.parse(subQueryExpr);
  }

  @Override
  public boolean isMatch(List<String> row) {

    return !subQuery.isMatch(row);
  }
}
