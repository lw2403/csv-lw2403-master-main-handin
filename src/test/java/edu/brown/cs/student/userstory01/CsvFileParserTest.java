package edu.brown.cs.student.userstory01;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** tests userstory01 search CSV data with and without column headers; */
public class CsvFileParserTest {

  /** test 1 searches csv with header using column index, search value */
  // JUNIT tests
  // Assertions class with assertEquals, assertTrue...
  // Assertions.fail(e) similar to throw error
  // @test test method
  @Test
  public void test1() {
    CsvFileParser parser = new CsvFileParser();
    List<List<String>> searchResult = null;
    String containValue = "Rigel Kentauru";
    int columnIndex = 1;
    try {
      searchResult =
          parser.parseAndSearch("data/stars/ten-star.csv", columnIndex, containValue, true);
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(2, searchResult.size());
    for (List<String> row : searchResult) {
      Assertions.assertTrue(
          row.get(columnIndex).contains(containValue), "result does not include the value");
    }
  }

  /** test 2 searches csv without header using column index, search value */
  @Test
  public void test2() {
    CsvFileParser parser = new CsvFileParser();
    List<List<String>> searchResult = null;
    try {
      searchResult = parser.parseAndSearch("data/stars/ten-star.csv", 1, "ProperName", false);
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size());
    Assertions.assertTrue(
        searchResult.get(0).contains("ProperName"), "should include 'ProperName'");
  }

  /**
   * Test 3-6 search for values that are, and aren’t, present in the CSV; Test 3 uses column name to
   * get rows containing "barnard's star"
   */
  @Test
  public void test3() {
    CsvFileParser parser = new CsvFileParser();
    List<List<String>> searchResult = null;
    try {
      searchResult = parser.parseAndSearch("data/stars/ten-star.csv", "ProperName", "Star");
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size(), "result count error");
    Assertions.assertTrue(
        searchResult.get(0).contains("Barnard's Star"), "should include 'Barnard's Star'");
  }

  /**
   * Searching for values that are, and aren’t, present in the CSV; using column name to find value
   * that does not exist xxxxxxxx
   */
  @Test
  public void test4() {
    CsvFileParser parser = new CsvFileParser();
    List<List<String>> searchResult = null;
    try {
      searchResult =
          parser.parseAndSearch("data/stars/ten-star.csv", "ProperName", "xxxxxxxxxxxxxxxxxx");
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(0, searchResult.size(), searchResult.toString());
  }

  // containValue exists
  @Test
  public void test5() {
    CsvFileParser parser = new CsvFileParser();
    List<List<String>> searchResult = null;
    try {
      searchResult = parser.parseAndSearch("data/stars/ten-star.csv", 1, "Star", true);
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size(), searchResult.toString());
    Assertions.assertTrue(
        searchResult.get(0).contains("Barnard's Star"), "should include 'Barnard's Star'");
  }

  // containValue does not exists
  @Test
  public void test6() {
    CsvFileParser parser = new CsvFileParser();
    List<List<String>> searchResult = null;
    try {
      searchResult =
          parser.parseAndSearch("data/stars/ten-star.csv", 1, "xxxxxxxxxxxxxxxxxx", true);
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(0, searchResult.size(), searchResult.toString());
  }

  /** test 7 tests exception with invalid columns */
  @Test
  public void test7() {
    CsvFileParser parser = new CsvFileParser();

    Assertions.assertThrows(
        CsvParseFailureException.class,
        () -> parser.parseAndSearch("data/stars/ten-star.csv", 99, "xxxxxxxxxxxxxxxxxx", true));

    Assertions.assertThrows(
        CsvParseFailureException.class,
        () ->
            parser.parseAndSearch("data/stars/ten-star.csv", "xxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxx"));
  }

  /** Searching for values that are present, but are in the wrong column; */
  @Test
  public void test8() {
    CsvFileParser parser = new CsvFileParser();
    List<List<String>> searchResult = null;
    try {
      searchResult = parser.parseAndSearch("data/stars/ten-star.csv", 1, "Star", true);
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size(), searchResult.toString());
    Assertions.assertTrue(
        searchResult.get(0).contains("Barnard's Star"), "include 'Barnard's Star'");

    try {
      searchResult = parser.parseAndSearch("data/stars/ten-star.csv", 2, "Star", true);
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(0, searchResult.size(), searchResult.toString());
  }

  /** file path does not exist */
  @Test
  public void test9() {
    CsvFileParser parser = new CsvFileParser();
    Assertions.assertThrows(
        CsvParseFailureException.class,
        () -> parser.parseAndSearch("data/stars/ten-starxxxxxxxxx.csv", 1, "Star", true));
  }

  /** column name does not exist */
  @Test
  public void test10() {
    CsvFileParser parser = new CsvFileParser();
    Assertions.assertThrows(
        CsvParseFailureException.class,
        () -> parser.parseAndSearch("data/stars/ten-star.csv", "xxxxxx", "Star"));
  }
}
