package util.roulette;

class BingoBall implements Cloneable {
    //�C���X�^���X�����{�[���̐���
    private int number;

    //�C���X�^���X�������Ƃ��Ɉ����Ń{�[���̐������w�肷��
    BingoBall(int number) {
        this.number = number;
    }

    //���̃C���X�^���X�̎��{�[���̐������擾����
    int getNumber() {
        return number;
    }

    //�����Ƃ��̃C���X�^���X�̃{�[���̐����������������肷��
    //�����ŗ^����ꂽ�I�u�W�F�N�g��BingoBall�N���X�̃C���X�^���X�ł͂Ȃ������ꍇ�A
    //�܂��̓{�[���̐�������v���Ȃ������ꍇ��false
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BingoBall)) return false;
        BingoBall ball = (BingoBall)obj;
        return this.getNumber() == ball.getNumber();
    }

    //����System.out.println�ł��̃C���X�^���X���o�͂��ꂽ�Ƃ���
    //�{�[���̐�������ʂɏo�͂����悤�ɂ��Ă邾���B
    //����System.out.println���\�b�h�͈����ŗ^����ꂽ�C���X�^���X��
    //toString()���\�b�h���Ă�Ŏ擾�����String�^�I�u�W�F�N�g����ʂɏo�͂��Ă�B
    //�����炱���������ɐ�����������ŕԋp�����悤��toString()���Ē�`���Ă����Ȃ���
    //println�ŌĂ΂ꂽ�Ƃ��ɂ��̃C���X�^���X�̃n�b�V���R�[�h���Ă����킯�̕�����Ȃ�
    //�����񂪉�ʂɏo�͂���Ă��܂��B
    @Override
    public String toString() {
        return String.valueOf(number);
    }

    //����̓N���[���������Ă����ĎQ�ƌ^�̃I�u�W�F�N�g���Q�Ɛ���R�s�[����̂ł͂Ȃ�
    //�C���X�^���X���̂��t���R�s�[�������Ƃ��Ɏg�p����B
    //���p����g���ƃV�����[�E�R�s�[�ł͂Ȃ��f�B�[�v�E�R�s�[�������Ƃ��ɌĂ΂�邗
    //�����Q�Ƃ���ʂ�ɂ��Ă���Ȃ�ɏڂ����Ȃ���"??????"��ԂɂȂ�Ǝv�����ǈꉞ�����Ƃ��B
    @Override
    protected BingoBall clone() throws CloneNotSupportedException {
        return (BingoBall) super.clone();
    }
}
