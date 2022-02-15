package csc439team4.blackjack;

import java.util.logging.*;

/**
 * View object that is used to manually input user responses for observing test cases
 * @author Cody Perdue, John Lorenzo, Sagar Bhandari
 */
public class TestView extends View {
    private double chips;
    private double bet;
    private String action;
    private static final Logger logger = Logger.getLogger(TestView.class.getName());

    /**
     * Prints a message, then requests input from the user
     * @param str message passed from the controller
     * @return input from user
     */
    @Override
    public String getInput(String str) {
        logger.entering(getClass().getName(), "getInput");

        if(str.toUpperCase().equals("QUIT")){
            logger.info("Test player quits, ending program");
            throw new IllegalArgumentException();
        }

        logger.info("Returning test player input");
        logger.exiting(getClass().getName(), "getInput");
        return str;
    }

    /**
     * Prints a message to the console
     * @param str message passed from the controller
     */
    @Override
    public void printMessage(String str) {
        logger.entering(getClass().getName(), "printMessage");

        logger.info("Printing message");
        System.out.println(str);

        logger.exiting(getClass().getName(), "printMessage");
    }

    /**
     * Gets the number of chips to buy from the player
     * @return number of chips the player wants to buy
     */
    @Override
    public double getChips() {
        logger.entering(getClass().getName(), "getChips");

        if(chips < 0){
            logger.info("Invalid number of chips. Throwing exception.");
            throw new IllegalArgumentException();
        }

        System.out.println(" > $ " + chips);

        logger.info("Returning number of test player's chips");
        logger.exiting(getClass().getName(), "getChips");
        return chips;
    }

    /**
     * Updates the players total chips
     * @param chips number of chips to add
     */
    public void setChips(double chips){
        logger.entering(getClass().getName(), "setChips");

        logger.info("Setting number of test player's chips");
        this.chips = chips;

        logger.exiting(getClass().getName(), "setChips");
    }

    /**
     * Gets the bet amount from the player
     * @return bet amount for the player
     */
    @Override
    public double getBet(Player player) {
        logger.entering(getClass().getName(), "getBet");

        if(bet < 10 || bet > 500 || bet > player.getChips()){
            logger.info("Invalid bet. Throwing exception.");
            throw new IllegalArgumentException();
        }

        System.out.println(" > $ " + bet);

        logger.info("Returning test player's bet");
        logger.exiting(getClass().getName(), "getBet");
        return player.getBet();
    }

    /**
     * Updates the bet amount for the player
     * @param bet new bet amount for the player
     */
    public void setBet(double bet, Player player){
        logger.entering(getClass().getName(), "setBet");

        logger.info("Setting test player's bet");
        this.bet = bet;
        player.setBet(bet);

        logger.exiting(getClass().getName(), "setBet");
    }

    /**
     * Test method for debugging purpose.
     * @param player to obtain player's hand
     * @param dealer to obtain dealer's hand
     * @param showDealer whether the dealer's first card is shown or not
     */
    public void displayHand(Player player, Dealer dealer, boolean showDealer){
        logger.entering(getClass().getName(), "displayHand");
        logger.info("Displaying both player's hands");


        if(showDealer){
            logger.info("Displaying final hand of the game. All cards are visible");
            System.out.println("\n---Final Hand---");
        }

        logger.info("Showing player's hand");
        System.out.println("Player's Hand:");
        for (Card card: player.getHand().getCards())
            System.out.println(card.toString());

        logger.info("Showing dealer's hand");
        System.out.println("Dealer's Hand:");
        for (int i = 0; i < dealer.getHand().getCards().size(); i++) {
            if (showDealer)
                System.out.println(dealer.getHand().getCards().get(i));
            else if (i > 0)
                System.out.println(dealer.getHand().getCards().get(i));
            else
                System.out.println("\tA card is not visible");
        }

        logger.exiting(getClass().getName(), "displayHand");
    }

    /**
     * Gets the player's move choice
     * @param player the player
     * @return  move choice for the player
     */
    @Override
    public String getAction(Player player) {
        logger.entering(getClass().getName(), "getAction");

        logger.info("Getting test player's next move");
        getInput(action);

        logger.info("Returning test player's next move");
        logger.exiting(getClass().getName(), "getAction");
        return action;
    }

    /**
     * Sets the move choice for the player
     * @param action new action for the player
     */
    public void setAction(String action){
        logger.entering(getClass().getName(), "setAction");

        logger.info("Setting test player's next move");
        this.action = action;

        logger.exiting(getClass().getName(), "setAction");
    }

}


