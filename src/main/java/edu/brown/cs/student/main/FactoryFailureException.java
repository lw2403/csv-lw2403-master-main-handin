package edu.brown.cs.student.main;

import java.util.ArrayList;
import java.util.List;

/** When CreatorFromRow fails to work, throw this exception */
public class FactoryFailureException extends Exception {

  final List<String> row;

  public FactoryFailureException(String message, List<String> row) {
    super(message);
    this.row = new ArrayList<>(row);
  }
}
