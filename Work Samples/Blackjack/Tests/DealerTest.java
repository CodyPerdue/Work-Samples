package csc439team4.blackjack;

import org.junit.Test;

import static org.junit.Assert.*;

public class DealerTest {

    @Test
    public void testHand() {
        Dealer d = new Dealer();

        Card c1 = new Card(2, 7);
        Card c2 = new Card(1, 2);

        d.addCard(c1);
        d.addCard(c2);

        assertEquals(d.getHand().getCards().get(0), c1);
        assertEquals(d.getHand().getCards().get(1), c2);
    }

    @Test
    public void testNewHand() {
        Dealer d = new Dealer();
        Hand h = new Hand();

        // check hand has added card
        d.addCard(new Card(1, 1));
        h = d.getHand();
        assertEquals(1, h.size());

        // check for fresh hand
        d.newHand();
        h = d.getHand();
        assertEquals(0, h.size());
    }
}