package model;

import util.mvc.AbstractListenableModel;
import model.player.Player;
import model.player.PlayerFactory;
import util.*;
import controller.commands.Command;
import java.util.ArrayList;
import java.util.Collections;
import model.map.MapModel;

public class Game extends AbstractListenableModel {
    private MapModel map;
    private boolean randomTurnOrder;
    private int turn = 0;
    private ArrayList<Integer> turnOrderIds;
    private int currentPlayerIndex;
    private boolean started = false;

    public Game() {
        super();
    }
    /**
     * Recupere le tour actuel du jeu
     * @return 
     */
    public int getTurn() {
        return turn;
    }

    public MapModel getMap() {
        return map;
    }
    /**
     * Recupere l'ordre des joueurs
     * @param index   
     */
    public int getTurnOrderId(int index) {
        if(index < turnOrderIds.size()) {
            return turnOrderIds.get(index);
        }
        return -1;
    }

    public int getCurrentPlayerId() {
        return turnOrderIds.get(currentPlayerIndex);
    }

    
    public Player getCurrentPlayer() {
        return map.getPlayerWithId(turnOrderIds.get(currentPlayerIndex));
    }
    /**
     * 
     * @return Le joueur est une IA?
     */
    public boolean currentPlayerIsAI() {
        Player player = map.getPlayerWithId(getCurrentPlayerId());
        return player.isAI();
    }

    /**
     * Execute la commande passe en paramètre
     * @param cmd
     * @return Commande valide?
     */
    public boolean parseCommand(Command cmd) {
        return cmd.execute(getCurrentPlayer());
    }
    /**
     * Verifie si le jeu est termine ou non
     * @return Jeu termine?
     */
    public boolean isFinished(){
        return map.getPlayerCount() <= 1;
    }

    /**
     * Cree une liste dont l'ordre des IDs de joueurs represente l'ordre des joueurs.
     * Elle n'est donc cree qu'une fois que tous les joueurs ont joue.
     */
    private void updateTurnOrderIds()  {
        turnOrderIds = map.getSortedPlayerIds();
        if(randomTurnOrder) {
            Collections.shuffle(turnOrderIds);
        }
    }
    /**
     * Initialisation du jeu
     * @param mapSize
     * @param wallCount
     * @param playerCount
     * @param aiCount
     * @param randomTurnOrder 
     */
    public void init(Vec2 mapSize, int wallCount, int playerCount, int aiCount, boolean randomTurnOrder){
        map = new MapModel(mapSize, wallCount);
        turn = 0;
        currentPlayerIndex = 0;
        this.randomTurnOrder = randomTurnOrder;
        PlayerFactory factory = new PlayerFactory(map);
        
        for (int i = 0; i < playerCount; i++) {
            factory.createDefaultPlayer();
        }
        for (int i = 0; i < aiCount; i++) {
            factory.createDefaultAI();
        }

        started = true;
        updateTurnOrderIds();
        fireChange();
    }
    /**
     * @return jeu lance?
     */
    
    public boolean hasStarted() {
        return started;
    }
    /**
     * Methode qui permet de passer au tour suivant
     * @return Partie terminee?
     */
    public boolean advanceTurn() {
        boolean result = false;

        // Get the next existing player:
        Player nextPlayer = null;
        for(int i = currentPlayerIndex + 1; i < turnOrderIds.size(); ++i) {
            int currentPlayerId = turnOrderIds.get(i);
            Player found = map.getPlayerWithId(currentPlayerId);

            if(found != null) {
                if(found.getCurrentEnergy() != 0) {
                    currentPlayerIndex = i;
                    nextPlayer = found;
                    break;
                }
            }
        }

        // If everybody has played, start a new turn:
        boolean allPlayersHavePlayed = (nextPlayer == null);
        map.endOfTurnUpdate(allPlayersHavePlayed);
        if(!isFinished()) {
            if (allPlayersHavePlayed) {
                updateTurnOrderIds();
                currentPlayerIndex = 0;
                nextPlayer = map.getPlayerWithId(turnOrderIds.get(0));
            }

            nextPlayer.setShield(false);
        } else {
            result = true;
        }

        ++turn;
        fireChange();
        return result;
    }
}
