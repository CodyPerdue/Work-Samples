package csc439team4.blackjack;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {
    Deck d = new Deck();

    @Test
    public void testPick() {
        for (int i = 0; i < 52; i++) {
            Card c = d.pick();
            int st = c.getSuit();
            int nm = c.getNumber();
            assertTrue(st >= 1 && st <= 4 && nm >= 1 && nm <= 13);
        }
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testPickEmptyDeck() {
        // pick all cards from deck
        for (int i = 0; i < 52; i++) {
            d.pick();
        }

        // attempt to pick again
        d.pick();
    }

    @Test
    public void testSize() {
        // pick 5 cards
        for (int i = 0; i < 5; i++) {
            d.pick();
        }

        // test size
        assertEquals(47, d.size());
    }
}