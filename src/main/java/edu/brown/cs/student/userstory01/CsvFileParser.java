package edu.brown.cs.student.userstory01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Uses CsvDataRowsParser Parse file based on different cases (e.g with or without header)
 * User can search based on column name or column index number
 */
public class CsvFileParser {
  //parseAndSearch method
  public List<List<String>> parseAndSearch(
      String fileName, int columnIndex, String containValue, boolean firstRowIsHeader)
      throws CsvParseFailureException {
    try (BufferedReader bufferedReader =
        new BufferedReader(new FileReader(fileName)) // read data from reader
    ) {
      if (firstRowIsHeader) { // read first line
        bufferedReader.readLine();
      }
      //call CsvDataRowsParser
      return CsvDataRowsParser.parseAndSearch(bufferedReader, columnIndex, containValue);
    } catch (FileNotFoundException e) {
      String errMsg = "ERROR:" + fileName + " not Found!";
      System.err.println(errMsg);
      throw new CsvParseFailureException(errMsg, e);
    } catch (IOException e) {
      String errMsg = "ERROR: read error," + e.getMessage();
      System.err.println(errMsg);
      throw new CsvParseFailureException(errMsg, e);
    }
  }

  /**
   * search via column name if column name is empty search all if column name does not exist, throw
   * exception
   *
   * @param fileName csv file path
   * @param columnName the name of the column searched
   * @param containValue the value being searched
   * @return all rows that meet requirements
   * @throws CsvParseFailureException file read error
   */
  public List<List<String>> parseAndSearch(String fileName, String columnName, String containValue)
      throws CsvParseFailureException {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
      // columnName -> columnIndex
      String line = bufferedReader.readLine();
      if (columnName == null) {
        // search all rows
        return CsvDataRowsParser.parseAndSearch(bufferedReader, -1, containValue);
      }
      // find the row to search for
      String[] columnNameArr = line.split(",");
      int columnIndex = -1;
      for (int i = 0; i < columnNameArr.length; i++) {
        String columnNameInArr = columnNameArr[i];
        if (columnNameInArr.equals(columnName)) {
          // column name to column index
          columnIndex = i;
        }
      }
      //if column name not found in header row
      if (columnIndex == -1) {
        throw new CsvParseFailureException("column name (" + columnName + ") not found in csv");
      } else {
        //call CsvDataRowsParser
        return CsvDataRowsParser.parseAndSearch(bufferedReader, columnIndex, containValue);
      }
    } catch (FileNotFoundException e) {
      System.err.println("ERROR:" + fileName + " not Found!");
      throw new CsvParseFailureException("column name (" + columnName + ") not found in csv", e);
    } catch (IOException e) {
      System.err.println("ERROR: read error," + e.getMessage());
      throw new CsvParseFailureException("column name (" + columnName + ") not found in csv", e);
    }
  }
}
