import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.jupiter.api.Test;

class HighScoreWriterTest {
    HighScoreWriter writer = new 
            HighScoreWriter("/Users/jake/eclipse-workspace/Boggle/files/testOutput.txt");
    HighScoreReader reader = new 
            HighScoreReader("/Users/jake/eclipse-workspace/Boggle/files/testOutput.txt");
    //uses a common writer for all tests to the same location

    @Test
    void testOutPutlooksGoodandIsInterpretedRight() { //gonna check how it looks and if it reads
        writer.write("homie", 89); 
        writer = new 
                HighScoreWriter("/Users/jake/eclipse-workspace/Boggle/files/testOutput.txt");
        writer.write("Jake", 6);
        writer = new 
                HighScoreWriter("/Users/jake/eclipse-workspace/Boggle/files/testOutput.txt");
        writer.write("Let's make some weird chars :)", 0);
        writer = new 
                HighScoreWriter("/Users/jake/eclipse-workspace/Boggle/files/testOutput.txt");
        writer.write("only 4 to play with top5", 40);
        Map<String, Person> peopleRecorded = reader.getMap();
        Collection<String> names = new TreeSet<String>();
        for (Person p : peopleRecorded.values()) {
            names.add(p.getName());
        }
        assertTrue(names.contains("homie"));
        assertTrue(names.contains("Jake"));
        assertEquals(peopleRecorded.get("Let's make some weird chars :)").getScore(), 0);
        assertEquals(reader.getTopFive().length, 5);
    }

}
