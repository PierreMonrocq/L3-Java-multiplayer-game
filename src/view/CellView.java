package view;

import model.map.CellComposite;
import model.map.VisibleElement;
import util.mvc.ListenerModel;

public class CellView implements ListenerModel {
    private CellComposite cell;
    private VisibleElement visible;
    
    /**
     * Vue d'une cellule
     * @param cell 
     */
    public CellView(CellComposite cell) {
        this.cell = cell;
        this.visible = cell.getVisible();
        cell.addListener(this);
    }

    @Override
    public void modelUpdated(Object source) {
        visible = cell.getVisible();
    }

    public VisibleElement getVisible() {
        return visible;
    }
}
