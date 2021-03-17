package view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Game;
import model.GameSettings;
import model.player.Player;
import util.mvc.ListenerModel;

public class GUIPanel extends JPanel implements ListenerModel {

    private Game game;
    private final int playerID;

    private JButton nextTurnButton;

    public GUIPanel(Game g, int playerID) {
        this.game = g;
        this.playerID = playerID;

        setLayout(null);

        //Button display position
        if (this.playerID != 0) {
            nextTurnButton = nextTurnButton(210);
            add(nextTurnButton);
        } else {
            nextTurnButton = nextTurnButton(25);
            add(nextTurnButton);
        }

        game.addListener(this);

        //Window properties
        int windowWidth = GameSettings.getInstance().getTileSize()
                * GameSettings.getInstance().getDefaultMapSize().getX();
        setPreferredSize(new Dimension(windowWidth, 65));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (game.hasStarted()) {
            setBackground(new Color(240, 228, 132));
            if ((playerID != 0) && (game.getMap().getPlayerWithId(playerID) != null)) {
                drawHealthBar(g, game.getMap().getPlayerWithId(playerID).getCurrentEnergy());
            }
            drawTextInformations(g, game.getTurn() + 1, game.getMap().getPlayerCount());
        }
    }

    @Override
    public void modelUpdated(Object source) {
        repaint();
    }

    /**
     * Dessine la barre de vie d'un joueur sur le GUI
     * @param g
     * @param value 
     */
    private void drawHealthBar(Graphics g, int value) {
        g.setColor(Color.WHITE);
        float health = (float) value / game.getMap().getPlayerWithId(playerID).getMaxEnergy();
        g.setColor(Color.RED);
        g.fillRect(25, 42, 155, 20);
        g.setColor(new Color(44, 96, 27));
        g.fillRect(25, 42, (int) (155 * health), 20);
        g.setColor(Color.BLACK);
        Font myFont = new Font("Courier New", 1, 14);
        g.setFont(myFont);
        g.drawString("Current Health: " + String.valueOf(value), 25, 37);
        g.drawRect(25, 42, 155, 20);
        g.drawRect(24, 41, 157, 22);
    }

    /**
     * Affiche du texte sur le GUI (tirs,bombes,mines)
     * @param g
     * @param turn
     * @param playerRemaining 
     */
    private void drawTextInformations(Graphics g, int turn, int playerRemaining) {
        Font myFont = new Font("Courier New", 1, 14);
        g.setFont(myFont);
        g.drawString("Turn:" + String.valueOf(turn) + "(P" + String.valueOf(game.getCurrentPlayerId()) + ")", 25, 17);//Healthbar end at x 215
        if (playerID != 0) {
            Player p = game.getMap().getPlayerWithId(playerID);
            if (p != null) {//Check if player is alive
                g.drawString("Shots:" + String.valueOf(game.getMap().getPlayerWithId(playerID).getShotsLeft()), 125, 17);
                g.drawString("Bombs:" + String.valueOf(game.getMap().getPlayerWithId(playerID).getBombsLeft()), 210, 17);
                g.drawString("Mines:" + String.valueOf(game.getMap().getPlayerWithId(playerID).getMinesLeft()), 295, 17);
            }
        } else {
            g.drawString("Alive:" + String.valueOf(playerRemaining), 125, 17);
        }
    }

    /**
     * Bouton pour passer au tour suivant
     * @param x
     */
    private JButton nextTurnButton(int x) {
        nextTurnButton = new JButton("Next turn");
        nextTurnButton.setBackground(new Color(249, 220, 102));
        nextTurnButton.setForeground(Color.black);
        nextTurnButton.setFocusable(false);
        Font myFont = new Font("Courier New", 1, 14);
        nextTurnButton.setFont(myFont);
        nextTurnButton.setSize(120, 35);
        nextTurnButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black, 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));

        nextTurnButton.setLocation(x, 29);
        nextTurnButton.addActionListener((ActionEvent e) -> {
            game.advanceTurn();
        });
        return nextTurnButton;
    }

}
