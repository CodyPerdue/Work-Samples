package csc439team4.blackjack;

import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {
    @Test
    public void testChipInput() {
        TestView view = new TestView();

        Player player = new Player();
        player.increaseChips(5000);

        view.setChips(5000);

        assertEquals(5000, player.getChips(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidChipInput() {
        TestView view = new TestView();
        Controller controller = new Controller(view);

        view.setChips(5);
        controller.playBlackjack();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBetInput() {
        TestView view = new TestView();
        Controller controller = new Controller(view);

        view.setChips(-5000);
        controller.playBlackjack();
    }

    @Test
    public void testHandHit() {
        TestView view = new TestView();
        view.setAction("HIT");

        Controller controller = new Controller(view);
        controller.initHands(
                new Card(1, 2), new Card(1, 2),     // player
                new Card(1, 2), new Card(1, 2));    // dealer

        controller.gameLoop();
    }

    @Test
    public void testHandDouble() {
        TestView  view = new TestView();
        view.setAction("DOUBLE");

        Controller controller = new Controller(view);
        controller.initHands(
                new Card(1, 2), new Card(1, 2),     // player
                new Card(1, 2), new Card(1, 2));    // dealer

        controller.gameLoop();
    }

    @Test
    public void testHandStand() {
        TestView view = new TestView();
        view.setAction("STAND");

        Controller controller = new Controller(view);
        controller.initHands(
                new Card(1, 2), new Card(1, 2),     // player
                new Card(1, 2), new Card(1, 2));    // dealer

        controller.gameLoop();
    }

    @Test
    public void testDealerBlackjack() {
        TestView view = new TestView();

        Controller controller = new Controller(view);
        controller.initHands(
                new Card(1, 2), new Card(1, 2),     // player
                new Card(1, 1), new Card(1, 13));    // dealer

        controller.gameLoop();
    }

    @Test
    public void testBothBlackjack() {
        TestView view = new TestView();

        Controller controller = new Controller(view);
        controller.initHands(
                new Card(2, 1), new Card(2, 12),     // player
                new Card(1, 1), new Card(1, 13));    // dealer

        controller.gameLoop();
    }

    @Test
    public void testPlayerBlackjack() {
        TestView view = new TestView();

        Controller controller = new Controller(view);
        controller.initHands(
                new Card(2, 1), new Card(2, 12),     // player
                new Card(1, 2), new Card(1, 13));    // dealer

        controller.gameLoop();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testQuit() {
        TestView view = new TestView();
        Player player = new Player();

        player.increaseChips(500);
        player.setBet(50);
        player.setAction("QUIT");
        view.setChips(500);
        view.setBet(50.0, player);

        view.setAction("QUIT");
        Controller controller = new Controller(view);

        controller.playBlackjack();
    }

    @Test
    public void testStandDealerBust(){
        TestView view = new TestView();

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.addCard(new Card(1, 4));
        pHand.addCard(new Card(1, 10));

        dHand.addCard(new Card(1, 10));
        dHand.addCard(new Card(1, 10));
        dHand.addCard(new Card(1, 10));


        Controller controller = new Controller(view);
        controller.initHands(pHand, dHand);
        controller.handStand();
    }

    @Test
    public void testStandWin(){
        TestView view = new TestView();

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.addCard(new Card(1, 10));
        pHand.addCard(new Card(1, 9));

        dHand.addCard(new Card(1, 10));
        dHand.addCard(new Card(1, 8));

        Controller controller = new Controller(view);
        controller.initHands(pHand, dHand);
        controller.handStand();
    }

    @Test
    public void testStandLose(){
        TestView view = new TestView();

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.addCard(new Card(1, 10));
        pHand.addCard(new Card(4, 8));

        dHand.addCard(new Card(3, 12));
        dHand.addCard(new Card(1, 9));

        Controller controller = new Controller(view);
        controller.initHands(pHand, dHand);
        controller.handStand();
    }

    @Test
    public void testStandPush(){
        TestView view = new TestView();

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.addCard(new Card(1, 10));
        pHand.addCard(new Card(4, 8));

        dHand.addCard(new Card(3, 12));
        dHand.addCard(new Card(1, 8));

        Controller controller = new Controller(view);
        controller.initHands(pHand, dHand);
        controller.handStand();
    }

    @Test
    public void testDoubleBust(){
        TestView view = new TestView();

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.addCard(new Card(1, 6));
        pHand.addCard(new Card(4, 6));
        pHand.addCard(new Card(4, 11));

        dHand.addCard(new Card(3, 12));
        dHand.addCard(new Card(1, 8));

        Controller controller = new Controller(view);
        controller.initHands(pHand, dHand);
        controller.handDouble();
    }

    @Test
    public void testHit(){
        TestView view = new TestView();
        view.setAction("HIT");

        for(int i = 0; i< 20; i++) {
            Controller controller = new Controller(view);
            controller.initHands();
            controller.handHit();
        }
    }
}