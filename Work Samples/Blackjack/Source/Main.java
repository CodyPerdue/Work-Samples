package csc439team4.blackjack;

import java.util.logging.Logger;

/**
 * Runs a game of Blackjack, a card game where the player attempts to beat the dealer by having a sum of cards
 * closest to 21 without going over. Includes betting mechanics similar to that of a casino.
 * @author Sagar Bhandari, John Lorenzo, Cody Perdue
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Main method of program. Initializes the game
     *
     * @param args required main argument
     */
    public static void main(String[] args) {
        logger.entering(Main.class.getName(), "Main");
        logger.info("Entering main");

        // Create View (CLIView)
        View view = new CLIView();

        // Create Controller
        Controller controller = new Controller(view);

        // Start the game
        controller.playBlackjack();

        logger.info("Exiting main");
        logger.exiting(Main.class.getName(), "Main");
    }
}


