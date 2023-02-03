package edu.brown.cs.student.sup;

import java.util.List;

public interface Query {

  boolean isMatch(List<String> row);
}
