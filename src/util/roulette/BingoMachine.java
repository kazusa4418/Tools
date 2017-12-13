package util.roulette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BingoMachine�N���X�̓r���S�Q�[���ɂ�����r���S�{�[���������_���ɓf���o��
 * �@�B�̋@�\�Ɩ�����񋟂��܂��B
 * �R���X�g���N�^�Ŏw�肳�ꂽ���܂ł̊e������BingoBall�^�I�u�W�F�N�g��ێ����A
 * ���ꂼ��ɑ΂��Ď擾��폜�̓�����s�����Ƃ��\�ł��B
 */
public class BingoMachine {
    private List<BingoBall> balls = new ArrayList<>();

    /**
     * �^����ꂽ��������BingoMachine���쐬���܂��B
     * �����͂��̃N���X�̃C���X�^���X���ێ�����{�[���̐����w�肵�܂��B
     * �����A�����ɂQ�O���w�肳�ꂽ�̂ł���΁A���̃N���X�̕ێ�����
     * BingoBall�^�I�u�W�F�N�g��1~20�܂ł̊e������\������20�ł��B
     *
     * �����l�ł͊eBingoBall�^�I�u�W�F�N�g�͏����ɐ��񂳂�Ċi�[����܂��B
     *
     * @param number �ێ�����BingoBall�^�I�u�W�F�N�g�̐����w�肵�܂��B
     */
    public BingoMachine(int number) {
        if (number < 0) throw new IllegalArgumentException("�����̒l���s���ł��B");
        for (int i = 1; i <= number; i++ ) {
            balls.add(new BingoBall(i));
        }
    }

    /**
     * �ێ����Ă���BingoBall�^�I�u�W�F�N�g���V���b�t�����܂��B
     * �R���X�g���N�^��BingoBall�^�I�u�W�F�N�g���쐬���ꂽ�Ƃ��A
     * ���̕��т͏����Ő��񂳂�Ă��܂��B
     * ���������āA���̃N���X�̃C���X�^���X���쐬����Ă������̃��\�b�h��
     * �Ă΂ꂽ�Ƃ��A�����_���Ȑ����̋ʂ̏o�͂������\�ɂȂ�܂��B
     */
    public void shuffle() {
        Collections.shuffle(balls);
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
     * @throws Index
     * @return 0�Ԗڂ̕ۑ�����Ă���BingoBall�^�I�u�W�F�N�g
     */
    public BingoBall getBall() {
        return balls.get(0);
    }

    /**
     * �ێ�����Ă���BingoBall�^�I�u�W�F�N�g�̂Ȃ������v�f���擾���܂��B
     * �w�肳���v�f��\���C���f�b�N�X�͈����ŗ^�����܂��B
     * �v�f���\�[�g����Ă���ꍇ�́A����̈Ӑ}�����I�u�W�F�N�g���擾���邱�Ƃ�
     * �\�ł��B
     * �V���b�t����ł����{@link #getBall()}���\�b�h�ƂȂ��ς��͂���܂���B
     * �擾����v�f���C���f�b�N�X�Ŏw�肵���Ƃ��Ă��ŏ��̗v�f�����o�����Ƃ��Ă�
     * ���ʂ𐄑����邱�Ƃ͂ł��܂���B
     *
     * @param index ���o���v�f�̃C���f�b�N�X
     * @return �擾����BingoBall�^�I�u�W�F�N�g
     */
    public BingoBall getBall(int index) {
        return balls.get(index);
    }

    /**
     * �ێ�����Ă���BingoBall�^�I�u�W�F�N�g�̂�������폜���܂��B
     * �w�肳���C���f�b�N�X��0���w�肳��܂��B
     * �v�f���\�[�g����Ă���ꍇ�́A�\�[�g���ɗv�f���폜���邱�Ƃ��\�ł��B
     * �v�f���V���b�t������Ă���̂ł���΍폜����v�f�̓����_���ɂȂ�܂��B
     *
     */
    public void removeBall() {
        balls.remove(0);
    }

    public void removeBall(int index) {
        balls.remove(index);
    }
}
