package edu.brown.cs.student.userstory03;

import edu.brown.cs.student.main.CreatorFromRow;
import edu.brown.cs.student.main.FactoryFailureException;
import java.util.List;

public class Star1EntityCreatorFromRow implements CreatorFromRow<Star1Entity> {

  // change  List<String> row to start1entity object type
  @Override
  public Star1Entity create(List<String> row) throws FactoryFailureException {
    Star1Entity entity = new Star1Entity();
    try {
      entity.setStarID(Integer.parseInt(row.get(0)));
      entity.setProperName(row.get(1));
      entity.setX(Double.parseDouble(row.get(2)));
      entity.setY(Double.parseDouble(row.get(3)));
      entity.setZ(Double.parseDouble(row.get(4)));
    } catch (Exception e) {
      throw new FactoryFailureException(e.getMessage(), row);
    }
    return entity;
  }
}
