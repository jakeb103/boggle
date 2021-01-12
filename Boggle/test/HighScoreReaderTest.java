import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HighScoreReaderTest {

    @Test
    void testReaderParsesForIntInComplexString() {
        HighScoreReader reader = 
                new HighScoreReader("/Users/jake/eclipse-workspace/Boggle/files/test.txt");
        Person p = new Person("ya boi", 0);
        assertEquals("h63 has nums before", reader.getTopFive()[0].getName());
        assertEquals(p.getName(), reader.getTopFive()[1].getName());
    }

    @Test
    void testWrongFileThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HighScoreReader("not a file");
        });
    }

}
