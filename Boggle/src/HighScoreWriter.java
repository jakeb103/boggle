import java.io.*;
import java.nio.file.Paths;

public class HighScoreWriter {
    BufferedWriter bw;

    public HighScoreWriter(String filePath) {
        File file = Paths.get(filePath).toFile();
        try {
            bw = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            System.out.println("Messed up in writer constructor");
            e.printStackTrace();
        }
    }

    public void write(String name, int score) {
        try {
            bw.newLine(); // write each entry on its own line
            bw.write(name + "&" + score); // Ampersand differentiator
            bw.close(); // close here and require new writer every time you write
                        // need that because otherwise there is no natural place to close the writer
                        // during the game
        } catch (IOException e) {
            System.out.println("Messed up in write method");
            e.printStackTrace();
        }
    }

}
