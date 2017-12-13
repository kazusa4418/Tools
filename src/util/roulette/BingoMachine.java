package util.roulette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* �߂��Ⴍ����R�[�h�������悤�ɂ݂��邾��H�H�H�H�H�H�H�H�H�H
 * ���͂قƂ�ǃR�[�h�̐�������۸��т͐��\�s���������ĂȂ��񂾂��B�B�B
 */

/**
 * BingoMachine�N���X�̓r���S�Q�[���ɂ�����r���S�{�[���������_���ɓf���o��
 * �@�B�̋@�\�Ɩ�����񋟂��܂��B
 * �R���X�g���N�^�Ŏw�肳�ꂽ���܂ł̊e������BingoBall�^�I�u�W�F�N�g��ێ����A
 * ���ꂼ��ɑ΂��Ď擾��폜�̑�����s�����Ƃ��\�ł��B
 */
public class BingoMachine {
    private int size;
    private List<BingoBall> balls = new ArrayList<>();

    /**
     * �^����ꂽ��������BingoMachine���쐬���܂��B
     * �����͂��̃N���X�̃C���X�^���X���ێ�����{�[���̐����w�肵�܂��B
     * �����A�����ɂQ�O���w�肳�ꂽ�̂ł���΁A���̃N���X�̕ێ�����
     * BingoBall�^�I�u�W�F�N�g��1~20�܂ł̊e������\������20�ł��B
     *
     * �����l�ł͊eBingoBall�^�I�u�W�F�N�g�͏����ɐ��񂳂�Ċi�[����܂��B
     *
     * @throws IllegalArgumentException ���̈������^����ꂽ�Ƃ�
     * @param number �ێ�����BingoBall�^�I�u�W�F�N�g�̐����w�肵�܂��B
     */
    public BingoMachine(int number) {
        //���ꂪ�R���X�g���N�^
        //���ꂪ�R���X�g���N�^
        //���ꂪ�R���X�g���N�^
        //���ꂪ�R���X�g���N�^
        //���ꂪ�R���X�g���N�^
        //���ꂪ�R���X�g���N�^
        if (number < 0) throw new IllegalArgumentException("�����̒l���s���ł��B");
        size = number;
        for (int i = 1; i <= number; i++ ) {
            balls.add(new BingoBall(i));
        }
    }

    /**
     * �ێ����Ă���BingoBall�^�I�u�W�F�N�g���V���b�t�����܂��B
     * �R���X�g���N�^��BingoBall�^�I�u�W�F�N�g���쐬���ꂽ�Ƃ��A
     * ���̕��т͏����Ő��񂳂�Ă��܂��B
     * ���������āA���̃N���X�̃C���X�^���X���쐬����Ă��̃��\�b�h��
     * �Ă΂ꂽ�Ƃ��A�����_���Ȑ����̋ʂ̏o�͂������\�ɂȂ�܂��B
     */
    public void shuffle() {
        Collections.shuffle(balls);
    }

    /**
     * �ێ����Ă���BingoBall�^�I�u�W�F�N�g���I�u�W�F�N�g�̎������̏����Ƀ\�[�g���܂��B
     */
    public void sortAsc() {
        /* �������ǂ݂Â炢�ł����g���ƕ֗��ȋL�@��p���Ă��܂��B
         * �����_��������Ȃ��Ǝ����ɂ��������Ȃ��Ǝv���܂��B
         * ���̋L�@�ɂ��Ă�����x�׋�����܂ł��̃R�[�h�ɂ͐G��Ȃ����Ƃ𐄏����܂��B
         */
        balls.sort((BingoBall ball1, BingoBall ball2) -> {
                        if (ball1.equals(ball2)) return 0;
                        return (ball1.getNumber() > ball2.getNumber()) ? 1 : -1; });
    }

    /**
     * �ێ����Ă���BingoBall�^�I�u�W�F�N�g���I�u�W�F�N�g�̎������̍~���Ƀ\�[�g���܂��B
     */
    public void sortDesc() {
        //�������B�B�B
        balls.sort((BingoBall ball1, BingoBall ball2) -> {
                        if(ball1.equals(ball2)) return 0;
                        return (ball1.getNumber() > ball2.getNumber()) ? -1 : 1; });
    }

    /**
     * �ێ����Ă���BingoBall�^�I�u�W�F�N�g���\�[�g���܂��B
     * �\�[�g��i�ɂ��Ă͌Ăяo�����������ŗ^���܂��B
     *
     * @param sorter Comparator<BingoBall>�����������\�[�g�N���X�B
     */
    public void sort(Comparator<BingoBall> sorter) {
        balls.sort(sorter);
    }

    /**
     * �ۑ�����Ă���BingoBall�^�I�u�W�F�N�g��List�Ŏ擾���܂��B
     * List�̗v�f�̓f�B�[�v�E�R�s�[����Ă��܂��B
     *
     * @return �ۑ�����Ă���BingoBall�^�I�u�W�F�N�g���i�[����Ă���List
     */
    public List<BingoBall> getBallList() {
        List<BingoBall> ballList = new ArrayList<>();
        try {
            for(BingoBall ball : balls) {
                ballList.add(ball.clone());
            }
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return ballList;
    }

    /**
     * �ێ�����Ă���BingoBall�^�I�u�W�F�N�g�̂Ȃ������v�f���擾���܂��B
     * �w�肳���v�f��\���C���f�b�N�X��0���w�肳��܂��B
     * �v�f���\�[�g����Ă���ꍇ�͗v�f���\�[�g���Ɏ��o���̂Ɏg�p�ł��܂����A
     * �V���b�t������Ă��܂��Ă���ꍇ�́A���o���v�f����肷�邱�Ƃ͂ł��܂���B
     *
     * �܂��́A�v�f�͎擾����邾���ł���A�폜�͂���܂���B
     * �폜���s�킸���̃��\�b�h��A���ŌĂяo���Ă��擾�����v�f�͓����ł��B
     *
     * @throws BallNotFoundException ����v�f�����݂��Ȃ��Ƃ�
     * @return 0�Ԗڂ̕ۑ�����Ă���BingoBall�^�I�u�W�F�N�g
     */

    public BingoBall getBall() {
        if (balls.size() == 0) throw new BallNotFoundException();
        return balls.get(0);
    }

    /**
     * �ێ�����Ă���BingoBall�^�I�u�W�F�N�g�̂Ȃ������v�f���擾���܂��B
     * �w�肳���v�f��\���C���f�b�N�X�͈����ŗ^�����܂��B
     * �v�f���\�[�g����Ă���ꍇ�́A����̈Ӑ}�����I�u�W�F�N�g���擾���邱�Ƃ�
     * �\�ł��B
     * �V���b�t����ł����{@link #getBall()}���\�b�h�ƂȂ��ς��͂���܂���B
     * ����́A�擾����v�f���C���f�b�N�X�Ŏw�肵���Ƃ��Ă��ŏ��̗v�f�����o�����Ƃ��Ă�
     * ���ʂ𐄑����邱�Ƃ��ł��Ȃ�����ł��B
     *
     * @param index ���o���v�f�̃C���f�b�N�X
     * @throws BallNotFoundException �w�肵���C���f�b�N�X�ɗv�f�����݂��Ȃ��Ƃ�
     * @return �擾����BingoBall�^�I�u�W�F�N�g
     */
    public BingoBall getBall(int index) {
        if (index < 0 || index >= balls.size())
            throw new BallNotFoundException();
        return balls.get(index);
    }

    /**
     * �ێ�����Ă���BingoBall�^�I�u�W�F�N�g�̂�������폜���܂��B
     * �w�肳���C���f�b�N�X��0�ł��B
     * �v�f���\�[�g����Ă���ꍇ�́A�\�[�g���ɗv�f���폜���邱�Ƃ��\�ł��B
     * �v�f���V���b�t������Ă���̂ł���΍폜����v�f�̓����_���Ɠ������Ȃ�܂��B
     *
     * @throws BallNotFoundException ����v�f�����݂��Ȃ��Ƃ�
     */
    public void removeBall() {
        if (balls.size() == 0) throw new BallNotFoundException();
        balls.remove(0);
    }

    /**
     * �ێ�����Ă���BingoBall�^�I�u�W�F�N�g�̂�������폜���܂��B
     * �����ō폜����v�f�̃C���f�b�N�X���w�肵�܂��B
     * �v�f���\�[�g����Ă���ꍇ�́A����̈Ӑ}�����I�u�W�F�N�g���폜���邱�Ƃ��\�ł��B
     * �V���b�t����ł���΁A{@link #removeBall()}���\�b�h�ƂȂ��ς��͂���܂���B
     * ����́A�폜����v�f���C���f�b�N�X�Ŏw�肵���Ƃ��Ă��ŏ��̗v�f���폜�����Ƃ��Ă�
     * ���ʂ𐄑����邱�Ƃ��ł��Ȃ�����ł��B
     *
     * @throws BallNotFoundException �w�肵���C���f�b�N�X�ɗv�f�����݂��Ȃ��Ƃ�
     * @param index �폜����v�f�̃C���f�b�N�X
     */
    public void removeBall(int index) {
        if (index < 0 || index >= balls.size()) throw new BallNotFoundException();
        balls.remove(index);
    }

    /**
     * ���̃C���X�^���X�����ꂽ�Ƃ���BingoBall�^�I�u�W�F�N�g�̕ێ���Ԃɕ������܂��B
     * �v�f���폜���ꂽ�肵����ł����̃��\�b�h���ĂԂ��Ƃŏ��������ꂽ��Ԃɖ߂����Ƃ��\�ł��B
     */
    public void initialize() {
        balls.clear();
        for (int i = 1; i <= size; i++ ) {
            balls.add(new BingoBall(i));
        }
    }
}
