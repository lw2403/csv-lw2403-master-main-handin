package edu.brown.cs.student.userstory02;

import edu.brown.cs.student.userstory01.CsvDataRowsParser;
import edu.brown.cs.student.userstory01.CsvParseFailureException;
import java.io.*;
import java.util.List;

/** this class gets data from the Reader test with file or string reader */
public class CsvParser2 {
  // returns rows, with column index
  public List<List<String>> parseAndSearch(
      Reader reader, int columnIndex, String containValue, boolean firstRowIsHeader)
      throws CsvParseFailureException {
    try (BufferedReader bufferedReader = new BufferedReader(reader)) {
      if (firstRowIsHeader) {
        bufferedReader.readLine();
      }
      // calls CsvDataRowsParser
      return CsvDataRowsParser.parseAndSearch(bufferedReader, columnIndex, containValue);
    } catch (IOException e) {
      String errMsg = "ERROR: read error," + e.getMessage();
      throw new CsvParseFailureException(errMsg, e);
    }
  }

  /**
   * return rows, with column name
   *
   * @param reader StringReader or FileReader or Other Reader
   * @param columnName column name
   * @param containValue search value
   * @return list of row
   * @throws CsvParseFailureException parse fail
   */
  public List<List<String>> parseAndSearch(Reader reader, String columnName, String containValue)
      throws CsvParseFailureException {
    try (BufferedReader bufferedReader = new BufferedReader(reader)) {
      // columnName -> columnIndex
      String line = bufferedReader.readLine();
      if (columnName == null) {
        // search all rows
        return CsvDataRowsParser.parseAndSearch(bufferedReader, -1, containValue);
      }
      // find row to search for
      String[] columnNameArr = line.split(",");
      int columnIndex = -1;
      for (int i = 0; i < columnNameArr.length; i++) {
        String columnNameInArr = columnNameArr[i];
        if (columnNameInArr.equals(columnName)) {
          columnIndex = i;
        }
      }
      if (columnIndex == -1) {
        throw new CsvParseFailureException("column name (" + columnName + ") not found in csv");
      } else {
        return CsvDataRowsParser.parseAndSearch(bufferedReader, columnIndex, containValue);
      }
    } catch (IOException e) {
      throw new CsvParseFailureException("column name (" + columnName + ") not found in csv", e);
    }
  }
}
