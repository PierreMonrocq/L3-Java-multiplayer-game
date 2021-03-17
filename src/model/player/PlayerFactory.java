package model.player;

import model.GameSettings;
import model.map.CellComposite;
import model.map.MapModel;
import util.Vec2;

import java.util.Random;

public class PlayerFactory {
    private MapModel map;

    public PlayerFactory(MapModel map) {
        this.map = map;
    }

    private void createPlayer(int energy, boolean AI, int shotsLeft, int bombsLeft, int minesLeft) {
        Vec2 dim = map.getDim();
        Random r = new Random();
        int x = r.nextInt(dim.getX());
        int y = r.nextInt(dim.getY());
        Vec2 spawnP = new Vec2(x, y);
        CellComposite cell = map.getCell(spawnP);

        // While the current cell can't contain a player, look another one up.
        while(cell.occupiesHigh()) {
            spawnP.setX(r.nextInt(dim.getX()));
            spawnP.setY(r.nextInt(dim.getY()));
            cell = map.getCell(spawnP);
        }

        Player p = new Player(spawnP, energy, map.getPlayerCount() + 1, AI, shotsLeft, bombsLeft, minesLeft, map);

        map.addPlayer(p);
    }

    private void createDefault(boolean AI) {
        createPlayer(GameSettings.getInstance().getMaxEnergyValue(), AI,
                GameSettings.getInstance().getShotsLeft(),
                GameSettings.getInstance().getBombsLeft(),
                GameSettings.getInstance().getShotsLeft());
    }

    public void createDefaultPlayer() {
        createDefault(false);
    }

    public void createDefaultAI() {
        createDefault(true);
    }
    
    public void createPlayerWithCustomWeaponStock(int energy, int shotsLeft, int bombsLeft, int minesLeft){
        createPlayer(energy, false, shotsLeft, bombsLeft, minesLeft);
    }
    
    public void createPlayerAIWithCustomWeaponStock(int energy, int shotsLeft, int bombsLeft, int minesLeft){
        createPlayer(energy, true, shotsLeft, bombsLeft, minesLeft);
    }
    
    public void createPlayerWithCustomEnergyStartAndWeapon(int energy, int minesLeft, int shotsLeft, int bombsLeft){
        createPlayer(energy, false, shotsLeft, bombsLeft, minesLeft);
    }
    
    public void createPlayerAIWithCustomEnergyStartAndWeapon(int energy, int minesLeft, int shotsLeft, int bombsLeft){
        createPlayer(energy, true, shotsLeft, bombsLeft, minesLeft);
    }
}