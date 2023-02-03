package edu.brown.cs.student.userstory01;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvDataRowsParser {

  /**
   * checks which data meet requirements. It's also used and called in other user stories
   *
   * @param columnIndex if columnIndex is -1:search all; if others search the corresponding columns
   * @param containValue value to search for
   * @return rows that meet requirements
   */
  public static List<List<String>> parseAndSearch(
      BufferedReader bufferedReader, int columnIndex, String containValue)
      throws IOException, CsvParseFailureException {

    List<List<String>> result = new ArrayList<>();

    String line;
    while ((line = bufferedReader.readLine()) != null) {
      String[] arr = line.split(",");
      if (columnIndex != -1) { // only search one column
        if (columnIndex >= arr.length) {
          String errMsg =
              "column index out of range,column index is "
                  + columnIndex
                  + ", column count="
                  + arr.length;
          throw new CsvParseFailureException(errMsg);
        }
        String value = arr[columnIndex];
        if (value.contains(containValue)) {
          result.add(Arrays.asList(arr));
        }
      } else { // search all column
        for (String value : arr) {
          if (value.contains(containValue)) {
            result.add(Arrays.asList(arr));
            break;
          }
        }
      }
    }
    return result;
  }
}
