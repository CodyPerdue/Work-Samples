package csc439team4.blackjack;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.*;

/**
 * Shoe class that is a collection of decks.
 * @author John Lorenzo, Cody Perdue
 */
public class Shoe {
    private ArrayList<Deck> shoe;
    private int startingCardCount;
    private static final Logger logger = Logger.getLogger(Shoe.class.getName());

    /**
     * Default Class constructor
     */
    public Shoe() {
        logger.entering(getClass().getName(), "Shoe");
        logger.info("Generating shoe with default constructor");

        shoe = new ArrayList<>();
        startingCardCount = 0;

        logger.info("Adding six decks to shoe by default");
        // generate 6 decks by default
        for(int i = 0; i < 6; i++){
            shoe.add(new Deck());
            startingCardCount += 52;
        }

        logger.exiting(getClass().getName(), "Shoe");
    }

    /**
     * Class constructor
     * @param numDecks the number of decks in the shoe
     */
    public Shoe(int numDecks){
        logger.entering(getClass().getName(), "Shoe");
        logger.info("Generating shoe with a specific number of decks");

        if(numDecks <= 0){
            logger.info("Invalid number of decks. Throwing exception.");
            throw new IllegalArgumentException();
        }

        shoe = new ArrayList<>();

        logger.info("Adding " + numDecks + " decks to shoe");
        for(int i = 0; i < numDecks; i++){
            shoe.add(new Deck());
            startingCardCount += 52;
        }

        logger.exiting(getClass().getName(), "Shoe");
    }

    /**
     * gets the number of decks in the shoe
     * @return number of decks in the shoe
     */
    public int numDecks(){
        logger.entering(getClass().getName(), "numDecks");
        logger.info("Returning number of decks in the shoe");
        logger.exiting(getClass().getName(), "numDecks");
        return this.shoe.size();
    }

    /**
     * gets the number of cards in the shoe
     * @return number of cards in the shoe
     */
    public int size(){
        logger.entering(getClass().getName(), "size");

        int total = 0;
        logger.info("Calculating size of the shoe");
        for(int i = 0; i < numDecks(); i++){
            total += shoe.get(i).size();
        }

        logger.info("Returning size of the shoe");
        logger.exiting(getClass().getName(), "size");
        return total;
    }

    /**
     * selects a random card from the shoe
     * @return randomly selected card from the shoe
     */
    public Card pick() {
        logger.entering(getClass().getName(), "pick");

        if (numDecks() <= 0) {
            logger.info("No decks available. Throwing exception.");
            throw new ArrayIndexOutOfBoundsException();
        }

        Random r = new Random();
        int randomDeck = r.nextInt(numDecks());

        logger.info("Selecting a random card from Deck # " + randomDeck);

        Card c = shoe.get(randomDeck).pick();

        if(shoe.get(randomDeck).size() == 0){
            logger.info("Removing Deck # " + randomDeck + ", out of cards. ");
            shoe.remove(randomDeck);
        }

        logger.info("Returning a random card from the shoe");
        logger.exiting(getClass().getName(), "pick");
        return c;
    }

    /**
     * Once card count is below a threshold, the shoe is repopulated to prevent cheating by card-counting
     */
    public void cut() {
        logger.entering(getClass().getName(), "cut");

        logger.info("Deciding if shoe needs to be cut");
        if (size() < startingCardCount / 5) {
            shoe.clear();
            startingCardCount = 0;

            logger.info("Cutting shoe. New shoe with 6 shuffled decks");

            // generate 6 decks by default
            for (int i = 0; i < 6; i++) {
                shoe.add(new Deck());
                startingCardCount += 52;
            }
        }

        logger.exiting(getClass().getName(), "cut");
    }

}
