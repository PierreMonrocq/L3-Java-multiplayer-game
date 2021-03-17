package view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.Game;
import model.GameSettings;
import util.proxy.ProxyVisibleElement;

import model.map.VisibleElement;
import model.player.Player;
import util.mvc.ListenerModel;
import util.LoadResources;
import util.Vec2;
import view.MapView;

/**
 *
 * @author Pierre
 */
public class GamePanel extends JPanel implements ListenerModel {

    private Game game;
    private final int playerID;
    private static final int TILE_SIZE = GameSettings.getInstance().getTileSize();
    private final ProxyVisibleElement proxy;
    private int windowWidth;
    private int windowHeight;
    private MapView mapView;

    public GamePanel(Game game, int playerID) {
        this.game = game;
        this.playerID = playerID;//if id == 0 display all player in one window
        this.proxy = new ProxyVisibleElement();
        System.out.println("Making map view");
        mapView = new MapView(game.getMap().getDim(), game.getMap());
        game.addListener(this);

        //Load Images
        LoadResources resources = new LoadResources();

        //Window Properties
        windowWidth = TILE_SIZE
                * GameSettings.getInstance().getDefaultMapSize().getX();
        windowHeight = TILE_SIZE
                * GameSettings.getInstance().getDefaultMapSize().getY() + 10;//+10 is because of GUI gap insertion
        setPreferredSize(new Dimension(windowWidth, windowHeight));

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (game.hasStarted()) {
            drawGuiGap(g);
            Vec2 dim = game.getMap().getDim();

            for (int y = 0; y < dim.getY(); y++) {
                for (int x = 0; x < dim.getX(); x++) {
                    Vec2 position = new Vec2(x, y);

                    VisibleElement proxyElement = proxy.displayElement(mapView.get(position), playerID);//proxy

                    switch (proxyElement.getType()) {
                        case EMPTY:
                            g.drawImage(LoadResources.getInstance().getGround_png(), TILE_SIZE * x, TILE_SIZE * y, this);
                            break;
                        case WALL:
                            g.drawImage(LoadResources.getInstance().getWall_png(), TILE_SIZE * x, TILE_SIZE * y, this);
                            break;
                        case PLAYER:
                            g.drawImage(LoadResources.getInstance().getGround_png(), TILE_SIZE * x, TILE_SIZE * y, this);
                            drawPlayerTextAndHealth(g, x, y);
                            drawPlayerColor(g, x, y);
                            break;
                        case BOMB:
                            g.drawImage(LoadResources.getInstance().getGround_png(), TILE_SIZE * x, TILE_SIZE * y, this);
                            g.drawImage(LoadResources.getInstance().getBomb_png(), TILE_SIZE * x, TILE_SIZE * y, null);
                            break;
                        case MINE:
                            g.drawImage(LoadResources.getInstance().getGround_png(), TILE_SIZE * x, TILE_SIZE * y, this);
                            g.drawImage(LoadResources.getInstance().getMine_png(), TILE_SIZE * x, TILE_SIZE * y, null);
                            break;
                        case PICKUP:
                            g.drawImage(LoadResources.getInstance().getGround_png(), TILE_SIZE * x, TILE_SIZE * y, this);
                            g.drawImage(LoadResources.getInstance().getPickup_png(), TILE_SIZE * x, TILE_SIZE * y, null);
                            break;
                    }
                }
            }
        }
        if (game.isFinished()) {
            drawWinnerName(g, windowWidth, windowHeight);
        }

    }

    @Override
    public void modelUpdated(Object source) {
        repaint();
    }

    /**
     * Dessine les barres de vie et les noms des joueurs dans le jeu
     * @param g
     * @param x
     * @param y 
     */
    private void drawPlayerTextAndHealth(Graphics g, int x, int y) {
        Font myFont = new Font("Courier New", 1, 10);
        g.setFont(myFont);

        Player player = game.getMap().getPlayerAt(new Vec2(x, y));
        g.setColor(!player.isShielded() ? new Color(0, 0, 0) : new Color(220, 220, 220));
        g.drawString("P" + String.valueOf(player.getId()), TILE_SIZE * x + TILE_SIZE / 3, TILE_SIZE * y - 4);

        float health = (float) player.getCurrentEnergy() / player.getMaxEnergy();
        g.setColor(!player.isShielded() ? new Color(44, 96, 27) : new Color(220, 220, 220));
        g.fillRect(TILE_SIZE * x + 2, TILE_SIZE * y - 1, (int) (28 * health), 2);
    }

    /**
     * Dessine un personnage avec une couleur
     * @param g
     * @param x
     * @param y 
     */
    private void drawPlayerColor(Graphics g, int x, int y) {
        Player player = game.getMap().getPlayerAt(new Vec2(x, y));
        if (playerID == 0) {
            g.drawImage(LoadResources.getInstance().getNeutral_png(), TILE_SIZE * x, TILE_SIZE * y, null);
        } else if (player.getId() == playerID) {
            g.drawImage(LoadResources.getInstance().getSelf_png(), TILE_SIZE * x, TILE_SIZE * y, null);
        } else {
            g.drawImage(LoadResources.getInstance().getEnemy_png(), TILE_SIZE * x, TILE_SIZE * y, null);
        }
    }

    /**
     * Dessine un espace pour l'espacement du panel jeu et le GUI
     * @param g 
     */
    private void drawGuiGap(Graphics g) {
        setBackground(new Color(240, 228, 132));
        g.translate(0, 10);
    }

    
    /**
     * Dessine la fin du jeu
     * @param g
     * @param width
     * @param height 
     */
    private void drawWinnerName(Graphics g, int width, int height) {
        g.setColor(new Color(240, 228, 132));
        g.fillRect((width/2)-75, (height/2)-65, 150, 30);
        Font myFont = new Font("Courier New", 1, 14);
        g.setFont(myFont);
        g.setColor(new Color(0,0,0));
        g.drawString("Game is finished", width/2-65, (height/2)-45);
    }
}
