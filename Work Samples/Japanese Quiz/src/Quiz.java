import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

/*
    This program generates a text-based quiz based on topics read from data files. This topic is split into at different
    subtopics with questions ranging from various different difficulty levels. These questions are then split into
    different types with a predetermined amount of questions per subtopic/level/type as defined by an input file. The
    quiz will give questions one at a time to the user, and the user's results will be presented both on screen as well
    as shared to an external file. This file will be sent to an algorithm to generate new quizzes tailored to the results.

    @author Cody Perdue
    @mentor Dr. Newell
    @class Honors Capstone Project
 */
public class Quiz {
    private static final int TOTAL_QUESTIONS = 480;     // total questions from input files
    private static final int NUM_SUBTOPICS = 5;         // amount of subtopics for the main topic
    private static final int NUM_LEVELS = 4;            // number of difficulty levels for questions
    private static final int NUM_TYPES = 4;             // amount of different types of questions (T/F, etc.)
    private static final int NUM_CATEGORIES = NUM_SUBTOPICS + NUM_LEVELS + NUM_TYPES;   // total amount of categories

    public static void main(String[] args) {
        //  array of all questions
        Question[] questions = new Question[TOTAL_QUESTIONS];

        // Create array that holds all topics->levels->types **ORDER IMPORTANT**
        Category[] categories = new Category[NUM_CATEGORIES];

        // obtain files     **NEEDS TO BE CHANGED FOR DIFFERENT SUBJECTS**
        File topic1 = new File("questions/Hiragana.txt");
        File topic2 = new File("questions/Katakana.txt");
        File topic3 = new File("questions/Radicals.txt");
        File topic4 = new File("questions/Kanji.txt");
        File topic5 = new File("questions/Vocabulary.txt");
        File numbers = new File("numbers.txt");

        // import information from files into arrays
        ImportNumbers(categories, numbers);
        ImportQuestions(questions, topic1, categories);
        ImportQuestions(questions, topic2, categories);
        ImportQuestions(questions, topic3, categories);
        ImportQuestions(questions, topic4, categories);
        ImportQuestions(questions, topic5, categories);

        // check if quiz can be made given category parameters, exit program if not
        CheckParameters(categories);

        // generate the quiz for the user to take
        Question[] quiz = GenerateQuiz(questions, categories);

        // present quiz to user
        TakeQuiz(quiz, categories);
    }

    /*
        This method imports the information for each subtopic/level/type
     */
    private static void ImportNumbers(Category[] categories, File file) {
        int n = 0;  // counter

        try {
            Scanner s = new Scanner(file).useDelimiter("\t|\r");
            for (int i = 0; i < NUM_CATEGORIES; i++) {
            //while (s.hasNext()) {
                // create new category
                String name = s.next();
                name = name.replace("\n", "");  // remove escape
                int numQuest = parseInt(s.next());
                Category c = new Category(numQuest, 0, name);

                // store new category in array
                categories[n] = c;

                n++;
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: File not found.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*
        This method imports questions from a given file and stores them into the question array.
     */
    private static void ImportQuestions(Question[] questionArray, File file, Category[] categories) {
        // find first open spot in question array
        int n = 0;
        while (questionArray[n] != null)
            n++;

        // populate array with questions from file
        try {
            Scanner s = new Scanner(file).useDelimiter("\t|\n");
            while (s.hasNext()) {
                // gather info from question
                String subtopic = s.next();
                String question = s.next();
                String answer = s.next();
                String level = s.next();
                String type = s.next();
                type = type.replace("\r", "");  // remove escape

                // associate subtopic/level/type with their appropriate Category objects
                Category subtopicCat = getCategory(subtopic, categories);
                Category levelCat = getCategory(level, categories);
                Category typeCat = getCategory(type, categories);

                // add info to Question class, add Question to array
                Question q = new Question(question, answer, subtopicCat, levelCat, typeCat);
                questionArray[n] = q;

                n++;
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: File not found.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*
        This method checks the parameters given to the categories to see if a valid quiz can be made.
        If not, an error message is shown and the program exits.
     */
    private static void CheckParameters(Category[] categories) {
        int subtopicCount = 0;
        int levelCount = 0;
        int typeCount = 0;

        // assign categories to their correct subcategory array
        for (int i = 0; i < categories.length; i++) {
            if (i < NUM_SUBTOPICS)
                subtopicCount += categories[i].getNumQuestions();
            else if (i < NUM_SUBTOPICS + NUM_LEVELS)
                levelCount += categories[i].getNumQuestions();
            else
                typeCount += categories[i].getNumQuestions();
        }

        // end program if question count not equal for each subcategory
        if (!((subtopicCount == levelCount) && (subtopicCount == typeCount))) {
            System.out.println("An error occurred: Subcategory question count unequal.");
            System.exit(1);
        }
    }

    /*
        This method generates the quiz by randomly selecting questions from the question array with aspects such
        as size and amount-per-type decided by constants declared at the top of Main.
     */
    private static Question[] GenerateQuiz(Question[] questionArray, Category[] categories) {
        // calculate quiz size by counting questions from each subject
        int quizSize = 0;
        for (int i = 0; i < NUM_SUBTOPICS; i++)
            quizSize += categories[i].getNumQuestions();

        // initialize variables
        Question[] quiz = new Question[quizSize];
        int[] toBeAdded = new int[quizSize];
        int questionCounter = 0;
        Random r = new Random();

        // find questions to be added to the quiz
        boolean allQuestionsAdded = false;
        while(!allQuestionsAdded) {
            // grab a random question and check its criteria
            int qToAdd = r.nextInt(TOTAL_QUESTIONS);    // select random question
            Question q = questionArray[qToAdd];

            boolean alreadyAdded = contains(toBeAdded, qToAdd);    // check if question is not already added to quiz
            boolean topicCheck = q.getSubtopic().getNumAdded() < q.getSubtopic().getNumQuestions();
            boolean levelCheck = q.getLevel().getNumAdded() < q.getLevel().getNumQuestions();
            boolean typeCheck = q.getType().getNumAdded() < q.getType().getNumQuestions();

            // add question if it fulfills needed criteria for quiz
            if (topicCheck && levelCheck && typeCheck && !alreadyAdded) {
                toBeAdded[questionCounter] = qToAdd;

                // increment counters for quiz and categories
                q.getSubtopic().incrementNumAdded();
                q.getLevel().incrementNumAdded();
                q.getType().incrementNumAdded();
                questionCounter++;
            }

            // check if all criteria for quiz is fulfilled
            allQuestionsAdded = true;
            for (Category cat : categories)
                if (cat.getNumAdded() < cat.getNumQuestions()) {
                    allQuestionsAdded = false;
                    break;
                }
        }

        // add marked questions to quiz
        for (int i = 0; i < quizSize; i++) {
            for (int j = 0; j < 4; j++) {
                quiz[i] = questionArray[toBeAdded[i]];
            }
        }

        return quiz;
    }

    /*
        This method presents the quiz to the user by giving a question one at a time that the user must answer. After
        finishing all of the questions, the results will be displayed on the screen as well as sent to an external file.
     */
    private static void TakeQuiz(Question[] quiz, Category[] categories) {
        int totalCorrect = 0;

        System.out.println("こんにちは！Thank you for taking this Japanese quiz! You will be given " + quiz.length +
                " questions during the duration of this quiz. All answers should be in romaji (English characters). " +
                "がんばれ!");

        // Present questions one at a time to user
        Scanner s = new Scanner(System.in);
        for (Question question : quiz) {
            boolean correct = false;

            System.out.println("\n" + question.getQuestion());     // show question
            if (s.nextLine().equals(question.getAnswer()))    // check if user answer is correct
                correct = true;

            if (correct) {      // if correct, increment counters
                System.out.println("Correct!");
                totalCorrect++;
                question.getSubtopic().incrementNumCorrect();
                question.getLevel().incrementNumCorrect();
                question.getType().incrementNumCorrect();
            } else {
                System.out.println("Incorrect. The correct answer is: " + question.getAnswer());
            }
        }
        s.close();

        // Print results to user
        System.out.println("\nおめでとう！The quiz is over! Here are your results:");
        System.out.println("\tTotal score: " + totalCorrect + "/" + quiz.length);
        for (Category cat : categories)
            System.out.println("\t" + cat.getName() + ": " + cat.getNumCorrect());

        // Print results to data file
        try {
            PrintStream o = new PrintStream(new File("results.txt"));
            System.setOut(o);
            System.out.println(NUM_SUBTOPICS + "\t" + NUM_LEVELS + "\t" + NUM_TYPES);   // print amount of categories
            for (Category cat : categories)     // print amount of questions per category
                System.out.println(cat.getName() + "\t" + cat.getNumQuestions() + "\t" + cat.getNumCorrect());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: File not found.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*
        This method checks if an array of ints already contains a specific int value and returns the answer as a boolean
     */
    private static boolean contains(int[] array, int num) {
        for (int i : array) {
            if (i == num) {
                return true;
            }
        }

        return false;
    }

    /*
        This method associates a category to the passed in String
     */
    private static Category getCategory(String toCategorize, Category[] categories) {
        for (Category category : categories)
            if (toCategorize.equals(category.getName()))
                return category;

        // failed to find a category, abort run
        System.out.println("An error occurred: Category unable to be matched.");
        System.exit(1);
        return new Category(0, 0, "");  // necessary for code to run
    }
}