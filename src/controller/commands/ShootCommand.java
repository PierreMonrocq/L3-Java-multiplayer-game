package controller.commands;

import model.player.Player;
import util.Vec2;

public class ShootCommand extends Command {
    private Vec2 direction;

    /**
     * Commande de tir a partir d'une direction
     * @param direction 
     */
    public ShootCommand(Vec2 direction) {
        this.direction = direction;
    }
    /**
     * Execute la commande de tir pour un joueur p
     * @param p
     * @return 
     */
    @Override
    public boolean execute(Player p) {
        return p.shoot(direction);
    }
}
