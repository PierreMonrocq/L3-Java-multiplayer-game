/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import ai.*;
import controller.commands.Command;
import model.Game;
import model.GameSettings;
import util.Vec2;
import view.WindowView;

/**
 *
 * @author pierr
 */
public class WindowController implements Controller { 
    private Game game;
    private ConsoleController consoleControl;
    private boolean isInitialised = false;
    private AICommandGenerator aiCommandGenerator;
    
    /**
     * Controler de la vue graphique
     * @param game 
     */
    public WindowController(Game game) {
        aiCommandGenerator = new AICommandGenerator();
        this.game = game;
        consoleControl = new ConsoleController(game);
    }
    
    /**
     * Affiche le menu
     */
    public void showMenu(){
        Menu menu = new Menu(this);
    }
    
    /**
     * Initialise le jeu
     */
    public void init(){
        Vec2 mapSize = GameSettings.getInstance().getDefaultMapSize();
        int walls = GameSettings.getInstance().getDefaultWallNumber();
        int nbPlayers = GameSettings.getInstance().getPlayerNumber();
        int nbIA = GameSettings.getInstance().getAiNumber();
        boolean random = GameSettings.getInstance().isRandomTurn();
        game.init(mapSize, walls, nbPlayers, nbIA, random);
        this.isInitialised = true;
    }
    /**
     * Permet de lancer le jeu à partir du Menu, cette méthode est nécessaire car le menu 
     * ne peut pas exécuter le code dans la classe Main directement
     */
     public void launchGame(){//Menu
         init();
         WindowView wv = new WindowView(game, "Methode conception");
         start();
    }
    
    /**
     * Lance le jeu
     */
    @Override
    public void start(){
        if(this.isInitialised){
            boolean isFinished = false;

            do {

                boolean correctCommand = false;
                while(!correctCommand) {//check for current turn
                    if (game.currentPlayerIsAI()) {
                        Command cmd = aiCommandGenerator.nextRandomCommand();
                        correctCommand = game.parseCommand(cmd);
                    } else {
                        Command cmd = this.consoleControl.askForCommand();
                        correctCommand = game.parseCommand(cmd);
                    }
                }

                isFinished = game.advanceTurn();
            } while(!isFinished);
        } else{
             throw new IllegalStateException("Game is not initialised");
        }
    }
}
