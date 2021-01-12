
public class Person { // just assigns a score and name together to replace a tuple
    private int score;
    private String name;

    public Person(String name1, int score1) {
        score = score1;
        name = name1;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
