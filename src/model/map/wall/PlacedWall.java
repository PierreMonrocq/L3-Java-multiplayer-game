package model.map.wall;

import model.map.AbstractCellLeaf;
import model.map.CellComponent;
import model.map.VisibleElement;
import util.Vec2;
import util.VisibleType;

public class PlacedWall extends AbstractCellLeaf {

    public PlacedWall(Vec2 position) {
        super(true, true, false, new VisibleElement(VisibleType.WALL), position);
    }

    @Override
    public void onEntry(CellComponent mover) {}
}
