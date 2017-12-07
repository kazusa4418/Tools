package util.file;

import java.io.*;

/**
 * FileControl�N���X�͈����ŗ^����ꂽFile�^�I�u�W�F�N�g��
 * ���o�͂��Ǘ����܂��B
 * �C���X�^���X�̓ǂݏ�����ObjectInputStream��ObjectOutputStream�Ŏ������܂��B
 *
 *
 * @author kazusa4418
 * @see java.io.File
 * @see java.io.ObjectInputStream
 * @see java.io.ObjectOutputStream
 */
public class FileControl {
    /** ���䂷��t�@�C�� */
    private File file;
    /** ���͂��Ǘ�����Stream */
    private ObjectInputStream ois;
    /** �o�͂��Ǘ�����Stream */
    private ObjectOutputStream oos;

    /**
     * �w�肳�ꂽFile�^�I�u�W�F�N�g�̓��o�͂��Ǘ�����
     * ObjectOutputStream��ObjectInputStream���쐬���܂��B
     * ��OIOException�̓X���[�����L���b�`����܂��B
     * �L���b�`���ꂽ�ꍇprintStackTrace���Ă΂��O�̏ڍ׏���W���G���[�o�͂���o�͂���܂��B
     *
     * @param file - ���o�͂��Ǘ�����File�^�I�u�W�F�N�g
     */
    public FileControl(File file) throws IOException {
        this.file = file;
            ois = new ObjectInputStream(new FileInputStream(file));
            oos = new ObjectOutputStream(new FileOutputStream(file));
    }

    /**
     *
     * @param o - �������ރI�u�W�F�N�g
     */
    public void write(Object o) {
        try {
            oos.writeObject(o);
            oos.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ObjectInputStream ����I�u�W�F�N�g��ǂݍ��݂܂��B
     * �I�u�W�F�N�g�̃N���X�A�N���X�̃V�O�l�`���A�N���X�̔�transient�t�B�[���h�����
     * ��static�t�B�[���h�̒l�Ƃ��̂��ׂẴX�[�p�[�E�^�C�v���ǂݍ��܂�܂��B
     * ����N���X�ɂ��Ẵf�t�H���g�̒��񉻕����́AwriteObject���\�b�h��readObject���\�b�h���g����
     * �I�[�o�[���C�h���邱�Ƃ��ł��܂��B
     * ���̃I�u�W�F�N�g�ɂ���ĎQ�Ƃ����I�u�W�F�N�g�͒��ԓI�ɓǂݍ��܂�A����ɂ���āA
     * ���S�ɓ����ȃI�u�W�F�N�g�E�O���t��readObject�ɂ���čč\�z����܂��B
     *
     * ���[�g�E�I�u�W�F�N�g�́A�Q�Ƃ���t�B�[���h�ƃI�u�W�F�N�g�̂��ׂĂ��������ꂽ���A
     * ���S�ɕ�������܂��B
     * ���̎��_�ŁA�����̓o�^���ꂽ�D�揇�ʂɊ�Â��āA�I�u�W�F�N�g���؃R�[���o�b�N�����s����܂��B
     * ���̃R�[���o�b�N�́A(���ʂ�readObject���\�b�h��)�I�u�W�F�N�g�ɂ���āA����炪�ʂɕ��������Ƃ���
     * �o�^����܂��B
     *
     * ��O��InputStream�Ɋւ������A���񉻕������ׂ��ł͂Ȃ��N���X�ɂ��ăX���[����܂��B
     * ���ׂĂ̗�O�́AInputStream�ɂƂ��Ēv���I�ŁAInputStream��s�m��̏�Ԃɂ��܂��B
     * �X�g���[���̏�Ԃ𖳎����邩�񕜏������邩�����߂�̂͌Ăяo�����ł��B
     *
     * @return �X�g���[������ǂݍ��܂ꂽ�I�u�W�F�N�g
     */
    public Object read() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }
}
