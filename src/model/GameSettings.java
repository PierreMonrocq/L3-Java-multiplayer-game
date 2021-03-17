package model;

import util.Vec2;

public class GameSettings {

    private static GameSettings instance;

    private int tileSize;
    private Vec2 defaultMapSize;
    private int defaultWallNumber;
    
    private int moveCost;
    private int shieldCost;
    private int shotCost;
    private int mineCost;
    private int bombCost;

    private int shotRange;
    private int bombExplosionDelay;

    private int shotDamage;
    private int bombDamage;
    private int mineDamage;

    private int shotsLeft;
    private int bombsLeft;
    private int minesLeft;

    private int startEnergyValue;
    private int maxEnergyValue;
    private int energyPickupValue;

    private boolean hideExplosives;
    
    private boolean randomTurn;
    
    private int minWallSize;
    private int maxWallSize;
    private boolean wallIntersection;
    
    private int maxPickupNumber;
    private int playerNumber;
    private int aiNumber;
    
    /**
     * Singleton, parametres de la logique du jeu
     */
    private GameSettings() {

        //Size
        this.tileSize = 32;
        this.defaultMapSize = new Vec2(15,15);
        
        //Costs
        this.moveCost = 1;
        this.shieldCost = 8;
        this.shotCost = 3;
        this.mineCost = 5;
        this.bombCost = 5;

        //Weapons
        this.shotRange = 3;
        this.bombExplosionDelay = 3;

        this.minesLeft = 3;
        this.shotsLeft = 15;
        this.bombsLeft = 3;

        //Damages
        this.shotDamage = 20;
        this.bombDamage = 30;
        this.mineDamage = 25;

        //Energy
        this.startEnergyValue = 100;
        this.maxEnergyValue = 100;
        this.energyPickupValue = 20;

        //Misc
        this.hideExplosives = false;
        this.maxPickupNumber = 4;
        this.randomTurn = false;
        this.playerNumber = 2;
        this.aiNumber = 2;
        
        // Wall generation parameters
        this.maxWallSize = 1;
        this.maxWallSize = 3;
        this.wallIntersection = true;
        this.defaultWallNumber = 8;
        
    }

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }

        return instance;
    }

    public int getShotCost() {
        return shotCost;
    }

    public int getMineCost() {
        return mineCost;
    }

    public int getBombCost() {
        return bombCost;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public int getShieldCost() {
        return shieldCost;
    }

    public int getShotRange() {
        return shotRange;
    }

    public int getBombExplosionDelay() {
        return bombExplosionDelay;
    }

    public int getShotDamage() {
        return shotDamage;
    }

    public int getBombDamage() {
        return bombDamage;
    }

    public boolean shouldHideExplosives() {
        return hideExplosives;
    }

    public int getMineDamage() {
        return mineDamage;
    }

    public int getMaxEnergyValue() {
        return maxEnergyValue;
    }

    public int getEnergyPickupValue() {
        return energyPickupValue;
    }

    public int getShotsLeft() {
        return shotsLeft;
    }

    public int getBombsLeft() {
        return bombsLeft;
    }

    public int getMinesLeft() {
        return minesLeft;
    }

    public int getTileSize() {
        return tileSize;
    }

    public Vec2 getDefaultMapSize() {
        return defaultMapSize;
    }

    public int getDefaultWallNumber() {
        return defaultWallNumber;
    }

    public int getStartEnergyValue() {
        return startEnergyValue;
    }

    public int getMinWallSize() {
        return minWallSize;
    }

    public int getMaxWallSize() {
        return maxWallSize;
    }

    public boolean wallsShouldIntersect() {
        return wallIntersection;
    }

    public int getMaxPickupNumber() {
        return maxPickupNumber;
    }
    
    public boolean isRandomTurn() {
        return randomTurn;
    }

    public void setRandomTurn(boolean randomTurn) {
        this.randomTurn = randomTurn;
    }

    public void setDefaultMapSize(Vec2 defaultMapSize) {
        this.defaultMapSize = defaultMapSize;
    }

    public void setDefaultWallNumber(int defaultWallNumber) {
        this.defaultWallNumber = defaultWallNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getAiNumber() {
        return aiNumber;
    }

    public void setAiNumber(int aiNumber) {
        this.aiNumber = aiNumber;
    }
}
