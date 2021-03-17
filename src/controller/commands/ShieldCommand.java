package controller.commands;

import model.player.Player;

public class ShieldCommand extends Command {
    
    /**
     * Active le bouclier d'un joueur
     * @param p
     * @return 
     */
    @Override
    public boolean execute(Player p) {
        p.shield();
        return true;
    }
}
