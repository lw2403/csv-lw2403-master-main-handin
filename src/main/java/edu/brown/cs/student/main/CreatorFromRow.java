package edu.brown.cs.student.main;

import java.util.List;

/**
 * this interface transforms a row into an object type T
 * @param <T> object type
 */
public interface CreatorFromRow<T> {
  T create(List<String> row) throws FactoryFailureException;
}
