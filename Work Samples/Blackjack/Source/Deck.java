package csc439team4.blackjack;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Deck class that contains all of the different playing cards in a standard deck (no jokers)
 * @author Cody Perdue
 */
public class Deck {
    private static final Logger logger = Logger.getLogger(Deck.class.getName());
    private ArrayList<Card> deck;

    /**
     * Class constructor
     */
    public Deck() {
        logger.entering(getClass().getName(), "Deck");
        logger.info("Generating deck with default constructor");
        this.deck = new ArrayList<>();

        // generate all cards, add to deck
        logger.info("Generates cards and adds it to deck");
        for (int i = 0; i < 52; i++) {
            int st = (i / 13) + 1;
            int nm = (i % 13) + 1;
            Card c = new Card(st, nm);
            this.deck.add(c);
        }
        logger.exiting(getClass().getName(), "Deck");
    }

    /**
     * selects random card from the deck
     * @return randomly selected card
     */
    public Card pick() {
        logger.entering(getClass().getName(), "pick");
        logger.info("Picks random card from the deck");
        logger.exiting(getClass().getName(), "pick");
        if (size() > 0) {
            Random r = new Random();
            return this.deck.remove(r.nextInt(size()));
        } else {
            throw new ArrayIndexOutOfBoundsException();

        }
    }

    /**
     * returns amount of remaining cards in the deck
     * @return size (amount of cards) of deck
     */
    public int size() {
        logger.entering(getClass().getName(), "size");
        logger.info("Returns the size of the deck");
        logger.exiting(getClass().getName(), "size");
        return this.deck.size();
    }
}
