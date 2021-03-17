/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import view.panel.GamePanel;
import model.Game;
import util.mvc.ListenerModel;
import view.panel.GUIPanel;

public class WindowView implements ListenerModel {

    private HashMap<Integer, JFrame> frames;
    private Game game;

    /**
     * Cree les fenetres pour chaque joueurs
     * @param game
     * @param title 
     */
    public WindowView(Game game, String title) {
        this.game = game;
        this.game.addListener(this);
        frames = new HashMap();
        if (game != null) {
            int count = game.getMap().getPlayerCount() + 1;
            for (int i = 0; i < count; i++) {
                JFrame f = new JFrame(i > 0 ? title + " - Player " + i + "'s view" : title + " - All players view");
                f.setLayout(new BorderLayout());
                f.add(new GUIPanel(game, i), BorderLayout.PAGE_START);
                f.add(new GamePanel(game, i), BorderLayout.CENTER);

                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setAlwaysOnTop(true);
                f.setLocationRelativeTo(null);
                f.pack();
                f.setVisible(true);
                frames.put(i, f);//we keep frame adress with it's playerid value
            }

        } else {
            throw new IllegalStateException("The game is not initialised yet");

        }

    }

    @Override
    public void modelUpdated(Object source) {
        for (Map.Entry<Integer, JFrame> id : frames.entrySet()) {
            if ((game.getMap().getPlayerWithId(id.getKey()) == null) && id.getKey() != 0) {
                id.getValue().setVisible(false);
                id.getValue().dispose();//Killing the frame
            }
        }
    }
}
