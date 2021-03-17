package controller.commands;

import model.player.Player;

public class WaitCommand extends Command {
    
    /**
     * Commande d'attente
     * @param p Joueur
     * @return
     */
    @Override
    public boolean execute(Player p) {
        return true;
    }
}
