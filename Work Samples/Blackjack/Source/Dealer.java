package csc439team4.blackjack;

import java.util.logging.Logger;

/**
 * Dealer Object that contains a hand of cards
 * @author John Lorenzo, Cody Perdue, Sagar Bhandari
 */
public class Dealer {
    private Hand hand;
    private static final Logger logger = Logger.getLogger(Dealer.class.getName());

    /**
     * Default constructor
     */
    public Dealer(){
        logger.entering(getClass().getName(), "Dealer");
        logger.info("Generating the hand");
        hand = new Hand();
        logger.exiting(getClass().getName(), "Dealer");
    }

    /**
     * Gets the current hand for the dealer
     * @return current hand of the dealer
     */
    public Hand getHand(){
        logger.entering(getClass().getName(), "getHand");
        logger.info("Gets the current hand of dealer and returns it");
        logger.exiting(getClass().getName(), "getHand");
        return hand;

    }

    /**
     * Remove all cards from the dealer's hand
     */
    public void newHand() {
        logger.entering(getClass().getName(), "newHand");
        logger.info("Removes the card from the dealer's hand");
        logger.exiting(getClass().getName(), "newHand");
        hand = new Hand();
    }

    /**
     * Adds a card to the dealer's hand
     * @param c a single card that is added to the dealer's hand
     */
    public void addCard(Card c){
        logger.entering(getClass().getName(), "addCard");
        logger.info("Adding a card to the dealer's hand ");
        logger.exiting(getClass().getName(), "addCard");
        hand.addCard(c);
    }
}
