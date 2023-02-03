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

/**
 * CsvParser's parseAndSearch method: return data that meet requirements Each row is represented by
 * an object type determined by creatorFromrow
 */
public class CsvParser4<ROW> {

  private final CreatorFromRow<ROW> creatorFromRow;

  public CsvParser4(CreatorFromRow<ROW> creatorFromRow) {
    this.creatorFromRow = creatorFromRow;
  }

  public List<ROW> parseAndSearch(Reader reader, String queryExpr, Boolean firstRowIsHeader)
      throws FactoryFailureException, QueryParserFailureException, CsvParseFailureException {
    String queryExprWithColumnIndex;
    try (BufferedReader bufferedReader = new BufferedReader(reader)) {
      if (firstRowIsHeader) {
        String headerLine = bufferedReader.readLine();
        String[] columnNames = headerLine.split(",");
        queryExprWithColumnIndex =
            QueryParser.exprTransferColumnNameToColumnIndex(queryExpr, columnNames);
      } else {
        queryExprWithColumnIndex = queryExpr;
      }
      Query query = QueryParser.parse(queryExprWithColumnIndex);
      List<ROW> result = new ArrayList<>(); // initialize a new empty object to store strings
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] arr = line.split(",");
        List<String> list = Arrays.asList(arr);
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
