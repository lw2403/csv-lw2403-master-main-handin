package edu.brown.cs.student.sup;

import edu.brown.cs.student.main.CreatorFromRow;
import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.userstory01.CsvParseFailureException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Search condition is now an expression */
public class CsvParser4<ROW> {

  private final CreatorFromRow<ROW> creatorFromRow;

  public CsvParser4(CreatorFromRow<ROW> creatorFromRow) {
    this.creatorFromRow = creatorFromRow;
  }

  /**
   * CsvParser's parseAndSearch method: return Each row is represented by an object type determined
   * by creatorFromrow
   *
   * @param reader read data from reader
   * @param queryExpr query expression
   * @param firstRowIsHeader whether first row is a header
   * @return rows that meet requirements
   * @throws FactoryFailureException When CreatorFromRow fails to work
   * @throws QueryParserFailureException Transform fails
   * @throws CsvParseFailureException File read or parse error such as file/column index/name does
   *     not exist
   */

  // changes from 3: String queryExpr
  public List<ROW> parseAndSearch(Reader reader, String queryExpr, Boolean firstRowIsHeader)
      throws FactoryFailureException, QueryParserFailureException, CsvParseFailureException {
    String queryExprWithColumnIndex;
    // read data
    try (BufferedReader bufferedReader = new BufferedReader(reader)) {
      if (firstRowIsHeader) {
        String headerLine = bufferedReader.readLine();
        String[] columnNames = headerLine.split(",");
        queryExprWithColumnIndex =
            QueryParser.exprTransferColumnNameToColumnIndex(queryExpr, columnNames);
      } else {
        queryExprWithColumnIndex = queryExpr;
      }
      // query interface
      Query query = QueryParser.parse(queryExprWithColumnIndex);
      List<ROW> result = new ArrayList<>(); // initialize a new empty object to store strings
      // row from reader
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] arr = line.split(",");
        // change from array to list
        List<String> list = Arrays.asList(arr);
        // check if data matches query
        if (query.isMatch(list)) {
          result.add(creatorFromRow.create(list));
        }
      }
      return result;
    } catch (IOException e) {
      String errMsg = "ERROR: read error," + e.getMessage();
      System.err.println(errMsg);
      throw new CsvParseFailureException(errMsg, e);
    }
  }
}
