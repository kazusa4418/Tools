package util.playingcard;

/**
 * This class is a class that realizes a playing card.
 * <p>
 * An instance represents one card, each holding
 * the numbers and suits on the card.
 * <p>
 * In many cases it is created automatically when you create
 * an instance of the CardStock class rather than creating
 * an instance of this class directly.
 *
 * @author kazusa4418
 * @see CardNumber
 * @see CardSuit
 * @see CardStock
 */
public class Card {
    /** Field that represent card figures */
    private final CardNumber number;
    /** Field that represents a suit of a card */
    private final CardSuit suit;


    /**
     * Create a Card type object.
     * <p>
     * The argument specifies the number and suit of the object to be created.
     * <p>
     *
     * 引数はカードの数字とマークを指定するのに使われます。
     *
     * @param number - カードの数字を指定するCardNumber型オブジェクト
     * @param suit   - カードのマークを指定するCardSuit型オブジェクト
     */
    public Card(CardNumber number, CardSuit suit) {
        checkArgument(number, suit);
        this.number = number;
        this.suit = suit;
    }

    private void checkArgument(CardNumber number, CardSuit suit) {
        if ((number.equals(CardNumber.JOKER) && !suit.equals(CardSuit.JOKER)) ||
                (!number.equals(CardNumber.JOKER) && suit.equals(CardSuit.JOKER))) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 指定された値からCard型オブジェクトを作成します。
     * 引数は数字とマークを指定するのに使用しますが数字を指定するならば0~13、
     * マークを指定するならば0~4までの数値しか受け付けません。
     * それ以外の数が入力された場合例外が発生するので一般には使用しないでください。
     *
     * @param number - カードの数字を指定するint型変数 0-13まで使用し0はJOKER
     * @param suit   - カードのマークを指定するint型変数 0-4まで使用し0はJOKER
     * @throws java.lang.ArrayIndexOutOfBoundsException 範囲外の値が引数に指定されたときに発生します。
     * @deprecated 範囲外の引数が与えられると正しく処理できません。
     *             別のコンストラクターを使用することを推奨します。
     */
    public Card(int number, int suit) {
        checkArgument(number, suit);
        CardNumber[] numbers = CardNumber.values();
        CardSuit[] suits = CardSuit.values();
        this.number = numbers[number];
        this.suit = suits[suit];
    }

    private void checkArgument(int number, int suit) {
        if (number == 0 && suit == 0) return;
        if (number < 1 || suit < 1 || number > 13 || suit > 4)
            throw new IllegalArgumentException();
    }

    /**
     * カードの数字をCardNumber型で返します。
     *
     * @return このインスタンスの持つCardNumber型を返します。
     */
    public CardNumber getNumber() {
        return this.number;
    }

    /**
     * カードのマークをCardSuit型で返します。
     *
     * @return このインスタンスの持つCardSuit型を返します。
     */
    public CardSuit getSuit() {
        return this.suit;
    }

    /**
     * このインスタンスと指定されたCard型インスタンスを比較します。
     *
     * @param card - 比較するCard型インスタンスです。
     * @return お互いのインスタンスが同一のものであればtrueを返します。
     */
    public boolean equals(Card card) {
        return card.number == this.number && card.suit == this.suit;
    }

    /**
     * カードの数字とマークを表すString型オブジェクトを返します。
     *
     * @return カードの数字とマークがカンマで区切られ「()」で囲まれた状態で表現されます。
     */
    @Override
    public String toString() {
        if (this.suit == CardSuit.JOKER) {
            return "(" + this.suit + ")";
        }
        return "(" + this.number + "," + this.suit + ")";
    }

    /**
     * カードの数字かマークを表すString型オブジェクトを返します。
     * 引数は数字とマークのどちらを表現するかを指定します。
     * trueならば数字を、falseならばマークを指定します。
     *
     * @param flag - 数字とマークどちらかを指定するboolean
     * @return 指定された表現でのString型オブジェクト
     */
    public String toString(boolean flag) {
        //trueを受け取るとカードの数字を、falseを受け取るとマークを返す
        return flag ? "(" + this.number + ")" : "(" + this.suit + ")";
    }
}
