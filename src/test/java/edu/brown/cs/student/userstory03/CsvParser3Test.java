package edu.brown.cs.student.userstory03;

import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.userstory01.CsvParseFailureException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CsvParser3Test {

  /**
   * Using multiple CreatorFromRow classes to extract CSV data in different formats.
   * Tests Star1EntityCreatorFromRow() to transform to start1entity
   */
  @Test
  public void test1() throws IOException {
    FileReader reader = new FileReader("data/stars/ten-star.csv");
    Star1EntityCreatorFromRow creatorFromRow = new Star1EntityCreatorFromRow();
    CsvParser3<Star1Entity> parser = new CsvParser3<>(creatorFromRow);
    List<Star1Entity> searchResult = null;
    try {
      // searchResult is start1entity
      searchResult = parser.parseAndSearch(reader, 1, "Rigel Kentauru", true);
    } catch (FactoryFailureException | CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(2, searchResult.size());
    Assertions.assertTrue(
        searchResult.get(0).getProperName().contains("Rigel Kentauru"),
        "should have 'Rigel Kentauru'");
    Assertions.assertTrue(
        searchResult.get(1).getProperName().contains("Rigel Kentauru"),
        "should have 'Rigel Kentauru'");
  }

  /** Tests Star2EntityCreatorFromRow() to transform to start2entity */
  @Test
  public void test2() throws IOException {
    FileReader reader = new FileReader("data/stars/ten-star.csv");
    Star2EntityCreatorFromRow creatorFromRow = new Star2EntityCreatorFromRow();
    CsvParser3<Star2Entity> parser = new CsvParser3<>(creatorFromRow);
    List<Star2Entity> searchResult = null;
    try {
      searchResult = parser.parseAndSearch(reader, 1, "Rigel Kentauru", true);
    } catch (FactoryFailureException | CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(2, searchResult.size());
    Assertions.assertTrue(
        searchResult.get(0).getProperName().contains("Rigel Kentauru"),
        "should have 'Rigel Kentauru'");
    Assertions.assertTrue(
        searchResult.get(1).getProperName().contains("Rigel Kentauru"),
        "should have 'Rigel Kentauru'");
  }

  /** Tests Star1EntityCreatorFromRow() to transform to start1entity with column name */
  @Test
  public void test3() throws IOException {
    FileReader reader = new FileReader("data/stars/ten-star.csv");
    Star2EntityCreatorFromRow creatorFromRow = new Star2EntityCreatorFromRow();
    CsvParser3<Star2Entity> parser = new CsvParser3<>(creatorFromRow);
    List<Star2Entity> searchResult = null;
    try {
      searchResult = parser.parseAndSearch(reader, "ProperName", "Rigel Kentauru");
    } catch (FactoryFailureException | CsvParseFailureException e) {
      Assertions.fail(e);
    }
    Assertions.assertEquals(2, searchResult.size());
    Assertions.assertTrue(
        searchResult.get(0).getProperName().contains("Rigel Kentauru"),
        "should include 'Rigel Kentauru'");
    Assertions.assertTrue(
        searchResult.get(1).getProperName().contains("Rigel Kentauru"),
        "should include 'Rigel Kentauru'");
  }
}
