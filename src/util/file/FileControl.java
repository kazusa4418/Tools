package util.file;

import java.io.*;

/**
 * FileControlクラスは引数で与えられたFile型オブジェクトの
 * 入出力を管理します。
 * 管理するFile型オブジェクトはutil.fileパッケージのDATAインターフェースが
 * 実装されているオブジェクトです。
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
     * 指定されたFile型オブジェクトの入出力を管理する
     * ObjectOutputStreamとObjectInputStreamを作成します。
     *
     * @param file - 入出力を管理するFile型オブジェクト
     * @throws IOException 入出力エラーが発生した場合
     * @throws FileNotFoundException 指定されたファイルが存在しなかった場合
     */
    public FileControl(File file) throws IOException, FileNotFoundException {
        this.file = file;
        ois = new ObjectInputStream(new FileInputStream(file));
        oos = new ObjectOutputStream(new FileOutputStream(file));
    }

    /**
     * 指定されたオブジェクトを ObjectOutputStream に書き込みます。
     *
     * @param o - 書き込むオブジェクト
     * @throws IOException 入出力エラーが発生した場合
     */
    public void wirte(Object o) throws IOException {
        oos.writeObject(o);
    }
}
