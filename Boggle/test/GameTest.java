import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the boggle functions
 */

public class GameTest {

    @Test
    public void testIsValidWord() { // tests edge cases and success
        Boggle g = new Boggle();
        assertFalse(g.isValidWord("numnums")); // not a word
        assertTrue(g.isValidWord("hypothetically")); // is a word
        assertFalse(g.isValidWord("a")); // too short
        assertFalse(g.isValidWord("be6t")); // includes numbers
        assertFalse(g.isValidWord("8"));
    }

    @Test
    public void testScoreFunction() {
        Boggle g = new Boggle();
        assertEquals(6, g.enterWord("banana")); // testing that entering words returns score change
        assertEquals(g.getScore(), 6);
        assertEquals(2, g.enterWord("no"));
        assertEquals(g.getScore(), 8);
        assertEquals(0, g.enterWord("boppingtons")); // enters invalid word shouldn't change score
        assertEquals(g.getScore(), 8);
        Collection<String> enteredJawns = g.getWordsEntered();
        assertTrue(enteredJawns.contains("banana"));
        assertTrue(enteredJawns.contains("no"));
        assertFalse(enteredJawns.contains("boppingtons")); // only valid words recorded as entered
    }

    @Test
    public void testBoardCreation() {
        Boggle g = new Boggle();
        Boggle h = new Boggle();
        String[][] boardg = g.getBoard();
        String[][] boardh = h.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(boardg[i][j] != null); // each one has a letter
                assertFalse(boardg[i][j].equals(boardh[i][j])); // chance of true. Run repeat
                // if passes at least once we know there's some randomization
                // cuz the whole board will have to be different to pass ~(10/26) chance
            }
        }
    }

    @Test
    public void testReset() {
        Boggle g = new Boggle();
        g.enterWord("word");
        g.enterWord("this");
        String[][] boardBefore = g.getBoard();
        Collection<String> wordsBefore = g.getWordsEntered();
        assertEquals(8, g.getScore());
        g.reset();
        String[][] boardAfter = g.getBoard();
        Collection<String> wordsAfter = g.getWordsEntered();
        assertTrue(wordsBefore.contains("this"));
        assertTrue(wordsBefore.contains("word")); // checks that words were recorded right
        assertFalse(wordsAfter.contains("word")); // checks that words not recorded post reset
        assertFalse(wordsAfter.contains("this"));
        assertEquals(0, g.getScore()); // score should be zero post reset
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(boardAfter[i][j] != null && boardBefore[i][j] != null);
                assertFalse(boardBefore[i][j].equals(boardAfter[i][j])); // same as above
            }
        }
    }

}
