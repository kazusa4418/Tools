package util.test;

import util.input.InputScanner;
import util.playingcard.Card;
import util.playingcard.CardStock;
import util.playingcard.Hand;

@SuppressWarnings("all")
public class TestClass {
    public static void main(String[] args) {
    }
    private void inputCheck() {
        InputScanner is = new InputScanner();
        is.setInMsg("入力 > ");
        is.setErrMsg("入力が不正です");

        String s = is.inputLineWithCheck()
    }
    private void playingcardCheck() {
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
