package edu.brown.cs.student.userstory01;

/**
 * when file read or parse error such as file or column index/name does not exist, throw this error
 */
public class CsvParseFailureException extends Exception {

  public CsvParseFailureException(String message) {
    super(message);
  }

  public CsvParseFailureException(String message, Throwable cause) {
    super(message, cause);
  }
}
