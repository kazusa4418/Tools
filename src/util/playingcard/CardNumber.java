package util.playingcard;

public enum CardNumber {
    JOKER("JOKER"),      //ordinal is 0
    num1("A"),           //ordinal is 1
    num2("2"),           //ordinal is 2
    num3("3"),           //ordinal is 3
    num4("4"),           //ordinal is 4
    num5("5"),           //ordinal is 5
    num6("6"),           //ordinal is 6
    num7("7"),           //ordinal is 7
    num8("8"),           //ordinal is 8
    num9("9"),           //ordinal is 9
    num10("10"),         //ordinal is 10
    num11("J"),          //ordinal is 11
    num12("Q"),          //ordinal is 12
    num13("K");          //ordinal is 13

    private final String number;

    CardNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }

    public int strength() {
        return ordinal();
    }
}
