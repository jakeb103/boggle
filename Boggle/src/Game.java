
/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This framework
 * is very effective for turn-based games. We STRONGLY recommend you review
 * these lecture slides, starting at slide 8, for more details on
 * Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view, implements a
 * bit of controller functionality through the reset button, and then
 * instantiates a GameBoard. The GameBoard will handle the rest of the game's
 * view and controller functionality, and it will instantiate a TicTacToe object
 * to serve as the game's model.
 */
public class Game implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Boggle by Brooks");
        frame.setLocation(400, 400);

        // panel for storing JToggleButtons
        final JPanel GamePanel = new JPanel();
        frame.add(GamePanel);
        GamePanel.setPreferredSize(new Dimension(400, 200));
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.EAST); // panel for words
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status); // this will contain all words
        status_panel.setPreferredSize(new Dimension(100, 200));
        final JPanel scorePanel = new JPanel();
        frame.add(scorePanel, BorderLayout.WEST);
        final JLabel score = new JLabel("Setting up...");
        scorePanel.add(score);
        JPanel buildingPanel = new JPanel(); // panel for instructions and word you're building
        frame.add(buildingPanel, BorderLayout.SOUTH);
        JLabel buildingLabel = new JLabel("Word You're Building:");
        JLabel buildingWord = new JLabel(""); // start blank, edited during run by GameBoard

        JLabel timer = new JLabel("TIME REMAINING: ");
        final JFrame nameFrame = new JFrame("Name");
        nameFrame.setLocation(400, 400); // name prompt
        JPanel textBoxPan = new JPanel();
        JLabel textBoxInfo = new JLabel("Enter your name");
        JTextField textField = new JTextField("your name here"); // stuff to enter name
        nameFrame.setPreferredSize(new Dimension(400, 300));
        nameFrame.add(textBoxPan);
        textBoxPan.add(textBoxInfo);
        textBoxPan.add(textField);
        nameFrame.setVisible(false); // so they have to press a button to see
        nameFrame.pack();
        JFrame highScoreFrame = new JFrame("High Scores");
        JPanel highScorePanel = new JPanel();
        highScoreFrame.setLocation(400, 400);
        highScoreFrame.setPreferredSize(new Dimension(100, 200));
        highScoreFrame.add(highScorePanel);
        highScoreFrame.setVisible(false);
        highScoreFrame.pack();

        JFrame instructionsFrame = new JFrame("Instructions");
        JPanel instructionsPanel = new JPanel();
        JTextArea instructions = new JTextArea("The goal of the game is to make as many "
                + "words as possible. \n You make these words by pressing the letters on the "
                + "display in \n "
                + "an order such that the word you " + "form is made entirely by connecting \n "
                + "letters. You can undo a letter press by pressing the undo button. Press reset to"
                + " make "
                + "\n a new board. "
                + "You enter words by pressing enter. Check high scores with the high score button."
                + " \n "
                + "Your score is based " + "off of how many letters you have in valid words. \nHave"
                + " fun!");
        instructionsFrame.setLocation(300, 400);
        instructionsFrame.setPreferredSize(new Dimension(600, 300));
        instructionsPanel.add(instructions);
        instructionsFrame.add(instructionsPanel);
        instructionsFrame.setVisible(false);
        instructionsFrame.pack();

        final JButton instructionsB = new JButton("Instructions");
        instructionsB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instructionsFrame.setVisible(true);
            }
        });
        buildingPanel.add(instructionsB);
        buildingPanel.add(buildingLabel, BorderLayout.NORTH);
        buildingPanel.add(buildingWord);

        // Game board
        final GameBoard board = new GameBoard(status, status_panel, score, buildingWord, timer,
                nameFrame, textField,
                highScorePanel);
        frame.add(board, BorderLayout.CENTER);
        System.out.println("didn't time out"); // recursive function merked me

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        control_panel.add(timer, BorderLayout.WEST);

        // Note here that when we add an action listener to the reset button, we define
        // it as an
        // anonymous inner class that is an instance of ActionListener with its
        // actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be
        // called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        final JButton enterWord = new JButton("Enter");
        enterWord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.enterWord();
            }
        });
        control_panel.add(enterWord);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.undo();
            }
        });
        control_panel.add(undo);
        final JButton highScores = new JButton("High Scores");
        highScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.updateHighScores();
                highScoreFrame.setVisible(true);
            }
        });
        control_panel.add(highScores);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
     * this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}