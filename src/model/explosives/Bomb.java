package model.explosives;

import model.GameSettings;
import model.map.MapModel;
import model.player.Player;
import util.VisibleType;
import util.Vec2;


public class Bomb extends Explosive {
    private int turnsBeforeExplosion;
    
    /**
     * Place une bombe sur la carte
     * @param m
     * @param position
     * @param owner 
     */
    public Bomb(MapModel m, Vec2 position, Player owner){
        super(VisibleType.BOMB, m, position, owner, GameSettings.getInstance().getBombDamage());
        this.turnsBeforeExplosion = GameSettings.getInstance().getBombExplosionDelay();
    }

    /**
     * Permet de faire exploser une bombe apres un certain delai
     */
    @Override
    public void update() {
        --turnsBeforeExplosion;
        if(turnsBeforeExplosion == 0){
            explode();
        }
    }
}



