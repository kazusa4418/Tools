package util.roulette;

import util.playingcard.CardNumber;

public class TestClass {
    public static void main(String[] args) {
        //�����ł̓��[���b�g�p�b�P�[�W�ɑ�����r���S�֘A�N���X�̃e�X�g���s���Ɠ�����
        //���̎g�p���@�ɂ��Đ������܂��B
        //�����ŏЉ�Ă��郁�\�b�h�̂ق��ɂ����\�b�h������܂��B
        //�N���X���������Ēǉ���������ł��܂��B
        //����A�\�[�X�t�@�C�����̂����Ă݂Ă��������B

        //�r���S������̂Ɏg���r���S�}�V�[���I�Ȃ��̂��C���X�^���X��
        //������BingoMachine�N���X�̎��������Ă��������B
        //���Ȃ݂ɃR���X�g���N�^�Ȃ̂ŃR���X�g���N�^�Ə����Ă���Ƃ���̃��\�b�h�I�Ȃ��̂����ĂˁB
        BingoMachine bm = new BingoMachine(20);

        //�r���S�}�V�[���̂Ȃ��̃{�[�����V���b�t������
        bm.shuffle();

        //�r���S�}�V�[���̂Ȃ��̃{�[��������o��
        BingoBall ball = bm.getBall();
        bm.removeBall(); //�����ł͎擾�����r���S�{�[�����}�V���̂Ȃ�����폜���Ă�

        //���o�����{�[������ʂɏo�͂���
        System.out.println(ball);

        //���o�����{�[���Ȃǂ����ׂă}�V���ɖ߂��ă}�V�����C���X�^���X�������Ƃ��̏�Ԃɖ߂�
        bm.initialize();

        CardNumber.values();

    }
}
