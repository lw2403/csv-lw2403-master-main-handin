package edu.brown.cs.student.sup;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QueryParser {

  /** transforms string expression to query object */
  public static Query parse(String expr) throws QueryParserFailureException {
    // basic query
    // regex
    // ^ start of string
    // -? any signs
    // [0-9]+ any integer
    // .* any string
    if (expr.matches("^-?[0-9]+.*")) {
      return new BasicQuery(expr);
    } else if (expr.toLowerCase().startsWith("and")) {
      return new AndQuery(expr);
    } else if (expr.toLowerCase().startsWith("or")) {
      return new OrQuery(expr);
    } else if (expr.toLowerCase().startsWith("not")) {
      return new NotQuery(expr);
    } else {
      throw new QueryParserFailureException(expr);
    }
  }

  /**
   * @param expr and( sub-expr ), or( sub-expr ), not( sub-expr )
   * @return sub-expr
   */
  public static String readSubQuery(String expr) {
    int left = expr.indexOf("(");
    int right = expr.lastIndexOf(")");
    return expr.substring(left + 1, right);
  }

  // xxx,xxx
  // and(a,b),or(a,b)
  public static String[] splitToTwoSubExpr(String twoExpr) throws QueryParserFailureException {
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < twoExpr.length(); i++) {
      char c = twoExpr.charAt(i);
      switch (c) {
        case '(':
          stack.push(c);
          break;
        case ')':
          stack.pop();
          break;
        case ',':
          if (stack.isEmpty()) {
            String sub1 = twoExpr.substring(0, i);
            String sub2 = twoExpr.substring(i + 1);
            return new String[] {sub1.trim(), sub2.trim()};
          }
          break;
      }
    }
    throw new QueryParserFailureException(twoExpr + " split fail");
  }

  /**
   * column 1 contains "hangman" -> 0=hangman
   * column name contains "hangman" -> 0=hangman
   * regex replace
   * @param queryExpr expression with column name
   * @param columnNames column names
   * @return expression with column index
   */
  public static String exprTransferColumnNameToColumnIndex(String queryExpr, String[] columnNames)
      throws QueryParserFailureException {
    //regex
    //column = column
    // \\s+ any blanks
    // .*? any string less
    // () group
    //\" \"string
    String regexExpr = "column\\s+(.*?)\\s+contains\\s+\"(.*?)\"";
    Pattern pattern = Pattern.compile(regexExpr);
    Matcher matcher = pattern.matcher(queryExpr);
    StringBuilder sbr = new StringBuilder();
    // change column name to column index
    //  column 1 contains "hangman" -> 0=hangman
    //  column name contains "hangman" -> 0=hangman regex
    while (matcher.find()) {
      String columnIndexOrColumnName = matcher.group(1);
      String containValue = matcher.group(2);
      int columnIndex = columnNameToColumnIndex(columnNames, columnIndexOrColumnName);
      matcher.appendReplacement(sbr, columnIndex + "=" + containValue);
    }
    // add remaining string to StringBuffer
    matcher.appendTail(sbr);
    return sbr.toString();
  }

  private static int columnNameToColumnIndex(String[] columnNames, String columnName)
      throws QueryParserFailureException {
    try {
      return Integer.parseInt(columnName);
    } catch (NumberFormatException e) {
      for (int i = 0; i < columnNames.length; i++) {
        if (columnNames[i].equals(columnName)) {
          return i;
        }
      }
    }
    throw new QueryParserFailureException(columnName + " NOT found!");
  }
}
