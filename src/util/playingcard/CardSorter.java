package util.playingcard;

import java.util.Comparator;

public class CardSorter implements Comparator<Card> {
    public int compare(Card card1, Card card2) {
        if (card1.isJOKER())
            return 1;
        if (card2.isJOKER())
            return -1;
        return Integer.compare(card1.strength(), card2.strength());
    }
}
