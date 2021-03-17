package controller.commands;

import model.player.Player;
import util.Vec2;

public class PlaceBombCommand extends Command {

    private Vec2 direction;

    /**
     * Place une bombe à partir d'un vecteur à 2 dimensions
     *
     * @param direction
     */
    public PlaceBombCommand(Vec2 direction) {
        this.direction = direction;
    }

    /**
     * Execute la commande
     *
     * @param p
     * @return Bombe positionnable ou non
     */
    @Override
    public boolean execute(Player p) {
        return p.placeBomb(direction);
    }
}
