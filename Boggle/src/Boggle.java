import java.util.*;

public class Boggle {
    private String[][] board;
    private Collection<String> wordsEntered;
    private int score;
    private Collection<String> dictionary;
    private FileLineIterator reader;

    public Boggle() {
        reader = new FileLineIterator("/Users/jake/eclipse-workspace/Boggle/files/dictionary.txt");
        dictionary = new TreeSet<String>();
        while (reader.hasNext()) {
            dictionary.add(reader.next()); // making the dictionary easy to parse
        }
        wordsEntered = new TreeSet<String>();
        reset();
    }

    public void reset() {
        board = new String[4][4];
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZAEIOURTNS";
        // string of all letters with commonly used letters repeated for higher percent
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double x = 35 * (Math.random());
                int y = (int) x;
                board[i][j] = "" + (chars.charAt(y)); // randomly picks a character from chars
            }
        }
        score = 0;
        wordsEntered.clear();
    }

    public boolean isValidWord(String word) {
        return (word.length() > 1 && dictionary.contains(word.toUpperCase()));
    } // check that the word is actually english and not single letter

    public int enterWord(String word) {
        if (isValidWord(word)) {
            if (!(wordsEntered.contains(word))) {
                score += word.length(); // points awarded for word length
                wordsEntered.add(word); // record word
                return word.length();
            }
        }
        return 0;
    } // this function returns an int so that the score change can be reflected in
      // gameboard

    public int getScore() {
        return score;
    }

    public String[][] getBoard() {
        String[][] jawn = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                jawn[i][j] = board[i][j];
            }
        }

        return jawn;
    }

    public Collection<String> getWordsEntered() { // for testing
        Collection<String> jawn = new TreeSet<String>();
        for (String s : wordsEntered) {
            jawn.add(s);
        }
        return jawn;
    }

}
