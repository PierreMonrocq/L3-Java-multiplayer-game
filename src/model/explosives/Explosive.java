package model.explosives;

import model.map.AbstractCellLeaf;
import model.map.CellComponent;
import model.player.Player;
import model.map.MapModel;
import util.Vec2;
import util.VisibleType;
import model.map.VisibleElement;

public abstract class Explosive extends AbstractCellLeaf {
    private MapModel m;
    private int ownerId;
    private int damage;
    
    public Explosive(VisibleType visible, MapModel m, Vec2 position, Player owner, int damage) {
        super(true, false, true, new VisibleElement(visible, owner.getId()), position);
        this.ownerId = owner.getId();
        this.damage = damage;
        this.m = m;
    }
    /**
     * Recupere l'id du joueur qui a pose l'explosif
     * @return 
     */
    public int getOwnerId() {
        return ownerId;
    }
    /**
     * Declenche l'explosion d'un explosif
     */
    public void explode() {
        Vec2 position = getPosition();

        for (int y = position.getY() - 1; y <= position.getY() + 1; y++) {
            for (int x = position.getX() - 1; x <= position.getX() + 1; x++) {
                Vec2 currentPos = new Vec2(x, y);
                Player p = m.getPlayerAt(currentPos);

                if(p != null) {
                    p.dealDamage(damage);
                }
            }
        }

        m.getCell(position).removeComponent(this);
        m.removeExplosive(this);
    }

    public void update() {}
    /**
     * Methode qui est executee lorsque qu'un component 
     * rentre dans la case où est placée la bombe
     * @param mover 
     */
    @Override
    public void onEntry(CellComponent mover) {
        explode();
    }
}
