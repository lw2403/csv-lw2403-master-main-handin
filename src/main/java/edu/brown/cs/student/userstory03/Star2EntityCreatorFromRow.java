package edu.brown.cs.student.userstory03;

import edu.brown.cs.student.main.CreatorFromRow;
import edu.brown.cs.student.main.FactoryFailureException;
import java.util.List;

/** transforms data into start1entity object type */
public class Star2EntityCreatorFromRow implements CreatorFromRow<Star2Entity> {

  // creatorFromRow is strategy to create a star1entity object from row
  @Override
  public Star2Entity create(List<String> row) throws FactoryFailureException {
    Star2Entity entity = new Star2Entity();
    try {
      entity.setStarID(row.get(0));
      entity.setProperName(row.get(1));
      entity.setPosition("(" + row.get(2) + "," + row.get(3) + "," + row.get(4));
    } catch (Exception e) {
      throw new FactoryFailureException(e.getMessage(), row);
    }
    return entity;
  }
}
