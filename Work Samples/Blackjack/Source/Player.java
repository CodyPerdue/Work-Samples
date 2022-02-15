package csc439team4.blackjack;

import java.util.logging.*;

/**
 * Player Object that contains a hand of cards and a number of chips
 * @author John Lorenzo, Cody Perdue
 */
public class Player {
    private Hand hand;
    private double chips;
    private double bet;
    private String action;
    private static final Logger logger = Logger.getLogger(Player.class.getName());

    /**
     * Default constructor
     */
    public Player() {
        logger.entering(getClass().getName(), "Player");

        logger.info("Creating a new player");
        this.hand = new Hand();
        this.chips = 0.0;

        logger.exiting(getClass().getName(), "Player");
    }

    /**
     * Gets the current hand for the player
     * @return current hand of the player
     */
    public Hand getHand(){
        logger.entering(getClass().getName(), "getHand");
        logger.info("Returning player's hand");
        logger.exiting(getClass().getName(), "getHand");
        return hand;
    }

    /**
     * Remove all cards from the player's hand
     */
    public void newHand() {
        logger.entering(getClass().getName(), "newHand");

        logger.info("Creating a new hand for the player");
        hand = new Hand();

        logger.exiting(getClass().getName(), "newHand");
    }

    /**
     * Gets the current amount of player chips
     * @return current amount of player chips
     */
    public double getChips(){
        logger.entering(getClass().getName(), "getChips");
        logger.info("Returning number of player's chips");
        logger.exiting(getClass().getName(), "getChips");
        return chips;
    }

    /**
     * Increases the amount of player chips
     * @param moreChips number of chips to add
     */
    public void increaseChips(double moreChips){
        logger.entering(getClass().getName(), "increaseChips");

        logger.info("Adding chips to the player");
        chips += moreChips;

        logger.exiting(getClass().getName(), "increaseChips");
    }

    /**
     * Decreases the amount of player chips
     * @param lessChips number of chips to subtract
     */
    public void decreaseChips(double lessChips){
        logger.entering(getClass().getName(), "decreaseChips");

        logger.info("Subtracting chips from the player");
        chips -= lessChips;

        logger.exiting(getClass().getName(), "decreaseChips");
    }

    /**
     * Adds a card to the player's hand
     * @param c a single card that is added to the player's hand
     */
    public void addCard(Card c){
        logger.entering(getClass().getName(), "addCard");

        logger.info("Adding a card to the player's hand");
        hand.addCard(c);

        logger.exiting(getClass().getName(), "addCard");
    }

    /**
     * Gets the bet amount of the player
     * @return bet amount of the player
     */
    public double getBet(){
        logger.entering(getClass().getName(), "getBet");
        logger.info("Returning player's bet");
        logger.exiting(getClass().getName(), "getBet");
        return bet;
    }

    /**
     * Sets the bet amount of the player
     * @param bet bet amount of the player
     */
    public void setBet(double bet){
        logger.entering(getClass().getName(), "setBet");

        logger.info("Setting player's bet");
        this.bet = bet;

        logger.exiting(getClass().getName(), "setBet");
    }

    /**
     * Gets the player's move choice
     * @return move choice for the player
     */
    public String getAction(){
        logger.entering(getClass().getName(), "getAction");
        logger.info("Returning player's action");
        logger.exiting(getClass().getName(), "getAction");
        return action;
    }

    /**
     * Sets the player's move choice
     * @param action move choice for the player
     */
    public void setAction(String action){
        logger.entering(getClass().getName(), "setAction");

        logger.info("Setting player's action");
        this.action = action;

        logger.exiting(getClass().getName(), "setAction");
    }
}
