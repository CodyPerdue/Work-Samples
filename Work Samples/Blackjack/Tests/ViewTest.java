package csc439team4.blackjack;

import org.junit.Test;

import static org.junit.Assert.*;

public class ViewTest {
    @Test
    public void testChips() {
        TestView view = new TestView();

        view.setChips(5000);

        assertEquals(5000, view.getChips(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidChips() {
        TestView view = new TestView();

        view.setChips(-100);
        view.getChips();
    }

    @Test
    public void testBet() {
        TestView view = new TestView();
        Player player = new Player();

        player.increaseChips(5000);
        view.setBet(450, player);

        assertEquals(450, view.getBet(player), 0.01);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidBet() {
        TestView view = new TestView();
        Player player = new Player();

        view.setBet(4, player);
        view.getBet(player);
    }

    @Test
    public void testGetInput() {
        TestView view = new TestView();

        assertEquals("Hello", view.getInput("Hello"));
    }

    @Test
    public void testDisplayHand() {
        TestView view = new TestView();
        Player p = new Player();
        Dealer d = new Dealer();

        p.addCard(new Card(1, 1));
        p.addCard(new Card(2, 1));
        d.addCard(new Card(3, 1));
        d.addCard(new Card(4, 1));

        view.displayHand(p, d, false);
        view.displayHand(p, d, true);
    }

    @Test
    public void testPrintMessage() {
        TestView view = new TestView();

        view.printMessage("Hello World!");
    }

    @Test
    public void testAction() {
        TestView view = new TestView();

        Player player = new Player();
        player.setAction("HIT");
        view.setAction("HIT");

        assertEquals("HIT", view.getAction(player));
    }
}