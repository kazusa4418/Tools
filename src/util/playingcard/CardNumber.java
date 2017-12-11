package util.playingcard;

/**
 * This enum represents the numbers in the playingcard.
 * <p>
 * Each numbers and its corresponding ordinal value match,
 * but JOKER only "ordinal is 0".
 * For example, the ordinal corresponding to "A" is 1 and
 * the ordinal corresponding to "K" is 13.
 * <p>
 * This enum is used to express the figures of playing cards,
 * and it is not recommended to use this enum in classes
 * unrelated to playing cards.
 *
 * @author kazusa4418
 * @see Enum
 * @see Card
 * @see CardSuit
 */
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

    /** Variable to store appearance of numbers */
    private String number;


    /**
     * Assign numeric appearance to the created instance.
     * For example, when "num11" is created, this constructor
     * stores the string"J" in the String type field.
     * When an instance is output to the screen, it outputs
     * the string stored here, so it is possible to output with
     * the same notation as the number of playingcard.
     *
     * @param number String displayed when outputting.
     */
    CardNumber(String number) {
        this.number = number;
    }

    /**
     * Obtains the string defined in the constructor.
     * <p>
     * If "num1", the string "A" is acquired,
     * and if "num5", the string "5" is acquired.
     *
     * @return The string defined int the constructor.
     */
    @Override
    public String toString() {
        return this.number;
    }
}
