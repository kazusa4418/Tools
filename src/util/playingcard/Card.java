package util.playingcard;

public class Card {
    /** Field that represent card figures */
    private final CardNumber number;
    /** Field that represents a suit of a card */
    private final CardSuit suit;

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

    public CardNumber getNum() {
        return this.number;
    }

    public CardSuit getSuit() {
        return this.suit;
    }

    public int strength() {
        return this.getNum().strength() * 10
                + this.getSuit().strength();
    }

    public boolean isJOKER() {
        return this.strength() == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card card = (Card) obj;
            return card.number
                    == this.number && card.suit == this.suit;
        } else return false;
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 31 * result * number.strength();
        result = 31 * result * suit.strength();
        return result;
    }

    @Override
    public String toString() {
        if (this.strength() == 0)
            return "(JOKER)";
        return String.format("(%s%s)", this.number, this.suit);
    }

    public String toString(boolean flag) {
        //trueを受け取るとカードの数字を、falseを受け取るとマークを返す
        return flag ? this.number.toString()
                    : this.suit.toString();
    }
}
