package csc439team4.blackjack;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * A Hand Class that creates hand object initialized with an empty collection of cards.
 * @author Sagar Bhandari, Cody Perdue
 */
public class Hand {
    private static final Logger logger = Logger.getLogger(Hand.class.getName());
    private ArrayList<Card> hand;

    /**
     * Creates default constructor for Hand() resulting in empty hand.
     */
    public Hand() {
        logger.entering(getClass().getName(), "Hand");
        logger.info("Generating the hand with default constructor");
        hand = new ArrayList<Card>();
        logger.exiting(getClass().getName(), "Hand");
    }

    /**
     * Adds a card to the Hand
     * @param c as a single card that is added to the hand
     */
    public void addCard(Card c){
        logger.entering(getClass().getName(), "addCard");
        logger.info("Adding the card");
        hand.add(c);
        logger.exiting(getClass().getName(), "addCard");
    }

    /**
     * Returns entire Hand of the player
     * @return ArrayList as entire hand
     */
    public ArrayList<Card> getCards(){
        logger.entering(getClass().getName(), "getCards");
        logger.info("Returns entire hand of the player");
        logger.exiting(getClass().getName(), "getCards");
        return hand;

    }

    /**
     * Returns the size of the Hand
     * @return int as size
     */
    public int size(){
        logger.entering(getClass().getName(), "size");
        logger.info("Returning the size of hand");
        logger.exiting(getClass().getName(), "size");
        return hand.size();


    }

    /**
     * Calculates the score of the given hand, with aces counting as either 1 or 11 as necessary
     * @return the total score of the hand
     */
    public int score() {
        logger.entering(getClass().getName(), "score");
        logger.info("Calculating the score of the hand");
        int score = 0;
        int aceCount = 0;
        logger.info("Assigning the card number");
        for (Card card : getCards()) {
            if (card.getNumber() == 1) {
                score += 11;
                aceCount++;
            } else if (card.getNumber() >= 10) {
                score += 10;
            } else {
                score += card.getNumber();
            }
        }

        // if score is too high and there are aces, switch an ace from 11 -> 1
        while (score > 21 && aceCount >= 1) {
            score -= 10;
            aceCount--;
        }
        logger.exiting(getClass().getName(), "score");
        return score;
    }
}



