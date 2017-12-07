package util.file;

import java.io.*;

/**
 * FileControlクラスは引数で与えられたFile型オブジェクトの
 * 入出力を管理します。
 * インスタンスの読み書きはObjectInputStreamとObjectOutputStreamで実現します。
 *
 *
 * @author kazusa4418
 * @see java.io.File
 * @see java.io.ObjectInputStream
 * @see java.io.ObjectOutputStream
 */
public class FileControl {
    /** 制御するファイル */
    private File file;
    /** 入力を管理するStream */
    private ObjectInputStream ois;
    /** 出力を管理するStream */
    private ObjectOutputStream oos;

    /**
     * 指定されたFile型オブジェクトの入出力を管理する
     * ObjectOutputStreamとObjectInputStreamを作成します。
     * 例外IOExceptionはスローせずキャッチされます。
     * キャッチされた場合printStackTraceが呼ばれ例外の詳細情報を標準エラー出力から出力されます。
     *
     * @param file - 入出力を管理するFile型オブジェクト
     */
    public FileControl(File file) throws IOException {
        this.file = file;
            ois = new ObjectInputStream(new FileInputStream(file));
            oos = new ObjectOutputStream(new FileOutputStream(file));
    }

    /**
     *
     * @param o - 書き込むオブジェクト
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
     * ObjectInputStream からオブジェクトを読み込みます。
     * オブジェクトのクラス、クラスのシグネチャ、クラスの非transientフィールドおよび
     * 非staticフィールドの値とそのすべてのスーパー・タイプが読み込まれます。
     * あるクラスについてのデフォルトの直列化復元は、writeObjectメソッドとreadObjectメソッドを使って
     * オーバーライドすることができます。
     * このオブジェクトによって参照されるオブジェクトは中間的に読み込まれ、それによって、
     * 完全に同等なオブジェクト・グラフがreadObjectによって再構築されます。
     *
     * ルート・オブジェクトは、参照するフィールドとオブジェクトのすべてが復元された時、
     * 完全に復元されます。
     * この時点で、それらの登録された優先順位に基づいて、オブジェクト検証コールバックが実行されます。
     * このコールバックは、(特別なreadObjectメソッドの)オブジェクトによって、それらが個別に復元されるときに
     * 登録されます。
     *
     * 例外はInputStreamに関する問題や、直列化復元すべきではないクラスについてスローされます。
     * すべての例外は、InputStreamにとって致命的で、InputStreamを不確定の状態にします。
     * ストリームの状態を無視するか回復処理するかを決めるのは呼び出し側です。
     *
     * @return ストリームから読み込まれたオブジェクト
     */
    public Object read() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }
}
