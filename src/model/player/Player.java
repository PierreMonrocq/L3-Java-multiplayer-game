package model.player;

import model.GameSettings;
import model.explosives.Bomb;
import model.explosives.Mine;
import model.map.AbstractCellLeaf;
import model.map.CellComponent;
import model.map.CellComposite;
import model.map.MapModel;
import model.map.VisibleElement;
import util.VisibleType;
import util.Vec2;

public class Player extends AbstractCellLeaf {

    private int currentEnergy;
    private int maxEnergy;
    private int id;
    private boolean AI;

    private int shotsLeft;
    private int bombsLeft;
    private int minesLeft;
    
    private boolean isShielded;
    
    private MapModel map;
    
    /**
     * Permet de creer un joueur avec differents parametres
     * @param position
     * @param energy
     * @param id
     * @param AI
     * @param shotCount
     * @param bombCount
     * @param mineCount
     * @param map 
     */
    public Player(Vec2 position, int energy, int id, boolean AI, int shotCount, int bombCount, int mineCount, MapModel map) {
        super(false, true, true, new VisibleElement(VisibleType.PLAYER), position);
        this.currentEnergy = energy;
        this.maxEnergy = energy;
        this.id = id;
        this.AI = AI;
        this.shotsLeft = shotCount;
        this.bombsLeft = bombCount;
        this.minesLeft = mineCount;
        this.map = map;
    }
    
    /**
     * Recupere l'id d'un joueur
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Recupere l'energie courante d'un joueur
     * @return 
     */
    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }
    /**
     * Active le bouclier du joueur
     * @param isShielded 
     */
    public void setShield(boolean isShielded) {
        this.isShielded = isShielded;
    }
    /**
     * Verifie si le joueur est actuellement protege par un bouclier
     * @return 
     */
    public boolean isShielded() {
        return isShielded;
    }
    
    public int getShotsLeft() {
        return shotsLeft;
    }

    public int getBombsLeft() {
        return bombsLeft;
    }

    public int getMinesLeft() {
        return minesLeft;
    }

    /**
     * @return Activation du bouclier
     */
    public boolean shield() {
        int cost = GameSettings.getInstance().getShieldCost();

        if(currentEnergy > cost) {
            isShielded = true;
            currentEnergy -= cost;
            return true;
        }

        return false;
    }

    /**
     * 
     * @param direction
     * Recupere la case dans laquelle le joueur souhaite entrer,
     * puis lui demande de valider le deplacement.
     */
    public boolean move(Vec2 direction) {
        boolean result = false;
        int cost = GameSettings.getInstance().getMoveCost();
        Vec2 desiredP = Vec2.sum(getPosition(), direction);
        CellComponent cell = map.getCell(desiredP);

        if(cell != null) {
            if ((currentEnergy > cost) && cell.allowsEntry(this)) {
                cell.onEntry(this);
                map.getCell(getPosition()).removeComponent(this);
                setPosition(desiredP);
                result = true;
            }
        }

        return result;
    }

    /**
     * Applique des degats au joueur
     * @param damage 
     */
    @Override
    public void dealDamage(int damage) {
        if (!isShielded || (damage < 0)) {
            currentEnergy -= damage;

            if(currentEnergy > maxEnergy) {
                currentEnergy = maxEnergy;
            }
            if(currentEnergy <= 0) {
                map.markPlayerForRemoval(this);
            }
        }
    }

    /**
     * 
     * Trace une ligne dans laquelle le joueur souhaite effectuer un tir,
     * puis lui demande de valider le tir
     * @param direction
     */
    public boolean shoot(Vec2 direction) {
        boolean result = false;
        int cost = GameSettings.getInstance().getShotCost();

        if((currentEnergy > cost) && (shotsLeft != 0)) {
            Vec2 shotStartP = Vec2.sum(getPosition(), direction);
            Vec2 shotEndP = new Vec2(shotStartP);

            CellComposite cell = map.getCell(shotEndP);
            // If we can at least shoot through the nearest cell,
            // we allow the player to shoot in that direction.
            if ((cell != null) && cell.canShootThrough()) {
                do {
                    cell.dealDamage(GameSettings.getInstance().getShotDamage());

                    shotEndP.add(direction);
                    cell = map.getCell(shotEndP);
                } while ((cell != null) && cell.canShootThrough());

                currentEnergy -= cost;
                --shotsLeft;
                result = true;
            }
        }

        return result;
    }
    /**
     * Recupere la case dans laquelle le joueur souhaite placer une bombe,
     * puis lui demande de valider la position.
     * @param direction
     */
    public boolean placeBomb(Vec2 direction) {
        boolean result = false;
        int cost = GameSettings.getInstance().getBombCost();

        if((currentEnergy > cost) && (bombsLeft != 0)) {
            Vec2 placeP = Vec2.sum(getPosition(), direction);

            Bomb bomb = new Bomb(map, placeP, this);

            if(map.addExplosive(bomb)) {
                --bombsLeft;
                result = true;
            }
            currentEnergy -= cost;
        }

        return result;
    }
    /**
     * Recupere la case dans laquelle le joueur souhaite placer une mine,
     * puis lui demande de valider la position.
     * @param direction
     */
    public boolean placeMine(Vec2 direction) {
        boolean result = false;
        int cost = GameSettings.getInstance().getMineCost();

        if((currentEnergy > cost) && (minesLeft != 0)) {
            Vec2 placeP = Vec2.sum(getPosition(), direction);

            Mine mine = new Mine(map, placeP, this);

            if(map.addExplosive(mine)) {
                --minesLeft;
                result = true;
            }
            currentEnergy -= cost;
        }

        return result;
    }

    public boolean isAI() {
        return AI;
    }

    @Override
    public void onEntry(CellComponent mover) {}
}

