package csc439team4.blackjack;

import java.util.logging.*;

/**
 * Controller object which contains all game logic
 * @author Cody Perdue, John Lorenzo, Sagar Bhandari
 */
public class Controller {
    private View view;
    private Player player;
    private Dealer dealer;
    private Shoe shoe;
    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    /**
     * Class constructor
     * @param view View object initialized in Main
     */
    public Controller(View view) {
        logger.entering(getClass().getName(), "Controller");

        logger.info("Assigning private values");
        this.view = view;
        this.player = new Player();
        this.dealer = new Dealer();
        this.shoe = new Shoe();

        logger.exiting(getClass().getName(), "Controller");
    }

    /**
     * Gets the current amount of player chips
     * @return current amount of player chips
     */
    public double getPlayerChips() {
        logger.entering(getClass().getName(), "getPlayerChips");
        logger.info("Returning player chips");
        logger.exiting(getClass().getName(), "getPlayerChips");
        return player.getChips();
    }

    /**
     * Starts the game of Blackjack
     */
    public void playBlackjack() {
        logger.entering(getClass().getName(), "playBlackjack");

        view.printMessage("Welcome to BlackJack! Type \"quit\" at any time to end the game.");

        // Prompts the user for how many chips they want to buy
        logger.info("Asking player buy-in");
        player.increaseChips(view.getChips());

        logger.info("Entering gameplay loop");
        logger.exiting(getClass().getName(), "playBlackjack");

        // Gameplay loop
        while (true) {
            logger.entering(getClass().getName(), "playBlackjack");

            // Reset player hands
            logger.info("Reset player hands");
            player.newHand();
            dealer.newHand();

            // Make sure player has enough chips to play
            logger.info("Making sure player has enough chips to play");
            if (getPlayerChips() < 10) {
                view.printMessage("\nYou have ran out of chips! Please purchase more.");
                player.increaseChips(view.getChips());
            }

            // Cut the shoe if necessary
            logger.info("Cutting shoe if necessary");
            shoe.cut();

            // Prompts the user for their bet
            logger.info("Prompting user for bet");
            player.setBet(view.getBet(player));

            // Subtracts the bet from player's chips
            logger.info("Subtracting bet from player's chips");
            player.decreaseChips(player.getBet());

            view.printMessage("\nRemaining chips " + getPlayerChips());

            // Deal two cards to the player and the dealer, then show player both hands
            logger.info("Initializing hands");

            //initHands();
            initHands();

            // Start playing the game
            logger.info("Starting round of Blackjack");

            logger.exiting(getClass().getName(), "playBlackjack");
            gameLoop();
        }

    }

    /**
     * Checks for a winner and if there isn't one prompts the player for a move
     */
    public void gameLoop(){
        logger.entering(getClass().getName(), "gameLoop");

        // Check if there's a winner and if not prompt the player for a move
        if(dealer.getHand().score() == 21 && player.getHand().score() != 21){
            logger.info("Dealer wins");
            view.displayHand(player, dealer, true);
            view.printMessage("\n --- The dealer has Blackjack! Sorry you lose. --- ");
        }
        else if(dealer.getHand().score() == 21 && player.getHand().score() == 21){
            logger.info("Dealer & Player draw");
            view.displayHand(player, dealer, true);
            view.printMessage("\n --- You and the dealer both have Blackjack. Push! --- ");
            player.increaseChips(player.getBet());
        }
        else if(player.getHand().score() == 21 && dealer.getHand().score() != 21){
            logger.info("Player wins");
            view.displayHand(player, dealer, true);
            view.printMessage("\n --- You have Blackjack. You win! ---");
            player.increaseChips(player.getBet() + player.getBet() * 1.5);
        }
        else{
            logger.info("Winner undecided, continuing game");
            view.displayHand(player, dealer, false);
            // Have the player take an action (hit, double, or stand)
            takeAction();
        }

        logger.exiting(getClass().getName(), "gameLoop");
    }

    /**
     * Starts the process of having the player take an action (hit, double, or stand)
     */
    public void takeAction() {
        logger.entering(getClass().getName(), "takeAction");

        logger.info("Obtaining action");
        player.setAction(view.getAction(player));
        String input = player.getAction();

        logger.info("Handling action");
        if (input.toUpperCase().equals("HIT")) {
            handHit();
        } else if (input.toUpperCase().equals("DOUBLE")) {
            handDouble();
        } else if (input.toUpperCase().equals("STAND")) {
            handStand();
        }

        logger.exiting(getClass().getName(), "takeAction");
    }

    /**
     * Has the player add a card to their hand, shows hands, and prompts further action
     */
    public void handHit() {
        logger.entering(getClass().getName(), "handHit");

        logger.info("Picked additional card");
        player.addCard(shoe.pick());

        if(player.getHand().score() > 21){
            logger.info("Player bust");
            view.displayHand(player, dealer, true);
            view.printMessage("\n --- Sorry you lose. You busted by going over 21.  --- ");
        }
        else if(player.getHand().score() == 21){
            logger.info("Player standing on 21");
            handStand();
        }
        else{
            logger.info("Game continuing");
            view.displayHand(player, dealer, false);
            takeAction();
        }

        logger.exiting(getClass().getName(), "handHit");
    }

    /**
     * Player's bet is doubled and they take one hit and then either busts or stands
     */
    public void handDouble() {
        logger.entering(getClass().getName(), "handDouble");

        logger.info("Doubling player bet and decreasing chip pool");
        player.decreaseChips(player.getBet());
        player.setBet(player.getBet() * 2);

        logger.info("Picking additional card");
        player.addCard(shoe.pick());
        if (player.getHand().score() > 21){
            logger.info("Player bust");
            view.printMessage("\n --- You got busted by going over 21. Sorry you lose! --- ");
        }
        else {
            logger.info("Player standing");
            handStand();
        }

        logger.exiting(getClass().getName(), "handDouble");
    }


    /**
     * Player stands and the dealer hits until 17 and then displays final hand
     */
    public void handStand() {
        logger.entering(getClass().getName(), "handStand");

        logger.info("Obtaining rest of dealer's hand");
        while(dealer.getHand().score() < 17){
            dealer.addCard(shoe.pick());
        }

        logger.info("Displaying dealer's full hand");
        view.displayHand(player, dealer, true);

        logger.info("Handling logic for game winner");
        if(dealer.getHand().score() > 21){
            view.printMessage("\n --- The dealer busted by going over 21. You win! --- ");
            player.increaseChips(player.getBet() * 2);
        }
        else if(player.getHand().score() > dealer.getHand().score()){
            view.printMessage("\n --- You have a higher score. You win! --- ");
            player.increaseChips(player.getBet() * 2);
        }
        else if(player.getHand().score() < dealer.getHand().score()){
            view.printMessage("\n --- You have a lower score. You lose! --- ");
        }
        else if(player.getHand().score() == dealer.getHand().score()){
            view.printMessage("\n --- You and the dealer have the same score. Push! ---");
            player.increaseChips(player.getBet());
        }

        logger.exiting(getClass().getName(), "handStand");
    }

    /**
     * Deal two cards to the player and the dealer
     */
    public void initHands() {
        logger.entering(getClass().getName(), "initHands");

        logger.info("Dealing player and dealer two cards");
        dealer.addCard(shoe.pick());
        player.addCard(shoe.pick());

        dealer.addCard(shoe.pick());
        player.addCard(shoe.pick());

        logger.exiting(getClass().getName(), "initHands");
    }

    /**
     * Deal two specific cards to the player and the dealer. Used for testing
     * @param p1 player's first card
     * @param p2 player's second card
     * @param d1 dealer's first card
     * @param d2 dealer's second card
     */
    public void initHands(Card p1, Card p2, Card d1, Card d2) {
        logger.entering(getClass().getName(), "initHands");

        logger.info("Dealing player and dealer two specific cards");
        dealer.addCard(d1);
        player.addCard(p1);

        dealer.addCard(d2);
        player.addCard(p2);

        logger.exiting(getClass().getName(), "initHands");
    }

    public void initHands(Hand pHand, Hand dHand) {
        logger.entering(getClass().getName(), "initHands");

        logger.info("Dealing player and dealer specific cards");
        for( Card c : pHand.getCards()){
            player.addCard(c);
        }

        for( Card c : dHand.getCards()){
            dealer.addCard(c);
        }

        logger.exiting(getClass().getName(), "initHands");
    }

}

