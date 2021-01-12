import java.io.*;
import java.util.*;

public class HighScoreReader {
    Map<String, Person> scores;
    BufferedReader br;

    public HighScoreReader(String filePath) {
        scores = new TreeMap<String, Person>();
        String s = "";
        int score = 0;
        br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            s = br.readLine(); // similar functionality to fileLineIterator
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (IOException e) {
            System.out.println("OOOF");
            s = null;
        }
        while (s != null) {
            if (!s.isEmpty() && s.contains("&")) { // so that we don't hit errors
                String[] personAndScore = s.split("&"); // with blanks
                String scoreString = "";
                for (int i = 0; i < personAndScore[1].length(); i++) { // score always stored second
                    char x = personAndScore[1].charAt(i);
                    if (x == '0' || x == '1' || x == '2' || x == '3' || x == '4' || x == '5' 
                            || x == '6' || x == '7' || x == '8' || x == '9') {
                        scoreString += x;
                    } // scoreString is for the purpose of parsing mixed Strings like Ja64k2e
                }
                if (!scoreString.isEmpty()) {
                    score = Integer.parseInt(scoreString);
                }
                String dude = personAndScore[0];
                if (scores.containsKey(dude)) {
                    dude = dude + "'"; // so that people don't overWrite high scores in map
                }
                Person person = new Person(dude, score);
                scores.put(dude, person);
            }
            try {
                s = br.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("YIKES");
                s = null;
            }
        }
        try {
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static Person getTop(Map<String, Person> jawn) { // helper for getTop5
        int max = 0;
        Person maxP = null;
        if (jawn.values() == null) {
            return null;
        }
        for (Person p : jawn.values()) {
            if (p.getScore() >= max) {
                max = p.getScore();
                maxP = p;
            }
        }
        return maxP;
    }

    private Map<String, Person> removeThing(Map<String, Person> jawn, Person p) { // second helper
        Map<String, Person> homies = new TreeMap<String, Person>();
        for (Person p1 : jawn.values()) {
            if (!p1.getName().equals(p.getName())) {
                homies.put(p1.getName(), p1); // add everyone except the one to be removed
            }
        }
        return homies;
    }

    public Person[] getTopFive() {
        int score = 0;
        String s;
        try {
            s = br.readLine();
        } catch (IOException e) {
            s = null;
        }

        while (s != null) { // this whole while loop is taken from above. Honestly you can skip over
            if (!s.isEmpty() && s.contains("&")) { // needed again for multiple read
                String[] personAndScore = s.split("&"); // during same game
                String scoreString = "";
                for (int i = 0; i < personAndScore[1].length(); i++) {
                    char x = personAndScore[1].charAt(i);
                    if (x == '0' || x == '1' || x == '2' || x == '3' || x == '4' || x == '5'
                            || x == '6' || x == '7' || x == '8' || x == '9') {
                        scoreString += x;
                    }
                }
                if (!scoreString.isEmpty()) {
                    score = Integer.parseInt(scoreString);
                }
                String dude = personAndScore[0];
                if (scores.containsKey(dude)) {
                    dude = dude + "'";
                }
                Person person = new Person(dude, score);
                scores.put(dude, person);
            }
            try {
                s = br.readLine();
            } catch (IOException e) {
                s = null;
            }
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Person[] thing = new Person[5];
        Map<String, Person> jawn = new TreeMap<String, Person>();
        for (Person p : scores.values()) {
            jawn.put(p.getName(), p); // made so that things don't get weird when we edit it
        }
        for (int i = 0; i < 5; i++) {
            thing[i] = getTop(jawn);
            jawn = removeThing(jawn, getTop(jawn));
        }
        return thing;

    }

    public Map<String, Person> getMap() { // made for testing purposes
        Map<String, Person> jawn = new TreeMap<String, Person>();
        for (Person p : scores.values()) {
            jawn.put(p.getName(), p);
        }
        return jawn;
    }
}
