package view;

import model.Game;
import model.map.VisibleElement;
import model.player.Player;
import util.mvc.ListenerModel;
import util.Vec2;
import util.proxy.ProxyVisibleElement;

public class ConsoleView implements ListenerModel {
    private Game game;
    private String mapString;
    private String positionsString;
    private String energyString;
    private String turnString;
    private ProxyVisibleElement proxy;

    public ConsoleView(Game game) {
        this.game = game;
        mapString = "";
        positionsString = "";
        energyString = "";
        turnString = "";
        game.addListener(this);
        
        this.proxy = new ProxyVisibleElement();
    }
    
    public void onTurnAdvance() {
        System.out.print(turnString);
        System.out.print(mapString);
        System.out.print(positionsString);
        System.out.print(energyString);
    }
    /**
     * Modele mis a jour
     * @param source 
     */
    @Override
    public void modelUpdated(Object source) {
        // Map:
        {
            mapString = "";
            VisibleElement[][] view = game.getMap().getVisibles();

            for (int y = 0; y < view.length; ++y) {
                for (int x = 0; x < view[0].length; ++x) {
                    mapString += " ";
                    
                    VisibleElement visible = proxy.displayElement(view[y][x],game.getCurrentPlayerId());

                    switch (visible.getType()) {
                        case EMPTY:
                            mapString += "-";
                            break;
                        case PLAYER:
                            mapString += "P";
                            break;
                        case WALL:
                            mapString += "W";
                            break;
                        case BOMB:
                            mapString += "B";
                            break;
                        case MINE:
                            mapString += "M";
                            break;
                        default:
                            break;
                    }
                }
                mapString += "\n";
            }
        }

        // Player information:
        {
            positionsString = "";
            energyString = "";

            for(int i = 0; i < game.getMap().getPlayerCount(); ++i) {
                int id = game.getTurnOrderId(i);
                Player player = game.getMap().getPlayerWithId(id);
                Vec2 p = player.getPosition();

                positionsString += "Player " + id + " is at position [" + p.getX() + ", " + p.getY() + "].\n";
                energyString += "Player " + id + " has " + player.getCurrentEnergy() + " energy left.\n";
            }
        }

        // Turn information:
        {
            turnString = "";

            turnString += "It is now turn " + game.getTurn() + ", player " + game.getCurrentPlayerId() + "'s turn.\n";
        }

        onTurnAdvance();
    }
}
