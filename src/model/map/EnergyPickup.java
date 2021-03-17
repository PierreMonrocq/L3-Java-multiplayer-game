package model.map;

import model.GameSettings;
import util.Vec2;
import util.VisibleType;

public class EnergyPickup extends AbstractCellLeaf {
    final private int energyValue;
    private MapModel m;

    public EnergyPickup(MapModel map, Vec2 position) {
        super(true, false, true, new VisibleElement(VisibleType.PICKUP), position);
        this.m = map;
        this.energyValue = GameSettings.getInstance().getEnergyPickupValue();
    }

    /**
     * Rend de l'energie quand un joueur entre dans la case
     * @param mover 
     */
    @Override
    public void onEntry(CellComponent mover) {
        mover.dealDamage(-energyValue);
        m.getCell(getPosition()).removeComponent(this);
    }
}
