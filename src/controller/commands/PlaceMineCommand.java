package controller.commands;

import model.player.Player;
import util.Vec2;

public class PlaceMineCommand extends Command {
    private Vec2 direction;
     
    /**
     * Place une mine à partir d'un vecteur à 2 dimensions
     *
     * @param direction
     */
    public PlaceMineCommand(Vec2 direction) {
        this.direction = direction;
    }
    
    /**
     * Execute la commande
     *
     * @param p
     * @return Mine positionnable ou non
     */
    @Override
    public boolean execute(Player p) {
        return p.placeMine(direction);
    }
}
