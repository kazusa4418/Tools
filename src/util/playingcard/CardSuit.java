package util.playingcard;

public enum CardSuit {
    JOKER("JOKER"),        //ordinal is 0
    SPADE("♠"),     //ordinal is 1
    HEART("♥"),       //ordinal is 2
    DIAMOND("♦"),     //ordinal is 3
    CLOVER("♣");  //ordinal is 4

    private String suit;

    CardSuit(String suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return this.suit;
    }

    public int strength() {
        return this.ordinal();
    }
}
