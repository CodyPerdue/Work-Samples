// The category class holds info for each category (all subtopics, levels, and types), along with getter & increment methods
public class Category {
    private final int numQuestions;
    private int numCorrect;
    private int numAdded = 0;
    private final String name;

    // Class Constructor
    public Category(int numQuestions, int numCorrect, String name) {
        this.numQuestions = numQuestions;
        this.numCorrect = numCorrect;
        this.name = name;
    }

    // Increment number of questions correct by one
    public void incrementNumCorrect() {
        numCorrect++;
    }

    // Increment number of question added by one
    public void incrementNumAdded() {
        numAdded++;
    }

    // Getters
    public int getNumQuestions() {
        return numQuestions;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public String getName() {
        return name;
    }

    public int getNumAdded() {
        return numAdded;
    }

}
