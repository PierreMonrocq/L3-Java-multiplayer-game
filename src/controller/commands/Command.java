package controller.commands;

import model.player.Player;

/**
 *
 * @author Pierre
 */
public abstract class Command {
    /**
     * Execute une Commande sur un joueur
     * @param p Joueur courant - pour l'instant, les methodes publiques disponibles
     * sont move, shoot, shield, placeBomb et placeMine
     * @return Valeur booleenne si la commande a bien été exécutée ou non
     */
    public abstract boolean execute(Player p);
}
