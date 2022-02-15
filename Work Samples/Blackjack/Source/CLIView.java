package csc439team4.blackjack;

import java.util.Scanner;
import java.util.logging.*;

/**
 * View object that interacts with the user through the Command Line Interface
 * @author Cody Perdue, John Lorenzo
 */
public class CLIView extends View {
    Scanner scn = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(CLIView.class.getName());

    /**
     * Prints a message, then requests input from the user
     * @param str message passed from the controller
     * @return input from user
     */
    @Override
    public String getInput(String str) {
        logger.entering(getClass().getName(), "getInput");

        System.out.println("\n" + str);
        System.out.print("> "); // arrow for user input for readability
        String input = scn.nextLine();
        if(input.toUpperCase().equals("QUIT")){
            logger.info("Player quit, ending program");
            System.exit(0);
        }

        logger.info("Returning player input");
        logger.exiting(getClass().getName(), "getInput");
        return input;
    }

    /**
     * Prints a message to the Command Line Interface
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
     * Gets the number of chips to buy from user input
     * @return number of chips the player wants to buy
     */
    @Override
    public double getChips() {
        logger.entering(getClass().getName(), "getChips");

        double chips = 0.0;
        try {
            String input = getInput("How many chips would you like to buy?");
            chips = Double.parseDouble(input);
            if (chips < 10) {
                logger.info("Player entered invalid number of chips. Throwing exception.");
                throw new IllegalArgumentException();
            }
        }
        catch(Exception e){
            System.out.println("Please enter a number 10 or greater.");
            logger.info("Exception caught. Recalling getChips().");
            chips = getChips();
        }

        logger.exiting(getClass().getName(), "getChips");
        return chips;
    }

    /**
     * Gets the bet amount for the player from user input
     * @return bet amount for the player
     */
    @Override
    public double getBet(Player player) {
        logger.entering(getClass().getName(), "getBet");

        double bet = 0.0;

        try {
            System.out.println("\nCurrent chips : \t " + player.getChips());
            String input = getInput("How many chips would you like to bet?");
            bet = Double.parseDouble(input);

            if (bet < 10 || bet > 500 || bet > player.getChips()) {
                logger.info("Player entered invalid bet. Throwing exception.");
                throw new IllegalArgumentException();
            }
        }
        catch(Exception e){
            System.out.println("Please enter a bet between 10-500 and less than your total chips.");
            logger.info("Exception caught. Recalling getBet().");
            bet = getBet(player);
        }

        logger.exiting(getClass().getName(), "getBet");
        return bet;
    }

    /**
     * Gets the player's move choice
     * @param player the player
     * @return  move choice for the player
     */
    @Override
    public String getAction(Player player) {
        logger.entering(getClass().getName(), "getAction");

        boolean doubleAllowed;

        int lowAceScore = 0;
        for (Card card: player.getHand().getCards()) {
            if (card.getNumber() == 1) {
                lowAceScore += 1;
            } else if (card.getNumber() >= 10) {
                lowAceScore += 10;
            } else {
                lowAceScore += card.getNumber();
            }
        }

        logger.info("Assigning \"doubleAllowed\"");
        if (player.getHand().size() == 2 && lowAceScore >= 9 && lowAceScore<= 11)
            doubleAllowed = true;
        else
            doubleAllowed = false;

        logger.info("Sending request for action");
        String input;
        if (doubleAllowed)
            input = getInput("Would you like to \"hit\", \"double\", or \"stand\"?");
        else
            input = getInput("Would you like to \"hit\" or \"stand\"?");

        logger.info("Returning based on request");
        logger.exiting(getClass().getName(), "getAction");
        if (input.toUpperCase().equals("HIT")) {
            return "HIT";
        } else if (input.toUpperCase().equals("DOUBLE") && doubleAllowed) {
            return "DOUBLE";
        } else if (input.toUpperCase().equals("STAND")) {
            return "STAND";
        } else {
            printMessage("Sorry, that was an incorrect input.");
            return getAction(player);
        }
    }

    /**
     * Displays the player's cards and dealer's card where the first card is hidden
     * @param player object
     * @param dealer object
     * @param showDealer whether the dealer's first card is shown or not
     */
    @Override
    public void displayHand(Player player, Dealer dealer, boolean showDealer){
        logger.entering(getClass().getName(), "displayHand");

        logger.info("Showing dealer's final hand");
        if(showDealer){
            System.out.println("\n--- Final Hand ---");
        }

        logger.info("Showing player's hand");
        System.out.println("\nPlayer's Hand:");
        for (Card card: player.getHand().getCards())
            System.out.println(card);

        logger.info("Showing dealer's hand with hidden card");
        System.out.println("Dealer's Hand:");
        for (int i = 0; i < dealer.getHand().getCards().size(); i++){
            if(showDealer)
                System.out.println(dealer.getHand().getCards().get(i));
            else if ( i > 0)
                System.out.println(dealer.getHand().getCards().get(i));
            else
                System.out.println("\tA card is not visible");
        }

        logger.exiting(getClass().getName(), "displayHand");
    }


}




