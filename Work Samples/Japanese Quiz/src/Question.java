// The question class holds all of the relevant info for all of the imported questions, and getter methods to obtain them
public class Question {
    private final String question;
    private final String answer;
    private final Category subtopic;
    private final Category level;
    private final Category type;

    // Class Constructor
    public Question(String question, String answer, Category subtopic, Category level, Category type) {
        this.question = question;
        this.answer = answer;
        this.subtopic = subtopic;
        this.level = level;
        this.type = type;
    }

    // Getters
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Category getSubtopic() {
        return subtopic;
    }

    public Category getLevel() {
        return level;
    }

    public Category getType() {
        return type;
    }

}
