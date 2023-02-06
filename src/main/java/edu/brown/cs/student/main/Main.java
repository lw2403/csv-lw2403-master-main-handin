package edu.brown.cs.student.main;

import edu.brown.cs.student.sup.CsvParser4;
import edu.brown.cs.student.sup.QueryParserFailureException;
import edu.brown.cs.student.userstory01.CsvFileParser;
import edu.brown.cs.student.userstory01.CsvParseFailureException;
import edu.brown.cs.student.userstory02.CsvParser2;
import edu.brown.cs.student.userstory03.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Scanner;

/** The Main class of our project. This is where execution begins. */
public final class Main {

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private Main(String[] args) {}

  private void run() {
    // dear student: you can remove this. you can remove anything. you're in cs32. you're free!
    System.out.println(
        "Your horoscope for this project:\n"
            + "Entrust in the Strategy pattern, and it shall give thee the sovereignty to "
            + "decide and the dexterity to change direction in the realm of thy code.");
    System.out.println("--------help---------");
    System.out.println("1 ---------userstory01");
    System.out.println("2----------userstory02");
    System.out.println("3----------userstory03");
    System.out.println("4------------------sup");
    try (Scanner sc = new Scanner(System.in)) {
      System.out.println("choose program, input 1/2/3/4:");
      int choose = sc.nextInt();
      sc.nextLine();
      switch (choose) {
        case 1 -> userStory01(sc);
        case 2 -> userStory02(sc);
        case 3 -> userStory03(sc);
        case 4 -> sup(sc);
          // else
        default -> System.err.println("input error!");
      }
    }
  }

  // strings input that meet csv format
  private StringReader getCsvTxtStringReaderSystemIn(Scanner sc) {
    System.out.println("input csv String, blank line means end:");
    // string that is easy to change
    StringBuilder buffer = new StringBuilder();
    String line;
    while ((line = sc.nextLine()) != null && line.trim().length() != 0) {
      buffer.append(line);
      buffer.append("\n");
    }
    if (buffer.length() > 0) {
      buffer.deleteCharAt(buffer.length() - 1);
    }
    return new StringReader(buffer.toString());
  }

  // read from string
  private void userStory02ReadFromString(Scanner sc) {

    StringReader reader = getCsvTxtStringReaderSystemIn(sc);

    Boolean firstRowIsHeader = inputFirstRowIsHeader(sc);
    if (firstRowIsHeader == null) {
      return;
    }

    Integer columnIndex = inputColumnIndex(sc);
    if (columnIndex == null) {
      return;
    }

    String searchValue = inputSearchValue(sc);

    userStory02searchAndPrint(reader, columnIndex, searchValue, firstRowIsHeader);
  }

  // read from file
  private void userStory02ReadFromFile(Scanner sc) {
    String filePath = inputFilePath(sc);

    Boolean firstRowIsHeader = inputFirstRowIsHeader(sc);
    if (firstRowIsHeader == null) {
      return;
    }

    Integer columnIndex = inputColumnIndex(sc);
    if (columnIndex == null) {
      return;
    }

    String searchValue = inputSearchValue(sc);

    FileReader reader;
    try {
      reader = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      System.err.println("file not found!:" + filePath);
      return;
    }
    userStory02searchAndPrint(reader, columnIndex, searchValue, firstRowIsHeader);
  }

  private void userStory02searchAndPrint(
      Reader reader, int columnIndex, String containValue, boolean firstRowIsHeader) {
    CsvParser2 parser = new CsvParser2();
    List<List<String>> searchResult;
    try {
      searchResult = parser.parseAndSearch(reader, columnIndex, containValue, firstRowIsHeader);
      printSearchResult1(searchResult);
    } catch (CsvParseFailureException e) {
      System.err.println(e.getMessage());
    }
  }

  private void userStory02(Scanner sc) {
    System.out.println("read from String or File?(string/file):");
    String csvTxtFrom = sc.nextLine().toLowerCase();
    switch (csvTxtFrom) {
        // read from string
      case "string" -> userStory02ReadFromString(sc);
        // read from file
      case "file" -> userStory02ReadFromFile(sc);
      default -> System.err.println("input error!");
    }
  }

  private String inputRowClassName(Scanner sc) {
    System.out.println("star1: has properties'starID,properName,x,y,z'");
    System.out.println("star2: has properties'starID,properName,position'");
    System.out.println("input row transfer to which type object?(star1/star2):");
    String typeName = sc.nextLine();
    if ("star1".equals(typeName) || "star2".equals(typeName)) {
      return typeName.toLowerCase();
    } else {
      System.err.println("input error!");
      return null;
    }
  }

  private void userStory03(Scanner sc) {
    String filePath = inputFilePath(sc);

    Boolean firstRowIsHeader = inputFirstRowIsHeader(sc);
    if (firstRowIsHeader == null) {
      return;
    }

    Integer columnIndex = inputColumnIndex(sc);
    if (columnIndex == null) {
      return;
    }

    String searchValue = inputSearchValue(sc);

    String transferRowToWhichType = inputRowClassName(sc);
    if (transferRowToWhichType == null) {
      return;
    }

    if ("star1".equals(transferRowToWhichType)) {
      CreatorFromRow<Star1Entity> creatorFromRow = new Star1EntityCreatorFromRow();
      CsvParser3<Star1Entity> parser = new CsvParser3<>(creatorFromRow);
      FileReader reader;
      try {
        reader = new FileReader(filePath);
      } catch (FileNotFoundException e) {
        System.err.println("file not found!:" + filePath);
        return;
      }
      List<Star1Entity> searchResult;
      try {
        searchResult = parser.parseAndSearch(reader, columnIndex, searchValue, firstRowIsHeader);
        printSearchResultWithEntity(searchResult);
      } catch (CsvParseFailureException | FactoryFailureException e) {
        System.err.println(e.getMessage());
      }
    } else if ("star2".equals(transferRowToWhichType)) {
      CreatorFromRow<Star2Entity> creatorFromRow = new Star2EntityCreatorFromRow();
      CsvParser3<Star2Entity> parser = new CsvParser3<>(creatorFromRow);
      FileReader reader;
      try {
        reader = new FileReader(filePath);
      } catch (FileNotFoundException e) {
        System.err.println("file not found!:" + filePath);
        return;
      }
      List<Star2Entity> searchResult;
      try {
        searchResult = parser.parseAndSearch(reader, columnIndex, searchValue, firstRowIsHeader);
        printSearchResultWithEntity(searchResult);
      } catch (CsvParseFailureException | FactoryFailureException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  private void printSearchResultWithEntity(List<?> searchResult) {
    System.out.println("record count:" + searchResult.size());
    System.out.println("records:");
    for (Object row : searchResult) {
      System.out.println(row);
    }
  }

  private void sup(Scanner sc) {
    String filePath = inputFilePath(sc);

    Boolean firstRowIsHeader = inputFirstRowIsHeader(sc);
    if (firstRowIsHeader == null) {
      return;
    }
    // expression
    System.out.println("express rule:");
    System.out.println("column 'columnIndex' contains \"value\",eg: column 1 contains \"Star\" ");
    System.out.println(
        "column 'columnName' contains \"value\",eg: column ProperName contains \"Star\" ");
    System.out.println(
        "and(sub expression1,sub expression2),eg:and(column 1 contains \"Star\",column ProperName contains \"Star\") ");
    System.out.println(
        "or(sub expression1,sub expression2),eg: or(column 1 contains \"tar\",not (column 1 contains \"S\") )");
    System.out.println("not(sub expression),eg: not(column 1 contains \"Star\")");
    System.out.println("input select expression:");
    String expr = sc.nextLine();

    String transferRowToWhichType = inputRowClassName(sc);
    if (transferRowToWhichType == null) {
      return;
    }
    // transform to star1 object
    if ("star1".equals(transferRowToWhichType)) {
      CreatorFromRow<Star1Entity> creatorFromRow = new Star1EntityCreatorFromRow();
      CsvParser4<Star1Entity> parser = new CsvParser4<>(creatorFromRow);
      FileReader reader;
      try {
        reader = new FileReader(filePath);
      } catch (FileNotFoundException e) {
        System.err.println("file not found!:" + filePath);
        return;
      }
      List<Star1Entity> searchResult;
      try {
        searchResult = parser.parseAndSearch(reader, expr, firstRowIsHeader);
        printSearchResultWithEntity(searchResult);
      } catch (CsvParseFailureException | FactoryFailureException | QueryParserFailureException e) {
        System.err.println(e.getMessage());
      }
      // transform to star2 object
    } else if ("star2".equals(transferRowToWhichType)) {
      CreatorFromRow<Star2Entity> creatorFromRow = new Star2EntityCreatorFromRow();
      CsvParser4<Star2Entity> parser = new CsvParser4<>(creatorFromRow);
      FileReader reader;
      try {
        reader = new FileReader(filePath);
      } catch (FileNotFoundException e) {
        System.err.println("file not found!:" + filePath);
        return;
      }
      List<Star2Entity> searchResult;
      try {
        searchResult = parser.parseAndSearch(reader, expr, firstRowIsHeader);
        printSearchResultWithEntity(searchResult);
      } catch (CsvParseFailureException | FactoryFailureException | QueryParserFailureException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  private String inputFilePath(Scanner sc) {
    System.out.println("input csv file path:");
    // delete space before and after
    return sc.nextLine().trim();
  }

  private Boolean inputFirstRowIsHeader(Scanner sc) {
    System.out.println("input the first row is header(y/n):");
    String firstRowIsHeaderInputTxt = sc.nextLine();
    boolean firstRowIsHeader;
    switch (firstRowIsHeaderInputTxt.toLowerCase()) {
      case "y", "true", "yes" -> firstRowIsHeader = true;
      case "n", "false", "no" -> firstRowIsHeader = false;
      default -> {
        System.err.println("input error!");
        return false;
      }
    }
    return firstRowIsHeader;
  }

  private Integer inputColumnIndex(Scanner sc) {
    System.out.println("input column index(-1 means search in all column):");
    String columnIndexLine = sc.nextLine();
    try {
      return Integer.parseInt(columnIndexLine);
    } catch (NumberFormatException e) {
      System.err.println("column index format error!");
      return null;
    }
  }

  private String inputSearchValue(Scanner sc) {
    System.out.println("input search value:");
    return sc.nextLine();
  }

  private void printSearchResult1(List<List<String>> searchResult) {
    System.out.println("record count:" + searchResult.size());
    System.out.println("records:");
    for (List<String> row : searchResult) {
      System.out.println(String.join(", ", row));
    }
  }

  private void userStory01(Scanner sc) {
    String filePath = inputFilePath(sc);

    Boolean firstRowIsHeader = inputFirstRowIsHeader(sc);
    if (firstRowIsHeader == null) {
      return;
    }

    Integer columnIndex = inputColumnIndex(sc);
    if (columnIndex == null) {
      return;
    }

    String searchValue = inputSearchValue(sc);

    CsvFileParser parser = new CsvFileParser();
    try {
      List<List<String>> searchResult =
          parser.parseAndSearch(filePath, columnIndex, searchValue, firstRowIsHeader);
      printSearchResult1(searchResult);
    } catch (CsvParseFailureException e) {
      System.err.println(e.getMessage());
    }
  }
}
