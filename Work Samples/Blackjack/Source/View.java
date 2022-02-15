package csc439team4.blackjack;

import java.io.IOException;

/**
 * Abstract View object that interacts with the user
 * @author Cody Perdue, John Lorenzo, Sagar Bhandari
 */
public abstract class View {
    /**
     * Prints a message, then requests input from the user
     * @param str message passed from the controller
     * @return input from user
     */
    public abstract String getInput(String str);

    /**
     * Prints a message to the screen
     * @param str message passed from the controller
     */
    public abstract void printMessage(String str);

    /**
     * Gets the number of chips to buy from the player
     * @return number of chips the player wants to buy
     */
    public abstract double getChips();

    /**
     * Gets the bet amount from the player
     * @param player the player
     * @return bet amount for the player
     */
    public abstract double getBet(Player player);

    /**
     * Gets the player's move choice
     * @param player the player
     * @return  move choice for the player
     */
    public abstract String getAction(Player player);

    /**
     * Prints the hand of both the player and the dealer
     * @param player the player
     * @param dealer the dealer
     * @param showDealer whether the dealer's first card is shown or not
     */
    public abstract void displayHand(Player player, Dealer dealer, boolean showDealer);
}
