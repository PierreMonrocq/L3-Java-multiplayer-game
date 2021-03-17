package view;

import model.map.MapModel;
import model.map.VisibleElement;
import util.Vec2;

public class MapView {
    private CellView[][] viewMap;

    public MapView(Vec2 dim, MapModel map) {
        viewMap = new CellView[dim.getY()][dim.getX()];

        for(int y = 0; y < dim.getY(); ++y) {
            for(int x = 0; x < dim.getX(); ++x) {
                viewMap[y][x] = new CellView(map.getCell(new Vec2(x, y)));
            }
        }
    }

    public VisibleElement get(Vec2 position) {
        return viewMap[position.getY()][position.getX()].getVisible();
    }
}
