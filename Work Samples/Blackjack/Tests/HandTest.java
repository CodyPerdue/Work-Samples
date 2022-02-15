package csc439team4.blackjack;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;


public class HandTest {

    @Test
    public void addCardTest() {
        Card c = new Card(2, 6);
        Hand h = new Hand();
        h.addCard(c);
        ArrayList<Card> handTest = new ArrayList<>();
        handTest.add(c);
        assertEquals(h.getCards(), handTest);
    }

    @Test
    public void getCardsTest() {
        Card c1 = new Card(2,6);
        Card c2 = new Card(1,8);
        Hand h = new Hand();
        h.addCard(c1);
        h.addCard(c2);
        ArrayList<Card> handTest = new ArrayList<>();
        handTest.add(c1);
        handTest.add(c2);
        assertEquals(h.getCards(), handTest);

    }

    @Test
    public void sizeTest() {
        Card c1 = new Card(2,6);
        Card c2 = new Card(1,8);
        Hand hand = new Hand();
        hand.addCard(c1);
        hand.addCard(c2);
        ArrayList<Card> handTest = new ArrayList<>();
        handTest.add(c1);
        handTest.add(c2);
        assertEquals(hand.size(), handTest.size());
    }


    @Test (expected = IllegalArgumentException.class)
    public void addCardArgumentTest(){
        Hand h = new Hand();
        h.addCard(new Card(8,4));
    }

    @Test
    public void scoreTest() {
        // with aces
        Hand h = new Hand();
        h.addCard(new Card(1, 1));
        h.addCard(new Card(2, 1));
        assertEquals(12, h.score());

        // with a face card
        h = new Hand();
        h.addCard(new Card(1, 13));
        h.addCard(new Card(1, 5));
        assertEquals(15, h.score());
    }

}
