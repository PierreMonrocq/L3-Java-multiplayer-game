package model.map;

import model.map.wall.PlacedWall;
import model.map.wall.Wall;
import model.explosives.Explosive;
import util.DeferredElementRemover;
import util.Vec2;

import java.util.*;

import model.GameSettings;
import model.player.Player;

public class MapModel {

    private CellComposite[][] map;
    
    // Map dimension
    private Vec2 dim;

    // Player data
    private ArrayList<Player> players;
    private DeferredElementRemover<Player> playerRemover;

    private ArrayList<Explosive> explosives;
    private DeferredElementRemover<Explosive> explosiveRemover;

    public MapModel(Vec2 dim, int wallCount) {
        this.dim = dim;
        this.map = new CellComposite[dim.getY()][dim.getX()];
        players = new ArrayList<>();
        playerRemover = new DeferredElementRemover<>(players);
        explosives = new ArrayList<>();
        explosiveRemover = new DeferredElementRemover<>(explosives);
        

        for (int y = 0; y < dim.getY(); y++) {
            for (int x = 0; x < dim.getX(); x++) {
                this.map[y][x] = new CellComposite(new Vec2(x, y));
            }
        }

        placeWalls(wallCount);
        spawnPickups();
    }

    private void addToMap(AbstractCellLeaf mapObject) {
        getCell(mapObject.getPosition()).onEntry(mapObject);
    }
    /**
     * @return Dimension du plateau
     */
    public Vec2 getDim() {
        return dim;
    }


    /**
     * @return Representation visible du plateau (utilise seulement par la version console)
     */
    public VisibleElement[][] getVisibles() {
        VisibleElement[][] result = new VisibleElement[dim.getY()][dim.getX()];

        for(int y = 0; y < dim.getY(); ++y) {
            for(int x = 0; x < dim.getX(); ++x) {
                CellComposite cell = map[y][x];

                VisibleElement viewValue = cell.getVisible();

                result[y][x] = viewValue;
            }
        }

        return result;
    }
    
    
    public ArrayList<Integer> getSortedPlayerIds() {
        ArrayList<Integer> result = new ArrayList();

        for(Player p : players) {
            result.add(p.getId());
        }

        Collections.sort(result);
        return result;
    }
    
    public Player getPlayerWithId(int id) {
        for(Player p : players) {
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Cree des murs sur le plateau
     * @param maxWallNumber 
     */
    private void placeWalls(int maxWallNumber) {
        ArrayList<Wall> walls = new ArrayList<>();

        boolean intersection = GameSettings.getInstance().wallsShouldIntersect();
        int minWallSize = GameSettings.getInstance().getMinWallSize();
        int maxWallSize = GameSettings.getInstance().getMaxWallSize();

        for (int i = 1; i < maxWallNumber + 1; i++) {
            Random r = new Random();
            int w = minWallSize + r.nextInt(maxWallSize - minWallSize + 1);
            int h = minWallSize + r.nextInt(maxWallSize - minWallSize + 1);
            int x = r.nextInt(dim.getX() - w - 1) + 1;
            int y = r.nextInt(dim.getY() - h - 1) + 1;
            Wall newWall = new Wall(x, y, w, h);

            boolean error = false;
            if (!intersection) {
                for (Wall wall : walls) {
                    if (newWall.intersects(wall)) {
                        error = true;
                        break;
                    }
                }
            }

            if (!error) {
                for (int wallY = newWall.getBottom(); wallY <= newWall.getTop(); wallY++) {
                    for (int wallX = newWall.getLeft(); wallX <= newWall.getRight(); wallX++) {
                        PlacedWall wall = new PlacedWall(new Vec2(wallX, wallY));
                        addToMap(wall);
                    }
                }
                walls.add(newWall);
            }
        }
    }
    /**
     * Cree les pastilles d'énergie sur la carte.
     */
    private void spawnPickups(){
        int maxPickupCount = GameSettings.getInstance().getMaxPickupNumber();

        for (int i = 0; i < maxPickupCount; i++) {
            Random r = new Random();
            int x = r.nextInt(dim.getX());
            int y = r.nextInt(dim.getY());
            Vec2 position = new Vec2(x,y);
            CellComponent dest = getCell(position);

            if(dest != null) {
                EnergyPickup pickup = new EnergyPickup(this, position);

                if(dest.allowsEntry(pickup)) {
                    addToMap(pickup);
                }
            }
        }
    }
    /**
     * Ajoute un joueur au plateau
     * @param p 
     */
    public void addPlayer(Player p) {
        addToMap(p);
        players.add(p);
    }

    /**
     * Recupere le nombre de joueurs actuel sur le plateau
     * @return 
     */
    public int getPlayerCount() {
        return players.size() - playerRemover.size();
    }
    /**
     * Verifie si un vecteur est dans le plateau
     * @param p
     * @return 
     */
    private boolean isInBounds(Vec2 p) {
        return (p.getX() >= 0) && (p.getX() < dim.getX()) && (p.getY() >= 0) && (p.getY() < dim.getY());
    }

    /** 
     * @param pos
     * @return Joueur a une position du plateau
     */
    public Player getPlayerAt(Vec2 pos) {
        for (Player p : players) {
            if (p.getPosition().equals(pos)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Enleve un explosif du plateau
     * @param e 
     */
    public void removeExplosive(Explosive e) {
        explosiveRemover.add(e);
    }
    
    /**
     * Met à jour l'état des explosifs de la carte
     * @param allPlayersHavePlayed 
     */
    public void updateExplosives(boolean allPlayersHavePlayed) {
        // Some explosive may be walked on *and* be on its last turn before
        // exploding, so we need to flush our removals both before and after
        // this loop.
        explosiveRemover.enactRemovals();

        if(allPlayersHavePlayed) {
            for (Explosive e : explosives) {
                e.update();
            }

            explosiveRemover.enactRemovals();
        }

    }
    /**
     * @param position
     * @return Contenu d'une case a une position précise
     */
    public CellComposite getCell(Vec2 position) {
        CellComposite result = null;

        if(isInBounds(position)) {
            result = map[position.getY()][position.getX()];
        }

        return result;
    }

    /**
     * Ajoute un explosif au plateau
     * @param explosive
     * @return 
     */
    public boolean addExplosive(Explosive explosive) {
        boolean result = false;
        Vec2 p = explosive.getPosition();
        CellComponent dest = getCell(p);

        if(dest != null) {
            if(dest.allowsEntry(explosive)) {
                addToMap(explosive);
                explosives.add(explosive);
                result = true;
            }
        }

        return result;
    }

    /**
     * Supprime les joueurs marques pour suppression
     */
    public void removeMarkedPlayers() {
        for(Player p : playerRemover) {
            getCell(p.getPosition()).removeComponent(p);
        }

        playerRemover.enactRemovals();
    }
    /**
     * Marque un joueur pour la suppression
     * @param player 
     */
    public void markPlayerForRemoval(Player player) {
        playerRemover.add(player);
    }

    /**
     * Permet d'effectuer une mise à jour a la fin d'un tour
     * @param allPlayersHavePlayed 
     */
    public void endOfTurnUpdate(boolean allPlayersHavePlayed) {
        updateExplosives(allPlayersHavePlayed);
        removeMarkedPlayers();
    }
}
