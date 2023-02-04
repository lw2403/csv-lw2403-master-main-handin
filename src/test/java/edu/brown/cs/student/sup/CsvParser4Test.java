package edu.brown.cs.student.sup;

import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.userstory01.CsvParseFailureException;
import edu.brown.cs.student.userstory03.Star1Entity;
import edu.brown.cs.student.userstory03.Star1EntityCreatorFromRow;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** tests expression, tests basic query, and query, or query, and not query */
public class CsvParser4Test {
  @Test
  public void test1() throws IOException {
    FileReader reader = new FileReader("data/stars/ten-star.csv");
    Star1EntityCreatorFromRow creatorFromRow = new Star1EntityCreatorFromRow();
    CsvParser4<Star1Entity> parser = new CsvParser4<>(creatorFromRow);
    List<Star1Entity> searchResult = null;
    try {
      searchResult =
          parser.parseAndSearch(reader, "and(not(and(1=Rigel Kentauru,not(0=54))),1=Sol)", true);
    } catch (FactoryFailureException | QueryParserFailureException | CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals("Sol", searchResult.get(0).getProperName());
    Assertions.assertEquals(0, searchResult.get(0).getStarID());
  }

  @Test
  public void test2() throws IOException {
    FileReader reader = new FileReader("data/stars/ten-star.csv");
    Star1EntityCreatorFromRow creatorFromRow = new Star1EntityCreatorFromRow();
    CsvParser4<Star1Entity> parser = new CsvParser4<>(creatorFromRow);
    List<Star1Entity> searchResult = null;
    try {
      searchResult =
          parser.parseAndSearch(
              reader,
              "and(not(and(column 1 contains \"Rigel Kentauru\",not(column StarID contains \"54\"))), column ProperName contains \"Sol\")",
              true);
    } catch (FactoryFailureException | QueryParserFailureException | CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals("Sol", searchResult.get(0).getProperName());
    Assertions.assertEquals(0, searchResult.get(0).getStarID());
  }

  @Test
  public void test3() throws IOException {
    FileReader reader = new FileReader("data/stars/ten-star.csv");
    Star1EntityCreatorFromRow creatorFromRow = new Star1EntityCreatorFromRow();
    CsvParser4<Star1Entity> parser = new CsvParser4<>(creatorFromRow);
    List<Star1Entity> searchResult = null;
    try {
      searchResult =
          parser.parseAndSearch(
              reader,
              "or(column 1 contains \"Rigel Kentaurus B\",column ProperName contains \"Rigel Kentaurus A\")",
              true);
    } catch (FactoryFailureException | QueryParserFailureException | CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(2, searchResult.size());
    Assertions.assertTrue(searchResult.get(0).getProperName().contains("Rigel Kentaurus"));
    Assertions.assertTrue(searchResult.get(1).getProperName().contains("Rigel Kentaurus"));
  }

  @Test
  public void test4() throws IOException {
    Star1EntityCreatorFromRow creatorFromRow = new Star1EntityCreatorFromRow();
    CsvParser4<Star1Entity> parser = new CsvParser4<>(creatorFromRow);
    List<Star1Entity> searchResult = null;
    try {
      FileReader reader = new FileReader("data/stars/ten-star.csv");
      searchResult = parser.parseAndSearch(reader, "column 1 contains \"Rigel Kentaurus A\"", true);
    } catch (FactoryFailureException | QueryParserFailureException | CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size());
    Assertions.assertTrue(searchResult.get(0).getProperName().contains("Rigel Kentaurus A"));

    try {
      FileReader reader = new FileReader("data/stars/ten-star.csv");
      searchResult = parser.parseAndSearch(reader, "column 2 contains \"Rigel Kentaurus A\"", true);
    } catch (FactoryFailureException | QueryParserFailureException | CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(0, searchResult.size());
  }
}
