package csc439team4.blackjack;

import java.util.logging.*;

/**
 * Card class that represents a playing card with a suit and number
 * @author Sagar Bhandari, John Lorenzo, Cody Perdue
 * @version 1.0
 */
public class Card {
    private int suit;
    private int number;
    private static final Logger logger = Logger.getLogger(Card.class.getName());

    /**
     * Class constructor
     *
     * @param suit   the card's suit (1 = Hearts, 2 = Diamonds, 3 = Spades, 4 = Clubs)
     * @param number the card's number (Ace = 1, 2-10, Jack = 11, Queen = 12, King = 13)
     */
    public Card(int suit, int number) {
        logger.entering(getClass().getName(), "Card");

        if (suit >= 1 && suit <= 4 && number >= 1 && number <= 13) {
            logger.info("Card received legal variables. Assigning variables.");
            this.suit = suit;
            this.number = number;
        } else {
            logger.info("Card received illegal variables. Throwing exception.");
            throw new IllegalArgumentException();
        }

        logger.exiting(getClass().getName(), "Card");
    }

    /**
     * Returns the card's number and suit as a string
     * @return the card's number and suit as a string
     */
    public String toString() {
        logger.entering(getClass().getName(), "toString");

        String str = "\t";
        logger.info("Adding number to string");
         switch (this.number) {
             case 1 :
                 str += "Ace";
                 break;
             case 11 :
                 str += "Jack";
                 break;
             case 12 :
                 str += "Queen";
                 break;
             case 13 :
                 str += "King";
                 break;
             default:
                str += this.number;
        }

        str = str + " of ";
        logger.info("Adding suit to string");
         switch (this.suit) {
             case 1 :
                 str += "Hearts";
                 break;
             case 2 :
                 str += "Diamonds";
                 break;
             case 3 :
                 str += "Spades";
                 break;
             default :
                 str += "Clubs";
                 break;
        }

        logger.exiting(getClass().getName(), "toString");
        return str;
    }

    /**
     * Getter for suit
     * @return value for suit
     */
    public int getSuit () {
        logger.entering(getClass().getName(), "getSuit");
        logger.info("Returning value for suit");
        logger.exiting(getClass().getName(), "getSuit");
        return suit;
    }

    /**
     * Getter for number
     * @return value for number
     */
    public int getNumber () {
        logger.entering(getClass().getName(), "getNumber");
        logger.info("Returning value for number");
        logger.exiting(getClass().getName(), "getNumber");
        return number;
    }
}


