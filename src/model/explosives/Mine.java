package model.explosives;

import model.GameSettings;
import model.map.MapModel;
import model.player.Player;
import util.VisibleType;
import util.Vec2;

public class Mine extends Explosive {
    /**
     * Place une bombe sur la carte
     * @param m
     * @param position
     * @param owner 
     */
   public Mine(MapModel m, Vec2 position, Player owner){
       super(VisibleType.MINE, m, position, owner, GameSettings.getInstance().getMineDamage());
   }
}
