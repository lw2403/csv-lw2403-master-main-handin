package edu.brown.cs.student.sup;

import java.util.List;

public class BasicQuery implements Query {

  private final Integer columnIndex;
  private final String containValue;

  // 1 contains "zhangsan"
  // -1 contains "lisi"
  // name contains "zhangsan"

  /**
   * column index = string e.g 1 = abc
   *
   * @param expr expression
   * @throws QueryParserFailureException Transform fails
   */
  public BasicQuery(String expr) throws QueryParserFailureException {
    String[] arr = expr.split("=");
    if (arr.length != 2) {
      throw new QueryParserFailureException(expr);
    }
    this.columnIndex = Integer.parseInt(arr[0].trim());
    this.containValue = arr[1].trim();
  }

  @Override
  public boolean isMatch(List<String> valuesInRow) {
    if (columnIndex >= 0) {
      return valuesInRow.get(columnIndex).contains(containValue);
    } else {
      for (String value : valuesInRow) {
        if (value.contains(containValue)) {
          return true;
        }
      }
      return false;
    }
  }
}
