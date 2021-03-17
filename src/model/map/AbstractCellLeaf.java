package model.map;

import util.Vec2;

public abstract class AbstractCellLeaf implements CellComponent {

    private Vec2 position;
    private boolean occupiesLow;
    private boolean occupiesHigh;
    private boolean canShootThrough;
    private VisibleElement visible;

    /**
     * Classe abstraite pour un objet sur une case
     * @param occupiesLow
     * @param occupiesHigh
     * @param canShootThrough
     * @param visible
     * @param position
     */
    public AbstractCellLeaf(boolean occupiesLow, boolean occupiesHigh, boolean canShootThrough,
            VisibleElement visible, Vec2 position) {
        this.occupiesLow = occupiesLow;
        this.occupiesHigh = occupiesHigh;
        this.canShootThrough = canShootThrough;
        this.visible = visible;
        this.position = position;
    }
    /**
     * Autorise l'acces a une case
     * @param mover
     * @return 
     */
    @Override
    public boolean allowsEntry(CellComponent mover) {
        boolean result = true;

        if ((occupiesLow && mover.occupiesLow()) || (occupiesHigh && mover.occupiesHigh())) {
            result = false;
        }

        return result;
    }
    /**
     * Detecte l'entree dans une case
     * @param mover 
     */
    @Override
    abstract public void onEntry(CellComponent mover);
    
    /**
     * Applique des degats a nous-memes
     * @param damage 
     */
    @Override
    public void dealDamage(int damage) {
    }
    
    /**
     * Les deux methodes forment un masque de collision
     * @return 
     */
    @Override
    public boolean occupiesLow() {
        return occupiesLow;
    }
    @Override
    public boolean occupiesHigh() {
        return occupiesHigh;
    }

    @Override
    public boolean canShootThrough() {
        return canShootThrough;
    }

    @Override
    public VisibleElement getVisible() {
        return visible;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }
}
