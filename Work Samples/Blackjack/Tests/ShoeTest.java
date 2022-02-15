package csc439team4.blackjack;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShoeTest {
    Shoe s = new Shoe(10);

    @Test
    public void testPick() {
        for (int i = 0; i < 520; i++) {
            Card c = s.pick();
            int st = c.getSuit();
            int nm = c.getNumber();
            assertTrue(st >= 1 && st <= 4 && nm >= 1 && nm <= 13);
        }
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testPickEmptyShoe() {
        // pick all cards from the shoe
        for (int i = 0; i < 520; i++) {
            s.pick();
        }

        // attempt to pick again
        s.pick();
    }

    @Test
    public void testNumDecks() {
        // pick all but one card from the shoe
        for(int i = 0; i < 519; i++){
            s.pick();
        }

        // there should only be one deck remaining
        assertEquals(1, s.numDecks());
    }

    @Test
    public void testDefaultNumDecks() {
        Shoe ds = new Shoe();
        assertEquals(6, ds.numDecks());
    }

    @Test
    public void testSize() {
        // pick 20 cards
        for(int i = 0; i < 20; i++){
            s.pick();
        }

        // test size
        assertEquals(500, s.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidShoe() {
        Shoe s = new Shoe(0);
    }

    @Test
    public void testShoeCut() {
        Shoe s = new Shoe();

        // cut does not occur
        for (int i = 0; i < 52; i++)
            s.pick();

        s.cut();
        assertEquals(52*5, s.size());

        // cut does occur
        for (int i = 0; i < (52*4); i++)
            s.pick();

        s.cut();
        assertEquals(52*6, s.size());
    }
}
