package main;

import controller.ConsoleController;
import controller.WindowController;
import model.Game;
import view.ConsoleView;
import view.WindowView;

public class Main {
    

    public static void main(String[] args) {

        if (args.length > 1) {
            System.out.println("");
            System.out.println("Expected 0 or 1 arguments.");
            System.out.println("arguments: console or menu");
            System.exit(1);
        }
        Game game = new Game();
        if (args.length == 1) {
            //Console
            if (args[0].equals("console")) {
                ConsoleController cc = new ConsoleController(game);
                ConsoleView cv = new ConsoleView(game);
                cc.start();
            } else if (args[0].equals("menu")) {
                //JFrame
                WindowController wc = new WindowController(game);
                wc.showMenu();
            }
        } else {
            System.out.println("Launching game with default settings (2 players, 2 AI)");
            //JFrame
            WindowController wc = new WindowController(game);
            wc.init();
            WindowView wv = new WindowView(game, "Java game");
            wc.start();
        }

    }
    
    
}
