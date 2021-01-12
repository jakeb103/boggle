
/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

import java.util.*;

/**
 * This class started as the tic tac toe example and is now edited to be awesome
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Boggle boggleGame; // model for the game
    private JLabel status; // used as a header for words
    private JLabel scores; // self explanatory
    private int points; // score for scores label
    private JLabel buildingWord; // word you're building
    private JToggleButton[][] buttons; // array of buttons
    private JPanel statusPan; // panel to add words post completion
    private JLabel timeboi; // timer label
    private int time;
    private LinkedList<JToggleButton> clickedSoFar; // list of what's been clicked (for undo)
    private LinkedList<JToggleButton> nearClicked; // list of what to disable
    private LinkedList<JLabel> wordsEntered; // list of what's been entered
    private HighScoreReader hsr; // reader to find high scores to display
    // note that there is no hsw since we need a new one every time they enter a
    // name
    private String name; // name they put in at the end of the game
    private JFrame nameFrame; // frame to prompt name
    private JTextField textBox; // box for name input
    private JPanel highScorePanel; // panel to display high scores
    private LinkedList<JLabel> hsEntered; // list of high scores
    private static final String
        FILEPATH = "/Users/jake/eclipse-workspace/Boggle/files/highScores.txt";

    // Game constants
    public static final int BOARD_WIDTH = 350;
    public static final int BOARD_HEIGHT = 100;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, JPanel statusPanel, JLabel score, JLabel wordBeingBuilt,
            JLabel timething,
            JFrame nameFrame1, JTextField textBox1, JPanel hsPan) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key
        // listener.
        setFocusable(true);
        nameFrame = nameFrame1;
        textBox = textBox1;
        highScorePanel = hsPan;
        textBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String dirtyName = textBox.getText();
                String[] splitnames = dirtyName.split("&");
                name = "";
                for (String jawn : splitnames) {
                    name += jawn; // to remove & from all names
                }
                if (name != "") {
                    nameFrame.setVisible(false);
                    HighScoreWriter hsw = new HighScoreWriter(FILEPATH);
                    hsw.write(name, boggleGame.getScore()); 
                    // writer made to write each name as you win
                }
            }
        });

        boggleGame = new Boggle(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
                             // repurposed as a word list
        timeboi = timething;
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime(); // method to just countdown the timer
            }
        });

        time = 91;
        timer.start();
        buttons = new JToggleButton[4][4]; // initialize the button array
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JToggleButton("X");
                buttons[i][j].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updatebuildingWord("Jawn"); // add actionlistener to be removed in reset
                    } // for uniformity

                });
                this.add(buttons[i][j]); // add to gameboard so it's displayed
            }
        }
        scores = score;
        buildingWord = wordBeingBuilt;
        repaint();
        clickedSoFar = new LinkedList<JToggleButton>();
        nearClicked = new LinkedList<JToggleButton>();
        statusPan = statusPanel;
        wordsEntered = new LinkedList<JLabel>();
        hsEntered = new LinkedList<JLabel>();
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        time = 91;
        clickedSoFar.clear();
        nearClicked.clear();
        points = 0;
        updateScores(0);
        boggleGame.reset();
        String[][] jawn = boggleGame.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) { // ok, this is where it gets messy (sorry)
                JToggleButton x = buttons[i][j]; // this is IMPORTANT (for listener)
                x.setSelected(false);
                x.setEnabled(true); // resetting means everything is enabled
                String y = jawn[i][j];
                int row = i;
                int col = j;
                buttons[i][j].setText(jawn[i][j]); // display board letter (randomized by board)
                buttons[i][j].removeActionListener(buttons[i][j].getActionListeners()[0]);
                buttons[i][j].addActionListener(new ActionListener() {
                    // had to removeActionListener so we don't accidentally type multiple letters
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nearClicked.clear(); // something was just clicked so now we need new stuff
                        if (x.isSelected()) { // have to make sure you're clicking for first time
                            x.setEnabled(false); // makes sure you can't click it twice
                            updatebuildingWord(y); // add letter associated with position to word
                            clickedSoFar.add(x); // for undo
                            for (int i1 = Math.max(0, row - 1); i1 <= Math.min(3, row + 1); i1++) {
                                for (int j1 = Math.max(0, col - 1); j1 <= Math.min(3, col + 1);
                                        j1++) {
                                    if (!clickedSoFar.contains(buttons[i1][j1])) {
                                        buttons[i1][j1].setEnabled(true);
                                        nearClicked.add(buttons[i1][j1]);
                                    } // that monstrosity above was to track what you can click
                                } // since it's close enough to your current letter
                            }
                            for (int i2 = 0; i2 < 4; i2++) {
                                for (int j2 = 0; j2 < 4; j2++) {
                                    if (!nearClicked.contains(buttons[i2][j2])) {
                                        buttons[i2][j2].setEnabled(false);
                                    } // have to disable things not close enough to your button
                                } // so users can't click together
                            }
                        } // ok we're through the hard part :)

                    }

                });
            }
        }
        status.setText("Your Words");
        buildingWord.setText(""); // no word you're building if you just reset
        for (JLabel p : wordsEntered) {
            Container x = p.getParent(); // want to totally remove, not just set invisible
            x.remove(p);
            x.validate();
            x.repaint();
        }
        wordsEntered.clear();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void enterWord() {
        nearClicked.clear(); // when you enter nothing should be clicked after
        clickedSoFar.clear();
        int x = boggleGame.enterWord(buildingWord.getText()); // get score change
        if (x != 0) { // if you got points for it, it was a word and that matters
            updateScores(x);
            JLabel p = new JLabel(buildingWord.getText());
            statusPan.add(p, BorderLayout.SOUTH);
            wordsEntered.add(p);
        }
        buildingWord.setText("");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setSelected(false);
            } // make sure everything is enabled and nothing is selected
        }
    }

    public void undo() {
        nearClicked.clear();
        String x = buildingWord.getText();
        x = x.substring(0, Math.max(0, x.length() - 1));
        buildingWord.setText(x);
        buildingWord.repaint(); // remove last letter from word then repaint JLABEL
        if (clickedSoFar.size() == 1) {
            JToggleButton c = clickedSoFar.removeLast(); // remove from clicked
            c.setEnabled(true);
            c.setSelected(false);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    buttons[i][j].setEnabled(true);
                } // everything should be enabled if nothing is selected
            }
        }
        if (clickedSoFar.size() > 1) {
            JToggleButton b = clickedSoFar.removeLast();
            b.setEnabled(true);
            b.setSelected(false);
            for (int i = 0; i < 4; i++) { // messy again my b
                for (int j = 0; j < 4; j++) { // we need to find the button pressed before b
                    if (clickedSoFar.peekLast().equals(buttons[i][j])) { // looking for last button
                        for (int i1 = Math.max(0, i - 1); i1 <= Math.min(3, i + 1); i1++) {
                            for (int j1 = Math.max(0, j - 1); j1 <= Math.min(3, j + 1); j1++) {
                                if (!clickedSoFar.contains(buttons[i1][j1])) {
                                    buttons[i1][j1].setEnabled(true);
                                    nearClicked.add(buttons[i1][j1]);
                                } // buttons near b need to be enabled and added to near clicked

                            }
                        }
                        for (int i2 = 0; i2 < 4; i2++) {
                            for (int j2 = 0; j2 < 4; j2++) {
                                if (!nearClicked.contains(buttons[i2][j2])) {
                                    buttons[i2][j2].setEnabled(false);
                                } // have to disable invalids again
                            }
                        }
                    }
                }
            }
        }
        this.repaint();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updatebuildingWord(String letter) {
        String x = buildingWord.getText();
        buildingWord.setText(x + letter);
        System.out.println(x + letter);
        this.repaint();
    }

    private void updateTime() {
        if (time == 1) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    buttons[i][j].setEnabled(false); // running out of time ends game
                }
            }
            nameFrame.setVisible(true); // enter name prompt
        }
        if (time > 0) {
            time = time - 1;
            String x = timeboi.getText().substring(0, 16);
            timeboi.setText(x + time);
            repaint();
        }
    }

    public void updateHighScores() {
        hsr = new HighScoreReader(FILEPATH);
        Person[] heads = hsr.getTopFive(); // people to display
        for (JLabel p : hsEntered) {
            if (p != null) {
                highScorePanel.remove(p);
                highScorePanel.validate();
                highScorePanel.repaint(); // remove all labels that were on the board
            }
        }
        for (Person p : heads) {
            if (p != null) {
                JLabel jawn = new JLabel(p.getName() + ": " + p.getScore());
                highScorePanel.add(jawn, BorderLayout.SOUTH);
                hsEntered.add(jawn); // add the high scores post update
            }
        }
    }

    private void updateScores(int x) {
        if (x == 0) {
            points = 0;
            scores.setText("SCORE: ");
        } else {
            points += x;
            scores.setText("SCORE: " + points);
        }
        this.repaint();
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach will not be
     * sufficient for most games, because it is not modular. All of the logic for
     * drawing the game board is in this method, and it does not take advantage of
     * helper methods. Consider breaking up your paintComponent logic into multiple
     * methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.add(buttons[i][j]); // place buttons on the board
            }
        }
        this.repaint();
        buildingWord.repaint(); // update the word as you go
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);

    }
}
