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

/** tests expression, tests basic query, and query, or query, and not query test1 */
public class CsvParser4Test {

  // and(not(and(1=Rigel Kentauru,not(0=54))),1=Sol)
  @Test
  public void test1() throws IOException {
    FileReader reader = new FileReader("data/stars/ten-star.csv");
    // creatorFromRow is strategy to create a star1entity object from row
    Star1EntityCreatorFromRow creatorFromRow = new Star1EntityCreatorFromRow();
    CsvParser4<Star1Entity> parser = new CsvParser4<>(creatorFromRow);
    // returns star1entity
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

  // "and(not(and(column 1 contains \"Rigel Kentauru\",not(column StarID contains \"54\"))), column
  // ProperName contains \"Sol\")",

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

  // "or(column 1 contains \"Rigel Kentaurus B\",column ProperName contains \"Rigel Kentaurus A\")"
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

  /**
   * Searching for values that are present, but are in the wrong column;
   *
   * @throws IOException
   */
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

  /**
   * column -1 means all columns
   *
   * @throws IOException
   */
  @Test
  public void test5() throws IOException {
    Star1EntityCreatorFromRow creatorFromRow = new Star1EntityCreatorFromRow();
    CsvParser4<Star1Entity> parser = new CsvParser4<>(creatorFromRow);
    List<Star1Entity> searchResult = null;
    try {
      FileReader reader = new FileReader("data/stars/ten-star.csv");
      searchResult =
          parser.parseAndSearch(reader, "column -1 contains \"Rigel Kentaurus A\"", true);
    } catch (FactoryFailureException | QueryParserFailureException | CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(1, searchResult.size());
    Assertions.assertTrue(searchResult.get(0).getProperName().contains("Rigel Kentaurus A"));
  }

  /**
   * exceptions
   *
   * @throws IOException
   */
  @Test
  public void test6() throws IOException {
    Star1EntityCreatorFromRow creatorFromRow = new Star1EntityCreatorFromRow();
    CsvParser4<Star1Entity> parser = new CsvParser4<>(creatorFromRow);
    {
      FileReader reader = new FileReader("data/stars/ten-star.csv");
      Assertions.assertThrows(
          QueryParserFailureException.class,
          () -> {
            parser.parseAndSearch(reader, "1=star=xx", true);
            // split by "=" should be 2 items
            // 1=star
          });
    }

    {
      FileReader reader = new FileReader("data/stars/ten-star.csv");
      Assertions.assertThrows(
          QueryParserFailureException.class,
          () -> {
            // should be ,
            parser.parseAndSearch(reader, "and(1=star;1=star)", true);
          });
    }

    {
      FileReader reader = new FileReader("data/stars/ten-star.csv");
      Assertions.assertThrows(
          QueryParserFailureException.class,
          () -> {
            // column name not found
            parser.parseAndSearch(reader, "column xxxxx contains \"star\"", true);
          });
    }

    {
      FileReader reader = new FileReader("data/stars/ten-star.csv");
      Assertions.assertThrows(
          QueryParserFailureException.class,
          () -> {
            // xor not supported
            parser.parseAndSearch(reader, "xor(1=star,1=star)", true);
          });
    }
  }
}
