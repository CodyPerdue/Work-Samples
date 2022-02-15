package csc439team4.blackjack;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void testHand() {
        Player p = new Player();

        Card c1 = new Card(3, 4);
        Card c2 = new Card(4, 10);

        p.addCard(c1);
        p.addCard(c2);

        assertEquals(p.getHand().getCards().get(0), c1);
        assertEquals(p.getHand().getCards().get(1), c2);
    }

    @Test
    public void testIncreaseChips(){
        Player p = new Player();

        p.increaseChips(100);

        assertEquals(100, p.getChips(), 0.01);
    }

    @Test
    public void testDecreaseChips(){
        Player p = new Player();

        p.increaseChips(100);
        p.decreaseChips(25);

        assertEquals(75, p.getChips(), 0.01);
    }

    @Test
    public void testNewHand() {
        Player p = new Player();
        Hand h = new Hand();

        // check hand has added card
        p.addCard(new Card(1, 1));
        h = p.getHand();
        assertEquals(1, h.size());

        // check for fresh hand
        p.newHand();
        h = p.getHand();
        assertEquals(0, h.size());
    }

    @Test
    public void testAction() {
        Player p = new Player();
        p.setAction("HIT");

        assertEquals("HIT", p.getAction());
    }
}
