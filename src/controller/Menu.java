package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import model.GameSettings;
import util.Vec2;

public class Menu extends JFrame {

    private JRadioButton randomTurn;
    private JTextField NbPlayersTF;
    private JTextField mapWidthTF;
    private JTextField mapHeightTF;
    private JTextField wallCountTF;
    private JTextField NbIATF;

    /**
     * Affiche le menu de séléction des paramètres, lance ensuite le jeu après vérification des paramètres
     * @param mainController Controleur de la fenêtre
     */
    public Menu(WindowController mainController) {

        super("Methode conception - Menu");
        setLayout(new GridLayout(7, 1));

        //Random turn
        JPanel pan2 = createJPanel("Random turn");
        randomTurn = createJTextFieldWithBooleanChoice("True", "False", pan2);
        getContentPane().add(pan2);

        //Number of players
        JPanel pan3 = createJPanel("Number of players (2-8 recommended, total of 2 players/AI required)");
        NbPlayersTF = createJTextField("3");
        pan3.add(NbPlayersTF);
        getContentPane().add(pan3);

        //Number of AI
        JPanel pan7 = createJPanel("Number of AI (2-8 recommended, total of 2 players/AI required)");
        NbIATF = createJTextField("8");
        pan7.add(NbIATF);
        getContentPane().add(pan7);

        //Map width
        JPanel pan4 = createJPanel("Map width (10-20 recommended, 5-30 allowed)");
        mapWidthTF = createJTextField("15");
        pan4.add(mapWidthTF);
        getContentPane().add(pan4);

        //Map height
        JPanel pan5 = createJPanel("Map height (10-25 recommended, 5-30 allowed)");
        mapHeightTF = createJTextField("15");
        pan5.add(mapHeightTF);
        getContentPane().add(pan5);

        //Walls
        JPanel pan6 = createJPanel("Maximum walls (0-2*map size recommended)");
        wallCountTF = createJTextField("8");
        pan6.add(wallCountTF);
        getContentPane().add(pan6);

        //Start Button
        JButton start = new JButton("Start the game");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if ((checkForException(NbPlayersTF, 0, 64))
                        && checkForException(NbIATF,  Integer.parseInt(NbPlayersTF.getText()) < 2 ? 2 : 0, ABORT)
                        && checkForException(mapWidthTF, 5, 30)
                        && checkForException(mapHeightTF, 5, 30)
                        && checkForException(wallCountTF, 0, 64)) {

                    boolean random = randomTurn.isSelected();
                    int mapWidth = Integer.parseInt(mapWidthTF.getText());
                    int mapHeight = Integer.parseInt(mapHeightTF.getText());
                    int nbPlayers = Integer.parseInt(NbPlayersTF.getText());
                    int nbIA = Integer.parseInt(NbIATF.getText());
                    int wallCount = Integer.parseInt(wallCountTF.getText());
                    GameSettings.getInstance().setRandomTurn(random);
                    GameSettings.getInstance().setDefaultMapSize(new Vec2(mapWidth, mapHeight));
                    GameSettings.getInstance().setDefaultWallNumber(wallCount);
                    GameSettings.getInstance().setPlayerNumber(nbPlayers);
                    GameSettings.getInstance().setAiNumber(nbIA);
                    setVisible(false);
                    dispose();
                    new Thread(new Runnable() {//New thread allowing swing to paint in other JFrames during transition
                        public void run() {
                            mainController.launchGame();
                        }
                    }).start();

                }
            }
        });

        add(start);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 450);

        setLocationRelativeTo(null);

        setVisible(true);
    }

    /**
     * Creer une une zone de saisie avec un texte par défaut
     * @param defaultValue Texte par défaut
     */
    private JTextField createJTextField(String defaultValue) {
        JTextField textField = new JTextField(defaultValue);
        textField.setForeground(Color.BLACK);
        textField.setBorder(new LineBorder(Color.gray, 1));
        textField.setPreferredSize(new Dimension(50, 24));
        return textField;
    }
    /**
     * Creer un JPanel avec des bordures et un titre
     * @param title Titre souhaité
     */
    private JPanel createJPanel(String title) {
        JPanel pan = new JPanel();
        Border borderTurn = BorderFactory.createTitledBorder(title);
        pan.setBorder(borderTurn);

        return pan;

    }
   
    /**
     * Met en place des JRadioButton dans un JPanel
     * @param choice1 Texte du premier bouton
     * @param choice2 Texte du deuxième bouton
     * @param pan JPanel où les boutons sont placés
     */
    private JRadioButton createJTextFieldWithBooleanChoice(String choice1, String choice2, JPanel pan) {
        ButtonGroup group = new ButtonGroup();
        JRadioButton yes = new JRadioButton(choice1);
        JRadioButton no = new JRadioButton(choice2, true);
        group.add(yes);
        group.add(no);
        pan.add(yes);
        pan.add(no);
        return yes;
    }

    /**
     * Affiche la fenêtre d'erreur
     */
    public void showErrorWindow() {
        JFrame error = new JFrame("Erreur");
        error.setLayout(new GridLayout(2, 1));
        error.setResizable(true);
        error.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        error.setSize(250, 100);
        JLabel lab = new JLabel(" Veuillez entrez des valeurs correctes");
        JButton tryAgain = new JButton("OK");
        error.add(lab);
        error.add(tryAgain);
        tryAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                error.setVisible(false);
                error.dispose();
            }
        });
        error.setLocationRelativeTo(null);
        error.setVisible(true);
    }

    /**
     * Permet d'intercepter les exceptions et d'afficher les erreurs sur les JTextField
     * @param textField
     * @param value1
     * @param value2
     */
    private boolean checkForException(JTextField textField, int value1, int value2) {
        try {
            int players = Integer.parseInt(textField.getText());
            if ((value1 <= players && players <= value2) == true) {
                textField.setForeground(Color.BLACK);
                textField.setBorder(new LineBorder(Color.gray, 1));
                return true;
            } else {
                textField.setForeground(Color.RED);
                textField.setBorder(new LineBorder(Color.red, 1));
            }

        } catch (NumberFormatException e) {
            textField.setForeground(Color.RED);
            textField.setBorder(new LineBorder(Color.red, 1));
        }
        return false;

    }

}
