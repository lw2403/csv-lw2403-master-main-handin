package edu.brown.cs.student.userstory03;

import edu.brown.cs.student.main.CreatorFromRow;
import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.userstory01.CsvParseFailureException;
import edu.brown.cs.student.userstory02.CsvParser2;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/** this class transforms the returned data into an object type of my choice */
public class CsvParser3<ROW> {

  private final CreatorFromRow<ROW> creatorFromRow;
  private final CsvParser2 csvParser2;
  /**
   * constructor
   *
   * @param creatorFromRow CreatorFromRow Interface
   */
  public CsvParser3(CreatorFromRow<ROW> creatorFromRow) {
    this.creatorFromRow = creatorFromRow;
    this.csvParser2 = new CsvParser2();
  }

  private List<ROW> transfer(List<List<String>> rows) throws FactoryFailureException {
    List<ROW> list = new ArrayList<>(rows.size());
    for (List<String> row : rows) {
      list.add(creatorFromRow.create(row));
    }
    return list;
  }

  public List<ROW> parseAndSearch(
      Reader reader, int columnIndex, String containValue, boolean firstRowIsHeader)
      throws CsvParseFailureException, FactoryFailureException {
    return transfer(csvParser2.parseAndSearch(reader, columnIndex, containValue, firstRowIsHeader));
  }

  public List<ROW> parseAndSearch(Reader reader, String columnName, String containValue)
      throws CsvParseFailureException, FactoryFailureException {
    return transfer(csvParser2.parseAndSearch(reader, columnName, containValue));
  }
}
