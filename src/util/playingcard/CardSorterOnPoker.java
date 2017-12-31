package util.playingcard;

import java.util.Comparator;

/**
 * CardSorterOnPokerはListなどでソートを行う際にソート方法を指定します。
 * ソート順序はポーカーのルールに従ってカードの強弱を比較しソートします。
 *
 * @author kazusa4418
 * @see Card
 * @see Comparator
 */
public class CardSorterOnPoker implements Comparator<Card> {
    /**
     * 受け取った2つのCard型インスタンスを比較します。
     *
     * @param card1 - 比較するCard型インスタンスです。
     * @param card2 - 比較されるCard型インスタンスです。
     * @return 比較するインスタンスが大小によって0, 1, -1 のいずれかが返却されます。
     */
    public int compare(Card card1, Card card2) {
        if (card1.getNum() == CardNumber.JOKER)
            return 1;
        if (card2.getNum() == CardNumber.JOKER)
            return -1;
        if (card1.getNum() == CardNumber.num1)
            return 1;
        if (card2.getNum() == CardNumber.num1)
            return -1;
        return Integer.compare(card1.strength(), card2.strength());
    }
}
