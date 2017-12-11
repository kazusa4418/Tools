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
     * �����̓J�[�h�̐����ƃ}�[�N���w�肷��̂Ɏg���܂��B
     *
     * @param number - �J�[�h�̐������w�肷��CardNumber�^�I�u�W�F�N�g
     * @param suit   - �J�[�h�̃}�[�N���w�肷��CardSuit�^�I�u�W�F�N�g
     */
    public Card(CardNumber number, CardSuit suit) {
        this.number = number;
        this.suit = suit;
    }

    /**
     * �w�肳�ꂽ�l����Card�^�I�u�W�F�N�g���쐬���܂��B
     * �����͐����ƃ}�[�N���w�肷��̂Ɏg�p���܂����������w�肷��Ȃ��0~13�A
     * �}�[�N���w�肷��Ȃ��0~4�܂ł̐��l�����󂯕t���܂���B
     * ����ȊO�̐������͂��ꂽ�ꍇ��O����������̂ň�ʂɂ͎g�p���Ȃ��ł��������B
     *
     * @param number - �J�[�h�̐������w�肷��int�^�ϐ� 0-13�܂Ŏg�p��0��JOKER
     * @param suit   - �J�[�h�̃}�[�N���w�肷��int�^�ϐ� 0-4�܂Ŏg�p��4��JOKER
     * @throws java.lang.ArrayIndexOutOfBoundsException �͈͊O�̒l�������Ɏw�肳�ꂽ�Ƃ��ɔ������܂��B
     * @deprecated �͈͊O�̈������^������Ɛ����������ł��܂���B
     *             �ʂ̃R���X�g���N�^�[���g�p���邱�Ƃ𐄏����܂��B
     */
    public Card(int number, int suit) {
        CardNumber[] numbers = CardNumber.values();
        CardSuit[] suits = CardSuit.values();
        //�^����ꂽ�����̂ǂ��炩��JOKER���������̂ł������ꍇJOKER�J�[�h�����
        //����ȊO�ł���Η^����ꂽ�������琔���ƃ}�[�N���������J�[�h�C���X�^���X�����
        if (numbers[number].ordinal() == 0 || suits[suit].ordinal() == 4) {
            this.number = CardNumber.JOKER;
            this.suit = CardSuit.JOKER;
        } else {
            this.number = numbers[number];
            this.suit = suits[suit];
        }
    }

    /**
     * �J�[�h�̐�����CardNumber�^�ŕԂ��܂��B
     *
     * @return ���̃C���X�^���X�̎���CardNumber�^��Ԃ��܂��B
     */
    public CardNumber getNumber() {
        return this.number;
    }

    /**
     * �J�[�h�̃}�[�N��CardSuit�^�ŕԂ��܂��B
     *
     * @return ���̃C���X�^���X�̎���CardSuit�^��Ԃ��܂��B
     */
    public CardSuit getSuit() {
        return this.suit;
    }

    /**
     * ���̃C���X�^���X�Ǝw�肳�ꂽCard�^�C���X�^���X���r���܂��B
     *
     * @param card - ��r����Card�^�C���X�^���X�ł��B
     * @return ���݂��̃C���X�^���X������̂��̂ł����true��Ԃ��܂��B
     */
    boolean equals(Card card) {
        return card.number == this.number && card.suit == this.suit;
    }

    /**
     * �J�[�h�̐����ƃ}�[�N��\��String�^�I�u�W�F�N�g��Ԃ��܂��B
     *
     * @return �J�[�h�̐����ƃ}�[�N���J���}�ŋ�؂��u()�v�ň͂܂ꂽ��Ԃŕ\������܂��B
     */
    @Override
    public String toString() {
        if (this.suit == CardSuit.JOKER) {
            return "(" + this.suit + ")";
        }
        return "(" + this.number + "," + this.suit + ")";
    }

    /**
     * �J�[�h�̐������}�[�N��\��String�^�I�u�W�F�N�g��Ԃ��܂��B
     * �����͐����ƃ}�[�N�̂ǂ����\�����邩���w�肵�܂��B
     * true�Ȃ�ΐ������Afalse�Ȃ�΃}�[�N���w�肵�܂��B
     *
     * @param flag - �����ƃ}�[�N�ǂ��炩���w�肷��boolean
     * @return �w�肳�ꂽ�\���ł�String�^�I�u�W�F�N�g
     */
    public String toString(boolean flag) {
        //true���󂯎��ƃJ�[�h�̐������Afalse���󂯎��ƃ}�[�N��Ԃ�
        if (flag) {
            return "(" + this.number + ")";
        } else {
            return "(" + this.suit + ")";
        }
    }
}
