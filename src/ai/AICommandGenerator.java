package ai;

import controller.commands.*;
import java.util.ArrayList;
import java.util.Random;

import util.Vec2;

public class AICommandGenerator {
    private ArrayList<String> moveList;
    public Random rand;
    
    /**
     * Genere des commandes aleatoires pour l'IA.
     */
    public AICommandGenerator() {
        moveList = new ArrayList<>();
        moveList.add("bomb");
        moveList.add("shield");
        moveList.add("move");
        moveList.add("fire");
        moveList.add("mine");
        rand = new Random();
    }
   
    /**
     * Seule fonction accessible depuis l'exterieur.
     * @return Une commande aleatoire.
     */
    public Command nextRandomCommand(){
        String choice = randomChoiceString();
        Command result = chooseRandomCommand(choice);

        return result;
    }
    
    /**
     * Utilitaire interne.
     * @return Une des chaines de moveList, ie. une des chaines representant une des commandes possibles.
     */
    private String randomChoiceString(){
        return moveList.get(rand.nextInt(moveList.size()));
    }
    
    /**
     * Utilitaire interne.
     * @param choix provient de randomChoiceString.
     * @return Une commande generee a partir de la chaine de commande.
     */
    private Command chooseRandomCommand(String choix){
        final Vec2[] possibleDirections = {new Vec2( 0, -1),
                                           new Vec2( 0,  1),
                                           new Vec2(-1,  0),
                                           new Vec2( 1,  0),
                                           new Vec2( 1, -1),
                                           new Vec2(-1, -1),
                                           new Vec2(-1,  1),
                                           new Vec2( 1,  1)};
        Command result = new WaitCommand();
        
        if (choix.equals("bomb")) {
            int choice = rand.nextInt(8);
            System.out.println(choice);
            result = new PlaceBombCommand(possibleDirections[choice]);
        } else if (choix == "fire"){
            int choice = rand.nextInt(4);
            System.out.println(choice);
            result = new ShootCommand(possibleDirections[choice]);
        } else if (choix == "mine"){
            int choice = rand.nextInt(8);
            System.out.println(choice);
            result = new PlaceMineCommand(possibleDirections[choice]);
        } else if (choix == "move"){
            int choice = rand.nextInt(4);
            System.out.println(choice);
            result = new MoveCommand(possibleDirections[choice]);
        } else if (choix == "shield"){
            result = new ShieldCommand();
        }

        return result;
    }

}
