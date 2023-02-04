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
    Assertions.assertTrue(matchValues.contains("Rigel Kentaurus B"), "need 'Rigel Kentaurus B'");
    Assertions.assertTrue(matchValues.contains("Rigel Kentaurus A"), "need 'Rigel Kentaurus A'");
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
    Assertions.assertTrue(searchResult.get(0).contains("Barnard's Star"), "need'Barnard's Star'");
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
    Assertions.assertTrue(searchResult.get(0).contains("Barnard's Star"), "need'Barnard's Star'");
  }
  @Test
  public void test4() {
    Assertions.assertThrows(CsvParseFailureException.class,()->{
      String allContent = new String("xxx,xxx2\n1");
      StringReader reader = new StringReader(allContent);
      parser.parseAndSearch(reader, 1, "Star", true);
    });
  }


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
    Assertions.assertTrue(searchResult.get(0).contains("Barnard's Star"), "应该包含'Barnard's Star'");
  }



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
    Assertions.assertThrows(CsvParseFailureException.class,()->{
      parser.parseAndSearch(reader, "ProperNamexxxx", "Star");
    });
  }
}
