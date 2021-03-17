package controller.commands;

import model.player.Player;
import util.Vec2;

public class MoveCommand extends Command {
    private Vec2 direction;

    /**
     * Deplace un joueur a partir d'un vecteur à 2 dimensions
     * @param direction 
     */
    public MoveCommand(Vec2 direction) {
        this.direction = direction;
    }

    /**
     * Execute le déplacement
     * @param p
     * @return Déplacement est possible ou non
     */
    @Override
    public boolean execute(Player p) {
        return p.move(direction);
    }
}
