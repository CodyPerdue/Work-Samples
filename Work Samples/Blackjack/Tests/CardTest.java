package csc439team4.blackjack;

import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void testGetSuit() {
        Card c = new Card(1, 1);
        assertEquals(1, c.getSuit());
    }

    @Test
    public void testGetNumber() {
        Card c = new Card(1, 1);
        assertEquals(1, c.getNumber());
    }

    @Test
    public void testValidCard() {
        Card c = new Card(2, 3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidSuit() {
        Card c = new Card(0, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidNumber() {
        Card c = new Card(1, 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidSuitAndNumber() {
        Card c = new Card(0, 0);
    }

    @Test
    public void testToString() {
        Card c1 = new Card (3, 13);
        Card c2 = new Card(1, 1);
        Card c3 = new Card(2, 7);
        Card c4 = new Card(4, 11);
        Card c5 = new Card(4, 12);


        assertEquals("\tKing of Spades", c1.toString());
        assertEquals("\tAce of Hearts", c2.toString());
        assertEquals("\t7 of Diamonds", c3.toString());
        assertEquals("\tJack of Clubs", c4.toString());
        assertEquals("\tQueen of Clubs", c5.toString());
    }
}