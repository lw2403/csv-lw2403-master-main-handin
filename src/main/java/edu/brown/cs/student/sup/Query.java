package edu.brown.cs.student.sup;

import java.util.List;

/** this interface checks if a row matches the current expression */
public interface Query {

  // returns t/f after checking if row matches current query
  boolean isMatch(List<String> row);
}
