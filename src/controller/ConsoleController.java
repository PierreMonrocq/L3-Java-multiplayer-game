package controller;

import ai.AICommandGenerator;
import controller.commands.*;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Game;
import util.Vec2;

public class ConsoleController implements Controller {

    private Game game;
    private final Scanner inputScanner;
    private AICommandGenerator aiCommandGenerator;

    /**
     * Controler de la console
     *
     * @param game
     */
    public ConsoleController(Game game) {
        this.game = game;
        inputScanner = new Scanner(System.in);
        aiCommandGenerator = new AICommandGenerator();
    }

    /**
     * Méthode qui demande un entier dans la console
     *
     * @param printString Chaine de caractère d'entrée
     * @param lowBound Borne inférieure
     * @param highBound Borne supérieure
     * @return entier correspondant aux bornes
     */
    private int askForInt(String printString, int lowBound, int highBound) {
        boolean done;
        int result = 0;

        do {
            System.out.println(printString);
            done = false;
            String inString = inputScanner.nextLine();

            if (inString.matches("-?\\d+(\\.\\d+)?")) {
                result = Integer.parseInt(inString);

                if ((result >= lowBound) && (result <= highBound)) {
                    done = true;
                }
            }
        } while (!done);

        return result;
    }

    /**
     * Méthode qui demande une reponse booléenne dans la console
     *
     * @param printString Chaine de caractère d'entrée
     * @return booléen
     */

    private boolean askForBoolean(String printString) {
        for (;;) {
            System.out.println(printString);
            String inString = inputScanner.nextLine();

            if (inString.equals("y") || inString.equals("yes")) {
                return true;
            } else if (inString.equals("n") || inString.equals("no")) {
                return false;
            }
        }
    }

    /**
     * Execute une commande en fonction du texte saisi
     *
     * @return Commande
     */
    public Command askForCommand() {
        System.out.println("Please enter your command now:");
        Command playerCommand = null;
        boolean done;

        do {
            done = true;
            String inString = inputScanner.nextLine();

            switch (inString) {
                case "quit":
                case "exit": {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                break;

                case "w":
                case "z": {
                    System.out.println("COMMAND: UP");
                    playerCommand = new MoveCommand(new Vec2(0, -1));
                }
                break;

                case "a":
                case "q": {
                    System.out.println("COMMAND: LEFT");
                    playerCommand = new MoveCommand(new Vec2(-1, 0));
                }
                break;

                case "s": {
                    System.out.println("COMMAND: DOWN");
                    playerCommand = new MoveCommand(new Vec2(0, 1));
                }
                break;

                case "d": {
                    System.out.println("COMMAND: RIGHT");
                    playerCommand = new MoveCommand(new Vec2(1, 0));
                }
                break;

                case "wait": {
                    System.out.println("COMMAND: WAIT");
                    playerCommand = new WaitCommand();
                }
                break;

                case "shoot": {
                    System.out.println("COMMAND: SHOOT");
                    Vec2 direction = askForShootDirection();
                    playerCommand = new ShootCommand(direction);
                }
                break;

                case "bomb": {
                    System.out.println("COMMAND: BOMB");
                    Vec2 direction = askForPlacement();
                    playerCommand = new PlaceBombCommand(direction);
                }
                break;

                case "mine": {
                    System.out.println("COMMAND: MINE");
                    Vec2 direction = askForPlacement();
                    playerCommand = new PlaceMineCommand(direction);
                }
                break;

                case "shield": {
                    System.out.println("COMMAND: SHIELD");
                    playerCommand = new ShieldCommand();
                }
                break;

                default: {
                    System.out.println("Unrecognised command " + inString + ".");
                    System.out.println("Valid commands are:\n- EXIT(exit/quit)\n- UP(w/z)\n- DOWN(s)\n- "
                            + "LEFT(a/q)\n- RIGHT(d)\n- WAIT(wait)\n- SHOOT(shoot)\n- EXPLOSIVE(bomb/mine)\n- SHIELD(shield)");
                    done = false;
                }
                break;
            }
        } while (!done);

        return playerCommand;
    }

    /**
     * Demande à l'utilisateur une direction de tir dans la console
     *
     * @return vecteur 2 dimension de la direction demandée
     */
    public Vec2 askForShootDirection() {
        Vec2 result = new Vec2(0, 0);
        Scanner sc = new Scanner(System.in);
        boolean done;

        do {
            done = true;
            System.out.println("Please specify the shot direction: where 'P' below is your position:\n 1 \n2P3\n 4");
            String shotDirectionString = sc.nextLine();

            switch (shotDirectionString) {
                case "w":
                case "z":
                case "1": {
                    result = new Vec2(0, -1);
                }
                break;

                case "a":
                case "q":
                case "2": {
                    result = new Vec2(-1, 0);
                }
                break;

                case "s":
                case "4": {
                    result = new Vec2(0, 1);
                }
                break;

                case "d":
                case "3": {
                    result = new Vec2(1, 0);
                }
                break;

                default: {
                    System.out.println("Unrecognised direction: " + shotDirectionString);
                    System.out.println("Recognised directions are:\n- UP(w/z/1)\n- DOWN(s/4)\n- LEFT(a/q/2)\n- RIGHT(d/3).");
                    done = false;
                }
                break;
            }
        } while (!done);

        return result;
    }

    /**
     * Demande à l'utilisateur une direction de placement d'un explosif dans la
     * console
     *
     * @return vecteur 2 dimension de la direction demandée
     */
    public Vec2 askForPlacement() {
        System.out.println("Please specify the desired placement, where 'P' below is your position:\n789\n4P6\n123");
        Scanner sc = new Scanner(System.in);
        Vec2 placementDirection = new Vec2();
        boolean done;

        do {
            done = true;
            String placementString = sc.nextLine();
            switch (placementString) {
                case "1":
                    placementDirection = new Vec2(-1, 1);
                    break;
                case "2":
                    placementDirection = new Vec2(0, 1);
                    break;
                case "3":
                    placementDirection = new Vec2(1, 1);
                    break;
                case "4":
                    placementDirection = new Vec2(-1, 0);
                    break;
                case "5":
                    System.out.println("You cannot place explosives on the same cell you are located.");
                    done = false;
                    break;
                case "6":
                    placementDirection = new Vec2(1, 0);
                    break;
                case "7":
                    placementDirection = new Vec2(-1, -1);
                    break;
                case "8":
                    placementDirection = new Vec2(0, -1);
                    break;
                case "9":
                    placementDirection = new Vec2(1, -1);
                    break;
                default:
                    System.out.println("Unrecognised position " + placementString + ".");
                    System.out.println("Accepted placements are 1 through 9, except 5. If it helps, think of it as a numpad.");
                    done = false;
                    break;
            }
        } while (!done);

        return placementDirection;
    }

    /**
     * Lance le jeu
     */
    @Override
    public void start() {
        int playerCount = askForInt("Please enter a valid number of players:", 2, 64);
        int iaCount = askForInt("Please enter a valid number of AI:", playerCount < 2 ? 2 : 0, 64);
        int mapWidth = askForInt("Please enter a valid map width:", 1, 512);
        int mapHeight = askForInt("Please enter a valid map height:", 1, 512);
        int wallCount = askForInt("Please enter a valid number of walls:", 1, 64);
        boolean randomTurnOrder = askForBoolean("Should the turn order be randomised? (y / n)");
        game.init(new Vec2(mapWidth, mapHeight), wallCount, playerCount, iaCount, randomTurnOrder);

        boolean isFinished = false;
        do {
            boolean correctCommand = false;
            while (!correctCommand) {

                if (game.currentPlayerIsAI()) {
                    Command cmd = aiCommandGenerator.nextRandomCommand();
                    correctCommand = game.parseCommand(cmd);
                } else {
                    Command cmd = this.askForCommand();
                    correctCommand = game.parseCommand(cmd);
                }

            }

            if (game.isFinished()) {
                break;
            }

            isFinished = game.advanceTurn();
        } while (!isFinished);

        System.out.println("Jeu terminé");
    }
}
