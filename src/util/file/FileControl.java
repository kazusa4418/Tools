package util.file;

import java.io.*;

/**
 * FileControl�N���X�͈����ŗ^����ꂽFile�^�I�u�W�F�N�g��
 * ���o�͂��Ǘ����܂��B
 * �Ǘ�����File�^�I�u�W�F�N�g��util.file�p�b�P�[�W��DATA�C���^�[�t�F�[�X��
 * ��������Ă���I�u�W�F�N�g�ł��B
 *
 * @author kazusa4418
 * @see java.io.File
 * @see java.io.ObjectInputStream
 * @see java.io.ObjectOutputStream
 */
public class FileControl {
    private File file;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    /**
     * �w�肳�ꂽFile�^�I�u�W�F�N�g�̓��o�͂��Ǘ�����
     * ObjectOutputStream��ObjectInputStream���쐬���܂��B
     *
     * @param file - ���o�͂��Ǘ�����File�^�I�u�W�F�N�g
     * @throws IOException ���o�̓G���[�����������ꍇ
     * @throws FileNotFoundException �w�肳�ꂽ�t�@�C�������݂��Ȃ������ꍇ
     */
    public FileControl(File file) throws IOException, FileNotFoundException {
        this.file = file;
        ois = new ObjectInputStream(new FileInputStream(file));
        oos = new ObjectOutputStream(new FileOutputStream(file));
    }

    /**
     * �w�肳�ꂽ�I�u�W�F�N�g�� ObjectOutputStream �ɏ������݂܂��B
     *
     * @param o - �������ރI�u�W�F�N�g
     * @throws IOException ���o�̓G���[�����������ꍇ
     */
    public void wirte(Object o) throws IOException {
        oos.writeObject(o);
    }
}
