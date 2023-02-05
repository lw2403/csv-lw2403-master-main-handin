package edu.brown.cs.student.userstory02;

import edu.brown.cs.student.userstory01.CsvParseFailureException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** CSV data in different Reader types (e.g., StringReader and FileReader); */
public class CsvParser2Test {

  CsvParser2 parser;

  @BeforeEach
  public void init() {
    parser = new CsvParser2();
  }

  /** test 1 tests CSV data in FileReader */
  // FileReader
  @Test
  public void test1() {

    FileReader reader = null;
    try {
      reader = new FileReader("data/stars/ten-star.csv");
    } catch (FileNotFoundException e) {
      Assertions.fail(e);
    }
    List<List<String>> searchResult = null;
    try {
      searchResult = parser.parseAndSearch(reader, 1, "Rigel Kentauru", true);
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(2, searchResult.size());
    ArrayList<String> matchValues = new ArrayList<>(2);
    matchValues.add(searchResult.get(0).get(1));
    matchValues.add(searchResult.get(1).get(1));
    Assertions.assertTrue(
        matchValues.contains("Rigel Kentaurus B"), "should have 'Rigel Kentaurus B'");
    Assertions.assertTrue(
        matchValues.contains("Rigel Kentaurus A"), "should have 'Rigel Kentaurus A'");
  }

  /** Tests CSV data in StringReader */
  @Test
  public void test2() {
    byte[] bytes = new byte[0];
    try {
      bytes = Files.readAllBytes(Paths.get("data/stars/ten-star.csv"));
    } catch (IOException e) {
      Assertions.fail(e);
    }
    String allContent = new String(bytes);
    StringReader reader = new StringReader(allContent);
    List<List<String>> searchResult = null;
    try {
      searchResult = parser.parseAndSearch(reader, 1, "Star", true);
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size(), searchResult.toString());
    Assertions.assertTrue(
        searchResult.get(0).contains("Barnard's Star"), "should have'Barnard's Star'");
  }

  /** Tests all rows, CSV data in StringReader */
  @Test
  public void test3() {
    byte[] bytes = new byte[0];
    try {
      bytes = Files.readAllBytes(Paths.get("data/stars/ten-star.csv"));
    } catch (IOException e) {
      Assertions.fail(e);
    }
    String allContent = new String(bytes);
    StringReader reader = new StringReader(allContent);
    List<List<String>> searchResult = null;
    try {
      searchResult = parser.parseAndSearch(reader, -1, "Star", true);
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size(), searchResult.toString());
    Assertions.assertTrue(
        searchResult.get(0).contains("Barnard's Star"), "should have'Barnard's Star'");
  }

  /** first row has 2 columns, second row has 1 column, searching 2nd column will throw error */
  @Test
  public void test4() {
    Assertions.assertThrows(
        CsvParseFailureException.class,
        () -> {
          String allContent = new String("xxx,xxx2\n1");
          StringReader reader = new StringReader(allContent);
          parser.parseAndSearch(reader, 1, "Star", true);
        });
  }

  /**
   * Searching for values by index, by column name, and without a column identifier and tests column
   * name, StringReader
   */
  @Test
  public void test5() {
    byte[] bytes = new byte[0];
    try {
      bytes = Files.readAllBytes(Paths.get("data/stars/ten-star.csv"));
    } catch (IOException e) {
      Assertions.fail(e);
    }
    String allContent = new String(bytes);
    StringReader reader = new StringReader(allContent);
    List<List<String>> searchResult = null;
    try {
      searchResult = parser.parseAndSearch(reader, "ProperName", "Star");
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size(), searchResult.toString());
    Assertions.assertTrue(
        searchResult.get(0).contains("Barnard's Star"), "should include 'Barnard's Star'");
  }

  /**
   * Searching for values that are, and aren’t, present in the CSV; tests column name does not
   * exists, StringReader
   */
  @Test
  public void test6() {
    byte[] bytes = new byte[0];
    try {
      bytes = Files.readAllBytes(Paths.get("data/stars/ten-star.csv"));
    } catch (IOException e) {
      Assertions.fail(e);
    }
    String allContent = new String(bytes);
    StringReader reader = new StringReader(allContent);
    Assertions.assertThrows(
        CsvParseFailureException.class,
        () -> {
          parser.parseAndSearch(reader, "ProperNamexxxx", "Star");
        });
  }

  /** column name is null,search all column */
  @Test
  public void test7() {
    byte[] bytes = new byte[0];
    try {
      bytes = Files.readAllBytes(Paths.get("data/stars/ten-star.csv"));
    } catch (IOException e) {
      Assertions.fail(e);
    }
    String allContent = new String(bytes);
    StringReader reader = new StringReader(allContent);
    List<List<String>> searchResult = null;
    try {
      searchResult = parser.parseAndSearch(reader, null, "Star");
    } catch (CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size(), searchResult.toString());
    Assertions.assertTrue(
        searchResult.get(0).contains("Barnard's Star"), "should have'Barnard's Star'");
  }

  /** column name is not found */
  @Test
  public void test8() {
    Assertions.assertThrows(
        CsvParseFailureException.class,
        () -> {
          String allContent = new String("xxx,xxx2\n1,2");
          StringReader reader = new StringReader(allContent);
          parser.parseAndSearch(reader, "aaa", "Star");
        });
  }
}
