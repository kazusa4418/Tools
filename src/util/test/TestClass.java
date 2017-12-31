package util.test;

import util.playingcard.Card;
import util.playingcard.CardStock;
import util.playingcard.Hand;

@SuppressWarnings("all")
public class TestClass {
    public static void main(String[] args) {
        Hand hand = new Hand();
        hand.add(new Card(1, 1));
        hand.add(new Card(2, 1));
        hand.add(new Card(3, 2));
        hand.add(new Card(0, 0));
        hand.add(new Card(12, 1));

        hand.sort();
        hand.show();

        CardStock cs = new CardStock();
        cs.initialize();
        Card card = cs.takeCard();
        cs.shuffle();

        hand.get(0).getNum();
        hand.get(0).getSuit();
        hand.get(0).strength();
    }
}
